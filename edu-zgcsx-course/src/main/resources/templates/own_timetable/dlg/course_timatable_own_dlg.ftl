<!-- 我的课表 弹出层 S-->
<div id="testName" class="detailTable dialog-kcxq" ms-controller="courseTimeTableDetrail" class="ms-controller">
    <table :if="@data.optionalCourse == undefined">
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
            <th>课程开始:</th>
            <td>{{data.workDay.startTime | empty}}</td>
            <th>课程结束:</th>
            <td>{{data.workDay.endTime | empty}}</td>
        </tr>
        <tr>
            <th>上课日期:</th>
            <td>{{time | empty}}</td>
            <th>教师:</th>
            <td class="text-break">
                <span :visible="data.teachers" :for="(index, el) in data.teachers">
                    <span :if="index == 0">{{el.name | empty}}</span>
                    <span :if="index > 0">,{{el.name | empty}}</span>
                </span>
                <span :visible="!data.teachers">
                    <span>{{data.teacherName | empty}}</span>
                </span>
            </td>
        </tr>
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

    <table :if="@data.optionalCourse != undefined">
        <tbody>
        <tr>
            <td class="text-center" rowspan="3">
                <img :attr="{src:showImg(data.optionalCourse.coverId, data.optionalCourse.courseType)}" alt="" width="170" height="100">
            </td>
            <th width="15%">课程名称:</th>
            <td width="20%">{{formatChooseCourseName(data.courseName, data.optionalCourse.aliasName) | empty}}</td>
            <th width="15%">上课地点:</th>
            <td width="20%">{{formatSpace(data.optionalCourse.space) | empty}}</td>
        </tr>
        <tr>
            <th>课程开始:</th>
            <td>{{data.optionalCourse.startTime | empty}}</td>
            <th>课程结束:</th>
            <td>{{data.optionalCourse.endTime | empty}}</td>
        </tr>
        <tr>
            <th>上课日期:</th>
            <td>{{time | empty}}</td>
            <th>教师:</th>
            <td class="text-break">
                <span :for="(index, el) in data.optionalCourse.teachers">
                    <span :if="index == 0">{{el.name | empty}}</span>
                    <span :if="index > 0">,{{el.name | empty}}</span>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="5">
                <p class="text-break"><b>课程简介：</b>{{@emptyPrint(data.optionalCourse.description)}}</p>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="text-center" style="padding: 10px;">
        <button class="btn btn-default btn-sm" :click="showStudent()" style="margin: 0 5px">查看学生</button>
        <button :if="@validate()" class="btn btn-default btn-sm" :click="showAttendance()" style="margin: 0 5px">考勤情况</button>
        <button class="btn btn-default btn-sm" :click="showResource()" style="margin: 0 5px">课程资源</button>
    </div>
</div>
<!-- 弹出层 E-->
<script type="text/javascript">
    var vmCourseimeTableDetail = avalon.define({
        $id: "courseTimeTableDetrail",
        data: {},
        time: '',
        week: -1,
        validate: function () { //验证时间，显示按钮
            var _startTime = vmCourseimeTableDetail.$model.data.workDay.startTime.split(":");
            var start = new Date(vmCourseimeTableDetail.$model.time);
            start.setHours(_startTime[0]);
            start.setMinutes(_startTime[1]);
            var date = new Date('${time!}');

            if (date >= start) { //系统时间 >= 课程开始时间
                return true;
            }
            return false;
        },
        showImg: function (coverId, type) {
            if (coverId) {
                return mainVm.showFile(coverId);
            }
            if (type === "2" || type === 2) {
                return mainVm.defaultImg.optional.show;
            }
            return mainVm.defaultImg.after.show;
        },
        showStudent: function () { //显示学生
            if (vmCourseimeTableDetail.$model.data.optionalCourse) {
                window.location.href = "${ctx}/optional/course_student?id=" + this.data.optionalCourse.id;
            } else {
                window.location.href = "${ctx}/course/page/basic/student/list?clazzId=" + this.data.clazzId + "&workDayId=" + this.data.workDay.id;
            }
        },
        showAttendance: function () { //显示考勤
            if (vmCourseimeTableDetail.$model.data.optionalCourse) {
                window.location.href = "${ctx}/studentAttendance/page/choose/list?optionalCourseId=" + this.data.optionalCourse.id + "&dayOfWeek=" + this.data.workDay.dayOfWeek;
            } else {
                //window.location.href = "${ctx}/studentAttendance/page/basic/list?workDayId="+this.data.workDay.id+"&clazzid="+this.data.clazzId+"&weekOfTerm="+this.week+"&dayOfWeek="+this.data.workDay.dayOfWeek+"&period="+this.data.workDay.period;
                window.location.href = "${ctx}/studentAttendance/page/basic/list?workDayId=" + this.data.workDay.id + "&clazzid=" + this.data.clazzId + "&weekOfTerm=" + this.week;
            }
        },
        showResource: function () { //课程资源
            var _url = "";
            if (vmCourseimeTableDetail.$model.data.optionalCourse) {
                //courseId：课程id 用于上传资源时写入(避免查询)
                _url += "&optionalCourseId=" + this.data.optionalCourse.id + "&courseId=" + this.data.optionalCourse.courseId;
            } else {
                _url += "&clazzId=" + this.data.clazzId + "&gradeId=" + this.data.gradeId + "&courseId=" + this.data.courseId; //班级id,年级id,课程id
            }
            window.location.href = "${ctx}/courseResource/page/resource/tab?tab=res" + _url;
        },
        emptyPrint: function (str) {
            return str || "该课程暂无简介！";
        },
        formatSpace: mainVm.formatSpace,
        formatChooseCourseName: mainVm.formatChooseCourseName
    });
    vmCourseimeTableDetail.data = vmCourseimeTable.$model.showData;
    vmCourseimeTableDetail.time = vmCourseimeTable.$model.showTime;
    vmCourseimeTableDetail.week = vmCourseimeTable.$model.week;
</script>