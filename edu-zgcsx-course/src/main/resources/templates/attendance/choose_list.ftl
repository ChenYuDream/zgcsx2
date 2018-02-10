<#include "/layout/layout.ftl"/>
<!--开放课程考勤-->
<@page title="考勤列表" hasFooter=false hasFooter=false breadWraps=[{"name":{"2":"拓展课程","5":"开放课程"}[xxlb],"href":"javascript:void(0)"},{"name":"缺勤列表"}]>
<div :controller="attendanceChooseDetail" class="ms-controller">
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
        </tr>
        </tbody>
    </table>
    <!-- 课程详情 E-->
    <!-- table表单模块 S-->
    <div class="table-content">
        <table class="table">
            <thead>
            <tr>
                <th width="25%">课程名称</th>
                <th width="30%">课程日期</th>
                <th width="20%">课程开课时间</th>
                <th width="10%">缺课人数</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr :for="(index, el) in @data.records">
                <td :attr="{rowspan:@data.records.length}" :if="index == 0"> {{formatChooseCourseName(course.courseName, course.aliasName)}}</td>
                <td> 第{{el.workDay.weekOfTerm}}周, {{@dayOfWeek[el.workDay.dayOfWeek]}}</td>
                <td> {{el.courseTime +' '+ el.workDay.startTime}}</td>
                <td><a :click="showAttStudent(el.workDay.id)" style="color: red;">{{el.attendanceCount}}</a></td>
                <td><a class="btn btn-default btn-xs" :click="@savePage(el.workDay.id)">添加考勤</a></td>
            </tr>
            </tbody>
        </table>

    </div>

    <!-- 分页+全选模块 S-->
    <div class="clearfix pagesBox" :if="@data.pages > 1">
        <div id="pagination" class="pull-right"></div>
    </div>
    <!-- 分页+全选模块 E-->
</div>

<script type="text/javascript">
    var vmAttendanceChooseDetail = avalon.define({
        $id: 'attendanceChooseDetail',
        current: 1,
        size: 20,
        dayOfWeek: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
        week: -1,
        opt: {},
        course: {},
        data: {},
        showData: {},
        loadCourse: function () {
            $.ajax({
                url: "${ctx}/optional/detail",
                data: {id: this.opt.optionalCourseId},
                type: "post",
                success: function (data) {
                    vmAttendanceChooseDetail.course = data.result;
                }
            });
        },
        initWeek: function (callback) { //获取当前周
            if (vmAttendanceChooseDetail.$model.week === -1) {
                $.ajax({
                    url: "${ctx}/workDay/week",
                    type: 'POST',
                    dataType: 'json',
                    success: function (data) {
                        vmAttendanceChooseDetail.week = data;
                        callback && callback();
                    }
                });
            } else {
                callback && callback();
            }
        },
        showAttStudent: function (workDayId) { //缺勤
            var _show = {};
            _show.optionalCourseId = this.course.id;
            _show.workDayId = workDayId;
            vmAttendanceChooseDetail.showData = _show;
            msgFn.dlg({
                url: "${ctx}/studentAttendance/page/choose/student/list",
                title: "缺勤学生",
                width: "600px"
            });
        },
        savePage: function (workDayId) {
            window.location.href = "${ctx}/studentAttendance/page/choose/add?optionalCourseId=" + this.opt.optionalCourseId + "&workDayId=" + workDayId;
        },
        init: function () {
            vmAttendanceChooseDetail.initWeek(function () {
                $.ajax({
                    url: "${ctx}/studentAttendance/choose/student/attendance",
                    data: {
                        optionalCourseId: vmAttendanceChooseDetail.$model.opt.optionalCourseId,
                        dayOfWeek: vmAttendanceChooseDetail.$model.opt.dayOfWeek,
                        weekOfTerm: vmAttendanceChooseDetail.$model.week,
                        current: vmAttendanceChooseDetail.$model.current,
                        size: vmAttendanceChooseDetail.$model.size
                    },
                    type: 'POST',
                    dataType: 'json',
                    success: function (data) {
                        vmAttendanceChooseDetail.data = data.result;

                        mainVm.renderPage(vmAttendanceChooseDetail.$model.data, function (curr) {
                            vmAttendanceChooseDetail.current = curr;
                            vmAttendanceChooseDetail.init();
                        });
                    }
                });
            });
        }
    });
    vmAttendanceChooseDetail.opt = {
        optionalCourseId: "${data.optionalCourseId!''}",
        dayOfWeek: "${data.dayOfWeek!''}"
    };
    vmAttendanceChooseDetail.loadCourse();
    vmAttendanceChooseDetail.init();
</script>
</@page>