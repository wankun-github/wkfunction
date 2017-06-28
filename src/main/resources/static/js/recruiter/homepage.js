$(function(){
    //进入页面加载
    $(document).ready(function() {
        loadingRecruiter();
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

    //侧边导航栏切换时间
    $(".sidebar-li").mouseenter(function () {
        $(this).css("background-color","#4FC77B")
        $(this).find("a").css({"font-size":"16px","font-weight": "700"});
    }).mouseleave(function () {
        $(this).css("background-color","#2B2C3E")
        $(this).find("a").css({"font-size":"14px","font-weight": "400"});
    }).click(function () {
        $("#PersonalCenter,#PositionCenter,#MessageCenter").hide();
        $("#"+this.id.split("-")[1]).show()
        if (this.id == "Li-PersonalCenter"){
            loadingRecruiter();
        }
    })

    //加载登录招聘者基本信息
    function loadingRecruiter() {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: '/recruiter/getRecruiter',
            processData : false,
            dataType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                getImg();
                $("#recruiterName").html('<span class="glyphicon glyphicon-user"></span>您好：'+data["name"]+'');
                $("#PositionCenterInformation-businessLicense").text(data["businessLicense"]);
                $("#PositionCenterInformation-accid").text(data["accid"]);
                $("#PositionCenterInformation-name").text(data["name"]);
                $("#PositionCenterInformation-address").text(data["address"]);
                $("#PositionCenterInformation-phone").text(data["phone"]);
                $("#PositionCenterInformation-companyType").text(data["companyType"]);
                $("#PositionCenterInformation-introduction").text(data["introduction"]);
                if (data["address"] == null){
                    $("#PositionCenterInformation-address").text("暂无信息");
                }
                if (data["companyType"] == null){
                    $("#PositionCenterInformation-companyType").text("暂无信息");
                }
                if (data["introduction"] == null){
                    $("#PositionCenterInformation-introduction").text("暂无信息");
                }
            },
            error: function (data) {
                exit();
            }
        });
    }

    //退出系统
    $("#exit").click(function () {
        exit();
    })

    //编辑公司基本信息
    $("#edit-EssentialInformation-saveButton").click(function () {
        var name = $("#edit-Name").val();
        var address = $("#edit-Address").val();
        var phone = $("#edit-Phone").val();
        var companyType = $("#edit-CompanyType").val();
        var introduction = $("#edit-Introduction").val();
        if (name == "" || address == "" || phone == "" || companyType == "" || introduction == ""){
            toastr["error"]("请完善基本信息填写");
        }else {
            var recruiter = {
                "name":name,
                "address":address,
                "phone":phone,
                "companyType":companyType,
                "introduction":introduction
            }
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: '/recruiter/update',
                data: JSON.stringify(recruiter),
                processData : false,
                dataType: false,
                cache: false,
                timeout: 600000,
                success: function (data) {
                    toastr["success"]("保存成功");
                    $("#PositionCenterInformation-businessLicense").text(data["businessLicense"]);
                    $("#PositionCenterInformation-accid").text(data["accid"]);
                    $("#PositionCenterInformation-name").text(data["name"]);
                    $("#PositionCenterInformation-address").text(data["addess"]);
                    $("#PositionCenterInformation-phone").text(data["phone"]);
                    $("#PositionCenterInformation-companyType").text(data["companyType"]);
                    $("#PositionCenterInformation-introduction").text(data["introduction"]);
                    $("#myModal-EssentialInformation").modal("hide");
                },
                error: function (data) {
                    toastr["error"](data);
                }
            });
        }
    })

    //修改密码
    $("#edit-Password-saveButton").click(function () {
        var oldPassword = $("#edit-oldpassword").val();
        var newPassword = $("#edit-newpassword").val();
        var repeatnewPassword = $("#edit-repeatNewpassword").val();
        if (oldPassword == ""){
            toastr["error"]("原密码不可为空");
        }else if (newPassword == ""){
            toastr["error"]("新密码不可为空");
        }else if (repeatnewPassword == ""){
            toastr["error"]("请输入确认密码");
        }else if (oldPassword == newPassword){
            toastr["error"]("新密码不可与原密码相同");
        }else if (newPassword != repeatnewPassword){
            toastr["error"]("两次密码输入不相同");
        }else {
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: '/recruiter/changePassWord/'+oldPassword+"/"+newPassword,
                processData : false,
                dataType: false,
                cache: false,
                timeout: 600000,
                success: function (data) {
                    window.location.href="/recruiter/login";
                },
                error: function (data) {
                    toastr["error"]("原密码错误")

                }
            });
        }
    })

    //a标签切换到公司相册时加载
    $("#complay-Aulme").click(function () {
        $.ajax({
            url: '/recruiter/getCompanyImgs' ,
            type: 'POST',
            success: function (data) {
                console.log(data)
                $("#Album-body-carousel-inner div img").remove()
                var num = 1;
                for (var i in data){
                    if (num == 1){
                        $("#Album-body-carousel-inner").append('<div class="item active"><img src="'+data[i]["companyImgUrl"]+'" height="100%" width="100%"/></div>')
                    }else {
                        $("#Album-body-carousel-inner").append('<div class="item"><img src="'+data[i]["companyImgUrl"]+'" height="100%" width="100%"/></div>')
                    }
                    num += 1;
                }
            },
            error: function (data) {
                console.log(data)
                toastr['error']("加载相册失败");
            }
        });
    })

    //上传公司相册图片
    $(document).on('click', '.fileinput-upload-button', function(e) {
        $(this).attr('type','button');
        var files = document.getElementById("Album-input").files;
        var formData = new FormData();
        for(i = 0; i<files.length; i++){
            formData.append("file", files[i]);
        }
        $.ajax({
            url: 'http://localhost:8080/file/recruiter/batchUploadImgs' ,
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                var checkedData = [];
                for (var i in data["p2pdata"]){
                    checkedData.push(data["p2pdata"][i])
                }
                uploadImgToAlbum(checkedData);
            },
            error: function (data) {
                toastr['error']("上传失败");
            }
        });
    });


    function exit() {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: '/recruiter/exit',
            processData : false,
            dataType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                window.location.href="/recruiter/login";
            },
            error: function (data) {

            }
        });
    }
    
    function uploadImgToAlbum(imgsData) {
        $.ajax({
            url: '/recruiter/uploadImgToAlbum/' ,
            type: 'POST',
            data: {
                "list":imgsData
            },
            dataType: "json",
            success: function (data) {
                console.log(data)
                toastr['success']("上传成功");
            },
            error: function (data) {
                console.log(data)
                toastr['error']("上传失败");
            }
        });
    }

    function getImg() {
        $.ajax({
            url: '/recruiter/getImg' ,
            type: 'POST',
            success: function (data) {
                if (data.length>0){
                    $("#headImg").attr("src",data[0]["companyImgUrl"])
                }
            },
            error: function (data) {

            }
        });
    }
});