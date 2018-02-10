<!-- 我的课程弹出框 -->
<div id="testName" class="detailTable dialog-kcxq" ms-controller="teacherTimetableOwnDetail" class="ms-controller">
    <table>
        <tbody>
        <tr>
            <td class="text-center" rowspan="3">
                <img src="${ctx}/static/common/images/gonggongke.png" alt="">
            </td>
            <th width="15%">课程名称:</th>
            <td width="20%">{{data.courseName | empty}}</td>
            <th width="15%">上课地点:</th>
            <td width="20%">{{data.gradeName | empty}}{{data.clazzName | empty}}班</td>
        </tr>
        <tr>
            <th>教师:</th>
            <td class="text-break">{{data.teacherName | empty}}</td>
        </tr>
        <tr></tr>
        <tr>
            <td colspan="5">
                <p class="text-break"><b>课程简介：</b>
                    <span :if="data.description != undefined">{{data.description}}</span>
                    <span :if="data.description == undefined">该课程暂无简介！</span>
                </p>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="text-center" style="padding: 10px;">
        <button class="btn btn-default btn-sm" :click="showStudent()" style="margin: 0 5px">查看学生</button>
        <button class="btn btn-default btn-sm" :click="showResource()" style="margin: 0 5px">课程资源</button>
    </div>
</div>
<script type="text/javascript">
    var vmTeacherTimetableOwnDetail = avalon.define({
        $id:"teacherTimetableOwnDetail",
        data:{},
        showStudent : function(){ //显示学生
            window.location.href = "${ctx}/course/page/basic/student/list?clazzId="+this.data.clazzId+"&courseId="+this.data.courseId;
        },
        showResource : function () { //课程资源
            var _url = "&clazzId="+this.data.clazzId+"&gradeId="+this.data.gradeId+"&courseId="+this.data.courseId; //班级id,年级id,课程id
            window.location.href = "${ctx}/courseResource/page/resource/tab?tab=res"+_url;
        }
    });
    vmTeacherTimetableOwnDetail.data = vmTeacherTimetableOwn.$model.showData;
</script>
