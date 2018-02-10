<#include "/layout/layout.ftl"/>
<@page title="我的课程" breadWraps=[{"name":"基础课程","href":"javascript:void(0)"},{"name":"我的课程"}]>
<div ms-controller="teacherTimetableOwn" class="ms-controller">
    <!-- table表单模块 S-->
    <div class="table-content">
        <table class="table">
            <thead>
            <tr>
                <th width="12%">课程名称</th>
                <th width="12%">课程层级</th>
                <th width="12%">课程类型</th>
                <th width="12%">上课地点</th>
                <th width="12%">满人数</th>
                <th width="12%">已选人数</th>
                <th width="12%">操作</th>
            </tr>
            </thead>
            <tbody>
            <tr :for="(index, el) in @data">
                <td>{{el.courseName}}</td>
                <td>{{el.kcdj}}</td>
                <td>必修</td>
                <td>{{el.gradeName}}{{el.clazzName}}班</td>
                <td>{{el.studentCount}}</td>
                <td>{{el.chooseStudentCount}}</td>
                <td><a class="btn btn-default btn-xs" :click="show(el)">详情</a></td>
            </tr>
            <tr :if="data.length == 0">
                <td colspan="7">暂无相关数据！</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>

<script type="text/javascript">
    var vmTeacherTimetableOwn = avalon.define({
        $id: 'teacherTimetableOwn',
        data   : [],
        showData : {},
        show : function (el) {
            vmTeacherTimetableOwn.showData = el;
            msgFn.dlg({
                url: "${ctx}/course/page/course/detail",
                title: "详情",
                width: "600px"
            });
        },
        init : function () {
            $.ajax({
                url: "${ctx}/teacher/basic/work",
                data : {
                    teacherId : "${onlineUser.id}"
                },
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    vmTeacherTimetableOwn.data = data.result;
                }
            });
        }
    });

    vmTeacherTimetableOwn.init();
</script>
</@page>