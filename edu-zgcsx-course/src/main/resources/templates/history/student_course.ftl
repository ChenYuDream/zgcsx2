<#--学生选课列表-->
<#include "/layout/layout.ftl"/>
<@page title="学生选课列表" hasFooter=false breadWraps=[{"name":"历史记录","href":"javascript:void(0)"},{"name":"学生选课列表"}]>
<div :controller="studentCourseController">
    <!-- 查询条件 S-->
    <form class="form-inline">
        <div class="clearfix item-group">
            <ul class="clearfix pull-left">
                <li>
                    <span>学年学期：</span>
                    <select class="select" title="学年学期" :change="@changeXnxq($event)">
                        <option :for="el in @xnxqs" :attr="{value:@el.id,selected:@condition.xn==@el.xn&&@condition.xq==@el.xq}">
                            {{el.xn+'学年第'+el.xq+'学期'}}
                        </option>
                    </select>
                </li>
                <li>
                    <span>所属校区：</span>
                    <select id="campusId" name="campusId" class="select" :duplex="@condition.campusId" title="所属校区">
                        <option value="">全部</option>
                        <option :for="el in @campuses" :attr={value:@el.id}>{{el.name}}</option>
                    </select>
                </li>
            </ul>
        </div>
        <div class="clearfix item-group">
            <ul class="clearfix pull-left">
                <li>
                    <span>课程类型：</span>
                    <select id="xxlb" name="xxlb" class="select" :duplex="@condition.xxlb" title="课程类型">
                        <option value="">== 请选择 ==</option>
                        <option :for="(key,val) in @xxlbs" :attr="{value:@key}">{{@val}}</option>
                    </select>
                </li>
                <li>
                    <span>学生姓名：</span>
                    <input style="width: 115px;" id="studentName" name="studentName" class="input" type="text" placeholder="请输入学生姓名" maxlength="30"
                           :duplex="@condition.studentName">
                </li>
                <li>
                    <span>课程定义：</span>
                    <input style="width: 115px;" id="aliasName" name="aliasName" class="input" type="text" placeholder="请输入课程定义" maxlength="30" :duplex="@condition.courseName">
                </li>
                <li>
                    <span>课程名称：</span>
                    <input style="width: 115px;" id="aliasName" name="aliasName" class="input" type="text" placeholder="请输入课程名称" maxlength="30" :duplex="@condition.aliasName">
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
            </div>
        </div>
    </form>
    <!-- 查询条件 E-->
    <!-- table表单模块 S-->
    <div class="table-content">
        <table class="table">
            <thead>
            <tr>
                <th>所属校区</p></th>
                <th>年级</th>
                <th>学生姓名</th>
                <th>学生性别</th>
                <th>课程定义(课程名称)</th>
                <th>课程类型</th>
            </tr>
            </thead>
            <tbody>
            <tr :for="(index,el) in @data.records">
                <td>{{el.student.campusName}}</p></td>
                <td>{{el.gradeName | nvl}}</td>
                <td>{{el.student.name}}</td>
                <td>{{sexes[el.student.sex]}}</td>
                <td :attr="{title:el.optionalCourse.baseCourse.courseName+'('+el.optionalCourse.aliasName+')'}">
                    {{el.optionalCourse.baseCourse.courseName+'('+el.optionalCourse.aliasName+')' | truncate(20)}}
                </td>
                <td>{{xxlbs[el.optionalCourse.baseCourse.xxlb]}}</td>
            </tr>
            <tr :if="initialized&&!@data.records.length">
                <td colspan="6"><@spring.message code="course.page.empty_result"/></td>
            </tr>
            </tbody>
        </table>
    </div>
    <!-- table表单模块 E-->
    <!-- 分页+全选模块 S-->
    <div class="clearfix pagesBox">
        <div id="pagination" class="pull-right"></div>
    </div>
    <!-- 分页+全选模块 E-->
</div>
<script type="text/javascript">
    var studentCourseVm=avalon.define({
        $id:"studentCourseController",
        changeXnxq: function (e) {
            var xnxq = avalon.Array.findById(mainVm.$model.xnxqs, e.target.value);
            mainVm.condition.xn = xnxq.xn;
            mainVm.condition.xq = xnxq.xq;
        }
    });
    $(function () {
        mainVm.initPage("${ctx}/optional/history/student/list", {
            xn: '',
            xq: '',
            campusId: "",
            gradeId: "",
            clazzId: "",
            courseName: "",
            aliasName: "",
            studentName: "",
            xxlb: ""
        });
        mainVm.loadXnxqs();
        mainVm.loadXnxq(function () {
            condition.xn = mainVm.condition.xn = mainVm.$model.xnxq.xn;
            condition.xq = mainVm.condition.xq = mainVm.$model.xnxq.xq;
            mainVm.search();
        });
        mainVm.loadCampuses();
    });
</script>
</@page>