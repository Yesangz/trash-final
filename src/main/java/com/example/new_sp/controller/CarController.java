package com.example.new_sp.controller;

import com.example.new_sp.Exception.BusinessException;
import com.example.new_sp.Masks.TypeMask;
import com.example.new_sp.Result.Code;
import com.example.new_sp.Result.Result;
import com.example.new_sp.domain.*;
import com.example.new_sp.service.BrandsService;
import com.example.new_sp.service.CarService;
import com.example.new_sp.service.CustomerService;
import com.example.new_sp.service.OrdersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/Car")
public class CarController {
    @Autowired
    private CarService cs;
    @Autowired
    private BrandsService bs;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    TransactionDefinition transactionDefinition;
    @Autowired
    DataSourceTransactionManager dataSourceTransactionManager;
    @Autowired
    HttpServletRequest request;


    @GetMapping("/getCars")
    public Result getCars(@RequestParam("pageSize") Integer pageSize, @RequestParam("currentPage")  Integer currentPage){
        int save_pageSize = pageSize;
        int save_currentPage = currentPage;

        List<Car> cars = cs.getCars(save_pageSize, save_currentPage);
        if(cars.isEmpty()){
            return new Result(null,Code.GET_ERR,"暂无车辆信息");
        }
        return new Result(cars, Code.GET_OK,"成功获取车辆信息");
    }

    @GetMapping("/getBrands")
    public Result getBrands(){
        List<Brands> brands = bs.getBrands();
        if(brands.isEmpty()){
            return new Result(null,Code.GET_ERR,"暂无车辆信息");
        }
        return new Result(brands,Code.GET_OK,"成功获取品牌信息");
    }

    @PostMapping("/selectCars")
    public Result selectCars(@RequestBody Map<String,Object>params) throws JsonProcessingException {
        ObjectMapper JACKSON = new ObjectMapper();
        //JSON转Sting再转为对象
        Integer brandID = JACKSON.readValue(JACKSON.writeValueAsString(params.get("brandID")), Integer.class);
        String model = JACKSON.readValue(JACKSON.writeValueAsString(params.get("model")), String.class);
        Integer lPrice = JACKSON.readValue(JACKSON.writeValueAsString(params.get("lPrice")), Integer.class);
        Integer hPrice = JACKSON.readValue(JACKSON.writeValueAsString(params.get("hPrice")), Integer.class);
        Integer pageSize = JACKSON.readValue(JACKSON.writeValueAsString(params.get("pageSize")), Integer.class);
        Integer currentPage = JACKSON.readValue(JACKSON.writeValueAsString(params.get("currentPage")),Integer.class);

        System.out.println(brandID+" "+model+" "+lPrice+" "+hPrice);
//        查询
        List<Car> cars = cs.selectCars(brandID, model, lPrice, hPrice, pageSize, currentPage);
        if(cars.isEmpty()){
            return new Result(null,Code.GET_ERR,"查询失败");
        }
        return new Result(cars,Code.GET_OK,"查询成功");
    }

    @PostMapping("/buy")
    public Result buyCar(@RequestBody Map<String,Object>params) throws JsonProcessingException {
        ObjectMapper JACKSON = new ObjectMapper();
//        CarID:row.CarID,
//        SalespersonID:this.salespersonBuy
        Integer CarID = JACKSON.readValue(JACKSON.writeValueAsString(params.get("CarID")), Integer.class);
        Integer SalespersonID = JACKSON.readValue(JACKSON.writeValueAsString(params.get("SalespersonID")), Integer.class);

        HttpSession session = request.getSession(false);
        account ac;
        if(Optional.ofNullable(session).isPresent()){
            ac = (account) session.getAttribute("account");
        }
        else{
            return new Result(null,Code.UPDATE_ERR,"账号未登录");
        }

//      开启事务
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        boolean b1 = false;
        boolean b2 = false;
        boolean b3 = false;
        try{
            Car car = cs.getOneCar(CarID);
            if(car.getQuantity() == 0 || car.getSalesState()=='0'){
                return new Result(null,Code.UPDATE_ERR,"该车辆无法被购买");
            }else {
                Integer q = car.getQuantity()-1;
                if(q==0){
                    //设置销售状态
                    car.setSalesState('0');
                }
                car.setQuantity(q);
                //更新车辆信息，再设置订单信息
                b1 = cs.updateOneCar(car);
                if(b1){
                    Orders order = new Orders();
                    //判断是否设置销售人员ID
                    if(Optional.ofNullable(SalespersonID).isPresent()){
                        order.setSalespersonID(SalespersonID);
                    }
                    //设置销售日期
                    Date date = new Date(System.currentTimeMillis());
                    order.setSalesDate(date);
                    //设置价格
                    order.setSalesPrice(car.getPrice());
                    //设置买家ID
                    Customer customer = customerService.getOneCustomer(ac);
                    order.setCustomerID(customer.getCustomerID());
                    //设置车辆ID
                    order.setCarID(car.getCarID());
                    //设置订单状态
                    order.setOrderState("待接收");

                    b2 = ordersService.insertOrder(order);
                }
            }
            //手动提交
            dataSourceTransactionManager.commit(transactionStatus);
        }catch (Exception e){
            //事务回滚
            dataSourceTransactionManager.rollback(transactionStatus);
        }finally {
            if(b1){
                if(b2){
                    return new Result(null,Code.SAVE_OK,"订单提交成功");
                }else {
                    return new Result(null,Code.SAVE_ERR,"订单提交失败");
                }
            }else {
                return new Result(null,Code.SAVE_ERR,"车辆信息不符合");
            }
        }
    }

    @PostMapping("/drop")
    public Result dropCar(@RequestBody Map<String,Object> params) throws JsonProcessingException {
        ObjectMapper JACKSON = new ObjectMapper();
        Integer CarID = JACKSON.readValue(JACKSON.writeValueAsString(params.get("CarID")), Integer.class);

        boolean b = false;
        try {
            b = cs.dropCarById(CarID);
        }catch (BusinessException be){
            return new Result(null,be.getCode(),be.getMessage());
        }
        if (b){
            return new Result(null,Code.DELETE_OK,"车辆删除成功");
        }
        else {
            return new Result(null,Code.DELETE_ERR,"车辆删除失败");
        }
    }

    @PostMapping("/add")
    public Result addCar(@RequestBody Car car){
        //判断品牌ID和品牌名都为空则出错
        if (!Optional.ofNullable(car.getBrandID()).isPresent() && !Optional.ofNullable(car.getBrand()).isPresent()){
            return new Result(null,Code.SAVE_ERR,"品牌为空");
        }
        else if (!Optional.ofNullable(car.getModel()).isPresent()){
            return new Result(null,Code.SAVE_ERR,"汽车型号为空");
        } else if (!Optional.ofNullable(car.getConfig()).isPresent()) {
            return new Result(null,Code.SAVE_ERR,"汽车配置为空");
        } else if (!Optional.ofNullable(car.getPrice()).isPresent()) {
            return new Result(null,Code.SAVE_ERR,"价格为空");
        } else if (!Optional.ofNullable(car.getQuantity()).isPresent()) {
            return new Result(null,Code.SAVE_ERR,"库存项为空，无库存可输入值为0");
        }

        boolean b = cs.addCar(car);
        if (b){
            return new Result(null,Code.SAVE_OK,"添加成功");
        }
        else {
            return new Result(null,Code.SAVE_ERR,"添加失败");
        }
    }
}
