package com.example.new_sp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.new_sp.domain.Brands;
import com.example.new_sp.domain.Car;

import java.util.List;

public interface BrandsService extends IService<Brands> {
    public List<Brands> getBrands();

    public boolean insertBrand(String BrandName);

    public boolean dropBrand(String BrandName);

    public boolean dropById(Integer BrandID);

    public Brands getOneByName(String BrandName);
}
