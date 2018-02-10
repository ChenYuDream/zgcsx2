<#--教师课表-->
<#include "/layout/layout.ftl"/>
<@page title="教师课表" breadWraps=[{"name":"历史记录","href":"javascript:void(0)"},{"name":"教师课表"}]>
<!-- 我的课表 S-->
<div :controller="courseTimeTable" class="ms-controller wdkb-wrap">
    <!-- 查询条件 S-->
    <form class="form-inline">
        <div class="clearfix item-group">
            <ul class="clearfix pull-left">
                <li>
                    <span>学年学期：</span>
                    <select class="select" title="学年学期" :change="@changeXnxq($event)">
                        <option :for="el in @xnxqs" :attr="{value:@el.id,selected:@xnxq.id == @el.id}">
                            {{el.xn+'学年第'+el.xq+'学期'}}
                        </option>
                    </select>
                </li>
                <li>
                    <input class="form-control input-sm pull-left" style="width:65%;" type="text"
                           :attr="{value:@data.teacher.name,title:@data.teacher.name}" placeholder="请选择任课教师..." disabled>
                    <button class="btn btn-danger btn-sm" type="button" :click="@selectTeacher">选择</button>
                </li>
                <li>
                    <a :click="@search" class="item-btn" href="javascript:void(0);">
                        <i class="iconfont icon-chazhao"></i>查找
                    </a>
                </li>
            </ul>
        </div>
    </form>
    <!-- 查询条件 E-->
    <div class="title-wrap clearfix">
        <div class="clearfix pull-right">
            <button class="btn btn-primary btn-xs" type="button" :click="@changeWeek(--@data.weekOfTerm)" :attr="{disabled:@data.weekOfTerm<=1}">上一周</button>
            <button class="btn btn-danger btn-xs disabled" type="button">{{data.weekOfTerm}}周</button>
            <button class="btn btn-primary btn-xs" type="button" :click="@changeWeek(++@data.weekOfTerm)" :attr="{disabled:@data.weekOfTerm>=@xnxq.weekCount}">下一周</button>
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
                <li></li>
                <li :for="(index,time) in @data.times">
                    <a class='item'
                       href='javascript:void(0);'
                       :for="el in @data[@index.toString()]"
                       :css="{top:(45+getHours('07:00',el.workDay.startTime)*constant.height)+'px',height: getHours(el.workDay.startTime,el.workDay.endTime)*constant.height+'px'}"
                       :class="@classes[el.optionalCourse?el.optionalCourse.courseType:'']"
                       :click='@showDetail(@el)'
                       :attr="{title:@formatTitle(@el)}">
                        <h5 class='text-overflow'><b>{{formatCourseName(el)}}</b></h5>
                        <h6 class='text-overflow'>{{formatSpace(el)}}</h6>
                    </a>
                </li>
            </ul>
        </div>
        <table class="tableWeek">
            <thead>
            <tr style="height: 45px;">
                <th width="9%">时间\星期</th>
                <th width="13%" :for="el in @data.times">{{el | date('EEEE')}}<h6>{{el}}</h6></th>
            </tr>
            </thead>
            <tbody>
            <!--ms-for: el in @hours-->
            <tr>
                <th rowspan="2">{{formatHour(el)}}<p>~</p>{{formatHour(el+1)}}</th>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            <!--ms-for-end:-->
            </tbody>
        </table>
    </div>
</div>
<!-- 我的课表 E-->

