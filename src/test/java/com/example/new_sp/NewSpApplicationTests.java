package com.example.new_sp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.new_sp.dao.AccountDao;
import com.example.new_sp.domain.*;
import com.example.new_sp.service.*;
import com.example.new_sp.service.impl.accountServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

class NewSpApplicationTests {

    void BinaryTest(){
        Orders order = new Orders();
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR,2023);
//        calendar.set(Calendar.MONTH,10);
//        calendar.set(Calendar.DAY_OF_MONTH,20);
//        Date e = new Date(calendar.getTimeInMillis());
//
//        Calendar instance = Calendar.getInstance();
//        calendar.set(Calendar.YEAR,2023);
//        calendar.set(Calendar.MONTH,10);
//        calendar.set(Calendar.DAY_OF_MONTH,29);
//        Date l = new Date(instance.getTimeInMillis());
//
//        Integer lp = 50;
//        Integer hp = 150;

//        System.out.println(e);
    }


    void OrderTest(){
    }
}
