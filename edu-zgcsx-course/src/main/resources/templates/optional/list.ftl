<#include "/layout/layout.ftl"/>
<@page title="选修课列表" hasFooter=false breadWraps=[{"name":"拓展课程","href":"javascript:void(0)"},{"name":"选修课列表"}]>
<div :controller="optionalController">
    <!-- 查询条件 S-->
    <form class="form-inline">
        <div class="clearfix item-group">
            <ul class="clearfix pull-left">
                <li>
                    <span>所属校区：</span>
                    <select id="campusId" name="campusId" class="select" :duplex="@condition.campusId" title="所属校区">
                        <option value="">全部</option>
                        <option :for="el in @campuses" :attr={value:@el.id}>{{el.name}}</option>
                    </select>
                </li>
                <li>
                    <span>课程名称：</span>
                    <input id="aliasName" name="aliasName" class="input" type="text" placeholder="请输入课程名称" maxlength="30" :duplex="@condition.aliasName">
                </li>
                <li>
                    <span>课程类型：</span>
                    <select id="xxlb" name="xxlb" class="select" :duplex="@condition.xxlb" title="课程类型">
                        <option value="">== 请选择 ==</option>
                        <option :for="(key,val) in @xxlbs" :attr="{value:@key}">{{@val}}</option>
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
                <a :click="@export" class="item-btn" href="javascript:void(0);">
                    <i class="iconfont icon-quanbudaochu"></i>导出
                </a>
            </div>
        </div>
    </form>
    <!-- 查询条件 E-->
    <!-- table表单模块 S-->
    <div class="table-content">
        <table class="table">
            <thead>
            <tr>
                <th>所属校区</p></th>
                <th>课程定义(课程名称)</th>
                <th>任课教师</th>
                <th>上课地点</th>
                <th>课程类型</th>
                <th>已选学生数量</th>
                <th>课程人数上限</th>
            </tr>
            </thead>
            <tbody>
            <tr :for="(index,el) in @data.records">
                <td>{{el.campusName}}</p></td>
                <td :attr="{title:@el.courseName+'('+@el.aliasName+')'}">{{el.courseName+'('+el.aliasName+')' | truncate(20)}}</td>
                <td :attr="{title:@formatArr(el.teachers,'name')}">{{formatArr(el.teachers,'name') | truncate(10)}}</td>
                <td :attr="{title:@formatSpace(el.space)}">{{formatSpace(el.space) | truncate(15)}}</td>
                <td>{{xxlbs[el.xxlb]}}</td>
                <td><a href="javascript:void(0)" :click="toPage('${ctx}/optional/course_student?id='+el.id)">{{el.studentCount}}</a></td>
                <td>{{el.limitCount}}</td>
            </tr>
            <tr :if="initialized&&!@data.records.length">
                <td colspan="7"><@spring.message code="course.page.empty_result"/></td>
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
            campusId: "",
            aliasName: "",
            xxlb: "",
            courseStatus: "2"
        });
        mainVm.loadCampuses();
        mainVm.search();
    });
    var optionalVm = avalon.define({
        $id: "optionalController",
        export: function () {
            var condition = $.param(mainVm.$model.data.condition);
            location.href = "${ctx}/optional/export/"+encodeURI("选修课列表")+"?" + condition;
        }
    });
</script>
</@page>