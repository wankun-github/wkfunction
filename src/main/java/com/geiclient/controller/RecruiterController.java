package com.geiclient.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geiclient.entity.CompanyImg;
import com.geiclient.entity.LoginPara;
import com.geiclient.entity.Recruiter;
import com.geiclient.repository.SendRequest;
import com.geiclient.util.SendRequestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cfzhu on 2017/5/3.
 */
@Controller
@RequestMapping("/recruiter")
public class RecruiterController {

    @Autowired
    private SendRequestConfig sendRequestConfig;


    /**
     * 跳转到登录界面
     * @return
     */
    @RequestMapping("/login")
    public String login(){
        return "/recruiter/login";
    }


    /**
     * 注册
     * @param recruiter
     * @return
     */
    @RequestMapping("/regist")
    public ResponseEntity<?> regist(@RequestBody Recruiter recruiter){
        Object result = null;
        try {
            result = SendRequest.sentPostWithToken(sendRequestConfig.getAddress()+"geiRecruiter/register", JSON.toJSONString(recruiter),null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        result = JSON.parseObject(result.toString());
        JSONObject a = (JSONObject) JSON.toJSON(result);
        if (a.get("errcode").equals(0)){
            return ResponseEntity.ok("注册成功");
        }else {
            return ResponseEntity.badRequest().body(a.get("errmsg"));
        }
    }


    /**
     * 登录，登录成功缓存token和招聘者信息
     * @param loginPara
     * @param httpSession
     * @return
     */
    @RequestMapping("/recruiterLogin")
    public ResponseEntity<?> recruiterLogin(@RequestBody LoginPara loginPara, HttpSession httpSession){
        Object result = null;
        try {
            result = SendRequest.sentPost2(sendRequestConfig.getAddress()+"oauth/recruiterToken",JSON.toJSONString(loginPara));
            result = JSON.parseObject(result.toString());
            JSONObject json = (JSONObject) JSON.toJSON(result);
            if (json.get("errcode").equals(0)){
                String access_token = "bearer "+((JSONObject)json.get("p2pdata")).get("access_token").toString();
                httpSession.setAttribute("recruiter_access_token",access_token);
                String recruiterID = ((JSONObject)json.get("p2pdata")).get("id").toString();
                Object app = SendRequest.sentGetWithToken(sendRequestConfig.getAddress()+"geiRecruiter/recruiter/getRecruiterInfoById/"+recruiterID,access_token);
                app = JSON.parseObject(app.toString());
                JSONObject a = (JSONObject) JSON.toJSON(app);
                Recruiter recruiter = JSON.parseObject(a.get("p2pdata").toString(),Recruiter.class);
                httpSession.setAttribute("recruiter",recruiter);
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
        return "/recruiter/homepage";
    }


    /**
     * 获取当前登录招聘者信息
     * @param httpSession
     * @return
     */
    @RequestMapping("/getRecruiter")
    public ResponseEntity<?> getRecruiter(HttpSession httpSession){
        Recruiter recruiter = (Recruiter) httpSession.getAttribute("recruiter");
        if (recruiter != null){
            return ResponseEntity.ok(recruiter);
        }else {
            return ResponseEntity.badRequest().body("加载信息失败");
        }
    }


    /**
     * 用户退出，清空session
     * @param httpSession
     * @return
     */
    @RequestMapping("/exit")
    public String exit(HttpSession httpSession){
        try{
            Recruiter recruiter = (Recruiter) httpSession.getAttribute("recruiter");
            if (recruiter != null){
                httpSession.removeAttribute("recruiter");
                return "/recruiter/login";
            }
        }catch (Exception e){
            return "/recruiter/login";
        }
        return "/recruiter/login";
    }


    /**
     * 更新基本信息
     * @param recruiterEntity
     * @param httpSession
     * @return
     */
    @RequestMapping("/update")
    public ResponseEntity<?> update(@RequestBody Recruiter recruiterEntity,HttpSession httpSession){
        String token = (String) httpSession.getAttribute("recruiter_access_token");
        Recruiter recruiter = (Recruiter) httpSession.getAttribute("recruiter");
        recruiter.setName(recruiterEntity.getName());//企业名称
        recruiter.setAddress(recruiterEntity.getAddress());//企业地址
        recruiter.setPhone(recruiterEntity.getPhone());//企业联系电话
        recruiter.setCompanyType(recruiterEntity.getCompanyType()); //企业类型
        recruiter.setIntroduction(recruiterEntity.getIntroduction());//企业简介
        Object result;
        try {
            result = SendRequest.sentPostWithToken(sendRequestConfig.getAddress()+"geiRecruiter/recruiter/updateInfo",JSON.toJSONString(recruiter),token);
            result = JSON.parseObject(result.toString());
            JSONObject json = (JSONObject) JSON.toJSON(result);
            if (json.get("errcode").equals(0)){
                return ResponseEntity.ok(json.get("p2pdata"));
            }else {
                return ResponseEntity.badRequest().body(json.get("errmsg"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e);
        }
    }


    /**
     * 修改密码
     * @param oldPassWord
     * @param newPassWord
     * @param httpSession
     * @return
     */
    @RequestMapping("/changePassWord/{oldPassWord}/{newPassWord}")
    public ResponseEntity<?> changePassWord(@PathVariable("oldPassWord") String oldPassWord,
                                            @PathVariable("newPassWord") String newPassWord,
                                            HttpSession httpSession){
        Map<String,String> parameterMap = new HashMap<>();
        parameterMap.put("id",((Recruiter)httpSession.getAttribute("recruiter")).getId().toString());
        parameterMap.put("passWord",oldPassWord);
        parameterMap.put("newPassWord",newPassWord);
        Map<String,String> headmap = new HashMap<>();
        headmap.put("Authorization",httpSession.getAttribute("recruiter_access_token").toString());
        try {
            Object result = SendRequest.sentPostWithToken(sendRequestConfig.getAddress()+"geiRecruiter/recruiter/changePassWord",parameterMap,headmap);
            result = JSON.parseObject(result.toString());
            JSONObject json = (JSONObject) JSON.toJSON(result);
            if (json.get("errcode").equals(0)){
                return ResponseEntity.ok(json.get("p2pdata"));
            }else {
                return ResponseEntity.badRequest().body(json.get("errmsg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e);
        }

    }


    /**
     * 上传图片到7牛后，获取图片地址，构建recruiter实体对象，直接保存到相册
     * @param list
     * @return
     */
    @RequestMapping("/uploadImgToAlbum")
    @ResponseBody
    public ResponseEntity<?> uploadImgToAlbum(@RequestParam(required = false, value = "list[]") String[] list,HttpSession httpSession){
        Recruiter recruiter = (Recruiter) httpSession.getAttribute("recruiter");
        String token  = httpSession.getAttribute("recruiter_access_token").toString();
        List<CompanyImg> CompanyImgList = new ArrayList<>();
        if (list.length > 0){
            for (String path : list){
                CompanyImg companyImg = new CompanyImg();
                companyImg.setCompanyImgUrl(path);
                companyImg.setRecruiter(recruiter);
                CompanyImgList.add(companyImg);
            }
            recruiter.setCompanyImgUrls(CompanyImgList);
            try {
                Object result = SendRequest.sentPostWithToken(sendRequestConfig.getAddress()+"geiRecruiter/recruiter/uploadImgToAlbum",JSON.toJSONString(recruiter),token);
                result = JSON.parseObject(result.toString());
                JSONObject json = (JSONObject) JSON.toJSON(result);
                if (json.get("errcode").equals(0)){
                    return ResponseEntity.ok(json.get("p2pdata"));
                }else {
                    return ResponseEntity.badRequest().body(json.get("errmsg"));
                }
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().body(e);
            }

        }
        return null;
    }


    /**
     * 获取当前登录的招聘者的相册图片集合
     * @param httpSession
     * @return
     */
    @RequestMapping("/getCompanyImgs")
    public ResponseEntity<?> getCompanyImgs(HttpSession httpSession){
        String id = ((Recruiter)httpSession.getAttribute("recruiter")).getId().toString();
        String token = httpSession.getAttribute("recruiter_access_token").toString();
        try {
            Object result = SendRequest.sentPostWithToken(sendRequestConfig.getAddress()+"geiRecruiter/recruiter/getCompanyImgs/"+id,null,token);
            result = JSON.parseObject(result.toString());
            JSONObject json = (JSONObject) JSON.toJSON(result);
            if (json.get("errcode").equals(0)){
                return ResponseEntity.ok(json.get("p2pdata"));
            }else {
                return ResponseEntity.badRequest().body(json.get("errmsg"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e);
        }
    }


    /**
     * 获取招聘者头像
     * @param httpSession
     * @return
     */
    @RequestMapping("/getImg")
    public ResponseEntity<?> getImg(HttpSession httpSession){
        String id = ((Recruiter)httpSession.getAttribute("recruiter")).getId().toString();
        String token = httpSession.getAttribute("recruiter_access_token").toString();
        try {
            Object result = SendRequest.sentPostWithToken(sendRequestConfig.getAddress()+"geiRecruiter/recruiter/getCompanyImgs/"+id,null,token);
            result = JSON.parseObject(result.toString());
            JSONObject json = (JSONObject) JSON.toJSON(result);
            if (json.get("errcode").equals(0)){
                return ResponseEntity.ok(json.get("p2pdata"));
            }else {
                return ResponseEntity.badRequest().body(json.get("errmsg"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e);
        }
    }










}
