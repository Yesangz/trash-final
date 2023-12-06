package com.example.new_sp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.new_sp.dao.CustomerDao;
import com.example.new_sp.dao.SalespersonGroupDao;
import com.example.new_sp.domain.Customer;
import com.example.new_sp.domain.Salesperson;
import com.example.new_sp.domain.SalespersonGroup;
import com.example.new_sp.service.CustomerService;
import com.example.new_sp.service.SalespersonGroupService;
import org.springframework.stereotype.Service;

@Service
public class SalespersonGroupServiceImpl extends ServiceImpl<SalespersonGroupDao, SalespersonGroup> implements SalespersonGroupService {
}
