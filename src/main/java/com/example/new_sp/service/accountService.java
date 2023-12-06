package com.example.new_sp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.new_sp.domain.account;
import jakarta.servlet.http.HttpServletRequest;

public interface accountService extends IService<account> {
    public account selectByAccountName(account ac);
    public boolean insertOneAccount(account ac);
    public boolean deleteOneAccountById(account ac);
    public boolean updatePassword(account ac);//修改密码
}
