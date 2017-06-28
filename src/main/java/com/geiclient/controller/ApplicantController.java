package com.geiclient.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.geiclient.entity.Applicant;
import com.geiclient.entity.LoginPara;
import com.geiclient.entity.Resume;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cfzhu on 2017/4/6.
 */
@Controller
@RequestMapping("/applicant")
public class ApplicantController {

    @Autowired
    private SendRequestConfig sendRequestConfig;


    /**
     * 跳转到主界面
     * @return
     */
    @RequestMapping("/homepage")
    public String homepage(){
        return "/applicant/homepage";
    }


    /**
     * 获取验证码
     * @return
     */
    @RequestMapping("/getVerification")
    public ResponseEntity<?> getVerification(){
        return ResponseEntity.ok("1");
    }


    /**
     * 跳转到注册界面
     * @return
     */
    @RequestMapping("/registpage")
    public String registpage(){
        return "/applicant/regist";
    }


    /**
     * 注册
     * @param applicant
     * @return
     */
    @RequestMapping("/regist")
    public ResponseEntity<?> regist(@RequestBody Applicant applicant){
        Object result = null;
        try {
            result = SendRequest.sentPost2(sendRequestConfig.getAddress()+"geiApplicant/register", JSON.toJSONString(applicant));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }


    /**
     * 登录，登录成功缓存token和用户信息
     * @param loginPara
     * @param httpSession
     * @return
     */
    @RequestMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginPara loginPara,HttpSession httpSession){
        Object result = null;
        try {
            result = SendRequest.sentPost2(sendRequestConfig.getAddress()+"oauth/token",JSON.toJSONString(loginPara));
            result = JSON.parseObject(result.toString());
            JSONObject json = (JSONObject) JSON.toJSON(result);
            System.out.println(json.get("errcode"));
            if (json.get("errcode").equals(0)){
                String access_token = "bearer "+((JSONObject)json.get("p2pdata")).get("access_token").toString();
                httpSession.setAttribute("access_token",access_token);
                String applicantID = ((JSONObject)json.get("p2pdata")).get("id").toString();
                Object app = SendRequest.sentGetWithToken(sendRequestConfig.getAddress()+"geiApplicant/applicant/getApplicantInfoById/"+applicantID,access_token);
                app = JSON.parseObject(app.toString());
                JSONObject a = (JSONObject) JSON.toJSON(app);
                Applicant applicant = JSON.parseObject(a.get("p2pdata").toString(),Applicant.class);
                httpSession.setAttribute("applicant",applicant);
                return ResponseEntity.ok(json);
            }else{
                return ResponseEntity.badRequest().body(json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }


    /**
     * 加载主界面
     * @param httpSession
     * @return
     */
    @RequestMapping("/loadingHomePage")
    public ResponseEntity<?> loadingHomepage(HttpSession httpSession){
        try{
            Applicant applicant = (Applicant) httpSession.getAttribute("applicant");
            if (applicant != null){
                return ResponseEntity.ok(applicant);
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body("error");
        }
        return ResponseEntity.badRequest().body("error");
    }


    /**
     * 用户退出，清空session
     * @param httpSession
     * @return
     */
    @RequestMapping("/exitGEI")
    public ResponseEntity<?> exitGEI(HttpSession httpSession){
        try{
            Applicant applicant = (Applicant) httpSession.getAttribute("applicant");
            if (applicant != null){
                httpSession.removeAttribute("applicant");
                return ResponseEntity.ok("success");
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body("error");
        }
        return ResponseEntity.badRequest().body("error");
    }


    /**
     * 保存修改的基本信息
     * @param applicant
     * @param httpSession
     * @return
     */
    @RequestMapping("/saveBasicInformation")
    public ResponseEntity<?> saveBasicInformation(@RequestBody Applicant applicant,HttpSession httpSession){
        try{
            Applicant applicantEntity = (Applicant) httpSession.getAttribute("applicant");

            String applicantID = applicantEntity.getId().toString();
            String access_token = httpSession.getAttribute("access_token").toString();
            Object app = SendRequest.sentGetWithToken(sendRequestConfig.getAddress()+"geiApplicant/applicant/getApplicantInfoById/"+applicantID,access_token);

            app = JSON.parseObject(app.toString());
            JSONObject a = (JSONObject) JSON.toJSON(app);
            applicantEntity = JSON.parseObject(a.get("p2pdata").toString(),Applicant.class);
            applicantEntity.setName(applicant.getName());
            applicantEntity.setGender(applicant.getGender());
            applicantEntity.setAge(applicant.getAge());

            app = SendRequest.sentPostWithToken(sendRequestConfig.getAddress()+"geiApplicant/applicant/updateInfo",JSON.toJSONString(applicantEntity),access_token);
            app = JSON.parseObject(app.toString());
            a = (JSONObject) JSON.toJSON(app);
            applicant = JSON.parseObject(a.get("p2pdata").toString(),Applicant.class);
            httpSession.setAttribute("applicant",applicant);
            return ResponseEntity.ok(applicant);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("error");
        }
    }


    /**
     * 获取当前登录者id
     * @param httpSession
     * @return
     */
    @RequestMapping("/getApplicantId")
    public ResponseEntity<?> getApplicantId(HttpSession httpSession){
        try{
            Applicant applicantEntity = (Applicant) httpSession.getAttribute("applicant");
            String applicantID = applicantEntity.getId().toString();
            System.out.println(applicantID);
            return ResponseEntity.ok(applicantID);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("error");
        }
    }


    /**
     * 修改用户密码
     * @param oldPassWord
     * @param newPassWord
     * @return
     */
    @RequestMapping("/changePassWord/{oldPassWord}/{newPassWord}")
    public ResponseEntity<?> changePassWord(@PathVariable("oldPassWord") String oldPassWord,
                                            @PathVariable("newPassWord") String newPassWord,
                                            HttpSession httpSession){
        String token = httpSession.getAttribute("access_token").toString();
        String id = ((Applicant)httpSession.getAttribute("applicant")).getId().toString();
        Map<String,String> map = new HashMap<>();
        map.put("id",id);
        map.put("passWord",oldPassWord);
        map.put("newPassWord",newPassWord);
        Map<String,String> headMap = new HashMap<>();
        headMap.put("Authorization",token);
        try {
            Object app = SendRequest.sentPostWithToken(sendRequestConfig.getAddress()+"geiApplicant/applicant/changePassWord",map,headMap);
            app = JSON.parseObject(app.toString());
            JSONObject a = (JSONObject) JSON.toJSON(app);
            if (a.get("errcode").toString().equals("0")){
                return ResponseEntity.ok("修改成功");
            }else {
                System.out.println(a.get("errmsg"));
                return ResponseEntity.badRequest().body(a.get("errmsg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("修改失败");
        }
    }

    /**
     * 保存新建简历
     * @param resume
     * @param httpSession
     * @return
     */
    @RequestMapping("/saveResume")
    public ResponseEntity<?> saveResume(@RequestBody Resume resume,HttpSession httpSession){
        Object result = "";
        resume.setApplicant((Applicant) httpSession.getAttribute("applicant"));
        try {
            result = SendRequest.sentPostWithToken(sendRequestConfig.getAddress()+"geiApplicant/applicant/saveResume", JSON.toJSONString(resume),httpSession.getAttribute("access_token").toString());
            result = JSON.parseObject(result.toString());
            JSONObject a = (JSONObject) JSON.toJSON(result);
            if (a.get("errcode").equals(0)){
                return ResponseEntity.ok("保存成功");
            }else {
                return ResponseEntity.badRequest().body(a.get("errmsg"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    /**
     * 获取简历列表
     * @return
     */
    @RequestMapping("/getResumeList/{page}")
    public ResponseEntity<?> getResumeList(@PathVariable("page") int page,HttpSession httpSession){
        Applicant applicant = (Applicant) httpSession.getAttribute("applicant");
        String token = httpSession.getAttribute("access_token").toString();
        try {
            String result = SendRequest.sentPostWithToken(sendRequestConfig.getAddress()+"geiApplicant/applicant/getResumeByApplicant",JSON.toJSONString(applicant),token).toString();
            JSONArray jsonArray = JSON.parseArray(result);
            return ResponseEntity.ok(jsonArray);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("获取简历列表异常");
        }
    }


    /**
     * 根据简历id获取详情
     * @param id
     * @return
     */
    @RequestMapping("/getResumeById/{id}")
    public ResponseEntity<?> getResumeById(@PathVariable("id") String id,HttpSession httpSession){
        String token = httpSession.getAttribute("access_token").toString();
        Object result = SendRequest.sentGetWithToken(sendRequestConfig.getAddress()+"geiApplicant/applicant/getResumeById/"+id,token);
        result = JSON.parseObject(result.toString());
        JSONObject a = (JSONObject) JSON.toJSON(result);
        if (a.get("errcode").equals(0)){
            return ResponseEntity.ok(a.get("p2pdata"));
        }else {
            return ResponseEntity.badRequest().body(a.get("errmsg"));
        }
    }


    /**
     * 根据简历id删除简历
     * @param id
     * @param httpSession
     * @return
     */
    @RequestMapping("/deleteResumeById/{id}")
    public ResponseEntity<?> deleteResumeById(@PathVariable("id") String id,HttpSession httpSession){
        Object result = null;
        try {
            result = SendRequest.sentPostWithToken(sendRequestConfig.getAddress()+"geiApplicant/applicant/deleteResumeById/"+id, null,httpSession.getAttribute("access_token").toString());
            result = JSON.parseObject(result.toString());
            JSONObject a = (JSONObject) JSON.toJSON(result);
            if (a.get("errcode").equals(0)){
                return ResponseEntity.ok("删除成功");
            }else {
                return ResponseEntity.badRequest().body(a.get("errmsg"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

}
