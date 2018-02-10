<#include "/layout/layout.ftl"/>
<@page title="学生选课列表" hasFooter=false breadWraps=[{"name":"拓展课程","href":"javascript:void(0)"},{"name":"学生选课列表"}]>
<div :controller="studentCourseController">
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
                <li :visible="condition.campusId">
                    <span>所属年级：</span>
                    <select id="gradeId" name="gradeId" class="select" :duplex="@condition.gradeId" title="所属年级">
                        <option value="">全部</option>
                        <option :for="el in @grades" :attr="{value:@el.id}">{{el.name}}</option>
                    </select>
                </li>
                <li :visible="condition.gradeId">
                    <span>所属班级：</span>
                    <select id="clazzId" name="clazzId" class="select" :duplex="@condition.clazzId" title="所属班级">
                        <option value="">全部</option>
                        <option :for="el in @clazzes" :attr="{value:@el.id}">{{el.name + '班'}}</option>
                    </select>
                </li>
                <li>
                    <span>是否选课：</span>
                    <select id="chosen" name="chosen" class="select" :duplex="@condition.chosen" title="课程类型">
                        <option value="">全部</option>
                        <option value="1">是</option>
                        <option value="0">否</option>
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
                    <input id="studentName" name="studentName" class="input" type="text" placeholder="请输入学生姓名" maxlength="30" :duplex="@condition.studentName">
                </li>
                <li>
                    <span>课程名称：</span>
                    <input id="aliasName" name="aliasName" class="input" type="text" placeholder="请输入课程名称" maxlength="30" :duplex="@condition.aliasName">
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
                    <i class="iconfont icon-quanbudaochu"></i>导出全部
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
                <th>所属班级</th>
                <th>学生姓名</th>
                <th>学生性别</th>
                <th>是否选课</th>
                <th>课程定义(课程名称)</th>
                <th>课程类型</th>
            </tr>
            </thead>
            <tbody>
            <tr :for="(index,el) in @data.records">
                <td>{{el.student.campusName}}</p></td>
                <td>{{el.student.gradeName+el.student.clazzName+'班'}}</td>
                <td>{{el.student.name}}</td>
                <td>{{sexes[el.student.sex]}}</td>
                <td>{{el.chosen?'是':'否'}}</td>
                <td :attr="{title:el.optionalCourse?(el.optionalCourse.courseName+'('+el.optionalCourse.aliasName+')'):''}">{{el.optionalCourse?(el.optionalCourse.courseName+'('+el.optionalCourse.aliasName+')'):'' | nvl | truncate(20)}}</td>
                <td>{{el.optionalCourse?xxlbs[el.optionalCourse.xxlb]:'' | nvl}}</td>
            </tr>
            <tr :if="initialized&&!@data.records.length">
                <td colspan="7"><@spring.message code="course.page.empty_result"/></td>
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
    $(function () {
        mainVm.initPage("${ctx}/optional/student/list", {
            campusId: "",
            gradeId: "",
            clazzId: "",
            aliasName: "",
            studentName: "",
            chosen: "",
            xxlb: ""
        });
        mainVm.loadCampuses();
        mainVm.search();
        mainVm.watchClazz();
    });
    var studentCourseVm=avalon.define({
        $id:'studentCourseController',
        export:function(){
            var condition = mainVm.$model.data.condition;
            location.href = '${ctx}/optional/export/student/list?'+$.param(condition);
        }
    });
</script>
</@page>