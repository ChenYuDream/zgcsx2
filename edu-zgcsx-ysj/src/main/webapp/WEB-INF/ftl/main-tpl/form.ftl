<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>中关村三小</title>

    <!-- 公共重置CSS -->
    <link href="${ctx}/static/css/common.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="http://at.alicdn.com/t/font_458254_uf1sbggrpo2bj4i.css">
    <!-- 样式美化CSS -->
    <link href="${ctx}/static/css/main.css" rel="stylesheet">
	<script src="https://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <script src="${ctx}/static/js/avalon.js"></script>
    <script src="${ctx}/static/js/common.js"></script>
    <!-- */ Layui控件 
         */ 注:文件先后顺序 S -->
    <link rel="stylesheet" type="text/css" href="${ctx}/static/plug-in/layui-v2.1.7/layui/css/layui.css">
    <script type="text/javascript" src="${ctx}/static/plug-in/layui-v2.1.7/layui/layui.js"></script>
    <!-- */ zTree控件 
         */ 注:文件先后顺序 S -->
    <link rel="stylesheet" type="text/css" href="${ctx}/static/plug-in/zTree/css/zTreeStyle/zTreeStyle.css">
    <script type="text/javascript" src="${ctx}/static/plug-in/zTree/js/jquery.ztree.all.min.js"></script>
    <!-- */ 验证插件 
         */ 注:文件先后顺序 S -->
    <script type="text/javascript" src="${ctx}/static/plug-in/nice-validator-1.1.2/dist/jquery.validator.min.js?local=zh-CN"></script>
    <script type="text/javascript" src="${ctx}/static/js/validator-init.js"></script>
    <!-- */ 上传组件 
         */ 注:文件先后顺序 S -->
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.9/themes/base/jquery-ui.css" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/static/plug-in/plupload-2.3.6/js/jquery.ui.plupload/css/jquery.ui.plupload.css">
    <script type="text/javascript" src="https://cdn.bootcss.com/jqueryui/1.10.2/jquery-ui.js"></script>
    <script type="text/javascript" src="${ctx}/static/plug-in/plupload-2.3.6/js/plupload.full.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/plug-in/plupload-2.3.6/js/jquery.ui.plupload/jquery.ui.plupload.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/plug-in/plupload-2.3.6/js/i18n/zh_CN.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/plupload-init.js"></script>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <!--[if IE 8]><![endif]-->
</head>
<body class="skin-night ms-controller" ms-controller="main">
<!-- 右侧功能模块 S-->
<div class="js_winHeight fixed-lr-wrap right-wrap">
    <div class="text-center close-btn">
        <i onclick="shortcutFn('.right-wrap','.js_shortcut_btn')" class="iconfont icon-X" title="关闭"></i>
    </div>
    <h3 class="text-center">功能操作区</h3>
    <div class="fn-tabList">
        <ul class="clearfix">
            <li>
                <a class="item-btn" href="#this" title="">
                    <span class="icon"><i class="iconfont icon-artboard12"></i>&nbsp;密码</span>
                    <span class="text">修改密码</span>
                </a>
            </li>
            <li>
                <a class="item-btn active" href="#this" title="">
                    <span class="icon"><i class="iconfont icon-huanfu"></i>&nbsp;皮肤</span>
                    <span class="text">设置皮肤</span>
                </a>
                <div class="skin-wrap" dir="rtl">
                    <ul>
                        <li class="active">
                            <div class="layout">
                                <h5 class="text-center">?确认选择吗</h5>
                                <a class="item-btn" href="#this" title="">预览</a>
                                <a class="item-btn" href="#this" title="">设置</a>
                            </div>
                            <i class="sj iconfont icon-sjg"></i>
                            <div class="img" style="background-image: url(${ctx}/static/images/skin-night.jpg)"></div>
                        </li>
                        <li>
                            <div class="layout">
                                <h5 class="text-center">?确认选择吗</h5>
                                <a class="item-btn" href="#this" title="">预览</a>
                                <a class="item-btn" href="#this" title="">设置</a>
                            </div>
                            <i class="sj iconfont icon-sjg"></i>
                            <img src="${ctx}/static/images/skin-qinzhu.jpg" alt="">
                        </li>
                    </ul>
                </div>
            </li>
        </ul>
    </div>
    <div class="footer-ico">
        <img src="${ctx}/static/images/footer-ico.png" alt="">
    </div>
