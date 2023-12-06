package com.example.new_sp.controller;

import com.example.new_sp.Exception.BusinessException;
import com.example.new_sp.Result.Code;
import com.example.new_sp.Result.Result;
import com.example.new_sp.domain.Brands;
import com.example.new_sp.service.BrandsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/Brands")
public class BrandsController {
    @Autowired
    private BrandsService brandsService;

    @PostMapping
    public Result insertBrand(@RequestBody Map<String,Object> params) throws JsonProcessingException {

        ObjectMapper JACKSON = new ObjectMapper();
        String BrandName = JACKSON.readValue(JACKSON.writeValueAsString(params.get("BrandName")), String.class);

        Brands checkBrand = brandsService.getOneByName(BrandName);
        if(Optional.ofNullable(checkBrand).isPresent()){
            return new Result(null,Code.SAVE_ERR,"品牌已存在");
        }


        boolean b = false;
        try{
            b = brandsService.insertBrand(BrandName);
        }catch (BusinessException be){
            return new Result(null,be.getCode(),be.getMessage());
        }
        if (b){
            return new Result(null, Code.SAVE_OK,"添加品牌成功");
        }
        else {
            return new Result(null,Code.SAVE_ERR,"添加品牌失败");
        }
    }

    @PostMapping("/drop")
    public Result dropBrand(@RequestBody Map<String,Object> params) throws JsonProcessingException {

        ObjectMapper JACKSON = new ObjectMapper();
        Integer brandID = JACKSON.readValue(JACKSON.writeValueAsString(params.get("brandID")), Integer.class);

        boolean b = false;
        try{
            b = brandsService.dropById(brandID);
        }catch (BusinessException be){
            return new Result(null,be.getCode(),be.getMessage());
        }
        if (b){
            return new Result(null, Code.DELETE_OK,"删除品牌成功");
        }
        else {
            return new Result(null,Code.DELETE_ERR,"删除品牌失败");
        }
    }
}
