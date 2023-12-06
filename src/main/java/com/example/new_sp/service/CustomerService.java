package com.example.new_sp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.new_sp.domain.Customer;
import com.example.new_sp.domain.account;

public interface CustomerService extends IService<Customer> {
//    public Customer selectByCustomer(Customer ac);
    public boolean insertOneCustomer(Customer ac);
    public Customer getOneCustomer(account ac);
    public boolean updateCustomer(Customer ca);
    public Customer getByID(Integer id);
}
