<#--班级课程表-->
<#include "/layout/layout.ftl"/>
<@page title="班级课表" breadWraps=[{"name":"历史记录","href":"javascript:void(0)"},{"name":"班级课表"}]>
<div :controller="clazzTimetableController" class="ms-controller">
    <!-- 查询条件 S-->
    <form class="form-inline">
        <div class="clearfix item-group">
            <ul class="clearfix pull-left">
                <li>
                    <span>学年学期：</span>
                    <select class="select" title="学年学期" :change="@changeXnxq($event)">
                        <option :for="el in @xnxqs" :attr="{value:@el.id,selected:@condition.xn==@el.xn&&@condition.xq==@el.xq}">
                            {{el.xn+'学年第'+el.xq+'学期'}}
                        </option>
                    </select>
                </li>
                <li>
                    <span>所属校区：</span>
                    <select id="campusId" name="campusId" class="select" :duplex="@condition.campusId" title="所属校区">
                        <option value="">全部</option>
                        <option :for="el in @campuses" :attr={value:@el.id}>{{el.name}}</option>
                    </select>
                </li>
                <li :visible="condition.campusId">
                    <span>所属年级：</span>
                    <select id="gradeId" name="gradeId" class="select" :duplex="@condition.gradeId" title="所属年级">
                        <option value="">全部</option>
                        <option :for="el in @grades" :attr="{value:@el.id}">{{el.name}}</option>
                    </select>
                </li>
                <li :visible="condition.gradeId">
                    <span>所属班级：</span>
                    <select id="clazzId" name="clazzId" class="select" :duplex="@condition.clazzId" title="所属班级">
                        <option value="">全部</option>
                        <option :for="el in @clazzes" :attr="{value:@el.id}">{{el.name + '班'}}</option>
                    </select>
                </li>
            </ul>
        </div>
        <hr/>
        <div class="clearfix item-group">
            <div class="toolList text-center">
                <a :click="@search" class="item-btn" href="javascript:void(0);">
                    <i class="iconfont icon-chazhao"></i>查找
                </a>
                <a :click="@clear" class="item-btn" href="javascript:void(0);">
                    <i class="iconfont icon-qingkong"></i>清空
                </a>
            <#--<a :click="@export" class="item-btn" href="javascript:void(0);">-->
            <#--<i class="iconfont icon-quanbudaochu"></i>导出-->
            <#--</a>-->
            </div>
        </div>
    </form>
    <!-- 查询条件 E-->
    <!-- table表单模块 S-->
    <div class="table-content">
        <table class="timeTable">
            <thead>
            <tr>
                <th width="12%">节次\星期</th>
                <#list workDayWeeks as week>
                    <th width="12%">${week.text}</th>
                </#list>
            </tr>
            </thead>
            <tbody>
            <tr :for="el in @periods">
                <td>
                    <span>
                        <p>{{el}}</p>
                    </span>
                </td>
                <#list workDayWeeks as week>
                    <td width="12%">
                        <span :if="!data[el]['${week.code}']">

                        </span>
                        <span :if="data[el]['${week.code}']&&!data[el]['${week.code}'].courseId">
                            <h5 style="color: blue;">
                                选修
                            </h5>
                        </span>
                        <span :if="data[el]['${week.code}'].baseCourse">
                            <h5>
                                <a href="javascript:void(0);" :click="showDetail(data[el]['${week.code}'])">{{data[el]['${week.code}'].baseCourse.courseName}}</a>
                            </h5>
                        </span>
                    </td>
                </#list>
            </tr>
            </tbody>
        </table>
    </div>
</div>

<script type="text/javascript">
    var clazzTimetableCondition = {
        xn: '',
        xq: '',
        campusId: '',
        gradeId: '',
        clazzId: ''
    };
    var clazzTimetableVm = avalon.define({
        $id: 'clazzTimetableController',
        data: {},
        periods: [1, 2, 3, 4, 5, 6],
        loadData: function () {
            msgFn.load(function (end) {
                $.ajax({
                    url: "${ctx}/history/clazz_timetable/list",
                    type: 'post',
                    data: mainVm.$model.condition,
                    success: function (data) {
                        var list = data.result, _data = {}, periods = clazzTimetableVm.$model.periods;
                        for (var i = 0; i < periods.length; i++) {
                            _data[periods[i]] = {};
                        }
                        for (i = 0; i < list.length; i++) {
                            _data[list[i].workDay.period][list[i].workDay.dayOfWeek] = list[i];
                        }
                        clazzTimetableVm.data = _data;
                        end();
                    }
                });
            });
        },
        clear: function () {
            mainVm.condition = clazzTimetableCondition;
        },
        search: function () {
            if (!mainVm.condition.xn || !mainVm.condition.xq) {
                return msgFn.msg(false, "请选择学年学期");
            }
            if (!mainVm.condition.campusId) {
                return msgFn.msg(false, "请选择所属校区");
            }
            if (!mainVm.condition.gradeId) {
                return msgFn.msg(false, "请选择所属年级");
            }
            if (!mainVm.condition.clazzId) {
                return msgFn.msg(false, "请选择所属班级");
            }
            clazzTimetableVm.loadData();
        },
        export: function () {

        },
        showDetail: function (el) {
            msgFn.dlg({
                url: "${ctx}/course/page/public/timetable/detail",
                title: "详情",
                width: "600px",
                success: function (ok) {
                    vmPublicTimetableDetail.data = {
                        courseName: el.baseCourse.courseName,
                        kcdj: el.baseCourse.courseDefinition,
                        kccc: el.baseCourse.courseLevel,
                        kclb: el.baseCourse.courseCategory,
                        xxlb: el.baseCourse.studyType,
                        description: el.baseCourse.description
                    };
                    ok();
                }
            });
        },
        changeXnxq: function (e) {
            var xnxq = avalon.Array.findById(mainVm.$model.xnxqs, e.target.value);
            mainVm.condition.xn = xnxq.xn;
            mainVm.condition.xq = xnxq.xq;
        }
    });
    $(function () {
        mainVm.loadXnxqs();
        mainVm.loadXnxq(function () {
            clazzTimetableCondition.xn = mainVm.$model.xnxq.xn;
            clazzTimetableCondition.xq = mainVm.$model.xnxq.xq;
            mainVm.condition = clazzTimetableCondition;
        });
        mainVm.loadCampuses();
        mainVm.watchClazz();
    });
</script>
</@page>