package com.example.new_sp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.new_sp.domain.Orders;

import java.sql.Date;
import java.util.List;

public interface OrdersService extends IService<Orders> {
    //时间和价格范围分别用Date和Integer。boolean isSalesperson用于判断是不是销售，主要用于给予所有销售查看没有指定销售的订单
    public List<Orders> getOrders(Orders order, Date early, Date late, Integer lowPrice, Integer highPrice,Integer pageSize, Integer currentPage,boolean isSalesperson);

    public Orders getOneOrder(Integer RecordID);

    public boolean updateOrder(Orders order);

    public boolean insertOrder(Orders order);
}
