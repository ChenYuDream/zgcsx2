<#include "/layout/layout.ftl"/>
<@page title="我的课表" breadWraps=[{"name":"课程管理","href":"javascript:void(0)"},{"name":"我的课表"}]>
<!-- 我的课表 S-->
<div ms-controller="courseTimeTable" class="ms-controller wdkb-wrap">
    <div class="title-wrap clearfix">
        <div class="clearfix pull-right">
            <button class="btn btn-primary btn-xs" type="button" :click="last()">上一周</button>
            <button class="btn btn-danger btn-xs disabled" type="button">{{@week}}周</button>
            <button class="btn btn-primary btn-xs" type="button" :click="next()">下一周</button>
        </div>
        <ul>
            <li class="jck">基础课</li>
            <li class="tzk">拓展课</li>
            <li class="kfk">开放课</li>
        </ul>
    </div>
    <div class="scheduleBox">
        <div class="scheduleItem">
            <ul class="clearfix">
                <li id="week__" class="js-week"></li>
                <#list allWeeks as week>
                    <li id="week_${week.code}" class="js-week"></li>
                </#list>
            </ul>
        </div>
        <table class="tableWeek">
            <thead>
            <tr style="height: 45px;">
                <th width="9%">时间\星期</th>
                <#list allWeeks as week>
                    <th width="13%">${week.fullText}<h6>{{@times[${week_index}]}}</h6></th>
                </#list>
            </tr>
            </thead>
            <tbody>
            <tr :for="(index, el) in @defaults">
                <th :if="index % 2 == 0" rowspan="2" ms-html="el"></th>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<!-- 我的课表 E-->

