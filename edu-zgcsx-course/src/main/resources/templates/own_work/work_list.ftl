<#include "/layout/layout.ftl"/>
<@page title="我的工作" breadWraps=[{"name":"课程管理","href":"javascript:void(0)"},{"name":"我的工作"}]>
<div ms-controller="workOwn" class="ms-controller">
    <!-- table表单模块 S-->
    <div class="table-content">
        <table class="table">
            <thead>
            <tr>
                <th>课程名称</th>
                <th width="10%">课程层级</th>
                <th width="10%">课程类型</th>
                <th width="30%">上课地点</th>
                <th width="8%">满人数</th>
                <th width="8%">已选人数</th>
                <th width="12%">操作</th>
            </tr>
            </thead>
            <tbody>
            <tr :for="el in @basicData">
                <td :attr="{title:el.courseName}">{{el.courseName | truncate(15,'...')}}</td>
                <td>{{el.kcdj}}</td>
                <td>必修</td>
                <td :attr="{title:el.gradeName + el.clazzName} + '班'">{{el.gradeName}}{{el.clazzName}}班</td>
                <td>{{el.studentCount}}</td>
                <td>{{el.chooseStudentCount}}</td>
                <td><a class="btn btn-default btn-xs" :click="@show(el)">详情</a></td>
            </tr>
            <tr :for="el in @chooseData">
                <td :attr="{title:formatChooseCourseName(el.courseName,el.optionalCourse.aliasName)}">{{formatChooseCourseName(el.courseName,el.optionalCourse.aliasName) | truncate(15,'...')}}</td>
                <td>{{el.itemText}}</td>
                <td>{{xxlbs[el.optionalCourse.courseType]}}</td>
                <td :attr="{title:@formatSpace(el.optionalCourse.space)}">
                    {{formatSpace(el.optionalCourse.space) | truncate(20,'...')}}
                </td>
                <td>{{el.optionalCourse.limitCount}}</td>
                <td>{{el.chooseStudentCount}}</td>
                <td><a class="btn btn-default btn-xs" :click="@show(el)">详情</a></td>
            </tr>
            <tr :if="basicData.length == 0 && chooseData.length == 0">
                <td colspan="7">暂无相关数据！</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>

<script type="text/javascript">
    var vmWork = avalon.define({
        $id: 'workOwn',
        basicData: [],
        chooseData: [],
        showData: {}, //弹出层数据
        show: function (el) {
            vmWork.showData = el;
            msgFn.dlg({
                url: "${ctx}/course/page/work/detail",
                title: "详情",
                width: "600px"
            });
        },
        init: function () {
            //加载基础层数据
            $.ajax({
                url: "${ctx}/teacher/basic/work",
                data: {teacherId: "${onlineUser.id}"},
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    vmWork.basicData = data.result;
                }
            });
            //加载拓展和开放层数据
            $.ajax({
                url: "${ctx}/teacher/choose/work",
                data: {teacherId: "${onlineUser.id}", week: 14},
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    vmWork.chooseData = data.result;
                }
            });
        }
    });

    vmWork.init();
</script>
</@page>