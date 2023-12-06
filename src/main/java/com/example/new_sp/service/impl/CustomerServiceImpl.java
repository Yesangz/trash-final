package com.example.new_sp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.new_sp.Exception.BusinessException;
import com.example.new_sp.Result.Code;
import com.example.new_sp.dao.AccountDao;
import com.example.new_sp.dao.CustomerDao;
import com.example.new_sp.domain.Customer;
import com.example.new_sp.domain.account;
import com.example.new_sp.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerDao, Customer> implements CustomerService {
    @Override
    public boolean insertOneCustomer(Customer customer) {
        LambdaQueryWrapper<Customer> select = new LambdaQueryWrapper<>();
        select.eq(Customer::getAccountID,customer.getAccountID());
        Customer one = getOne(select);
        if(Optional.ofNullable(one).isPresent()){
            throw new BusinessException(Code.SAVE_ERR,"账户已存在");
        }
        else
            return save(customer);
    }

    public Customer getOneCustomer(account ac){
        LambdaQueryWrapper<Customer> getLmq = new LambdaQueryWrapper<>();
        getLmq.eq(Customer::getAccountID,ac.getID());

        Customer one = getOne(getLmq);
        return one;
    }
    public boolean updateCustomer(Customer ca){
        LambdaQueryWrapper<Customer> getLmq = new LambdaQueryWrapper<>();
        getLmq.eq(Customer::getCustomerID,ca.getCustomerID());

        Customer customerFromId = getOne(getLmq);

        customerFromId.setName(ca.getName());
        customerFromId.setEmail(ca.getEmail());
        customerFromId.setPhone(ca.getPhone());
        customerFromId.setAddress(ca.getAddress());

        UpdateWrapper<Customer> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("CustomerID",customerFromId.getCustomerID());

        boolean b = this.update(customerFromId,updateWrapper);
        return b;
    }

    public Customer getByID(Integer id){
        if (!Optional.ofNullable(id).isPresent()){
            throw new NullPointerException();
        }

        LambdaQueryWrapper<Customer> lmq = new LambdaQueryWrapper<>();
        lmq.eq(Customer::getCustomerID,id);
        return getOne(lmq);
    }
}
