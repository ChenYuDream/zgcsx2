<#include "/layout/layout.ftl"/>
<!--添加基础课程考勤页面-->
<@page title="添加学生考勤" hasFooter=false breadWraps=[{"name":"课程管理","href":"javascript:void(0)"},{"name":"添加学生考勤"}]>
<div ms-controller="addAttendanct" class="ms-controller">
    <!-- 学生考勤 S-->
    <table class="detailTable">
        <tbody>
        <tr>
            <th width="10%">课程名称:</th>
            <td>
                {{pData.courseName}}
            </td>
            <th width="10%">主讲教师:</th>
            <td>
                {{pData.teacherName}}
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
        <tr>
            <th width="10%">缺勤学生:</th>
            <td colspan="3">
                <textarea rows="10" readonly class="form-control" style="border:1px solid #ddd;background: #fff" id="studentName"></textarea>
                <div>
                    <button :click="show" class="btn btn-default btn-xs" style="margin: 10px 5px;" type="button">添加</button>
                    <button :click="clear" class="btn btn-default btn-xs" style="margin: 10px 5px;" type="button">清空</button>
                </div>
            </td>
        </tr>
        </tbody>
    </table>
    <div class="text-center">
        <button :click="save" class="btn btn-primary" type="button">保存</button>
    </div>
    <!-- 学生考勤 E-->
</div>

<script type="text/javascript">
    var students = []; //选中学生

    var vmAddAttendance = avalon.define({
        $id: 'addAttendanct',
        clazzId : '',
        workDayId : '',
        week : -1,
        pData : {},
        studentIds : [],   //学生id
        studentNames : [], //学生姓名
        studentObj : [],
        clear : function(){
            students = [];
            vmAddAttendance.studentIds = [];
            vmAddAttendance.studentNames = [];
            vmAddAttendance.studentObj = [];
            $("#studentName").val("");
        },
        show : function(){
            students = this.$model.studentObj;
            msgFn.dlg({
                url: "${ctx}/studentAttendance/page/student/list",
                title: "详情",
                width: "600px"
            });
        },
        save : function(){ //保存考勤
            if(this.studentIds.length > 0){
                msgFn.load(function(end){
                    $.ajax({
                        url: "${ctx}/studentAttendance/save/attendance",
                        data : {studentIds : vmAddAttendance.$model.studentIds.join(), workDayId : vmAddAttendance.$model.workDayId, weekOfTerm : vmAddAttendance.$model.week},
                        type: 'POST',
                        dataType: 'json',
                        success: function (data) {
                            end();
                            msgFn.msg(data);
                            if(data.success){
                                window.history.go(-1);
                            }
                        }
                    });
                });
            }else{
                msgFn.msg(false, "请选择缺勤学生！");
            }
        },
        load : function(){ //加载列表公用信息
            $.ajax({
                url: "${ctx}/teacher/timetable/own",
                data: {clazzId: this.clazzId, workDayId: this.workDayId, teacherId : '${onlineUser.id}'},
                type: "post",
                success: function (data) {
                    vmAddAttendance.pData = data.result;
                }
            });
        },
        loadStudent : function(){ //加载缺勤学生
            $.ajax({
                url: "${ctx}/api/students/all",
                data : {
                    clazzId: this.clazzId,
                    current: this.current,
                    size: this.size
                },
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    vmStudent.data = data.result;

                    $(window).resize();
                    mainVm.renderPage(vmStudent.$model.data, function(curr){
                        vmAddAttendance.current = curr;
                        vmAddAttendance.init();
                    });
                }
            });
        }
    });

    vmAddAttendance.clazzId = "${clazzId!''}";
    vmAddAttendance.workDayId = "${workDayId!''}";
    vmAddAttendance.week = "${week!''}";
    vmAddAttendance.load();
</script>
</@page>