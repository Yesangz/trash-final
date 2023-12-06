package com.example.new_sp.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.new_sp.Exception.BusinessException;
import com.example.new_sp.Exception.ExceptionAdvice;
import com.example.new_sp.Result.Code;
import com.example.new_sp.dao.AccountDao;
import com.example.new_sp.domain.Customer;
import com.example.new_sp.domain.account;
import com.example.new_sp.service.accountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class accountServiceImpl extends ServiceImpl<AccountDao,account> implements accountService {
    @Override
    public account selectByAccountName(account ac){
        LambdaQueryWrapper<account> lqw = new LambdaQueryWrapper<>();
        lqw.eq(account::getAccountName,ac.getAccountName()).last("limit 1");
        account one = getOne(lqw);
        return one;
    }

    @Override
    public boolean insertOneAccount(account ac) {
        LambdaQueryWrapper<account> select = new LambdaQueryWrapper<>();
        select.eq(account::getAccountName,ac.getAccountName());
        account one = getOne(select);

        if(Optional.ofNullable(one).isPresent()){
            throw new BusinessException(Code.SAVE_ERR,"用户已存在");
        }
        //        为了排除ID，避免ID的随机值
//        account account = new account();
//        account.setAccountName(one.getAccountName());
//        account.setPassword(one.getPassword());
//        account.setType(one.getType());
//        account.setAuthority(one.getAuthority());
        return save(ac);
    }

    public boolean deleteOneAccountById(account ac){
        LambdaQueryWrapper<account> select = new LambdaQueryWrapper<>();
        select.eq(account::getID,ac.getID());
        account one = getOne(select);
        if(Optional.ofNullable(one).isPresent()){
            return removeById(ac.getID());
        }
        else
            throw new BusinessException(Code.DELETE_ERR,"用户不存在");
    }

    @Override
    public boolean updatePassword(account ac) {

        LambdaQueryWrapper<account> getLmq = new LambdaQueryWrapper<>();
        getLmq.eq(account::getID,ac.getID());

        account accountFromId = getOne(getLmq);

        accountFromId.setPassword(ac.getPassword());

        UpdateWrapper<account> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ID",accountFromId.getID());

        boolean b = this.update(accountFromId,updateWrapper);
        return b;
    }
}