<script type="text/javascript">
    $(function () {
        mainVm.loadXnxqs();
        mainVm.loadXnxq(function () {
            vmCourseimeTable.init();
        });
    });
    var vmCourseimeTable = avalon.define({
        $id: 'courseTimeTable',
        data: {
            weekOfTerm: 1,//当前第几周
            teacher: {//当前选择教师
                id: "${onlineUser.id}",
                name: "${onlineUser.userName!}"
            },
            <#list allWeeks as week>
                '${week.code}': [],
            </#list>
            times: []//当前周列表
        },
        classes: {
            '': 'jck',//基础
            '2': 'tzk',//选修
            '5': 'kfk'//课后一小时
        },
        constant: {
            height: 80//每小时高度（px）
        },
        hours: [7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20],
        getHours: function (start, end) {//计算时差（HH:mm）
            return dateUtils.getHours(start, end);
        },
        formatHour: function (n) {
            return numberUtils.PrefixInteger(n, 2) + ":00";
        },
        loadDate: function () { //得到当前周的日期
            $.post("${ctx}/workDay/date", {
                weekOfTerm: vmCourseimeTable.$model.data.weekOfTerm,
                id: mainVm.$model.xnxq.id
            }, function (data) {
                vmCourseimeTable.data.times = data;
            });
        },
        loadBasic: function () {
            $.post("${ctx}/teacher/basic/timetable/teachers", {
                teacherId: vmCourseimeTable.data.teacher.id,
                "workDay.xnxq.xn": mainVm.$model.xnxq.xn,
                "workDay.xnxq.xq": mainVm.$model.xnxq.xq
            }, function (data) {
                var list = data.result;
                for (var i = 0; i < list.length; i++) {
                    list[i].workDay.date = mainVm.getDate(vmCourseimeTable.$model.data.weekOfTerm, list[i].workDay.dayOfWeek);
                    vmCourseimeTable.data[list[i].workDay.dayOfWeek.toString()].push(list[i]);
                }
                vmCourseimeTable.loadOptional();
            });
        },
        loadOptional: function () {
            $.post("${ctx}/teacher/choose/timetable", {
                teacherId: vmCourseimeTable.data.teacher.id,
                week: vmCourseimeTable.$model.data.weekOfTerm,
                "workDay.xnxq.xn": mainVm.$model.xnxq.xn,
                "workDay.xnxq.xq": mainVm.$model.xnxq.xq
            }, function (data) {
                var list = data.result;
                for (var i = 0; i < list.length; i++) {
                    list[i].workDay.date = mainVm.getDate(list[i].workDay.weekOfTerm, list[i].workDay.dayOfWeek);
                    vmCourseimeTable.data[list[i].workDay.dayOfWeek.toString()].push(list[i]);
                }
                vmCourseimeTable.data = vmCourseimeTable.$model.data;
            });
        },
        selectTeacher: function () {
            msgFn.dlg({
                title: '选择任课教师',
                url: '${ctx}/optional/choose_teacher',
                width: '800px',
                success: function (end, close) {
                    end();
                    chooseTeacherVm.init();
                    chooseTeacherVm.single = true;
                    chooseTeacherVm.chooseOne = function (el) {
                        vmCourseimeTable.data.teacher = el.teacher;
                        close();
                    };
                }
            });
        },
        search: function () {
            <#list allWeeks as week>
                vmCourseimeTable.data['${week.code}'] = [];
            </#list>
            this.loadDate();
            this.loadBasic();
            // this.loadOptional();
        },
        init: function () {
            var weekOfTerm = mainVm.getWeekOfTerm(new Date('${.now?string("yyyy-MM-dd")}'));
            if (weekOfTerm > mainVm.$model.xnxq.weekCount) {
                weekOfTerm = mainVm.$model.xnxq.weekCount;
            }
            vmCourseimeTable.data.weekOfTerm = weekOfTerm;
            vmCourseimeTable.search();
        },
        changeWeek: function (week) {
            if (week > 0 && week <= mainVm.$model.xnxq.weekCount) {
                this.search();
            }
        },
        showDetail: function (el) {
            msgFn.dlg({
                url: "${ctx}/history/course_detail",
                title: "详情",
                width: "600px",
                success: function (resize, close) {
                    resize();
                    courseDetailVm.init(el);
                }
            });
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
        formatTitle: function (el) {
            return el.workDay.startTime + '~' + el.workDay.endTime + '\n' + this.formatSpace(el) + '\n' + this.formatCourseName(el);
        },
        changeXnxq: function (e) {
            mainVm.xnxq = avalon.Array.findById(mainVm.$model.xnxqs, e.target.value);
            if (vmCourseimeTable.data.weekOfTerm >= mainVm.xnxq.weekCount) {
                vmCourseimeTable.data.weekOfTerm = mainVm.xnxq.weekCount;
            }
        }
    });
</script>
</@page>