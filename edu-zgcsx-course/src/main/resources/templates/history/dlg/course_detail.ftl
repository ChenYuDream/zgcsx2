<!-- 我的课表 弹出层 S-->
<div id="testName" class="detailTable dialog-kcxq" :controller="courseDetailController">
    <table>
        <tbody>
        <tr>
            <td class="text-center" rowspan="3">
                <img :attr="{src:@data.optionalCourse?showImg(data.optionalCourse.coverId, data.optionalCourse.courseType):'${ctx}/static/common/images/gonggongke.png'}" alt=""
                     width="170" height="100">
            </td>
            <th width="15%">课程名称:</th>
            <td width="20%">{{formatCourseName(data)}}</td>
            <th width="15%">上课地点:</th>
            <td width="20%">{{formatSpace(data)}}</td>
        </tr>
        <tr>
            <th>课程开始:</th>
            <td>{{(data.optionalCourse?data.optionalCourse:data.workDay).startTime | empty}}</td>
            <th>课程结束:</th>
            <td>{{(data.optionalCourse?data.optionalCourse:data.workDay).endTime | empty}}</td>
        </tr>
        <tr>
            <th>上课日期:</th>
            <td>{{data.workDay.date}}</td>
            <th>教师:</th>
            <td class="text-break">
                {{formatTeachers(data.optionalCourse?data.optionalCourse.teachers:data.teachers)}}
            </td>
        </tr>
        <tr>
            <td colspan="5">
                <p class="text-break"><b>课程简介：</b>{{(data.optionalCourse?data.optionalCourse.description:data.description)||'该课程暂无简介！'}}</p>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<!-- 弹出层 E-->
<script type="text/javascript">
    var courseDetailVm;
    avalon.ready(function () {
        courseDetailVm = avalon.define({
            $id: "courseDetailController",
            data: {},
            init: function (data) {
                this.data = data;
            },
            formatCourseName: function (el) {
                if (el.optionalCourse) {
                    return mainVm.formatChooseCourseName(el.courseName, el.optionalCourse.aliasName);
                }
                return el.courseName;
            },
            formatSpace: function (el) {
                if (el.optionalCourse) {
                    return mainVm.formatSpace(el.optionalCourse.space);
                }
                return el.gradeName + el.clazzName + '班';
            },
            formatTeachers:function(teachers){
                return teachers.map(function(el){return el.name}).join();
            },
            showImg: function (coverId, type) {
                if (coverId) {
                    return mainVm.showFile(coverId);
                }
                if(type === "2" || type === 2){
                    return mainVm.defaultImg.optional.show;
                }
                return mainVm.defaultImg.after.show;
            }
        });
    });
</script>