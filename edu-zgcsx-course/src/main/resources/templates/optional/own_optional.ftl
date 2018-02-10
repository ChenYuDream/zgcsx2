<#include "/layout/layout.ftl"/>
<#assign prefix = {"2":"optional","5":"after"}[xxlb]/>
<#assign prefixName = {"own":"我的","all":""}[showType]/>
<#assign suffixName = {"2":"选课列表","5":"课后一小时列表"}[xxlb]/>
<#assign modeName = {"2":"拓展课程","5":"开放课程"}[xxlb]/>
<#assign pageParam={"title":prefixName+suffixName,"breadWraps":[{"name":modeName,"href":"javascript:void(0)"},{"name":prefixName+suffixName}]}/>
<@page title=pageParam.title hasFooter=false breadWraps=pageParam.breadWraps>
<div :controller="optionalController">
    <!-- 查询条件 S-->
    <form class="form-inline">
        <div class="clearfix item-group">
            <ul class="clearfix pull-left">
                <li>
                    <span>所属校区：</span>
                    <select id="campusId" name="campusId" class="select" :duplex="@condition.campusId" title="所属校区">
                        <option value="">全部</option>
                        <option :for="el in @campuses" :attr={value:@el.id}>{{el.name}}</option>
                    </select>
                </li>
                <li>
                    <span>课程名称：</span>
                    <input id="aliasName" name="aliasName" class="input" type="text" placeholder="请输入课程名称" :duplex="@condition.aliasName" style="width: 160px;" maxlength="30">
                </li>
                <li>
                    <span>课程状态：</span>
                    <select id="courseStatus" name="courseStatus" class="select" :duplex="@condition.courseStatus" title="课程状态">
                        <option value="">全部</option>
                        <option :for="(key,val) in @courseStatuses" :attr="{value:@key}">{{@val}}</option>
                    </select>
                </li>
                <li>
                    <span>课程类别：</span>
                    <select id="kclb" name="kclb" style="width: 150px;" class="select" :duplex="@condition.kclb" title="课程类别">
                        <option value="">全部</option>
                        <option :for="el in @codeData.courseCategories" :attr={value:@el.itemValue}>{{el.itemText}}
                        </option>
                    </select>
                </li>
            </ul>
        </div>
        <hr/>
        <div class="clearfix item-group">
            <div class="toolList text-center">
                <a :click="@search" class="item-btn" href="javascript:void(0);">
                    <i class="iconfont icon-chazhao"></i>查找
                </a>
                <a :click="@clear" class="item-btn" href="javascript:void(0);">
                    <i class="iconfont icon-qingkong"></i>清空
                </a>
                <a :click="@export" class="item-btn" href="javascript:void(0);">
                    <i class="iconfont icon-quanbudaochu"></i>导出
                </a>
            </div>
        </div>
    </form>
    <!-- 查询条件 E-->
    <!-- 选课模块 S-->
    <div class="xkList-Wrap">
        <div class="media" :for="(index,el) in @data.records">
            <div class="media-left">
                <img class="media-object" :attr="{src:@showFile(@el)}" width="170" height="112" alt="选课图型...">
            </div>
            <div class="media-body">
                <h5 class="media-heading">课程定义：<span class="text-muted">{{el.courseName + ' ( ' + courseStatuses[el.courseStatus] + ' )'}}</span></h5>
                <h5 class="media-heading">课程名称：<span class="text-muted">{{el.aliasName}}</span></h5>
                <h5 class="media-heading">课程时间：<span class="text-muted">{{el.startTime + '至' + el.endTime}}</span></h5>
                <h5 class="media-heading">课程班级：<span class="text-danger" :attr="{title:formatClazzes(el.clazzes)}">{{formatClazzes(el.clazzes) | truncate(40)}}</span></h5>
            </div>
            <div class="media-right">
                <#if showType == "all">
                    <a :if="el.courseStatus == '1'" class="btn btn-primary btn-xs" href="javascript:void(0)" title="发布" :click="publish(@el)">发布</a>
                    <a :if="el.courseStatus == '1'" class="btn btn-default btn-xs" href="javascript:void(0)" title="修改" :click="edit(@el)">修改</a>
                </#if>
                <a :if="el.courseStatus == '2'" class="btn btn-primary btn-xs" href="javascript:void(0)" title="操作" :click="operate(@el)">操作</a>
                <#if showType == "all">
                    <a :if="el.courseStatus == '1'" class="btn btn-default btn-xs" href="javascript:void(0)" title="删除" :click="del(@el)">删除</a>
                </#if>
                <a class="btn btn-default btn-xs" href="javascript:void(0)" title="详情" :click="detail(@el)">详情</a>
            </div>
        </div>
    </div>
    <!-- 选课模块 E-->
    <!-- 分页+全选模块 S-->
    <div class="clearfix pagesBox">
        <div id="pagination" class="pull-right"></div>
    </div>
    <!-- 分页+全选模块 E-->
