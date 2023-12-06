package com.example.new_sp.controller;

import com.example.new_sp.Exception.BusinessException;
import com.example.new_sp.Masks.AuthorityMask;
import com.example.new_sp.Masks.TypeMask;
import com.example.new_sp.Result.Code;
import com.example.new_sp.Result.Result;
import com.example.new_sp.domain.Customer;
import com.example.new_sp.domain.Salesperson;
import com.example.new_sp.domain.account;
import com.example.new_sp.service.CustomerService;
import com.example.new_sp.service.SalespersonGroupService;
import com.example.new_sp.service.SalespersonService;
import com.example.new_sp.service.accountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/Account")
public class accountController {
    @Autowired
    private accountService service;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private SalespersonService salespersonService;
    @Autowired
    private SalespersonGroupService salespersonGroupService;
    @Autowired
    HttpServletRequest request;

    @GetMapping
    public Result getAccount(){//通过session用来获得账户信息，主要是获得账户类型
        HttpSession session = request.getSession(false);
        if (Optional.ofNullable(session).isPresent()){
            account sessionAccount = (account) session.getAttribute("account");
            return new Result(sessionAccount,Code.GET_OK,"账号获取成功");
        }else {
            return new Result(null,Code.GET_ERR,"账号未登录");
        }
    }

    @PostMapping
    public Result Login(@RequestBody account ad, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        account accountSession = (account) session.getAttribute("account");
        if(Optional.ofNullable(accountSession).isPresent()){
            if((accountSession.getAuthority() & TypeMask.Customer)==TypeMask.Customer){
                return new Result("/main.html",Code.GET_OK,"重定向");
            }
        }

        account Real_account = service.selectByAccountName(ad);
        System.out.println(ad);
        if(Optional.ofNullable(Real_account).isPresent()){
            if(Real_account.getPassword().equals(ad.getPassword())){

                System.out.println("登陆成功");

                //通过Session来实现登录状态记录
                session.setAttribute("account",Real_account);



                return new Result("/main.html", Code.GET_OK,"登录成功");
            }else {
                System.out.println("登陆失败");
                return  new Result(null,Code.GET_ERR,"用户名或密码错误");
            }
        }
        else {
            System.out.println("走到这里了");
            return new Result(null,Code.GET_ERR,"用户名或密码错误");
        }
    }

    @PostMapping("/register")
    public Result Register(@RequestBody Map<String,Object> map) throws JsonProcessingException {
        System.out.println("注册开始");
        ObjectMapper JACKSON = new ObjectMapper();
        Object account = map.get("registerForm");
        Object form = map.get("typeForm");
        account Real_account = JACKSON.readValue(JACKSON.writeValueAsString(account),account.class);
        Real_account.setID(null);

        System.out.println("插入前"+Real_account);


        boolean b = service.insertOneAccount(Real_account);
        if (b) {
            //注册成功后，利用用户名获得账户ID
            Real_account = service.selectByAccountName(Real_account);
            System.out.println(Real_account);
            //根据类型插入到不同的用户表中
            if(Real_account.getType().equals("Customer")){
                Customer customer = JACKSON.readValue((JACKSON.writeValueAsString(form)), Customer.class);
                customer.setAccountID(Real_account.getID());
                boolean bool_customer;
                try{
                    bool_customer = customerService.insertOneCustomer(customer);
                }catch (BusinessException be){
                    return new Result(customer,Code.SAVE_ERR,"该买家用户已存在");
                }
                if (bool_customer){
                    HttpSession session = request.getSession();
                    session.setAttribute("account",Real_account);

                    return new Result(Real_account, Code.SAVE_OK, "注册成功");
                }
                else {
                    service.deleteOneAccountById(Real_account);
                    return new Result(null, Code.SYSTEM_BUSINESS_ERROR, "该买家用户已存在");
                }

            }else if(Real_account.getType().equals("Salesperson")){
                Salesperson salesperson = JACKSON.readValue(JACKSON.writeValueAsString(form), Salesperson.class);
                salesperson.setAccountID(Real_account.getID());
                boolean bool_salesperson;
                try{
                    bool_salesperson = salespersonService.insertOneSalesperson(salesperson);
                } catch (BusinessException Be){
                    service.deleteOneAccountById(Real_account);
                    throw Be;
                }
                if (bool_salesperson){
                    HttpSession session = request.getSession();
                    session.setAttribute("account",Real_account);

                    return new Result(Real_account, Code.SAVE_OK, "注册成功");
                }
                else {
                    return new Result(null, Code.SYSTEM_BUSINESS_ERROR, "该销售用户已存在");
                }
            }
            return new Result(Real_account, Code.SAVE_OK, "注册成功");
        }else {
            return new Result(null, Code.SYSTEM_BUSINESS_ERROR, "用户已存在");
        }
    }

    @PostMapping("/update")
    public Result updateAccount(@RequestBody Map<String,Object> map) throws JsonProcessingException {
        ObjectMapper JACKSON = new ObjectMapper();
        Integer AccountID = JACKSON.readValue(JACKSON.writeValueAsString(map.get("AccountID")),Integer.class);
        String Pass = JACKSON.readValue(JACKSON.writeValueAsString(map.get("Pass")),String.class);

        boolean check = Pass.isEmpty()||Pass.isBlank();
        if(!check){
            account tmp = new account();
            tmp.setPassword(Pass);
            tmp.setID(AccountID);
            boolean b = service.updatePassword(tmp);
            return new Result(null, Code.UPDATE_OK,"密码修改成功");
        }
        else {
            return new Result(null,Code.UPDATE_ERR,"密码修改失败");
        }
    }
    @PostMapping("/logout")
    public Result logoutAccount(){
        System.out.println("退出登录");
        HttpSession session = request.getSession(false);
        if (Optional.ofNullable(session).isPresent()){
            session.setAttribute("account",null);
            System.out.println("登出成功");
            return new Result(null,Code.DELETE_OK,"登出成功");
        }
        else {
            System.out.println("登出失败");
            return new Result(null,Code.DELETE_ERR,"登出失败，未登录");
        }
    }
}
