package com.example.new_sp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.new_sp.domain.Car;

import java.util.List;

public interface CarService extends IService<Car> {
    public List<Car> getCars(Integer pageSize, Integer currentPage);
    public List<Car> selectCars(Integer brandId,String model, Integer lPrice, Integer hPrice,Integer pageSize, Integer currentPage);

    public Car getOneCar(Integer CarID);
    public boolean updateOneCar(Car car);

    public boolean dropCarById(Integer CarID);

    public boolean addCar(Car car);
}
