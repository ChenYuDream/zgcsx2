<#include "/layout/layout.ftl"/>
<@page title="已录入课程" breadWraps=[{"name":"课程管理","href":"javascript:void(0)"},{"name":"已录入课程"}]>
<div ms-controller="addTimeTable">
    <!-- 查询条件 S-->
    <form class="form-inline">
        <div class="clearfix item-group">
            <ul class="clearfix pull-left">
                <li>
                    <span>教师：</span>
                    <xmp cached="true"
                         :widget="{is:'ms-nameFiltrate',dataList:@teachers,_data:{id:@opt.teacherId,name:@opt.teacherName}}"/>
                    </xmp>
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
                    <div >
                        <h5>{{elem.teacherName}}</h5>
                        <h5>{{elem.courseName}}</h5>
                        <h6>{{elem.campusName}}</h6>
                        <h6>{{elem.className}}</h6>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
    <#include "common/course_manager_js.ftl">
<script type="text/javascript">
    window.setTimeout("vmAddTimeTable.searchListFn()", 1);
</script>
</@page>