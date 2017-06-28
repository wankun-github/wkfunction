/**
 * Created by cfzhu on 2017/4/6.
 */

$(function(){
    //上传按钮状态（0=上传用户基本信息头像，1=上传简历头像）
    var uploadState = 0;

    //进入页面加载
    $(document).ready(function() {
        loadingHomepage();
    });

    //提示框样式
    toastr.options = {

        closeButton: false,
        debug: false, //是否开起debug
        progressBar: false, //是否显示进度条，当为false时候不显示；当为true时候，显示进度条，当进度条缩短到0时候，消息通知弹窗消失
        positionClass: "toast-top-center",//位置信息，消息弹窗显示的位置，可以显示的位置对应的值
        onclick: null,
        showDuration: "200",//显示动作（从无到有这个动作）持续的时间
        hideDuration: "500",//隐藏动作持续的时间
        timeOut: "1000",//间隔的时间
        extendedTimeOut: "500",
        showEasing: "swing",
        hideEasing: "linear",
        showMethod: "fadeIn",//显示的方式，和jQuery相同，可以是show()
        hideMethod: "fadeOut"//隐藏的方式，和jquery相同，可以是hide()
    };

    $("#JobPlaza,#CompanyWorld,#PersonalCenter").mouseenter(function(){
        $(this).addClass("active");
    }).mouseleave(function(){
        $(this).removeClass("active");
    });

    $("#JobPlaza").click(function () {
        $("#div-jobplaza").show();
        $("#div-companyworld").hide();
        $("#div-myhome").hide();
    });
    $("#CompanyWorld").click(function () {
        $("#div-jobplaza").hide();
        $("#div-companyworld").show();
        $("#div-myhome").hide();
    });
    $("#PersonalCenter").click(function () {
        $("#div-jobplaza").hide();
        $("#div-companyworld").hide();
        $("#div-myhome").show();
        loadingBasicInformation();
    });

    // 刷新验证码
    $("#getverification").click(function(){
        getVerification();
    });

    // 注册a标签点击事件
    $("#regist").click(function () {
        window.open("/applicant/registpage")
    });

    //获取验证码
    function getVerification() {
        var img_src = 'http://localhost:8080/captcha/getcaptcha?t='+Math.random();
        $("#getverificationImg").attr("src",img_src);
    }

    //登录按钮点击事件
    $("#login").click(function () {
        var accid = $("#account").val();
        var password = $("#password").val();
        var verification = $("#verification").val();

        if (accid == "" || accid == null){
            toastr['error']("登录账号不可为空");
        }else if (password == "" || password == null){
            toastr['error']("登录密码不可为空");
        }else if (verification == "" || verification == null){
            toastr['error']("验证码不可为空");
        }else {
            var loginPara = {
                "clientId":"098f6bcd4621d373cade4e832627b4f6",
                "accid":accid,
                "password":password,
                "captchaCode":verification
            };
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: '/applicant/login',
                processData : false,
                data: JSON.stringify(loginPara),
                dataType: false,
                cache: false,
                timeout: 600000,
                success: function (data) {
                    console.log(data);
                    if (data.errcode == "0"){
                        $('#loginModal').modal('hide');
                       loadingHomepage();
                    }else if (data.errcode == "30004"){
                        toastr['error']("用户名或密码错误");
                    }
                },
                error: function (data) {
                    toastr['error']("用户名或密码错误");
                }
            });
        }
    });
    
    //登录成功加载页面
    function loadingHomepage() {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: '/applicant/loadingHomePage',
            processData : false,
            dataType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                $("#PersonalCenter").show();
                $("#login_a").hide();
                $("#login_li a").remove();
                $("#login_li").append('<a role="menuitem" tabindex="-1" href="#" style="float: left">'+"欢迎你："+data.accid+'</a><a id="exitGEI" href="#" style="float: right;">退出</a>');
                $("#exitGEI").click(function () {
                    $.ajax({
                        type: "POST",
                        contentType: "application/json",
                        url: '/applicant/exitGEI',
                        processData : false,
                        dataType: false,
                        cache: false,
                        timeout: 600000,
                        success: function (data) {
                            $("#PersonalCenter").hide();
                            $("#login_li a").remove();
                            $("#login_li").append('<a id="login_li" href="#" data-toggle="modal" data-target="#loginModal">'+"登录"+'</a>');
                        },
                        error: function (data) {
                            $("#PersonalCenter").hide();
                            $("#login_li a").remove();
                            $("#login_li").append('<a id="login_li" href="#" data-toggle="modal" data-target="#loginModal">'+"登录"+'</a>');
                        }
                    });
                });
            },
            error: function (data) {

            }
        });
    }

    //切换折叠指示图标
    $(".panel-heading").click(function(e){
        $(this).find("span").toggleClass("glyphicon-chevron-down");
        $(this).find("span").toggleClass("glyphicon-chevron-up");
    });

    //加载用户基本资料
    function loadingBasicInformation(){
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: '/applicant/loadingHomePage',
            processData : false,
            dataType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                $("#imgUrl").attr("src",data.imgUrl);
                $("#accid").val(data.accid);
                $("#name").val(data.name);
                $("#age").val(data.age);
                if (data.gender == 0){
                    $("#gender1").attr("checked","checked");
                }else if (data.gender == 1){
                    $("#gender2").attr("checked","checked");
                }
            },
            error: function (data) {

            }
        });
    }

    //个人中心界面切换
    $("#ModifyInformation-Button,#ModifyPassword-Button,#MyResume-Button,#NewlyResume-Button").click(function () {
        $("#MyHomePage").children("div").hide();
        var showId = (this.id).split("-")[0]+"-Div";
        $("#"+showId).show();
    });

    //保存基本信息
    $("#saveBasicInformation").click(function () {
        saveBasicInformation();
    });

    //上传用户头像或者简历图片
    $(document).on('click', '.fileinput-upload-button', function(e) {
        if (uploadState == 1){
            $(this).attr('type','button');
            var formData = new FormData($( "#resume-upload")[0]);
            $.ajax({
                url: 'http://localhost:8080/file/applicant/uploadResumeImg' ,
                type: 'POST',
                data: formData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (returndata) {
                    console.log(returndata)
                    toastr['success']("上传成功");
                    $("#resume-img").attr("src",returndata.p2pdata);
                },
                error: function (returndata) {
                    console.log(returndata)
                    toastr['error']("上传失败");
                }
            });
        }else {
            $(this).attr('type','button');
            $.ajax({
                url: '/applicant/getApplicantId' ,
                type: 'POST',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (data) {
                    $("#applicantID").val(data);
                },
                error: function (data) {
                    console.log(data)
                    toastr['error']("上传失败");
                }
            });
            var formData = new FormData($( "#uploadForm" )[0]);
            $.ajax({
                url: 'http://localhost:8080/file/applicant/uploadImg' ,
                type: 'POST',
                data: formData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (returndata) {
                    console.log(returndata)
                    toastr['success']("上传成功");
                    $("#imgUrl").attr("src",returndata.p2pdata.imgUrl);
                },
                error: function (returndata) {
                    console.log(returndata)
                    toastr['error']("上传失败");
                }
            });
        }
    });

    //用户修改密码
    $("#ModifyPassword").click(function () {
        var oldPassWord = $("#Modify-oldPassWord").val();
        var newPassWord = $("#Modify-newPassWord").val();
        var confirmPassWord = $("#Modify-confirmPassWord").val();
        if (oldPassWord == "" || newPassWord == "" || confirmPassWord == ""){
            toastr['error']("输入框不可为空");
        }else if (oldPassWord.indexOf(" ")>=0 || newPassWord.indexOf(" ")>=0 || confirmPassWord.indexOf(" ")>=0){
            toastr['error']("输入框不可包含空格");
        }else if (oldPassWord == newPassWord){
            toastr['error']("新密码与原密码相同");
        }else if (newPassWord != confirmPassWord){
            toastr['error']("确认密码与新密码不同");
        }else {
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: '/applicant/changePassWord/'+oldPassWord+'/'+newPassWord,
                processData : false,
                dataType: false,
                cache: false,
                timeout: 600000,
                success: function (data) {
                    console.log(data);
                    toastr['success'](data);
                },
                error: function (data) {
                    console.log(data.responseText);
                    toastr['error'](data.responseText);
                }
            });
        }
    });

    //我的简历按钮
    $("#MyResume-Button").click(function () {
        var page = 1;
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: '/applicant/getResumeList/'+page,
            processData : false,
            dataType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                console.log(data);
                $("#MyResume-Table tbody tr").remove();
                for (var i in data){
                    $("#MyResume-Table tbody").append('<tr id="MyResume-'+data[i].id+'">' +
                                                      '<td>'+data[i].title+'</td>' +
                                                      '<td>' +
                                                        '<button id="MyResumeButton-'+data[i].id+'" type="button" class="btn btn-primary btn-xs" data-toggle="modal" data-target="#myModal-MyResume">查看</button>&nbsp;&nbsp;' +
                                                        '<button id="MyResumeDeleteButton-'+data[i].id+'" type="button" class="btn btn-danger btn-xs">删除</button>' +
                                                      '</td>' +
                                                      '</tr>');
                    //查看简历按钮绑定事件
                    $("#MyResumeButton-"+data[i].id).click(function () {
                        var id = this.id.split("-")[1];
                        $.ajax({
                            type:"POST",
                            contentType: "application/json",
                            url: '/applicant/getResumeById/'+id,
                            processData : false,
                            dataType: false,
                            cache: false,
                            timeout: 600000,
                            success:function (data) {
                                console.log(data)
                                //设置基本信息
                                $("#seeResume-4").val(data["title"])
                                $("#seeResume-resume-img").attr("src",data["imgUrl"])
                                $("#seeResume-5").val(data["name"])
                                $("#seeResume-6").val(data["age"])
                                $("#seeResume-8").val(data["phone"])
                                $("#seeResume-9").val(data["address"])
                                $("#seeResume-10").val(data["email"])
                                if (data["gender"] == 0){
                                    $("#seeResume-optionsRadios1").attr("checked","checked");
                                }else {
                                    $("#seeResume-optionsRadios2").attr("checked","checked");
                                }
                                //设置教育背景
                                $("#seeResume-EducationalBgs-Table tbody tr").remove()
                                for (var i in data["educationalBgs"]){
                                    $("#seeResume-EducationalBgs-Table tbody").append('<tr>' +
                                        '<td>'+format(data["educationalBgs"][i]["startTime"])+'</td>' +
                                        '<td>'+format(data["educationalBgs"][i]["endTime"])+'</td>' +
                                        '<td>'+data["educationalBgs"][i]["schoolName"]+'</td>' +
                                        '<td>'+data["educationalBgs"][i]["major"]+'</td>' +
                                        '</tr>');
                                }
                                //设置工作经历
                                $("#seeResume-WorkExperiences-Table tbody tr").remove()
                                for (var i in data["workExperiences"]){
                                    console.log(data["workExperiences"][i])
                                    $("#seeResume-WorkExperiences-Table tbody").append('<tr>' +
                                        '<td>'+format(data["workExperiences"][i]["startTime"])+'</td>' +
                                        '<td>'+format(data["workExperiences"][i]["endTime"])+'</td>' +
                                        '<td>'+data["workExperiences"][i]["companyName"]+'</td>' +
                                        '<td>'+data["workExperiences"][i]["position"]+'</td>' +
                                        '<td>'+data["workExperiences"][i]["remark"]+'</td>' +
                                        '</tr>');
                                }
                                //设置技能证书
                                $("#seeResume-SkillCertificates-Table tbody tr").remove()
                                for (var i in data["skillCertificates"]){
                                    console.log(data["skillCertificates"][i])
                                    $("#seeResume-SkillCertificates-Table tbody").append('<tr>' +
                                        '<td>'+data["skillCertificates"][i]["name"]+'</td>' +
                                        '</tr>');
                                }
                                //设置自我介绍
                                $("#seeResume-Evaluation-textarea").val(data["evaluation"])
                            },
                            error:function (data) {
                                toastr["error"](data.responseText);
                            }
                        });
                    });
                    //删除简历按钮绑定事件
                    $("#MyResumeDeleteButton-"+data[i].id).click(function () {
                        var id = this.id.split("-")[1];
                        $.ajax({
                            type:"POST",
                            contentType: "application/json",
                            url: '/applicant/deleteResumeById/'+id,
                            processData : false,
                            dataType: false,
                            cache: false,
                            timeout: 600000,
                            success:function (data) {
                                $("#MyResume-"+id).remove();
                                toastr["success"](data);
                            },
                            error:function (data) {
                                toastr["error"](data.responseText);
                            }
                        });
                    });
                }
            },
            error: function (data) {
                console.log(data.responseText);
                toastr['error'](data.responseText);
            }
        });
    });

    //上传简历头像按钮点击触发uploadStatus状态转变
    $("#resume-upload-button").click(function () {
        uploadState = 1;
    });

    //简历模态框关闭触发事件(修改uploadStatus=0)
    $('#myModal').modal('hide');
    $('#myModal').on('hide.bs.modal', function () {
        uploadState = 0
    });


    //添加教育背景
    $("#saveEducationalBgs").click(function () {
        var startTime = $("#EducationalBgsStartTime").val();
        var endTime = $("#EducationalBgsEndTime").val();
        var schoolName = $("#EducationalBgsSchoolName").val();
        var major = $("#EducationalBgsMajor").val();
        if (startTime == "" || endTime == "" || schoolName == "" || major == ""){
                toastr["error"]("请按要求填写信息");
        }else {
            $("#EducationalBgs-Table tbody").append('<tr data-toggle="modal" data-target="#myModal-modify-EducationalBgs">' +
                                                    '<td>'+startTime+'</td>' +
                                                    '<td>'+endTime+'</td>' +
                                                    '<td>'+schoolName+'</td>' +
                                                    '<td>'+major+'</td>' +
                                                    '</tr>');
        }
    });
    //教育背景绑定点击事件
    $("#EducationalBgs-Table tbody").on("click","tr", function() {
        $(this).attr("id","EducationalBgs-Table-Tr");
        var startTime;
        var endTime;
        var schoolName;
        var major;
        var i = 0;
        $(this).find("td").each(function(i){
            if (i == 0){
                startTime = $(this).text();
            }else if (i == 1){
                endTime = $(this).text();
            }else if (i == 2){
                schoolName = $(this).text();
            }else if (i == 3){
                major = $(this).text();
            }
            i += 1;
        });
        $("#modifyEducationalBgsStartTime").val(startTime);
        $("#modifyEducationalBgsEndTime").val(endTime);
        $("#modifyEducationalBgsSchoolName").val(schoolName);
        $("#modifyEducationalBgsMajor").val(major);
    });
    //修改教育背景按钮
    $("#saveModifyEducationalBgs").click(function () {
        var startTime = $("#modifyEducationalBgsStartTime").val();
        var endTime = $("#modifyEducationalBgsEndTime").val();
        var schoolName = $("#modifyEducationalBgsSchoolName").val();
        var major = $("#modifyEducationalBgsMajor").val();
        $("#EducationalBgs-Table-Tr td").remove();
        $("#EducationalBgs-Table-Tr").append('<td>'+startTime+'</td>' +
                                          '<td>'+endTime+'</td>' +
                                          '<td>'+schoolName+'</td>' +
                                          '<td>'+major+'</td>'
                                          );
        $("#EducationalBgs-Table-Tr").removeAttr("id");
    });
    //删除教育背景
    $("#deleteEducationalBgs").click(function () {
        $("#EducationalBgs-Table-Tr").remove();
    });
    //退出教育背景修改框时清除当前点击tr的id
    $('#myModal-modify-EducationalBgs').on('hide.bs.modal', function () {
        $("#EducationalBgs-Table-Tr").removeAttr("id");
    });


    //添加工作经历
    $("#saveWorkExperience").click(function () {
        var startTime = $("#WorkExperienceStartTime").val();
        var endTime = $("#WorkExperienceEndTime").val();
        var CompanyName = $("#CompanyName").val();
        var Position = $("#Position").val();
        var Remark = $("#Remark").val();
        if (startTime == "" || endTime == "" || CompanyName == "" || Position == "" || Remark == ""){
            toastr["error"]("请按要求填写信息");
        }else {
            $("#WorkExperiences-Table tbody").append('<tr data-toggle="modal" data-target="#myModal-modify-WorkExperience">' +
                '<td>'+startTime+'</td>' +
                '<td>'+endTime+'</td>' +
                '<td>'+CompanyName+'</td>' +
                '<td>'+Position+'</td>' +
                '<td>'+Remark+'</td>' +
                '</tr>');
        }
    });
    //工作经历绑定点击事件
    $("#WorkExperiences-Table tbody").on("click","tr", function() {
        $(this).attr("id","WorkExperiences-Table-Tr");
        var startTime;
        var endTime;
        var CompanyName;
        var Position;
        var Remark;
        var i = 0;
        $(this).find("td").each(function(i){
            if (i == 0){
                startTime = $(this).text();
            }else if (i == 1){
                endTime = $(this).text();
            }else if (i == 2){
                CompanyName = $(this).text();
            }else if (i == 3){
                Position = $(this).text();
            }else if (i == 4){
                Remark = $(this).text();
            }
            i += 1;
        });
        $("#modify-WorkExperienceStartTime").val(startTime);
        $("#modify-WorkExperienceEndTime").val(endTime);
        $("#modify-CompanyName").val(CompanyName);
        $("#modify-Position").val(Position);
        $("#modify-Remark").val(Remark);
    });
    //修改工作经历按钮
    $("#saveModifyWorkExperience").click(function () {
        var startTime = $("#modify-WorkExperienceStartTime").val();
        var endTime = $("#modify-WorkExperienceEndTime").val();
        var CompanyName = $("#modify-CompanyName").val();
        var Position = $("#modify-Position").val();
        var Remark = $("#modify-Remark").val();
        $("#WorkExperiences-Table-Tr td").remove();
        $("#WorkExperiences-Table-Tr").append('<td>'+startTime+'</td>' +
                                              '<td>'+endTime+'</td>' +
                                              '<td>'+CompanyName+'</td>' +
                                              '<td>'+Position+'</td>'+
                                              '<td>'+Remark+'</td>'
                                             );
        $("#WorkExperiences-Table-Tr").removeAttr("id");
    });
    //删除工作经历
    $("#deleteWorkExperience").click(function () {
        $("#WorkExperiences-Table-Tr").remove();
    });
    //退出工作经历修改框时清除当前点击tr的id
    $('#myModal-modify-WorkExperience').on('hide.bs.modal', function () {
        $("#WorkExperiences-Table-Tr").removeAttr("id");
    });


    //添加技能证书
    $("#saveSkillCertificate").click(function () {
        var name = $("#SkillCertificateName").val();
        if (name == ""){
            toastr["error"]("请按要求填写信息");
        }else {
            $("#SkillCertificates-Table tbody").append('<tr data-toggle="modal" data-target="#myModal-modify-SkillCertificate">' +
                '<td>'+name+'</td>' +
                '</tr>');
        }
    });
    //工作经历绑定点击事件
    $("#SkillCertificates-Table tbody").on("click","tr", function() {
        $(this).attr("id","SkillCertificates-Table-Tr");
        var name;
        $(this).find("td").each(function(i){
            name = $(this).text();
        });
        $("#modify-SkillCertificateName").val(name);
    });
    //修改技能证书按钮
    $("#saveModifySkillCertificate").click(function () {
        var name = $("#modify-SkillCertificateName").val();
        $("#SkillCertificates-Table-Tr td").remove();
        $("#SkillCertificates-Table-Tr").append('<td>'+name+'</td>');
        $("#SkillCertificates-Table-Tr").removeAttr("id");
    });
    //删除技能证书
    $("#deleteSkillCertificate").click(function () {
        $("#SkillCertificates-Table-Tr").remove();
    });
    //退出技能证书修改框时清除当前点击tr的id
    $('#myModal-modify-SkillCertificate').on('hide.bs.modal', function () {
        $("#SkillCertificates-Table-Tr").removeAttr("id");
    });

    //保存简历
    $("#saveResume").click(function () {
        var title = $("#input-4").val();
        var name = $("#input-5").val();
        var age = $("#input-6").val();
        var gender = $("input[name='optionsRadios']:checked").val();
        var phone = $("#input-8").val();
        var address = $("#input-9").val();
        var email = $("#input-10").val();
        var imgUrl = $("#resume-img").attr("src");
        var educationalBgs = [];
        var workExperiences = [];
        var skillCertificates = [];
        var evaluation = $("#Evaluation-textarea").val();

        //获取教育背景表格，循环td给educationalBgs数组赋值
        $("#EducationalBgs-Table tbody").find("tr").each(function(i){
            var startTime;
            var endTime;
            var schoolName;
            var major;
            $(this).find("td").each(function (i) {
                if (i == 0){
                    startTime = $(this).text();
                }else if (i == 1){
                    endTime = $(this).text();
                }else if (i == 2){
                    schoolName = $(this).text();
                }else if (i == 3){
                    major = $(this).text();
                }
            });
            var educationalBg = {
                "startTime":startTime,
                "endTime":endTime,
                "schoolName":schoolName,
                "major":major
            }
            educationalBgs.push(educationalBg)
        });
        //获取工作经历表格，循环td给workExperiences数组赋值
        $("#WorkExperiences-Table tbody").find("tr").each(function(i){
            var startTime;
            var endTime;
            var companyName;
            var position;
            var remark;
            $(this).find("td").each(function (i) {
                if (i == 0){
                    startTime = $(this).text();
                }else if (i == 1){
                    endTime = $(this).text();
                }else if (i == 2){
                    companyName = $(this).text();
                }else if (i == 3){
                    position = $(this).text();
                }else if (i == 4){
                    remark = $(this).text();
                }
            });
            var WorkExperience = {
                "startTime":startTime,
                "endTime":endTime,
                "companyName":companyName,
                "position":position,
                "remark":remark
            }
            workExperiences.push(WorkExperience)
        });
        //获取技能证书表格，循环td给skillCertificates数组赋值
        $("#SkillCertificates-Table tbody").find("tr").each(function(i){
            var name;
            $(this).find("td").each(function (i) {
                if (i == 0){
                    name = $(this).text();
                }
            });
            var skillCertificate = {
                "name":name
            }
            skillCertificates.push(skillCertificate)
        });

        if(title == "" || name == "" || age == "" || gender == "" || phone == "" || address == "" || email == "" || imgUrl == ""){
            toastr["error"]("请完善基本信息");
        }else if (imgUrl == "/img/applicant/resume/resume-默认图片.jpg"){
            toastr["error"]("请上传头像图片");
        }else if (evaluation == ""){
            toastr["error"]("请完善自我介绍信息");
        }else if (educationalBgs.length <= 0){
            toastr["error"]("请完善教育背景信息");
        }else if (workExperiences.length <= 0){
            toastr["error"]("请完善工作经历信息");
        }else if (skillCertificates.length <= 0){
            toastr["error"]("请完善技能证书信息");
        }else {
            var resume = {
                "title":title,
                "name":name,
                "age":age,
                "gender":gender,
                "phone":phone,
                "address":address,
                "email":email,
                "imgUrl":imgUrl,
                "educationalBgs":educationalBgs,
                "workExperiences":workExperiences,
                "skillCertificates":skillCertificates,
                "evaluation":evaluation
            }
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: '/applicant/saveResume',
                data:JSON.stringify(resume),
                processData : false,
                dataType: false,
                cache: false,
                timeout: 600000,
                success: function (data) {
                    console.log(data);
                    toastr['success'](data);
                    $("#input-4").val("");
                    $("#input-5").val("");
                    $("#input-6").val("");
                    $("#input-8").val("");
                    $("#input-9").val("");
                    $("#input-10").val("");
                    $("#resume-img").attr("src","/img/applicant/resume/resume-默认图片.jpg");
                    $("#Evaluation-textarea").val("");
                    $("#EducationalBgs-Table tbody").find("tr").remove();
                    $("#WorkExperiences-Table tbody").find("tr").remove();
                    $("#SkillCertificates-Table tbody").find("tr").remove();
                },
                error: function (data) {
                    console.log(data.responseText);
                    toastr['error'](data.responseText);
                }
            });
            console.log(resume)
        }

    });


    function saveBasicInformation(){
        var name = $("#name").val();
        var gender = $('input:radio[name="genderRadios"]:checked').val();
        var age = $("#age").val();
        var accid = $("#accid").val();
        var r = /^\+?[1-9][0-9]*$/;　　//正整数
        var flag=r.test(age);
        if (name == ""){
            toastr['error']("姓名不可为空");
        }else if (name.indexOf(" ") != -1){
            toastr['error']("姓名中不可包含空格");
        }else if (!flag){
            toastr['error']("年龄应为正整数");
        }else {

            var applicant = {
                "accid":accid,
                "name":name,
                "gender":gender,
                "age":age
            };
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: '/applicant/saveBasicInformation',
                data: JSON.stringify(applicant),
                processData : false,
                dataType: false,
                cache: false,
                timeout: 600000,
                success: function (data) {
                    $("#imgUrl").attr("src",data.imgUrl);
                    $("#accid").val(data.accid);
                    $("#name").val(data.name);
                    $("#age").val(data.age);
                    if (data.gender == 0){
                        $("#gender1").attr("checked","checked");
                    }else if (data.gender == 1){
                        $("#gender2").attr("checked","checked");
                    }
                    toastr['success']("保存成功");
                },
                error: function (data) {
                    toastr['error']("保存失败");
                }
            });
        }
    }

    //jq 将时间戳转换为格式化字符串
    function add0(m){return m<10?'0'+m:m }
    function format(timestamp) {
        //timestamp是整数，否则要parseInt转换,不会出现少个0的情况
        var time = new Date(timestamp);
        var year = time.getFullYear();
        var month = time.getMonth()+1;
        var date = time.getDate();
        var hours = time.getHours();
        var minutes = time.getMinutes();
        var seconds = time.getSeconds();
        // return year+'-'+add0(month)+'-'+add0(date)+' '+add0(hours)+':'+add0(minutes)+':'+add0(seconds);
        return year+'-'+add0(month)+'-'+add0(date);
    }
});



