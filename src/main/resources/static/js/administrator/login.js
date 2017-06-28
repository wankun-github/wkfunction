$(function(){
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

    //登录
    $("#loginButton").click(function () {
        var accid = $("#account").val();
        var password = $("#password").val();

        if (accid == "" || password == ""){
            toastr["error"]("登录账号和密码不可为空");
        }else {
            var administrator = {
                "accid":accid,
                "password":password
            };
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: '/administrator/administratorLogin',
                processData : false,
                data: JSON.stringify(administrator),
                dataType: false,
                cache: false,
                timeout: 600000,
                success: function (data) {
                    console.log(data);
                    if (data.errcode == "0"){
                        window.location.href="/administrator/homepage";
                    }else if (data.errcode == "30004"){
                        toastr['error']("登录账号或者密码错误");
                    }
                },
                error: function (data) {
                    toastr['error'](data.responseText);
                }
            });
        }
    });
});