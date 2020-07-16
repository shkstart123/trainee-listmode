package com.demo.oss.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.*;
import com.aliyun.oss.model.*;
import com.demo.oss.config.AliyunConfig;
import com.demo.oss.vo.FileUploadResult;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * @author shkstart
 * @create 2020-07-16 15:10
 */
@Service
public class MultipartUploadService {
    @Autowired
    private OSS ossClient;
    @Autowired
    private AliyunConfig aliyunConfig;
    private ExecutorService executorService;
    private static final Logger logger = LoggerFactory.getLogger(MultipartUploadService.class);
    private static String bucketName = "train-boss";
    private static String key = "";

    private static List<PartETag> partETags = Collections.synchronizedList(new ArrayList<PartETag>());

    public FileUploadResult multipartUpload(MultipartFile uploadFile) throws IOException {
        /*
         * Constructs a client instance with your account for accessing OSS
         */

        executorService = Executors.newFixedThreadPool(5);
        FileUploadResult fileUploadResult = new FileUploadResult();
        key = getFilePath(uploadFile.getOriginalFilename());
        try {
            /*
             * Claim a upload id firstly
             */
            String uploadId = claimUploadId();
            logger.info("Claiming a new upload id " + uploadId + "\n");

            /*
             * Calculate how many parts to be divided
             */
            final long partSize = 1 * 1024 * 1024L;   // 1MB
            long fileLength = uploadFile.getSize();
            int partCount = (int) (fileLength / partSize);
            if (fileLength % partSize != 0) {
                partCount++;
            }
            if (partCount > 10000) {
                throw new RuntimeException("Total parts count should not exceed 10000");
            } else {
                logger.info("Total parts count " + partCount + "\n");
            }

            /*
             * Upload multiparts to your bucket
             */
            logger.info("Begin to upload multiparts to OSS from a file\n");
            for (int i = 0; i < partCount; i++) {
                long startPos = i * partSize;
                long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;
                executorService.execute(new PartUploader(uploadFile, startPos, curPartSize, i + 1, uploadId));
            }

            /*
             * Waiting for all parts finished
             */
            executorService.shutdown();
            while (!executorService.isTerminated()) {
                try {
                    executorService.awaitTermination(5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            /*
             * Verify whether all parts are finished
             */
            if (partETags.size() != partCount) {
                throw new IllegalStateException("Upload multiparts fail due to some parts are not finished yet");
            } else {
                logger.info("Succeed to complete multiparts into an object named " + key + "\n");
            }

            /*
             * View all parts uploaded recently
             */
            listAllParts(uploadId);

            /*
             * Complete to upload multiparts
             */
            completeMultipartUpload(uploadId);

            /*
             * Fetch the object that newly created at the step below.
             */

            /*logger.debug("Fetching an object");
            client.getObject(new GetObjectRequest(bucketName, key), new File(localFilePath));*/

        } catch (OSSException oe) {
            logger.debug("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            logger.debug("Error Message: " + oe.getErrorMessage());
            logger.debug("Error Code:       " + oe.getErrorCode());
            logger.debug("Request ID:      " + oe.getRequestId());
            logger.debug("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            logger.debug("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            logger.debug("Error Message: " + ce.getMessage());
        }
        fileUploadResult.setStatus("done");
        fileUploadResult.setResponse("success");
        fileUploadResult.setName(this.aliyunConfig.getUrlPrefix() + key);

        return fileUploadResult;
    }

    private   class PartUploader implements Runnable {
        private long startPos;
        private MultipartFile multipartFile;
        private long partSize;
        private int partNumber;
        private String uploadId;

        public PartUploader(MultipartFile multipartFile, long startPos, long partSize, int partNumber, String uploadId) {
            this.multipartFile = multipartFile;
            this.startPos = startPos;
            this.partSize = partSize;
            this.partNumber = partNumber;
            this.uploadId = uploadId;
        }

        @Override
        public void run() {
            InputStream instream = null;
            try {
                instream = multipartFile.getInputStream();
                instream.skip(this.startPos);
                UploadPartRequest uploadPartRequest = new UploadPartRequest();
                uploadPartRequest.setBucketName(bucketName);
                uploadPartRequest.setKey(key);
                uploadPartRequest.setUploadId(this.uploadId);
                uploadPartRequest.setInputStream(instream);
                uploadPartRequest.setPartSize(this.partSize);
                uploadPartRequest.setPartNumber(this.partNumber);
                UploadPartResult uploadPartResult = ossClient.uploadPart(uploadPartRequest);
                logger.info("Part#" + this.partNumber + " done\n");
                synchronized (partETags) {
                    partETags.add(uploadPartResult.getPartETag());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (instream != null) {
                    try {
                        instream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    private String claimUploadId() {
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, key);
        InitiateMultipartUploadResult result = ossClient.initiateMultipartUpload(request);
        return result.getUploadId();
    }

    private void completeMultipartUpload(String uploadId) {
            // Make part numbers in ascending order
            Collections.sort(partETags, new Comparator<PartETag>() {

                @Override
                public int compare(PartETag p1, PartETag p2) {
                    return p1.getPartNumber() - p2.getPartNumber();
                }
            });

        logger.info("Completing to upload multiparts\n");
        CompleteMultipartUploadRequest completeMultipartUploadRequest =
                new CompleteMultipartUploadRequest(bucketName, key, uploadId, partETags);
        ossClient.completeMultipartUpload(completeMultipartUploadRequest);
    }

    private void listAllParts(String uploadId) {
        logger.info("Listing all parts......");
        ListPartsRequest listPartsRequest = new ListPartsRequest(bucketName, key, uploadId);
        PartListing partListing = ossClient.listParts(listPartsRequest);

        int partCount = partListing.getParts().size();
        for (int i = 0; i < partCount; i++) {
            PartSummary partSummary = partListing.getParts().get(i);
            logger.info("\tPart#" + partSummary.getPartNumber() + ", ETag=" + partSummary.getETag());
        }

    }

    /**
     * 生成路径以及文件名 例如：15564277465972939.jpg
     */
    private String getFilePath(String sourceFileName) {
        DateTime dateTime = new DateTime();
        return System.currentTimeMillis() +
                RandomUtils.nextInt(100, 9999) + "." +
                StringUtils.substringAfterLast(sourceFileName, ".");
    }

    /**
     * @desc 下载文件
     */
    public void exportOssFile(OutputStream os, String objectName) throws Throwable {

        // 下载请求，10个任务并发下载，启动断点续传。
        DownloadFileRequest downloadFileRequest = new DownloadFileRequest(bucketName, objectName);
        downloadFileRequest.setDownloadFile("e:/desktop/" + "xxx" + objectName);
        downloadFileRequest.setPartSize(1 * 1024 * 1024);
        downloadFileRequest.setTaskNum(10);
        downloadFileRequest.setEnableCheckpoint(true);
        downloadFileRequest.setCheckpointFile("e:/desktop/h.ucp");

        // 下载文件。
        DownloadFileResult downloadRes = ossClient.downloadFile(downloadFileRequest);

        // 下载成功时，会返回文件元信息。
        downloadRes.getObjectMetadata();


        os.close();

    }
}
