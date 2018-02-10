<#include "/layout/layout.ftl"/>
<!--基础课程考勤-->
<@page title="考勤列表" hasFooter=false breadWraps=[{"name":"课程管理","href":"javascript:void(0)"},{"name":"缺勤列表"}]>
<div ms-controller="attendanceDetail" class="ms-controller">
    <!-- 课程详情 S-->
    <table class="detailTable">
        <tbody>
        <tr>
            <th width="10%">课程名称:</th>
            <td>
                {{pData.courseName}}
            </td>
            <th width="10%">主讲教师:</th>
            <td>
                <span :for="(index, el) in pData.teachers">
                    <span :if="index == 0">{{el.name | empty}}</span>
                    <span :if="index > 0">,{{el.name | empty}}</span>
                </span>
            </td>
        </tr>
        <tr>
            <th width="10%">班级名称:</th>
            <td>
                {{pData.gradeName | empty}}{{pData.clazzName | empty}}班
            </td>
            <th width="10%">上课地点:</th>
            <td>
                {{pData.gradeName | empty}}{{pData.clazzName | empty}}班
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
                <th width="30%">上课时间</th>
                <th width="20%">上课日期</th>
                <th width="10%">缺课人数</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr :for="el in @data.records">
                <td>  {{pData.courseName}} </td>
                <td>  第{{el.workDay.weekOfTerm}}周, {{@dayOfWeek[el.workDay.dayOfWeek-1]}}, 第{{el.workDay.period}}节</td>
                <td>  {{el.courseTime | data("yyyy-MM-dd")}} </td>
                <td>  <a :click="showAttStudent(el.workDay.id)" style="color: red;">{{el.attendanceCount}}</a> </td>
                <td>  <a class="btn btn-default btn-xs" :click="@savePage(el)">添加考勤</a> </td>
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
    var vmAttendanceDetail = avalon.define({
        $id: 'attendanceDetail',
        current : 1,
        size : 20,
        sex : ["男", "女"],
        dayOfWeek : ["周一", "周二", "周三", "周四", "周五", "周六", "周日"],
        opt : {},    //接收页面参数
        data : {},   //列表数据
        pData : {},  //公用信息
        showData : {}, //跳转页面参数
        savePage : function(el){
            var _workDayId = el.workDay.id || this.opt.workDayId;
            window.location.href = "${ctx}/studentAttendance/page/basic/add?clazzId="+this.opt.clazzid+"&workDayId="+_workDayId+"&week="+el.workDay.weekOfTerm;
        },
        load : function( callback){ //加载列表公用信息
            $.ajax({
                url: "${ctx}/teacher/timetable/teachers/own",
                data: {clazzId: this.opt.clazzid, workDayId: this.opt.workDayId, teacherId : '${onlineUser.id}'},
                type: "post",
                success: function (data) {
                    vmAttendanceDetail.pData = data.result;
                    callback && callback();
                }
            });
        },
        showAttStudent : function (id){ //缺勤详情
            var _show = {};
            _show.clazzId = this.opt.clazzid;
            _show.workDayId = id;
            vmAttendanceDetail.showData = _show;
            msgFn.dlg({
                url: "${ctx}/studentAttendance/page/basic/student/list",
                title: "缺勤学生",
                width: "600px"
            });
        },
        init : function(){
            vmAttendanceDetail.load(function () {
                var _data = vmAttendanceDetail.$model.opt;
                _data.current = vmAttendanceDetail.$model.current;
                _data.size = vmAttendanceDetail.$model.size;
                _data.courseId = vmAttendanceDetail.$model.pData.courseId; //课程id
                _data.teacherId = "${onlineUser.id!''}";

                $.ajax({
                    url: "${ctx}/studentAttendance/basic/student/attendance",
                    data : _data,
                    type: 'POST',
                    dataType: 'json',
                    success: function (data) {
                        vmAttendanceDetail.data = data.result;

                        mainVm.renderPage(vmAttendanceDetail.$model.data, function(curr){
                            vmAttendanceDetail.current = curr;
                            vmAttendanceDetail.init();
                        });
                    }
                });
            });
        }
    });

    vmAttendanceDetail.opt = {
        workDayId : "${data.workDayId!''}",
        clazzid : "${data.clazzid!''}",
        weekOfTerm : "${data.weekOfTerm!''}",
        dayOfWeek : "${data.dayOfWeek!''}",
        period : "${data.period!''}",
        campusId : "${data.campusId!''}"
    };

    vmAttendanceDetail.init();
</script>
</@page>