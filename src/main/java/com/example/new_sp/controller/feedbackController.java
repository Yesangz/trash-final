package com.example.new_sp.controller;

import com.example.new_sp.Result.Code;
import com.example.new_sp.Result.Result;
import com.example.new_sp.domain.account;
import com.example.new_sp.domain.feedback;
import com.example.new_sp.service.feedbackService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/feedback")
public class feedbackController {
    @Autowired
    private feedbackService FbS;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/content")
    public Result getFeedback(){
        return new Result(FbS.getFeedback(), Code.GET_OK,"获得意见反馈成功");
    }

    @PostMapping
    public Result insertFeedback(@RequestBody Map<String,Object> map) throws JsonProcessingException {
        HttpSession session = request.getSession(false);
        account sessionAccount;
        if (Optional.ofNullable(session).isPresent()){
            sessionAccount = (account) session.getAttribute("account");
        }else {
            return new Result(null,Code.SAVE_ERR,"账号未登录");
        }

        ObjectMapper JACKSON = new ObjectMapper();
        String Real_content = JACKSON.readValue(JACKSON.writeValueAsString(map.get("content")), String.class);
        feedback fb = new feedback();
        fb.setContent(Real_content);
        fb.setAccountID(sessionAccount.getID());
        if(FbS.insertFeedback(fb)){
            return new Result(null,Code.SAVE_OK,"提交反馈成功");
        }
        else
            return new Result(null,Code.SAVE_ERR,"提交反馈失败");
    }

    @PostMapping("/drop")
    public Result dropFeedback(@RequestBody feedback fb){
        boolean b = FbS.dropFeedback(fb);
        if (b){
            return new Result(null,Code.DELETE_OK,"删除成功");
        }else {
            return new Result(null,Code.DELETE_ERR,"删除失败");
        }

    }
}
