package com.demo.mall.service;

import com.alibaba.fastjson.JSON;


import com.demo.mall.dao.BillDAO;

import com.demo.mall.entity.Bill;
import com.demo.mall.entity.BillItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * @author shkstart
 * @create 2020-07-12 8:47
 */
@Service
public class BillService {

    @Autowired
    private BillItemService billItemService;
    @Autowired
    private BillDAO billDAO;
    @Autowired
    private SuggestionService suggestionService;
    @Autowired
    private GoodService goodService;


    public void settleCart(int uid) {
        Bill bill= new Bill();

        Map<Integer, BillItem> billItems = billItemService.getCart();
        Iterator<Map.Entry<Integer, BillItem>> it = billItems.entrySet().iterator();

        BillItem billItem = null;
        bill.setUid(uid);             //此处假设默认用户Id
        bill.setDate(new Date());
        bill.setStatus(0);
        billDAO.insert(bill);

        while (it.hasNext()) {
            Map.Entry<Integer, BillItem> entry = it.next();
            billItem =  entry.getValue();
            billItem.setBid(bill.getId());
            billItemService.insert(billItem);
        }


        billItems.clear();
    }
    public int updateSatus(Bill bill){
        return billDAO.updateById(bill);
    }

    public Bill selectById(int id){
        return billDAO.selectById(id);
    }

    public String list(){
        List<Map<String, Object>> billItems = new ArrayList<>();
        List<Bill> bills = billDAO.selectList(null);
        for(Bill bill:bills){
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("bill",bill);
            hashMap.put("suggestions",suggestionService.selectByBid(bill.getId()));
            List<HashMap<String,Object>> contentList = new ArrayList<>();
            List<BillItem> billItemList = billItemService.selectByBid(bill.getId());
            for (BillItem billItem : billItemList) {
                HashMap<String,Object> content = new HashMap<>();
                content.put("item",billItem);
                content.put("goods",goodService.selectById(billItem.getGid()));
                contentList.add(content);
            }
            hashMap.put("contentList",contentList);
            billItems.add(hashMap);
        }
        return JSON.toJSONString(billItems);

    }
    public Bill findBillById(int id){
        return billDAO.findBillById(id);
    }
}
