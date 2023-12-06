package com.example.new_sp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.new_sp.dao.OrdersDao;
import com.example.new_sp.domain.Orders;
import com.example.new_sp.service.OrdersService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersDao, Orders> implements OrdersService {
    @Override
    public List<Orders> getOrders(Orders order, Date early, Date late, Integer lowPrice, Integer highPrice,Integer pageSize, Integer currentPage,boolean isSalesperson) {
        boolean bool_state;
        if(Optional.ofNullable(order.getOrderState()).isPresent()){
            bool_state = !( order.getOrderState().isEmpty() || order.getOrderState().isBlank() );
        }
        else {
            bool_state = false;
        }

        LambdaQueryWrapper<Orders> lmq = new LambdaQueryWrapper<>();
        lmq.eq(Optional.ofNullable(order.getRecordID()).isPresent(),Orders::getRecordID,order.getRecordID())
                .eq(Optional.ofNullable(order.getCustomerID()).isPresent(),Orders::getCustomerID,order.getCustomerID())
                .eq(Optional.ofNullable(order.getCarID()).isPresent(),Orders::getCarID,order.getCarID())
                .eq(Optional.ofNullable(order.getSalespersonID()).isPresent(),Orders::getSalespersonID,order.getSalespersonID())
                .eq(bool_state,Orders::getOrderState,order.getOrderState())
                .ge(Optional.ofNullable(early).isPresent(),Orders::getSalesDate,early)
                .le(Optional.ofNullable(late).isPresent(),Orders::getSalesDate,late)
                .ge(Optional.ofNullable(lowPrice).isPresent(),Orders::getSalesPrice,lowPrice)
                .le(Optional.ofNullable(highPrice).isPresent(),Orders::getSalesPrice,highPrice)
                .last("limit "+((currentPage-1)*pageSize)+","+pageSize);

        if (isSalesperson){
            lmq.or(wp->wp.isNull(Orders::getSalespersonID).eq(Orders::getOrderState,"待接收"))
                    .eq(Optional.ofNullable(order.getRecordID()).isPresent(),Orders::getRecordID,order.getRecordID())
                    .eq(Optional.ofNullable(order.getCustomerID()).isPresent(),Orders::getCustomerID,order.getCustomerID())
                    .eq(Optional.ofNullable(order.getCarID()).isPresent(),Orders::getCarID,order.getCarID())
                    .eq(bool_state,Orders::getOrderState,order.getOrderState())
                    .ge(Optional.ofNullable(early).isPresent(),Orders::getSalesDate,early)
                    .le(Optional.ofNullable(late).isPresent(),Orders::getSalesDate,late)
                    .ge(Optional.ofNullable(lowPrice).isPresent(),Orders::getSalesPrice,lowPrice)
                    .le(Optional.ofNullable(highPrice).isPresent(),Orders::getSalesPrice,highPrice);
        }
//        else {
//            lmq.eq(Optional.ofNullable(order.getSalespersonID()).isPresent(),Orders::getSalespersonID,order.getSalespersonID());
//
//        }

        List<Orders> orders = list(lmq);


        return orders;
    }

    public Orders getOneOrder(Integer RecordID){
        if (!Optional.ofNullable(RecordID).isPresent()){
            throw new NullPointerException();
        }
        LambdaQueryWrapper<Orders> lmq = new LambdaQueryWrapper<>();
        lmq.eq(Orders::getRecordID,RecordID);

        Orders one = getOne(lmq);
        return one;
    }

    @Override
    public boolean updateOrder(Orders order) {
        UpdateWrapper<Orders> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("RecordID",order.getRecordID());


        return this.update(order,updateWrapper);
    }

    public boolean insertOrder(Orders order){
        if (!Optional.ofNullable(order).isPresent()){
            throw new NullPointerException();
        }

        return save(order);
    }
}
