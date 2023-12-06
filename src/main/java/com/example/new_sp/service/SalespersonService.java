package com.example.new_sp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.new_sp.domain.Salesperson;
import com.example.new_sp.domain.account;

public interface SalespersonService extends IService<Salesperson> {
    public boolean insertOneSalesperson(Salesperson Sp);//插入新销售
    public Salesperson getOneSalesperson(account ac);//根据ID来获得对应销售详细信息
    public boolean updateSalesperson(Salesperson sp);//根据销售ID来修改详细信息
    public Salesperson getByID(Integer id);
}
