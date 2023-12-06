package com.example.new_sp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.new_sp.domain.Car;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CarDao extends BaseMapper<Car> {
}
