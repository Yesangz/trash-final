package com.example.new_sp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.new_sp.Exception.BusinessException;
import com.example.new_sp.Result.Code;
import com.example.new_sp.dao.SalespersonDao;
import com.example.new_sp.domain.Customer;
import com.example.new_sp.domain.Salesperson;
import com.example.new_sp.domain.SalespersonGroup;
import com.example.new_sp.domain.account;
import com.example.new_sp.service.SalespersonGroupService;
import com.example.new_sp.service.SalespersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SalespersonServiceImpl extends ServiceImpl<SalespersonDao, Salesperson> implements SalespersonService {
    @Autowired
    private SalespersonGroupService groupService;
    @Override
    public boolean insertOneSalesperson(Salesperson Sp) {
        LambdaQueryWrapper<SalespersonGroup> selectSg = new LambdaQueryWrapper<>();
        selectSg.eq(SalespersonGroup::getGroupId,Sp.getSalesperson_group());
        SalespersonGroup groupServiceOne = groupService.getOne(selectSg);//查询销售团队

        //若销售团队存在，则插入该销售账户
        if(Optional.ofNullable(groupServiceOne).isPresent()){
            LambdaQueryWrapper<Salesperson> selectSp = new LambdaQueryWrapper<>();
            selectSp.eq(Salesperson::getAccountID,Sp.getAccountID());
            Salesperson one = getOne(selectSp);
            if(Optional.ofNullable(one).isPresent()){
                throw new BusinessException(Code.SAVE_ERR,"账户已存在");
            }
            else
                return save(Sp);
        }
        else {
            throw new BusinessException(Code.SAVE_ERR,"销售团队不存在");
        }


    }

    @Override
    public Salesperson getOneSalesperson(account ac) {
        LambdaQueryWrapper<Salesperson> getLmq = new LambdaQueryWrapper<>();
        getLmq.eq(Salesperson::getAccountID,ac.getID());

        Salesperson one = getOne(getLmq);
        return one;
    }

    @Override
    public boolean updateSalesperson(Salesperson sp) {
        LambdaQueryWrapper<Salesperson> getLmq = new LambdaQueryWrapper<>();
        getLmq.eq(Salesperson::getSalespersonID,sp.getSalespersonID());

        Salesperson salespersonFromId = getOne(getLmq);

        salespersonFromId.setName(sp.getName());
        salespersonFromId.setPhone(sp.getPhone());
        salespersonFromId.setTarget(sp.getTarget());

        UpdateWrapper<Salesperson> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("SalespersonID",salespersonFromId.getSalespersonID());

        boolean b = this.update(salespersonFromId,updateWrapper);
        return b;
    }

    public Salesperson getByID(Integer id){
        if (!Optional.ofNullable(id).isPresent()){
            throw new NullPointerException();
        }
        LambdaQueryWrapper<Salesperson> lmq = new LambdaQueryWrapper<>();
        lmq.eq(Salesperson::getSalespersonID,id);

        return getOne(lmq);
    }
}
