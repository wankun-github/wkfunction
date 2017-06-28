$(function(){

    //进入页面加载
    $(document).ready(function() {
        loadingPendingAuditRecruiter();
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

    $(".sidebar-li").mouseenter(function () {
        $(this).css("background-color","#4FC77B")
        $(this).find("a").css({"font-size":"16px","font-weight": "700"});
    }).mouseleave(function () {
        $(this).css("background-color","#2B2C3E")
        $(this).find("a").css({"font-size":"14px","font-weight": "400"});
    }).click(function () {
        var id = this.id.split("-")[1];
        $("#ToExamineRecruiter").hide()
        $("#AdministrationApplicant").hide()
        $("#AdministrationRecruiter").hide()
        $("#AdministrationPosition").hide()
        $("#"+id).show();
        if (id == "ToExamineRecruiter"){
            loadingPendingAuditRecruiter();
        }
    })

    //加载待审核的新注册招聘者
    function loadingPendingAuditRecruiter() {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: '/administrator/getNotAuditedRecruiters',
            processData : false,
            dataType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                console.log(data);
                $("#ToExamineRecruiter-Table tbody tr").remove();
                for(var i in data){
                    $("#ToExamineRecruiter-Table tbody").append('<tr>' +
                                                                '<td>'+data[i]["name"]+'</td>' +
                                                                '<td>'+data[i]["businessLicense"]+'</td>' +
                                                                '<td>' +
                                                                    '<div class="btn-group">' +
                                                                        '<button id="SeeRecruiter-'+data[i]["id"]+'" type="button" class="btn btn-default" data-toggle="modal" data-target="#myModal-ToExamineRecruiter-DetailedInformation">查看信息</button>' +
                                                                        '<button id="AuditRecruiter-'+data[i]["id"]+'" type="button" class="btn btn-default" data-toggle="modal" data-target="#myModal-ToExamineRecruiter-Audit">审核</button>' +
                                                                    '</div>' +
                                                                '</td>' +
                                                                '</tr>');
                    //绑定查看公司详细信息按钮事件
                    $("#SeeRecruiter-"+data[i]["id"]).click(function () {
                        var id = this.id.split("-")[1]
                        SeeRecruiter(id)
                    })
                    //审核按钮绑定事件
                    $("#AuditRecruiter-"+data[i]["id"]).click(function () {
                        var id = this.id.split("-")[1]
                        $("#modal-body-ToExamineRecruiter-Audit button").remove();
                        $("#modal-body-ToExamineRecruiter-Audit").append('' +
                            '<button id="NotPass-'+id+'" type="button" class="btn btn-danger" style="margin-left:25%;float: left">审核不通过</button>' +
                            '<button id="Adopt-'+id+'" type="button" class="btn btn-primary" style="margin-left:20%">审核通过</button>'
                        );
                        //绑定审核不通过
                        $("#NotPass-"+id).click(function () {
                            NotPass(id);
                        });
                        //绑定审核通过
                        $("#Adopt-"+id).click(function () {
                            Adopt(id);
                        });
                    })
                }
            },
            error: function (data) {
                toastr['error'](data.responseText);
            }
        });
    }

    //加载审核的招聘者的详细信息
    function SeeRecruiter(id) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: '/administrator/getRecruiterInfoById/'+id,
            processData : false,
            dataType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                console.log(data);
                $("#P-BusinessLicense").text(data["businessLicense"])
                $("#P-Name").text(data["name"])
                $("#P-Address").text(data["address"])
                $("#P-CompanyType").text(data["companyType"])
                $("#P-Phone").text(data["phone"])
                if (data["address"] == null){
                    $("#P-Address").text("暂无信息")
                }
                if (data["companyType"] == null){
                    $("#P-CompanyType").text("暂无信息")
                }
            },
            error: function (data) {
                toastr['error'](data.responseText);
            }
        });
    }

    //审核不通过
    function NotPass(id) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: '/administrator/unapprove/'+id,
            processData : false,
            dataType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                console.log(data);
                toastr["success"]("审核不通过,已删除该公司相关信息");
                $("#myModal-ToExamineRecruiter-Audit").modal("hide");
                loadingPendingAuditRecruiter();
            },
            error: function (data) {
                toastr['error'](data.responseText);
            }
        });
    }
    
    //审核通过
    function Adopt(id) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: '/administrator/approved/'+id,
            processData : false,
            dataType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                console.log(data);
                toastr["success"]("审核不通");
                $("#myModal-ToExamineRecruiter-Audit").modal("hide");
                loadingPendingAuditRecruiter();
            },
            error: function (data) {
                toastr['error'](data.responseText);
            }
        });
    }
});