</div>
<!-- 右侧功能模块 E-->
<div class="container">
    <!-- 左侧菜单导航 S-->
    <div class="js_winHeight fixed-lr-wrap left-wrap">
        <a class="logo" href="#this" title="">
            <img src="${ctx}/static/images/logo.png" width="160" height="140" alt="">
        </a>
        <menu id="js_menu" class="menu-wrap">
            <li>
                <a class="title" href="#this"><i class="iconfont icon-rili"></i>云日历</a>
            </li>
            <li class="active">
                <a class="title" href="#this"><i class="iconfont icon-jian1"></i>云事件</a>
                <ul class="item-list" style="display:block;">
                    <li class="active">
                        <a href="#this" title="">事件发起</a>
                    </li>
                    <li>
                        <a href="#this" title="">事件记录</a>
                    </li>
                </ul>
            </li>
        </menu>
        <div class="footer-ico">
            <img src="images/footer-ico.png" alt="">
        </div>
    </div>
    <!-- 左侧菜单导航 E-->
    <!-- 主内容区域 S-->
    <div class="main">
        <h1 class="title-pj text-center">三小云事件系统</h1>
        <section id="js_fixed_header" class="header-wrap">
            <!-- 系统菜单分类 S-->
            <div id="js_systeMenu" class="systematics-wrap">
                <ul class="clearfix">
                    <li class="active"><a href="#this"><i class="iconfont icon-fenlei"></i><span class="text-overflow">职称评审</span></a></li>
                    <li><a href="#this"><i class="iconfont icon-xinzeng"></i><span class="text-overflow">双向竟凭</span></a></li>
                    <li><a href="#this"><i class="iconfont icon-huanfu"></i><span class="text-overflow">职称评审</span></a></li>
                    <li><a href="#this"><i class="iconfont icon-daohangshouye"></i><span class="text-overflow">双向竟凭</span></a></li>
                    <li><a href="#this"><i class="iconfont icon-chazhao"></i><span class="text-overflow">职称评审</span></a></li>
                    <li><a href="#this"><i class="iconfont icon-qingkong"></i><span class="text-overflow">双向竟凭</span></a></li>
                </ul>
            </div>
            <!-- 系统菜单分类 E-->
            <div class="clearfix">
                <div class="info pull-left">
                    <img src="${ctx}/static/images/default.png" width="35" height="35" alt="">
                    你好！<strong>张远航</strong>今天是:<strong>2017-10-30 (周一)</strong>
                </div>
                <div class="toolList pull-right">
                    <ul class="clearfix">
                        <li>
                            <!-- 系统导航 S-->
                            <a onclick="systeMenu('#js_systeMenu',this)" class="item-btn" href="#this" title=""><i class="iconfont icon-fenlei"></i>系统导航</a>
                            <!-- 系统导航 S-->
                        </li>
                        <li>
                            <!-- 退出系统 S-->
                            <a class="item-btn" href="#this" title=""><i class="iconfont icon-tuichu"></i>退出系统</a>
                            <!-- 退出系统 S-->
                        </li>
                        <li>
                            <!-- 其它 S-->
                            <a onclick="shortcutFn('.right-wrap',this)" class="item-btn js_shortcut_btn" href="#this" title="系统操作">
                                <i class="iconfont icon-fenlei1" style="margin-right: 0"></i>
                            </a>
                            <!-- 其它 S-->
                        </li>
                    </ul>
                </div>
            </div>
        </section>
        <!-- table切换 E-->
        <section class="main-wrap">
            <div class="content">
                <!-- 快捷操作栏 S-->
                <section class="clearfix">
                    <!-- 面包绡 S-->
                    <div class="bread-wrap pull-left">
                        <a class="first" href="#this" title=""><strong><i class="iconfont icon-daohangshouye"></i>导航</strong></a>&gt;
                        <a href="#this" title="">教务管理</a>&gt;
                        <span class="last" href="#this" title="">校历管理</span>
                    </div>
                    <!-- 面包绡 S-->

                    <div class="toolList pull-right">
                        <ul class="clearfix">
                            <li>
                                <!-- 返回上一页 S-->
                                <a class="item-btn"
                                   href="javascript:history.go(-1);location.reload()">
                                   <i class="iconfont icon-icon"></i>返回</a>
                                <!-- 返回上一页 E-->
                            </li>
                        </ul>
                    </div>
                </section>
                <!-- 快捷操作栏 E-->
                <!-- form表单提交模块 S-->
                <div class="form-wrap" ms-important="evenForm">
                    <form id="form-validator" class="form-horizontal">
                        <div class="form-group form-group-sm">
                            <label class="col-sm-2 control-label">事件名称<span class="text-danger">*</span>：</label>
                            <div class="col-sm-6">
                                <input ms-duplex="@evenData.noticeTitle" name="headline" type="text" class="form-control" placeholder="请填写事件名称...">
                            </div>
                        </div>
                        <div class="form-group form-group-sm">
                            <label class="col-sm-2 control-label">事件时间<span class="text-danger">*</span>：</label>
                            <div class="col-sm-4">
                                <input ms-duplex="@evenData.evenTime" id="dateTime" name="dateTime" type="text" class="form-control" placeholder="开始时间 至 结束时间">
                            </div>
                        </div>
                        <div class="form-group form-group-sm">
                            <label class="col-sm-2 control-label">事件地址<span class="text-danger">*</span>：</label>
                            <div class="col-sm-5">
                                <input ms-duplex="@evenData.place" name="place" type="text" class="form-control" placeholder="请填写事件名称...">
                            </div>
                            <div class="col-sm-3">
                                <button :click="@addCommonFn('address','place')" type="button" class="btn btn-danger btn-xs">添加</button>
                                <button :click="@clearFn('place')" :if="@evenData.place !== ''" type="button" class="btn btn-default btn-xs">清空</button>
                            </div>
                        </div>
                        <br/>
                        <div class="form-group form-group-sm">
                            <label class="col-sm-2 control-label">发起人<span class="text-danger">*</span>：</label>
                            <div class="col-sm-3">
                                <input :attr="{value:@viewData.initiator,title:@viewData.initiator}" name="initiator" type="text" class="form-control" readonly placeholder="请选择发起人...">
                            </div>
                            <div class="col-sm-3">
                                <button :click="@addCommonFn('people','initiator')" type="button" class="btn btn-danger btn-xs">添加</button>
                                <button :click="@clearFn('initiator')" :if="@viewData.initiator.length" type="button" class="btn btn-default btn-xs">清空</button>
                            </div>
                        </div>
                        <div class="form-group form-group-sm">
                            <label class="col-sm-2 control-label">参与者(教师)：</label>
                            <div class="col-sm-3">
                                <input :attr="{value:@viewData.teachers,title:@viewData.teachers}" type="text" class="form-control" readonly placeholder="请添加参与教师...">
                            </div>
                            <div class="col-sm-3">
                                <button :click="@addCommonFn('people','teachers')" type="button" class="btn btn-danger btn-xs">添加</button>
                                <button :click="@clearFn('teachers')" :if="@viewData.teachers.length" type="button" class="btn btn-default btn-xs">清空</button>
                            </div>
                        </div>
                        <div class="form-group form-group-sm">
                            <label class="col-sm-2 control-label">参与者(学生)：</label>
                            <div class="col-sm-3">
                                <input :attr="{value:@viewData.students,title:@viewData.students}" type="text" class="form-control" readonly placeholder="请添加参与学生...">
                            </div>
                            <div class="col-sm-3">
                                <button :click="@addCommonFn('people','students')" type="button" class="btn btn-danger btn-xs">添加</button>
                                <button :click="@clearFn('students')" :if="@viewData.students.length" type="button" class="btn btn-default btn-xs">清空</button>
                            </div>
                        </div>
                        <br/>
                        <div class="form-group form-group-sm">
                            <label class="col-sm-2 control-label">事件性质<span class="text-danger">*</span>：</label>
                            <div class="col-sm-3">
                                <label class="radio-inline">
                                    <input ms-duplex="@evenData.evenProperty" name="sjxz" type="radio" value="1">个人事件
                                </label>
                                <label class="radio-inline">
                                    <input ms-duplex="@evenData.evenProperty" name="sjxz" type="radio" value="2">部门事件
                                </label>
                            </div>
                        </div>
                        <div class="form-group form-group-sm">
                            <label class="col-sm-2 control-label">事件等级<span class="text-danger">*</span>：</label>
                            <div class="col-sm-10">
                                <label ms-for="(index,el) in @opt.noticeLevel" class="radio-inline">
                                    <input ms-duplex="@evenData.noticeLevel" :attr="{value:el.value}" name="sjdj" type="radio">{{el.text}}
                                </label>
                            </div>
                        </div>
                        <div class="form-group form-group-sm">
                            <label class="col-sm-2 control-label">事件类型<span class="text-danger">*</span>：</label>
                            <div class="col-sm-10">
                                <label ms-for="(index,el) in @opt.evenStyle" class="radio-inline">
                                    <input ms-duplex="@evenData.evenStyle" :attr="{value:el.value}" name="sjlx" type="radio">{{el.text}}
                                </label>
                            </div>
                        </div>
                        <div class="form-group form-group-sm">
                            <label class="col-sm-2 control-label">事件级别<span class="text-danger">*</span>：</label>
                            <div class="col-sm-10">
                                <label ms-for="(index,el) in @opt.evenLevel" class="radio-inline">
                                    <input ms-duplex="@evenData.evenLevel" :attr="{value:el.value}" name="level" type="radio">{{el.text}}
                                </label>
                            </div>
                        </div>
                        <div class="form-group form-group-sm">
                            <label class="col-sm-2 control-label">事件体系<span class="text-danger">*</span>：</label>
                            <div class="col-sm-10">
                                <label ms-for="(index,el) in @opt.evenTypes" class="checkbox-inline">
                                    <input ms-duplex="@evenData.evenTypes" :attr="{value:el.value}" name="level" type="checkbox">{{el.text}}
                                </label>
                            </div>
                        </div>
                        <br/>
                        <div class="form-group form-group-sm">
                            <label class="col-sm-2 control-label">事件描述<span class="text-danger">*</span>：</label>
                            <div class="col-sm-9">
                                <textarea ms-duplex="@evenData.noticeContent" name="description" class="form-control" rows="8" placeholder="请描述事件基本信息"></textarea>
                            </div>
                        </div>
                        <br/>
                        <div class="form-group form-group-sm">
                            <label class="col-sm-2 control-label">事件附件：</label>
                            <div class="col-sm-9">
                                <div id="uploader">
                                    <p>你的浏览器没有Flash,Silverlight或HTML5支持。</p>
                                </div>
                            </div>
                        </div>
                        <br/>
                        <div class="form-group form-group-sm">
                            <div class="col-sm-offset-2 col-sm-9">
                                <button type="submit" class="btn btn-primary">提交</button>
                            </div>
                        </div>
                    </form>
                </div>
                <!-- form表单提交模块 E-->
                <footer class="text-center footer-info"><b>版权所有</b>：中关村三小&nbsp;&nbsp;&nbsp;&nbsp;<b>研发厂商</b>：京师励耘</footer>
            </div>
        </section>
    </div>
    <!-- 主内容区域 E-->
