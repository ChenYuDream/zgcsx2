<!-- 我的课表 弹出层 S-->
<div class="detailTable dialog-kcxq" :controller="courseDetailController">
    <table>
        <tbody>
        <tr>
            <td class="text-center" rowspan="3">
                <img width="180" height="110" :attr="{src:showImg(data.coverId, data.xxlb)}" alt="">
            </td>
            <th width="15%">课程名称:</th>
            <td width="20%">{{data.courseName+'('+data.aliasName+')'}}</td>
            <th width="15%">上课地点:</th>
            <td width="20%">{{formatSpace(data.space)}}</td>
        </tr>
        <tr>
            <th>课程开始:</th>
            <td>{{data.startTime}}</td>
            <th>课程结束:</th>
            <td>{{data.endTime}}</td>
        </tr>
        <tr>
            <th>教师:</th>
            <td colspan="3" class="text-break">{{formatArr(data.teachers,'name')}}</td>
        </tr>
        <tr>
            <td colspan="5">
                <p class="text-break"><b>课程简介：</b>{{data.description||'该课程暂无简介！'}}</p>
            </td>
        </tr>
        </tbody>
    </table>
    <div class="text-center" style="padding: 10px;">
        <button class="btn btn-default btn-sm" style="margin: 0 5px" :click="showStudent">查看学生</button>
        <button class="btn btn-default btn-sm" style="margin: 0 5px" :click="showAttendance">考勤情况</button>
        <button class="btn btn-default btn-sm" style="margin: 0 5px" :click="showResource">课程资源</button>
    </div>
</div>
<!-- 弹出层 E-->
<script type="text/javascript">
    var courseDetailVm = avalon.define({
        $id: 'courseDetailController',
        data: {},
        showImg: function (coverId, type) {
            if (coverId) {
                return mainVm.showFile(coverId);
            }
            if(type === "2" || type === 2){
                return mainVm.defaultImg.optional.show;
            }
            return mainVm.defaultImg.after.show;
        },
        showStudent: function () { //显示学生
            location.href = "${ctx}/optional/course_student?id=" + this.data.id;
        },
        showAttendance: function () { //显示考勤
            location.href = "${ctx}/studentAttendance/page/choose/list?optionalCourseId=" + this.data.id;
        },
        showResource: function () { //课程资源
            var _url = "&optionalCourseId=" + this.data.id + "&courseId=" + this.data.courseId;
            location.href = "${ctx}/courseResource/page/resource/tab?tab=res" + _url;
        },
        defaultImg:mainVm.$model.defaultImg,
        formatArr:mainVm.formatArr,
        formatSpace:mainVm.formatSpace
    });
</script>