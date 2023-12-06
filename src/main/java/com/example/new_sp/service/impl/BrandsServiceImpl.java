package com.example.new_sp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.new_sp.Exception.BusinessException;
import com.example.new_sp.Result.Code;
import com.example.new_sp.dao.BrandsDao;
import com.example.new_sp.domain.Brands;
import com.example.new_sp.service.BrandsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandsServiceImpl extends ServiceImpl<BrandsDao, Brands> implements BrandsService {
    @Override
    public List<Brands> getBrands() {
        return list();
    }

    @Override
    public boolean insertBrand(String BrandName) {
        if (!Optional.ofNullable(BrandName).isPresent() || BrandName.isEmpty() || BrandName.isBlank()){
            throw new BusinessException(Code.SAVE_ERR,"参数为空");
        }

        Brands brand = new Brands();
        brand.setBrandName(BrandName);

        return save(brand);
    }

    @Override
    public boolean dropBrand(String BrandName) {
        if (!Optional.ofNullable(BrandName).isPresent() || BrandName.isEmpty() || BrandName.isBlank()){
            throw new BusinessException(Code.DELETE_ERR,"参数为空");
        }
        LambdaQueryWrapper<Brands> lmq = new LambdaQueryWrapper<>();
        lmq.eq(Optional.ofNullable(BrandName).isPresent(),Brands::getBrandName,BrandName);


        return remove(lmq);
    }

    @Override
    public boolean dropById(Integer BrandID) {
        if (!Optional.ofNullable(BrandID).isPresent()){
            throw new BusinessException(Code.SAVE_ERR,"参数为空");
        }
        LambdaQueryWrapper<Brands> lmq = new LambdaQueryWrapper<>();
        lmq.eq(Optional.ofNullable(BrandID).isPresent(),Brands::getBrandID,BrandID);

        return remove(lmq);
    }

    @Override
    public Brands getOneByName(String BrandName) {
        if (!Optional.ofNullable(BrandName).isPresent() || BrandName.isEmpty() || BrandName.isBlank()){
            throw new BusinessException(Code.SAVE_ERR,"参数为空");
        }
        LambdaQueryWrapper<Brands> lmq = new LambdaQueryWrapper<>();
        lmq.eq(Optional.ofNullable(BrandName).isPresent(),Brands::getBrandName,BrandName);

        return getOne(lmq);
    }


}
