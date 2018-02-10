<#include "/layout/layout.ftl"/>
<@page title="课程添加" breadWraps=[{"name":"课程管理","href":"javascript:void(0)"},{"name":"课程添加"}]>
<div ms-controller="addTimeTable">
    <!-- 查询条件 S-->
    <form class="form-inline">
        <div class="clearfix item-group">
            <ul class="clearfix pull-left">
                <li>
                    <span>校区：</span>
                    <select ms-duplex="@opt.campusId"
                            data-duplex-changed="@duplexFn($event,'campus')"
                            class="form-control input-sm">
                        <option :for="(index,el) in @campus"
                                :attr="{value:el.id,_index:index}">{{el.name}}
                        </option>
                    </select>
                </li>
                <li>
                    <span>年级：</span>
                    <select :duplex="@opt.gradeId"
                            data-duplex-changed="@duplexFn($event,'grade')"
                            class="form-control input-sm">
                        <option :for="(index,el) in @grade" :attr="{value:el.id,_index:index}">{{el.name}}</option>
                    </select>
                </li>
            </ul>
            <ul class="clearfix pull-right">
                <li>
                    <span>学科：</span>
                    <select :duplex="@opt.courseName"
                            :attr="{value:'== 请选择 =='}"
                            data-duplex-changed="@duplexFn($event,'subject')"
                            class="form-control input-sm">
                        <option value="">== 请选择 ==</option>
                        <option :for="(index,el) in @subject"
                                :attr="{value:el.courseName,courseId:el.id}">{{el.courseName}}
                        </option>
                    </select>
                </li>
            </ul>
        </div>
    </form>
    <!-- 查询条件 E-->
    <!-- table表单模块 S-->
    <div class="table-content">
        <table class="timeTable">
            <thead>
            <tr>
                <th colspan="2">课时\星期</th>
                <th width="12%">周日</th>
                <th width="12%">周一</th>
                <th width="12%">周二</th>
                <th width="12%">周三</th>
                <th width="12%">周四</th>
                <th width="12%">周五</th>
                <th width="12%">周六</th>
            </tr>
            </thead>
            <tbody>
            <tr :for="(index,el) in @_dataList.courseSections">
                <td :if="index == 0" rowspan="4" width="30"><b>上午</b></td>
                <td :if="index == 4" class="bg-info" rowspan="4" width="30"><b>下午</b></td>
                <td class="title" width="100">{{el.sectionIndex}}</td>
                <td :for="elem in el.courses">
                    <div :if="elem.editView">
                        <h5>{{elem.courseName}}</h5>
                        <h6>{{elem.campusName}}</h6>
                        <h6>{{elem.className}}</h6>
                        <button :click="@addFn(elem,'edit')" class="btn btn-danger btn-xs" type="button">编辑</button>
                        <button :click="@clearFn(elem)" class="btn btn-default btn-xs" type="button">删除</button>
                    </div>
                    <div :if="elem.addView">
                        <em>-</em>
                        <button :click="@addFn(elem)" class="btn btn-danger btn-xs" type="button">新增</button>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="table-content text-center">
        <button :click="@submitFn" class="btn btn-primary" type="button">确认提交</button>
    </div>
</div>
    <#include "common/course_manager_js.ftl">
<script type="text/javascript">
    vmAddTimeTable.opt.teacherId='${onlineUser.id}';
    window.setTimeout("vmAddTimeTable.searchListFn()", 1);
</script>
</@page>