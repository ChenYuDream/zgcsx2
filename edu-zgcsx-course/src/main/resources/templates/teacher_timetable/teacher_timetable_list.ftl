<#include "/layout/layout.ftl"/>
<@page title="教师任课列表" hasFooter=false breadWraps=[{"name":"课程管理","href":"javascript:void(0)"},{"name":"教师任课列表"}]>
<div ms-controller="teacherTimetable" class="ms-controller">
    <!-- 查询条件 S-->
    <form class="form-inline">
        <div class="clearfix item-group">
            <ul class="clearfix pull-left">
                <li>
                    <span>校区：</span>
                    <select ms-duplex="@opt.campusId" :attr="{value:'== 请选择 =='}" class="form-control input-sm">
                        <option value="">== 请选择 ==</option>
                        <option :for="(index,el) in @campus" :attr="{value:el.id}">{{el.name}}</option>
                    </select>
                </li>
                <li>
                    <span>教师名称：</span>
                    <input :duplex="@opt.teacherName" class="form-control input-sm" placeholder="请输入教师名称" maxlength="30"/>
                </li>
                <li>
                    <span>课程名称：</span>
                    <input :duplex="@opt.courseName" class="form-control input-sm" placeholder="请输入课程名称" maxlength="30"/>
                </li>
            </ul>
            <div class="toolList pull-left">
                <ul class="clearfix">
                    <li>
                        <a :click="@init('find')" class="item-btn" href="javascript:void(0);">
                            <i class="iconfont icon-chazhao"></i>查找
                        </a>
                    </li>
                    <li>
                        <a :click="@clear()" class="item-btn" href="javascript:void(0);">
                            <i class="iconfont icon-qingkong"></i>清空
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </form>
    <!-- 查询条件 E-->
    <!-- table表单模块 S-->
    <div class="table-content">
        <table class="table">
            <thead>
            <tr>
                <th width="12%">序号</th>
                <th width="12%">校区</th>
                <th width="12%">课程名称</th>
                <th width="12%">班级</th>
                <th width="12%">教师名称</th>
                <th width="12%">课程类型</th>
                <th width="12%">操作</th>
            </tr>
            </thead>
            <tbody>
            <tr :for="(index, el) in @data.records">
                <td>{{(@data.current - 1) * @data.size + (index+1)}}</td>
                <td>{{el.campusName}}</td>
                <td>{{el.courseName}}</td>
                <td>{{el.gradeName}}{{el.clazzName}}班</td>
                <td>{{el.teacherName}}</td>
                <td>{{el.xxlb}}</td>
                <td><a class="btn btn-default btn-xs" :click="show(el)">详情</a></td>
            </tr>
            <tr :if="data.pages == 0">
                <td colspan="7">暂无相关数据！</td>
            </tr>
            </tbody>
        </table>
    </div>

    <!-- 分页+全选模块 S-->
    <div class="clearfix pagesBox" :if="@data.pages > 1">
        <div id="pagination" class="pull-right"></div>
    </div>
    <!-- 分页+全选模块 E-->
</div>

<script type="text/javascript">
    var _opt = {
        campusId: '',
        teacherName: '',
        courseName: ''
    };
    var vmTeacherTimetable = avalon.define({
        $id: 'teacherTimetable',
        current : 1,
        size : 20,
        campus : [], //校区
        data   : {},
        showData : {},
        opt: _opt,
        dataOpt : _opt,
        show : function (el) {
            vmTeacherTimetable.showData = el;
            msgFn.dlg({
                url: "${ctx}/course/page/teacher/timetable/detail",
                title: "详情",
                width: "600px"
            });
        },
        loadCampus : function () {
            $.ajax({
                url: "${ctx}/api/campus",
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    vmTeacherTimetable.campus = data.result;
                }
            });
        },
        clear : function(){
            vmTeacherTimetable.opt = _opt;
        },
        init : function (type) {
            if(type === 'find'){       //点击了查询按钮
                vmTeacherTimetable.current = 1;
                vmTeacherTimetable.dataOpt = vmTeacherTimetable.$model.opt;
            }
            $.ajax({
                url: "${ctx}/teacher/basic/teacher/teach",
                data : {
                    current : this.current,
                    size : this.size,
                    campusId : this.dataOpt.campusId,
                    teacherName : this.dataOpt.teacherName,
                    courseName : this.dataOpt.courseName
                },
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    vmTeacherTimetable.data = data.result;
                    mainVm.renderPage(vmTeacherTimetable.$model.data, function(curr){
                        vmTeacherTimetable.current = curr;
                        vmTeacherTimetable.init();
                    });
                }
            });
        }
    });

    vmTeacherTimetable.loadCampus();
    vmTeacherTimetable.init();
</script>
</@page>