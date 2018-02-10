<#include "/layout/layout.ftl"/>
<@page title="查看学生信息" hasFooter=false breadWraps=[{"name":{"2":"拓展课程","5":"开放课程"}[xxlb],"href":"javascript:void(0)"},{"name":"查看学生"}]>
<div :controller="courseStudentController">
    <!-- 课程详情 S-->
    <table class="detailTable">
        <tbody>
        <tr>
            <th width="10%">课程定义:</th>
            <td>
                {{course.courseName}}
            </td>
            <th width="10%">课程名称:</th>
            <td>
                {{course.aliasName}}
            </td>
            <th width="10%">人数:</th>
            <td>
                {{course.studentCount}}
            </td>
        </tr>
        </tbody>
    </table>
    <!-- 课程详情 E-->
    <!-- table表单模块 S-->
    <div class="table-content">
        <table class="table">
            <thead>
            <tr>
                <th width="25%">所属校区</p></th>
                <th>所属班级</th>
                <th width="20%">学生姓名</th>
                <th width="8%">学生性别</th>
            </tr>
            </thead>
            <tbody>
            <tr :for="(index,el) in @data.records">
                <td>{{el.campusName}}</p></td>
                <td>{{el.gradeName+el.clazzName+'班'}}</td>
                <td>{{el.name}}</td>
                <td>{{sexes[el.sex]}}</td>
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
        mainVm.initPage("${ctx}/optional/students", {
            id: "${id}",
            campusId: "",
            gradeId: "",
            clazzId: "",
            studentName: ""
        });
        mainVm.search();
    });
    var courseStudentVm = avalon.define({
        $id: "courseStudentController",
        course: {},
        loadCourse: function () {
            $.ajax({
                url: "${ctx}/optional/detail",
                data: {id: "${id}"},
                type: "post",
                success: function (data) {
                    courseStudentVm.course = data.result;
                }
            });
        }
    });
    courseStudentVm.loadCourse();
</script>
</@page>