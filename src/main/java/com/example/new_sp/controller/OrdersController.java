package com.example.new_sp.controller;

import com.example.new_sp.Masks.TypeMask;
import com.example.new_sp.Result.Code;
import com.example.new_sp.Result.Result;
import com.example.new_sp.domain.*;
import com.example.new_sp.service.CarService;
import com.example.new_sp.service.CustomerService;
import com.example.new_sp.service.OrdersService;
import com.example.new_sp.service.SalespersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/Orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private CustomerService cs;
    @Autowired
    private SalespersonService salespersonService;
    @Autowired
    private CarService carService;
    @Autowired
    HttpServletRequest request;

    @GetMapping
    public Result getOrders(@RequestParam("pageSize") Integer pageSize, @RequestParam("currentPage")  Integer currentPage){
        System.out.println("获得订单信息");
        Orders order = new Orders();
        List<Orders> orders = ordersService.getOrders(order,null,null,null,null,pageSize,currentPage,false);
        return new Result(orders, Code.GET_OK,"全部订单信息获取成功");
    }

    @PostMapping("/selectOrders")
    public Result selectOrders(@RequestBody Map<String,Object> params) throws JsonProcessingException {
        //接收参数
        ObjectMapper JACKSON = new ObjectMapper();

        System.out.println("order : "+JACKSON.writeValueAsString(params.get("order")));
        Orders order = JACKSON.readValue(JACKSON.writeValueAsString(params.get("order")), Orders.class);
        Date earlyDate = JACKSON.readValue(JACKSON.writeValueAsString(params.get("earlyDate")), Date.class);
        Date lateDate = JACKSON.readValue(JACKSON.writeValueAsString(params.get("lateDate")), Date.class);
        Integer highPrice = JACKSON.readValue(JACKSON.writeValueAsString(params.get("highPrice")), Integer.class);
        Integer lowPrice = JACKSON.readValue(JACKSON.writeValueAsString(params.get("lowPrice")), Integer.class);
        Integer pageSize = JACKSON.readValue(JACKSON.writeValueAsString(params.get("pageSize")), Integer.class);
        Integer currentPage = JACKSON.readValue(JACKSON.writeValueAsString(params.get("currentPage")),Integer.class);

        //判断用户类型，来限制搜索订单类型权限
        HttpSession session = request.getSession(false);
        boolean isSalesperson = false;
        if(Optional.ofNullable(session).isPresent()){
            account ac = (account) session.getAttribute("account");
            System.out.println(ac.getAuthority()&TypeMask.Supplier);
            //强制查询与账号相关的订单
            //因为supplier和管理员的订单查看权限是一样的，因此不需要再写管理员的
            if ((ac.getAuthority()& TypeMask.Supplier)==TypeMask.Supplier){

            }
            else if ((ac.getAuthority()& TypeMask.Salesperson)==TypeMask.Salesperson){
                Salesperson oneSalesperson = salespersonService.getOneSalesperson(ac);
                order.setSalespersonID(oneSalesperson.getSalespersonID());
                isSalesperson=true;
            }
            else if ((ac.getAuthority()& TypeMask.Customer)==TypeMask.Customer){
                Customer customer = cs.getOneCustomer(ac);
                order.setCustomerID(customer.getCustomerID());
            }
        }else {
            return new Result(null,Code.GET_ERR,"账号未登录");
        }


        List<Orders> orders = ordersService.getOrders(order, earlyDate, lateDate, lowPrice, highPrice, pageSize, currentPage,isSalesperson);
        if(orders.isEmpty()){
            return new Result(null,Code.GET_ERR,"无订单");
        }{
            return new Result(orders,Code.GET_OK,"订单获取成功");
        }
    }

    @GetMapping("/detail")
    public Result getDetail(@RequestParam("RecordID") Integer RecordID){

        if (!Optional.ofNullable(RecordID).isPresent()){
            return new Result(null,Code.GET_ERR,"订单号为空");
        }
        //获得订单信息
        Orders order = ordersService.getOneOrder(RecordID);
        //获得车辆信息
        Integer carID = order.getCarID();
        Car car = carService.getOneCar(carID);

        //获得用户信息
        Customer customer = cs.getByID(order.getCustomerID());
        //获得销售信息
        Salesperson salesperson;
        try{
            salesperson =  salespersonService.getByID(order.getSalespersonID());
        }catch (NullPointerException e){
            salesperson = new Salesperson();
            salesperson.setName("无");
            salesperson.setPhone("无");
        }


        Map<String,Object> map = new HashMap<>();
        map.put("Brand",car.getBrand());
        map.put("Model",car.getModel());
        map.put("Config",car.getConfig());
        map.put("SalesPrice",order.getSalesPrice());
        map.put("SalesDate",order.getSalesDate());
        map.put("OrderState",order.getOrderState());
        map.put("CustomerName",customer.getName());
        map.put("CustomerPhone",customer.getPhone());
        map.put("SalespersonName",salesperson.getName());
        map.put("SalespersonPhone",salesperson.getPhone());


        return new Result(map,Code.GET_OK,"获取详细成功");
    }

    @GetMapping("/getOne")
    public Result getOne(@RequestParam("RecordID") Integer RecordID){
        HttpSession session = request.getSession(false);
        if(Optional.ofNullable(session).isPresent()){
            account ac = (account) session.getAttribute("account");
            if (ac.getType()=="Customer"){
                return new Result(null,Code.GET_ERR,"该用户类型无法进行此操作");
            }
        }
        else {
            return new Result(null,Code.GET_ERR,"用户未登录");
        }


        if (!Optional.ofNullable(RecordID).isPresent()){
            return new Result(null,Code.GET_ERR,"订单号为空");
        }
        //获得订单信息
        Orders order = ordersService.getOneOrder(RecordID);
        return new Result(order,Code.GET_OK,"获取订单成功");
    }

    @PostMapping("/edit")
    public Result editOrder(@RequestBody Orders order){
        System.out.println(order);
        if(order.getOrderState().equals("订单废除")){
            Car car = carService.getOneCar(order.getCarID());
            car.setQuantity(car.getQuantity()+1);
            carService.updateOneCar(car);
        }
        boolean b = ordersService.updateOrder(order);
        if (b){
            return new Result(null,Code.SAVE_OK,"订单更新成功");
        }
        return new Result(null,Code.SAVE_ERR,"订单更新失败");
    }

}
