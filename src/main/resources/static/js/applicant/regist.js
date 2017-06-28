/**
 * Created by cfzhu on 2017/4/13.
 */
/**
 * Created by cfzhu on 2017/4/6.
 */

$(function(){
    <!-- 提示框样式 -->
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


    $("#regist").click(function () {
        var applicant;
        var accid =$("#accid").val();
        var passWord =$("#passWord").val();
        if (accid == null || accid == ""){
            toastr['error']("登录账号不可为空");
        }else {
            if (passWord == null || passWord == ""){
                toastr['error']("密码不可为空");
            }else {
                applicant = {
                    "accid":accid,
                    "passWord":passWord
                }
                $.ajax({
                    type: "POST",
                    contentType: "application/json",
                    url: '/applicant/regist',
                    processData : false,
                    data: JSON.stringify(applicant),
                    dataType: false,
                    cache: false,
                    timeout: 600000,
                    success: function (data) {
                        data = JSON.parse(data);
                        if (data.errcode == "0"){
                            toastr['success']("注册成功，请返回登录，三秒后自动关闭本页面");
                            setTimeout(function(){
                                window.close();
                            },3000);
                        }else if (data.errcode == "414"){
                            toastr['error']("此用户名已经存在");
                        }

                    },
                    error: function (data) {
                        toastr['error'](JSON.parse(data.responseText).p2pdata);
                    }
                });
            }
        }


    });
});