</div>
<script type="text/javascript">
avalon.define({
    $id:'main',
});
var vmForm = avalon.define({
    $id:'evenForm',
    evenData:{
        id:'',//事件ID;
        noticeTitle:'',//事件标题;
        evenTime:'',//事件时间;
        place:'',//事件地点;
        initiator:'',//发起人;
        teachers:[],//参与教师;
        students:[],//参与学生;
        evenProperty:'',//事件性质;
        noticeLevel:'',//事件等级;
        evenStyle:'',//事件类型;
        evenLevel:'',//事件级别;
        evenTypes:[],//事件体系;
        noticeContent:''//事件描述;
    },
    viewData:{//存储姓名;
        initiator:[],//发起人;
        teachers:[],//参与教师;
        students:[],//参与学生;
    },
    opt:{
        noticeLevel:[],//事件等级;
        evenStyle:[],//事件类型;
        evenLevel:[],//事件级别;
        evenTypes:[],//事件体系;
    },
    loadEvent:function(){
        $.ajax({
            url: '${ctx}/dict/all',
            type: 'GET',
            dataType: 'json',
            success:function(data){
                var _data = data.result;
                vmForm.opt.noticeLevel = _data.cims_event_level;//事件等级;
                vmForm.opt.evenStyle = _data.even_style;//事件类型;
                vmForm.opt.evenLevel = _data.even_level;//事件级别;
                vmForm.opt.evenTypes = _data.even_type;//事件体系;
            }
        })
    },
    addCommonFn:function(template,_type){
        var opt = {
            url:''
        }
        switch(template){
            case 'address':
                opt.url = 'addTemplate-address.html';
                break;
            case 'people':
                opt.url = 'addTemplate-people.html';
                break;
        };
        $.post(opt.url, function(str) {
            layer.open({
                type: 1,
                area:'755px',
                content: str,
                btn: ['确定','取消'],
                btnAlign :'c',
                scrollbar: false,
                success:function(){
                    switch(_type){
                        case 'place':
                            vmAddress.port = {
                                type:'place',
                                echoData:'',
                                treeUrl:'',
                                url:'${ctx}/tree/space/all'
                            };
                            vmAddress.loadListFn(vmAddress.firstActive);
                            break;
                        case 'initiator':
                            vmPeople.port = {
                                type:'initiator',
                                echoData:{
                                    userId:vmForm.$model.evenData.initiator,
                                    name:vmForm.$model.viewData.initiator
                                },
                                treeUrl:'${ctx}/tree/all',
                                url:'${ctx}/tree/teacher/search'
                            };
                            setting.data = {
                                key:{
                                    title:'text',
                                    name:'text'
                                },
                                simpleData: {
                                    enable: true,
                                    idKey: "id",
                                    pIdKey: "parentId",
                                    rootPId: 0
                                }
                            };
                            vmPeople.ztreeList();
                            vmPeople.dataListFn();
                            break;
                        case 'teachers':
                            vmPeople.port = {
                                type:'teachers',
                                echoData:{
                                    userId:vmForm.$model.evenData.teachers,
                                    name:vmForm.$model.viewData.teachers
                                },
                                treeUrl:'${ctx}/tree/all',
                                url:'${ctx}/tree/teacher/search'
                            };
                            setting.data = {
                                key:{
                                    title:'text',
                                    name:'text'
                                },
                                simpleData: {
                                    enable: true,
                                    idKey: "id",
                                    pIdKey: "parentId",
                                    rootPId: 0
                                }
                            };
                            vmPeople.ztreeList();
                            vmPeople.dataListFn();
                            break;
                        case 'students':
                            vmPeople.port = {
                                type:'students',
                                echoData:{
                                    userId:vmForm.$model.evenData.students,
                                    name:vmForm.$model.viewData.students
                                },
                                treeUrl:'${ctx}/tree/school',
                                url:'${ctx}/tree/student/search'
                            };
                            setting.data = {
                                key:{
                                    title:'nodeName',
                                    name:'nodeName',
                                    children: "subTree"
                                },
                                simpleData: {
                                    idKey: "id"
                                }
                            };
                            vmPeople.ztreeList();
                            vmPeople.dataListFn();
                            break;
                    }
                    avalon.scan(document.body);
                },
                yes:function(index,layero){
                    switch(_type){
                        case 'place':
                            vmForm.evenData.place = vmAddress.$model.port.echoData;
                            break;
                        case 'initiator':
                            vmForm.evenData.initiator = vmPeople.$model.port.echoData.userId;
                            vmForm.viewData.initiator = vmPeople.$model.port.echoData.name;
                        break;
                        case 'teachers':
                            vmForm.evenData.teachers = vmPeople.$model.port.echoData.userId;
                            vmForm.viewData.teachers = vmPeople.$model.port.echoData.name;
                        break;
                        case 'students':
                            vmForm.evenData.students = vmPeople.$model.port.echoData.userId;
                            vmForm.viewData.students = vmPeople.$model.port.echoData.name;
                        break;
                    }
                    layer.close(index);
                }
            });
        });
    },
    submitEventFn:function(){
        $.ajax({
            url: '${ctx}/notice/add',
            type: 'POST',
            data: this.$model.evenData,
            success:function(data){
                layer.msg("事件添加成功!",{time:500},function(){
                    window.location.reload()
                });
            },
            error:function(data){
                layer.msg(data.msg,function(){
                    window.location.reload()
                });
            }
        })
    },
    clearFn:function(item){
        switch(item){
            case 'place':
                this.evenData.place =''//事件地点;
                break;
            case 'initiator':
                this.evenData.initiator =[]//事件发起人;
                this.viewData.initiator =[]//事件发起人;
                break;
            case 'teachers':
                this.evenData.teachers = []//参与教师;
                this.viewData.teachers = []//参与教师;
                break;
            case 'students':
                this.evenData.students = []//参与学生;
                this.viewData.students = []//参与学生;
                break;
        }
    }
});
layui.use(['layer','laydate'], function(){
    var laydate = layui.laydate,
        layer = layui.layer;
    laydate.render({
        elem: '#dateTime',
        type: 'datetime',
        range: true,
        theme: 'grid',
        min:0,
        done: function(value, date, endDate){
            $('#form-validator').validator({
                fields:{
                    'dateTime':{
                        rule: 'required',//事件时间;
                        msg: {
                            required: '事件时间必填 !'
                        }
                    }
                }
            })
        }
    });
});
$(function(){
    vmForm.loadEvent();
    // Handle the case when form was submitted before uploading has finished
    $('#form-horizontal').submit(function(e) {
        // Files in queue upload them first
        if ($('#uploader').plupload('getFiles').length > 0) {

            // When all files are uploaded submit form
            $('#uploader').on('complete', function() {
                $('#form-horizontal')[0].submit();
            });

            $('#uploader').plupload('start');
        } else {
            alert("You must have at least one file in the queue.");
        }
        return false; // Keep the form from submitting
    });
})

/**随机生成UUID唯一标识码*/
vmForm.evenData.id = uuid();
function uuid() {
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 32; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    var uuid = s.join("");
    return uuid;
}
</script>
</body>
</html>