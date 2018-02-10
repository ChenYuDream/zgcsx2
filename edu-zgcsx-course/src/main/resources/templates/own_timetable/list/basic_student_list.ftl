<!--查看学生页面-->
<#include "/layout/layout.ftl"/>
<@page title="查看学生" hasFooter=false breadWraps=[{"name":"查看学生"}]>
<div :controller="showStudent">
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
                <span :if="pData.teachers != undefined" :for="(index, el) in pData.teachers">
                    <span :if="index == 0">{{el.name | empty}}</span>
                    <span :if="index > 0">,{{el.name | empty}}</span>
                </span>
                <span :if="pData.teachers == undefined">
                    {{pData.teacherName}}
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
        <tr>
            <th width="10%">上课人数:</th>
            <td>
                {{pData.studentCount}}
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
                <th width="35%">学生姓名</th>
                <th width="30%">学生性别</th>
                <th>学号</th>
            </tr>
            </thead>
            <tbody>
            <tr :for="(index,el) in @data.records">
                <td>{{el.name}}</td>
                <td>{{sexes[el.sex]}}</td>
                <td>{{el.code}}</td>
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
    var vmShowStudent = avalon.define({
        $id: "showStudent",
        current : 1,
        size : 20,
        pData: {},
        data : {},
        opt : {},
        load : function(){ //加载列表公用信息
            var _url;
            if(this.opt.teacherId === "undefined"){
                vmShowStudent.opt.teacherId = "";
            }
            if(this.opt.workDayId){//当workDayId不为空时查询所有教师，为空时查询vmShowStudent.opt.teacherId
                _url = "${ctx}/teacher/timetable/teachers/own";
            }else{
                _url = "${ctx}/teacher/timetable/own";
            }
            $.ajax({
                url: _url,
                data: this.opt,
                type: "post",
                success: function (data) {
                    vmShowStudent.pData = data.result;
                }
            });
        },
        init : function(){
            $.ajax({
                url: "${ctx}/api/students/all",
                data : {
                    clazzId: "${clazzId}",
                    current: this.current,
                    size: this.size
                },
                type: "post",
                success: function (data) {
                    vmShowStudent.data = data.result;
                    mainVm.renderPage(vmShowStudent.$model.data, function(curr){
                        vmShowStudent.current = curr;
                        vmShowStudent.init();
                    });
                }
            });
        }
    });
    vmShowStudent.opt = {
        clazzId : "${clazzId!''}",
        workDayId : "${workDayId!''}",
        courseId : "${courseId!''}",
        teacherId : "${teacherId!onlineUser.id}"
    };
    vmShowStudent.load();
    vmShowStudent.init();
</script>
</@page>