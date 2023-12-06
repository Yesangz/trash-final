package com.example.new_sp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.new_sp.Exception.BusinessException;
import com.example.new_sp.Result.Code;
import com.example.new_sp.dao.CarDao;
import com.example.new_sp.domain.Car;
import com.example.new_sp.domain.Orders;
import com.example.new_sp.service.CarService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl extends ServiceImpl<CarDao, Car> implements CarService {

    public List<Car> getCars(Integer pageSize, Integer currentPage) {
        LambdaQueryWrapper<Car> lmq = new LambdaQueryWrapper<>();
        lmq.select().last("limit "+((currentPage-1)*pageSize)+","+pageSize);

        List<Car> cars = list(lmq);
        return cars;
    }

    public List<Car> selectCars(Integer brandId,String model, Integer lPrice, Integer hPrice,Integer pageSize, Integer currentPage){

        boolean modelState;
        if(Optional.ofNullable(model).isPresent()){
            modelState =  !(model.isBlank()||model.isEmpty());
        }
        else {
            modelState=false;
        }

        LambdaQueryWrapper<Car> lmq = new LambdaQueryWrapper<>();
        lmq.eq(null!= brandId,Car::getBrandID,brandId)
                .eq(modelState,Car::getModel,model)
                .ge(null!=lPrice, Car::getPrice, lPrice)
                .lt(null != hPrice, Car::getPrice, hPrice)
                .last("limit "+((currentPage-1)*pageSize)+","+pageSize);
        List<Car> cars = list(lmq);
        return cars;
    }

    public Car getOneCar(Integer CarID){
        if (!Optional.ofNullable(CarID).isPresent()){
            throw new NullPointerException();
        }
        LambdaQueryWrapper<Car> lmq = new LambdaQueryWrapper<>();
        lmq.eq(Car::getCarID,CarID);

        Car one = getOne(lmq);
        return one;
    }

    public boolean updateOneCar(Car car){
        if (!Optional.ofNullable(car).isPresent()){
            throw new NullPointerException();
        }

        UpdateWrapper<Car> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("CarID",car.getCarID());

        return update(car,updateWrapper);
    }

    @Override
    public boolean dropCarById(Integer CarID) {
        if (!Optional.ofNullable(CarID).isPresent()){
            throw new BusinessException(Code.DELETE_ERR,"参数为空");
        }
        LambdaQueryWrapper<Car> lmq = new LambdaQueryWrapper<>();
        lmq.eq(Car::getCarID,CarID);

        return remove(lmq);
    }

    @Override
    public boolean addCar(Car car) {
        if (!Optional.ofNullable(car).isPresent()){
            throw new NullPointerException();
        }
        return save(car);
    }
}
