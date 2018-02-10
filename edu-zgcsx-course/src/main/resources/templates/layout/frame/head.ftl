<!-- 头部标签 -->
<#macro head title="首页">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>${title} - <@spring.message code="course.title"/></title>
<link rel="stylesheet" type="text/css" href="http://at.alicdn.com/t/font_458254_iuk6asprlhht1emi.css">
<!-- 公共重置CSS -->
<link rel="stylesheet" type="text/css" href="${ctx}/static/common/plug-in/layui-v2.1.7/layui/css/layui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/common/plug-in/zTree/css/zTreeStyle/zTreeStyle.css">
<link href="${ctx}/static/common/css/common.css" rel="stylesheet">
<!-- 样式美化CSS -->
<link href="${ctx}/static/common/css/main.css" rel="stylesheet">

<script src="${ctx}/webjars/jquery/1.11.3/jquery.min.js"></script>
<script src="${ctx}/static/common/js/common.js"></script>
<script src="${ctx}/js/avalon-2.2.8.js" type="text/javascript"></script>
<script src="${ctx}/static/common/js/navMenu-tpl.js" type="text/javascript"></script>
<script src="${ctx}/static/common/js/toolNavigation-tpl.js" type="text/javascript"></script>
<script src="${ctx}/static/common/js/toolSkin-tpl.js" type="text/javascript"></script>
<!-- */ Layui控件
     */ 注:文件先后顺序 S -->
<script type="text/javascript" src="${ctx}/static/common/plug-in/layui-v2.1.7/layui/layui.js"></script>
<!-- */ zTree控件
     */ 注:文件先后顺序 S -->
<script type="text/javascript" src="${ctx}/static/common/plug-in/zTree/js/jquery.ztree.all.min.js"></script>
<!-- */ 验证插件
     */ 注:文件先后顺序 S -->
<script type="text/javascript" src="${ctx}/static/common/plug-in/nice-validator-1.1.2/dist/jquery.validator.min.js?local=zh-CN"></script>
<script type="text/javascript" src="${ctx}/static/common/js/validator-init.js"></script>
<script src="${ctx}/static/common/js/teacherName-tpl.js" type="text/javascript"></script>
<script src="${ctx}/js/function.js" type="text/javascript"></script>

<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
<!--[if IE 8]><![endif]-->
<script type="text/javascript">
    var _loginOut = '${ctx}/logout'; //common.js中有使用
    <#--if(!'${onlineUser.id!}'){-->
        <#--location.href = _loginOut;-->
    <#--}-->
    <#if onlineUser.id?exists>
    var vmMain = avalon.define({
        $id: 'main',
        userInfo: {},//当前用户信息;
        commonUrl: '${commonUrl}/',//公共系统URL;
    <#--roleSize:'${user.roleSize!}',//当前用户角色个数;-->
        roleSize: '${onlineUser.roleSize}',//当前用户角色个数;
        navList: [],//系统导航数据;
        menuList: [],//导航菜单;
        sysNum: '8',//当前系统编号;
        sysPath: '${ctx}',//当前系统路径;
        menuPath: '${uri}',//当前系统页面菜单路径
        logoutPath: '${ctx}/logout',//系统退出;
        skinShow: false,//皮肤操作显示;
        skin: '',
        _data: {
            userId: '${onlineUser.id}',
            roleId: '${onlineUser.roleId}'
        },
        comonDataFn: function () {
            $.ajax({
                url: '${commonUrl}/api/pageInfo',
                type: 'POST',
                dataType: 'json',
                data: this.$model._data,
                success: function (data) {
                    data.result.helloInfo.roleName="${onlineUser.roleName}";
                    vmMain.userInfo = data.result.helloInfo;
                    vmMain.navList = data.result.sysGuid;
                    vmMain.skin = data.result.skin;
                    vmMain.menuList = data.result.menu;
                    <#if uri == "/" || uri == "">
                        location.href='${ctx}'+data.result.menu[0].subMenu[0].url;
                    </#if>
                }
            })
        },
        skinFn: function () {
            this.skinShow = !this.skinShow;
        },
        saveSkinFn: function (el) {
            var _data = {
                userId: this.$model._data.userId,
                skin: el.skinName
            };
            layer.confirm('<div class="text-center">你是否要切换皮肤?</div>', {btnAlign: 'c'}, function () {
                $.ajax({
                    url: "${commonUrl}/api/background/change",
                    type: 'POST',
                    dataType: 'json',
                    data: _data,
                    success: function (data) {
                        vmMain.skin = el.skinName;
                        layer.msg("皮肤切换成功!");
                        window.location.reload();
                    }
                })
            });
        },
        resize: function () {
            $(window).resize();
        }
    });
    vmMain.comonDataFn();
    avalon.config({debug: false});
    </#if>
</script>
<!-- */ 上传组件
     */ 注:文件先后顺序 S -->
<link rel="stylesheet" href="${ctx}/static/common/plug-in/plupload-2.3.6/jquery-1.8.9-ui.css" type="text/css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/common/plug-in/plupload-2.3.6/js/jquery.ui.plupload/css/jquery.ui.plupload.css">
<script type="text/javascript" src="${ctx}/static/common/plug-in/plupload-2.3.6/js/jquery-ui.js"></script>
<script type="text/javascript" src="${ctx}/static/common/plug-in/plupload-2.3.6/js/plupload.full.min.js"></script>
<script type="text/javascript" src="${ctx}/static/common/plug-in/plupload-2.3.6/js/jquery.ui.plupload/jquery.ui.plupload.min.js"></script>
<script type="text/javascript" src="${ctx}/static/common/plug-in/plupload-2.3.6/js/i18n/zh_CN.js"></script>
</#macro>
