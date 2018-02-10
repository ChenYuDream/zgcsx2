<!--教师列表 弹出框-->
<div ms-controller="teacher" class="ms-controller">
    <!-- 查询条件 S-->
    <form class="form-inline">
        <div class="clearfix item-group">
            <ul class="clearfix pull-left">
                <li>
                    <span>姓名：</span>
                    <input :duplex="@opt.name" class="form-control input-sm">
                </li>
                <li>
                    <span>性别：</span>
                    <select ms-duplex="@opt.sex"  class="form-control input-sm js-courseId">
                        <option value="">== 请选择 ==</option>
                        <option value="1">男</option>
                        <option value="2">女</option>
                    </select>
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
                <th></th>
                <th width="20%">姓名</th>
                <th width="20%">别名</th>
                <th width="20%">所在校区</th>
                <th width="22%">身份证号码</th>
                <th width="10%">性别</th>
            </tr>
            </thead>
            <tbody>
                <tr :for="el in @data.records">
                    <td> <input type="radio" ms-duplex="@teacherId" :attr="{value: el.id, id:el.id, teacherName:el.name}" name="teacherId"> </td>
                    <td> {{el.name | empty}} </td>
                    <td> {{el.aliasName | empty}} </td>
                    <td> {{el.campusName | empty}} </td>
                    <td> {{el.idCard | idCard}} </td>
                    <td> {{@sex[el.sex-1] | empty}} </td>
                </tr>
                <tr :if="data.pages == 0">
                    <td colspan="6">暂无相关数据！</td>
                </tr>
            </tbody>
        </table>
    </div>

    <!-- 分页+全选模块 S-->
    <div class="clearfix pagesBox" :if="@data.pages > 1">
        <div id="pagination" class="pull-right"></div>
    </div>
    <!-- 分页+全选模块 E-->

    <div class="clearfix pull-right" style="margin-right : 10px">
        <button class="btn btn-primary btn-sm" type="button" style="margin: 10px 5px;" :click="update()">确定</button>
        <button onclick="javascript:layer.closeAll();" class="btn btn-default btn-sm" style="margin: 10px 5px;" type="button">关闭</button>
    </div>
</div>

<script type="text/javascript">
    var _optTeacher = {
        name : '',     //姓名
        sex : ''       //性别
    };

    var vmTeacher = avalon.define({
        $id:"teacher",
        current : 1,
        size : 10,
        campusId : "${campusId!''}",
        clazzId : "${clazzId!''}",
        workDayId : "${workDayId!''}",
        oldTeacherId : "${teacherId!''}", //需要替换的教师id(一个课程可能对应多个教师)
        teacherId : '', //记录选中的教师id
        sex : ["男", "女"],
        opt : _optTeacher,  //记录输入的查询条件
        dataOpt : _optTeacher, //实际查询的条件
        data:{},
        clear : function(){
            vmTeacher.opt = _optTeacher;
            vmTeacher.dataOpt = _optTeacher;
        },
        update : function (){
            if(vmTeacher.$model.teacherId){
                msgFn.confirm({msg:'确认要将当前课程的任课教师设置为['+$("#"+vmTeacher.$model.teacherId).attr("teacherName")+']吗?',title:'教师修改确认',callback:function(){
                    $.ajax({
                        url: "${ctx}/teacher/update/timetable/teacher",
                        data : {
                            teacherId : vmTeacher.$model.teacherId,
                            teacherName : $("#"+vmTeacher.$model.teacherId).attr("teacherName"),
                            clazzId : vmTeacher.$model.clazzId,
                            workDayId : vmTeacher.$model.workDayId,
                            oldTeacherId : vmTeacher.$model.oldTeacherId
                        },
                        type: 'POST',
                        dataType: 'json',
                        success: function (data) {
                            if(data.success){
                                vmTimetableDetail.init();
                                layer.closeAll();
                            }
                            msgFn.msg(data);
                        }
                    });
                }});
            }else{
                msgFn.msg(false, "请选择一位教师")
            }

        },
        init : function(type){
            if(type === 'find'){       //点击了查询按钮
                vmTeacher.current = 1;
                vmTeacher.dataOpt = vmTeacher.$model.opt;
            }
            $.ajax({
                url: "${ctx}/api/teachers",
                data : {
                    name : vmTeacher.$model.dataOpt.name,
                    sex : vmTeacher.$model.dataOpt.sex,
                    campusId : vmTeacher.$model.campusId,
                    current :  vmTeacher.$model.current,
                    size : vmTeacher.$model.size
                },
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    vmTeacher.data = data.result;

                    $("[name='teacherId']").removeAttr("checked"); //清空
                    vmTeacher.teacherId = '';

                    $(window).resize();
                    mainVm.renderPage(vmTeacher.$model.data, function(curr){
                        vmTeacher.current = curr;
                        vmTeacher.init();
                    });
                }
            });
        }
    });

    vmTeacher.init();
</script>