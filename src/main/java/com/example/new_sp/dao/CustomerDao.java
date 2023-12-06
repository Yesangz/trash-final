package com.example.new_sp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.new_sp.domain.Customer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerDao extends BaseMapper<Customer> {
}