<script type="text/javascript">
    var vmCourseimeTable = avalon.define({
        $id: 'courseTimeTable',
        defaults: [
            "07:00<p>~</p>08:00", "",
            "08:00<p>~</p>09:00", "",
            "09:00<p>~</p>10:00", "",
            "10:00<p>~</p>11:00", "",
            "11:00<p>~</p>12:00", "",
            "12:00<p>~</p>13:00", "",
            "13:00<p>~</p>14:00", "",
            "14:00<p>~</p>15:00", "",
            "15:00<p>~</p>16:00", "",
            "16:00<p>~</p>17:00", "",
            "17:00<p>~</p>18:00", "",
            "18:00<p>~</p>19:00", "",
            "19:00<p>~</p>20:00", "",
            "20:00<p>~</p>21:00", ""
        ],
        week: -1, //当前周
        weekCount: -1, //学期总周数
        showData: {}, //保存弹出层数据
        showTime: '', //保存弹出层对应的时间
        times: [],    //记录当前周的日期
        initWeekCount: function () {
            $.ajax({
                url: "${ctx}/workDay/week/count",
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    vmCourseimeTable.weekCount = data;
                    vmCourseimeTable.initWeek();
                }
            });
        },
        initWeek: function () { //获取当前周
            $.ajax({
                url: "${ctx}/workDay/week",
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    if(data*1 > vmCourseimeTable.$model.weekCount){
                        vmCourseimeTable.week = vmCourseimeTable.$model.weekCount;
                    } else if (data*1 < 1){
                        vmCourseimeTable.week = 1;
                    } else {
                        vmCourseimeTable.week = data;
                    }

                    vmCourseimeTable.init();
                }
            });
        },
        initDate: function () { //得到当前周的日期
            $.ajax({
                url: "${ctx}/workDay/date",
                data: {weekOfTerm: vmCourseimeTable.$model.week},
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    vmCourseimeTable.times = data;
                }
            });
        },
        last: function () { //上一周
            if (vmCourseimeTable.$model.week > 1) {
                vmCourseimeTable.week = vmCourseimeTable.$model.week - 1;
                vmCourseimeTable.init();
            }
        },
        next: function () { //下一周
            if (vmCourseimeTable.$model.week < vmCourseimeTable.$model.weekCount) {
                vmCourseimeTable.week = vmCourseimeTable.$model.week + 1;
                vmCourseimeTable.init();
            }
        },
        init : function(){
            $(".js-week").html(""); //清空数据

            vmCourseimeTable.initDate();

            //加载基础层数据
            $.ajax({
                url: "${ctx}/teacher/basic/timetable/teachers",
                data: {teacherId: "${onlineUser.id}"},
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    var list = data.result;
                    for (var i = 0; i < list.length; i++) {
                        var listi = list[i];
                        var _top = getTop(listi.workDay.startTime); //计算显示高度;
                        var _height = getHeight(listi.workDay.startTime, listi.workDay.endTime); //计算面板高度
                        var showTitle = listi.workDay.startTime + "~" + listi.workDay.endTime + "\n" + listi.gradeName + listi.clazzName + "班" + "\n" + listi.courseName;
                        var showHtml = "<a style='top:" + _top + "px;height:" + _height + "px' class='item jck' href='javascript:void(0);' onclick='show(" + JSON.stringify(listi) + ")' title='" + showTitle + "'>" +
                                "<h5 class='text-overflow'><b>" + listi.courseName + "</b></h5>" +
                                "<h6 class='text-overflow'>" + listi.gradeName + listi.clazzName + "班" + "</h6>" +
                                "</a>";
                        $("#week_" + listi.workDay.dayOfWeek).append(showHtml);
                    }
                }
            });
            //加载拓展和开放层数据
            $.ajax({
                url: "${ctx}/teacher/choose/timetable",
                data: {teacherId: "${onlineUser.id}", week: vmCourseimeTable.$model.week},
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    var list = data.result;
                    for (var i = 0; i < list.length; i++) {
                        var listi = list[i];
                        /*var teachers = new Array;
                        for(var j=0; j<listi.optionalCourse.teachers.length; j++){
                            teachers[j] = listi.optionalCourse.teachers[j].name;
                        }*/

                        var _class = "";
                        if (listi.optionalCourse.courseType === "2") {
                            _class = "tzk";
                        } else {
                            _class = "kfk";
                        }
                        var _top = getTop(listi.workDay.startTime); //计算显示高度;
                        var _height = getHeight(listi.workDay.startTime, listi.workDay.endTime); //计算面板高度
                        var showTitle = listi.workDay.startTime + "~" + listi.workDay.endTime + "\n" + mainVm.formatSpace(listi.optionalCourse.space) + "\n" + mainVm.formatChooseCourseName(listi.courseName, listi.optionalCourse.aliasName);
                        var showHtml = "<a style='top:" + _top + "px;height:" + _height + "px' class='item " + _class + "' href='javascript:void(0);' onclick='show(" + JSON.stringify(listi) + ")' title='" + showTitle + "'>" +
                                "<h5 class='text-overflow'><b>" + mainVm.formatChooseCourseName(listi.courseName, listi.optionalCourse.aliasName) + "</b></h5>" +
                                "<h6 class='text-overflow'>" + mainVm.formatSpace(listi.optionalCourse.space) + "</h6>" +
                                "</a>";
                        $("#week_" + listi.workDay.dayOfWeek).append(showHtml);
                    }
                }
            });
        }
    });

    vmCourseimeTable.initWeekCount();

    function show(el) {
        vmCourseimeTable.showData = el;
        vmCourseimeTable.showTime = mainVm.getDate(vmCourseimeTable.week,el.workDay.dayOfWeek);
                // vmCourseimeTable.$model.times[el.workDay.dayOfWeek - 1];
        msgFn.dlg({
            url: "${ctx}/course/page/course/timetable/own/dlg",
            title: "详情",
            width: "600px"
        });
    }

    //计算显示高度
    var _height = 80; //1小时的高度
    var _course = 7;  //课程开始时间
    function getTop(time) {
        if (time) {
            var t = time.split(":");
            var hour = t[0] * 1;
            var minute = t[1] * 1;
            return Math.floor((hour - _course) * _height + (minute / 60 * _height) + 45);
        }
        return 0;
    }

    //计算面板高度
    function getHeight(startTime, endTime) {
        var _startTime = startTime.split(":");
        var _endTime = endTime.split(":");
        var s = new Date(2000, 1, 1, _startTime[0], _startTime[1], 0);
        var e = new Date(2000, 1, 1, _endTime[0], _endTime[1], 0);
        var minutes = (e.getTime() - s.getTime()) / (1000 * 60);
        return Math.floor(minutes / 60 * _height);
    }
</script>
</@page>