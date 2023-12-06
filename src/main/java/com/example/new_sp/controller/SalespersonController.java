package com.example.new_sp.controller;

import com.example.new_sp.Result.Code;
import com.example.new_sp.Result.Result;
import com.example.new_sp.domain.Customer;
import com.example.new_sp.domain.Salesperson;
import com.example.new_sp.domain.account;
import com.example.new_sp.service.SalespersonService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/Salesperson")
public class SalespersonController {
    @Autowired
    private SalespersonService salespersonService;
    @Autowired
    HttpServletRequest request;

    @GetMapping
    public Result getOneAccount(){//通过session获得用户的对应账号类型的详细信息，用来获取更新账户信息前的最新数据
        HttpSession session = request.getSession(false);
        if(Optional.ofNullable(session).isPresent()){
            account sessionAccount = (account) session.getAttribute("account");
            //以上都是通用的session获得操作

            Salesperson salesperson = salespersonService.getOneSalesperson(sessionAccount);
            return new Result(salesperson, Code.GET_OK,"账户信息获取成功");
        }else {
            return new Result(null, Code.GET_ERR,"账号未登录");
        }
    }

    @PostMapping("/update")
    public Result updateCustomer(@RequestBody Salesperson salesperson){//更新账户信息
        System.out.println(salesperson);
        boolean b = salespersonService.updateSalesperson(salesperson);
        if(b){
            return new Result(null,Code.UPDATE_OK,"账户信息更新成功");
        }else {
            return new Result(null,Code.UPDATE_ERR,"账户信息更新失败");
        }
    }
}
