<#include "/layout/layout.ftl"/>
<#assign baseModuleName = {"2":"拓展课程","5":"开放课程"}[xxlb]/>
<@page title="我教的课程" hasFooter=true breadWraps=[{"name":baseModuleName,"href":"javascript:void(0)"},{"name":"我教的课程"}]>
<div :controller="courseDetailController">
    <!-- table表单模块 S-->
    <div class="table-content">
        <table class="table">
            <thead>
            <tr>
                <th>所属校区</p></th>
                <th>课程定义(课程名称)</th>
                <th>课程层次</th>
                <th>课程类型</th>
                <th>上课地点</th>
                <th>已选人数</th>
                <th>满人数</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr :for="(index,el) in @data.records">
                <td>{{el.campusName}}</p></td>
                <td :attr="{title:el.courseName+'('+el.aliasName+')'}">{{el.courseName+'('+el.aliasName+')' | truncate(20)}}</td>
                <td>{{codeData.formatCourseDefinition(el.baseCourse.kcdj)}}</td>
                <td>{{xxlbs[el.xxlb]}}</td>
                <td :attr="{title:@formatSpace(el.space)}">{{formatSpace(el.space) | truncate(15)}}</td>
                <td>{{el.studentCount}}</td>
                <td>{{el.limitCount}}</td>
                <td><a class="btn btn-default btn-xs" href="javascript:void(0)" :click="operate(@el)">详情</a></td>
            </tr>
            <tr :if="initialized&&!@data.records.length">
                <td colspan="8"><@spring.message code="course.page.empty_result"/></td>
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
    $(function () {
        mainVm.initPage("${ctx}/optional/optional_list", {
            teacherId: "${onlineUser.id}",
            xxlb: "${xxlb}",
            courseStatus : "2"
        });
        mainVm.search();
        mainVm.codeData.loadCourseDefinitions();
    });
    var courseDetailVm = avalon.define({
        $id: 'courseDetailController',
        operate: function (el) {
            msgFn.dlg({
                title: '课程详情',
                url: '${ctx}/optional/course_detail',
                width: '600px',
                success: function (end, close) {
                    end();
                    courseDetailVm.data = el;
                }
            });
        }
    });
</script>
</@page>