</div>
<script type="text/javascript">
    $(function () {
        mainVm.initPage("${ctx}/optional/optional_list", {
            campusId: "",
            <#if showType=="own">
            teacherId: "${onlineUser.id}",
            </#if>
            aliasName: "",
            courseStatus: "",
            kclb: "",
            xxlb: "${xxlb}"
        });
        mainVm.loadCampuses();
        mainVm.codeData.loadCourseCategories();
        mainVm.search();
        mainVm.toolBtnList.push({name: '新增', href: '${ctx}/${prefix}/${showType}_${prefix}/add', icon: 'xinzeng'});
    });
    var optionalVm = avalon.define({
        $id: "optionalController",
        publish: function (el) {
            this.check(el, function (course) {
                if (course.courseStatus === '2') {
                    return msgFn.msg(false, '该课程已发布！');
                }
                msgFn.confirm("发布课程", "确定发布吗？", function () {
                    $.post('${ctx}/optional/publish', {id: el.id}, function (data) {
                        msgFn.msg(data);
                        if (data.success) {
                            el.courseStatus = '2';
                        }
                    });
                });
            });
        },
        edit: function (el) {
            this.check(el, function (course) {
                if (course.courseStatus === '2') {
                    return msgFn.msg(false, '该课程已发布！');
                }
                location.href = "${ctx}/${prefix}/${showType}_${prefix}/edit?id=" + el.id;
            });
        },
        detail: function (el) {
            location.href = "${ctx}/${prefix}/detail?id=" + el.id;
        },
        del: function (el) {
            this.check(el, function (course) {
                if (course.courseStatus === '2') {
                    return msgFn.msg(false, '该课程已发布！');
                }
                msgFn.confirm("删除课程", "确定删除吗？", function () {
                    $.post('${ctx}/optional/delete', {id: el.id}, function (data) {
                        msgFn.msg(data);
                        mainVm.loadData();
                    });
                });
            });
        },
        operate: function (el) {
            msgFn.dlg({
                title: '课程详情',
                url: '${ctx}/optional/course_detail',
                width: '600px',
                success: function (end, close) {
                    end();
                    courseDetailVm.data = el;
                }
            });
        },
        check: function (el, callback) {
            $.post('${ctx}/optional/detail', {id: el.id}, function (data) {
                if (!data.result) {
                    mainVm.loadData();
                    return msgFn.msg(false, '该课程不存在！');
                }
                callback(data.result);
            });
        },
        export: function () {
            var condition = $.param(mainVm.$model.data.condition);
            location.href = "${ctx}/optional/export/" + encodeURI("${prefixName+suffixName}") + "?" + condition;
        },
        showFile: function (el) {
            if (el.coverId) {
                return mainVm.showFile(el.coverId);
            }
            return mainVm.defaultImg["${prefix}"].show;
        }
    });
</script>
</@page>