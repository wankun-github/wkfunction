package com.geiclient.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geiclient.entity.Administrator;
import com.geiclient.repository.SendRequest;
import com.geiclient.util.SendRequestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by cfzhu on 2017/5/3.
 */
@Controller
@RequestMapping("/administrator")
public class AdministratorController {

    @Autowired
    private SendRequestConfig sendRequestConfig;


    /**
     * 跳转到登录界面
     * @return
     */
    @RequestMapping("/login")
    public String login(){
        return "/administrator/login";
    }

    /**
     * 登录，登录成功缓存token和用户信息
     * @param administrator
     * @param httpSession
     * @return
     */
    @RequestMapping("/administratorLogin")
    public ResponseEntity<?> login(@RequestBody Administrator administrator, HttpSession httpSession){
        Object result = null;
        try {
            result = SendRequest.sentPost2(sendRequestConfig.getAddress()+"geiAdministrator/login", JSON.toJSONString(administrator));
            result = JSON.parseObject(result.toString());
            JSONObject json = (JSONObject) JSON.toJSON(result);
            System.out.println(json.get("errcode"));
            if (json.get("errcode").equals(0)){
                return ResponseEntity.ok(json);
            }else{
                return ResponseEntity.ok(json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }


    /**
     * 跳转到主界面
     * @return
     */
    @RequestMapping("/homepage")
    public String homepage(){
        return "/administrator/homepage";
    }


    /**
     * 获取待审核招聘者列表
     * @return
     */
    @RequestMapping("/getNotAuditedRecruiters")
    public ResponseEntity<?> getNotAuditedRecruiters(){
        Object result = null;
        result = SendRequest.sendGet(sendRequestConfig.getAddress()+"geiAdministrator/getNotAuditedRecruiters", null,null);
        result = JSON.parseObject(result.toString());
        JSONObject json = (JSONObject) JSON.toJSON(result);
        if (json.get("errcode").equals(0)){
            return ResponseEntity.ok(json.get("p2pdata"));
        }else{
            return ResponseEntity.badRequest().body(json);
        }
    }


    /**
     * 获取招聘者详细信息
     * @param id
     * @return
     */
    @RequestMapping("/getRecruiterInfoById/{id}")
    public ResponseEntity<?> getRecruiterInfoById(@PathVariable("id") String id){
        Object result = null;
        result = SendRequest.sendGet(sendRequestConfig.getAddress()+"geiAdministrator/getRecruiterInfoById/"+id, null,null);
        result = JSON.parseObject(result.toString());
        JSONObject json = (JSONObject) JSON.toJSON(result);
        if (json.get("errcode").equals(0)){
            return ResponseEntity.ok(json.get("p2pdata"));
        }else{
            return ResponseEntity.badRequest().body(json);
        }
    }


    /**
     * 审核不通过
     * @param id
     * @return
     */
    @RequestMapping("/unapprove/{id}")
    public ResponseEntity<?> unapprove(@PathVariable("id") String id){
        Object result = null;
        result = SendRequest.sendPost(sendRequestConfig.getAddress()+"geiAdministrator/unapprove/"+id, null);
        result = JSON.parseObject(result.toString());
        JSONObject json = (JSONObject) JSON.toJSON(result);
        if (json.get("errcode").equals(0)){
            return ResponseEntity.ok(json.get("p2pdata"));
        }else{
            return ResponseEntity.badRequest().body(json);
        }
    }


    /**
     * 审核通过
     * @param id
     * @return
     */
    @RequestMapping("/approved/{id}")
    public ResponseEntity<?> approved(@PathVariable("id") String id){
        Object result = null;
        result = SendRequest.sendPost(sendRequestConfig.getAddress()+"geiAdministrator/approved/"+id, null);
        result = JSON.parseObject(result.toString());
        JSONObject json = (JSONObject) JSON.toJSON(result);
        if (json.get("errcode").equals(0)){
            return ResponseEntity.ok(json.get("p2pdata"));
        }else{
            return ResponseEntity.badRequest().body(json);
        }
    }
}
