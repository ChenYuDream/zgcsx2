<#include "/layout/layout.ftl"/>
<@page title="选修课占位" breadWraps=[{"name":"课程管理","href":"javascript:void(0)"},{"name":"选修课占位"}]>
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
                <td :if="index == 4" class="bg-info" rowspan="3" width="30"><b>下午</b></td>
                <td class="title" width="100">{{el.sectionIndex}}<h6>(<b>{{el.sectionTime}}</b>)</h6></td>
                <td :for="elem in el.courses">
                    <div :if="elem.editView">
                        <h5>{{elem.courseName}}</h5>
                        <button :click="@clearFn(elem)" class="btn btn-default btn-xs" type="button">删除</button>
                    </div>
                    <div :if="elem.addView">
                        <em>-</em>
                        <button :click="@addFn(elem)" class="btn btn-danger btn-xs" type="button">新增选修课占位</button>
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

<script type="text/javascript">
    var flag = 0;
    var _path = {
        search: "${ctx}/courseManager/query/search/all",
        dataList: function (campusId, gradeId) {
            return "${ctx}/course/selective/query/" + campusId + "/" + gradeId;
        },
        submitCheck: function (gradeId) {
            return "${ctx}/course/selective/check/" + gradeId;
        },
        submitSave: function (gradeId) {
            return "${ctx}/course/selective/save/" + gradeId;
        }
    };
    var vm = avalon.define({
        $id: 'addTimeTable',
        _data: {},
        opt: {
            campus: '',//校区
            campusId: '',//校区ID
            gradeId: '',//年级
            gradeName: '',//年级
            courseId: '',//学科
            courseName: '',//学科
        },
        firstSelected: '-1',
        secondSelected: '-1',
        campus: [],//校区
        grade: [],//年级
        workDayIds: [],
        _dataList: [],
        duplexFn: function (e, elem) {
            switch (elem) {
                case 'campus':
                    this.firstSelected = $(e.target).find("option:selected").attr("_index");
                    vm.grade = vm.campus[this.firstSelected].gradeList;
                    vm.opt.gradeId = vm.$model.grade[0].id;
                    vm.dataListFn();
                    break;
                case 'grade':
                    this.secondSelected = $(e.target).find("option:selected").attr("_index");
                    vm.dataListFn();
                    break;
            }
        },
        //搜索条件
        searchListFn: function () {
            $.post(_path.search, function (data) {
                vm.campus = data.result.xqList;
                vm.grade = data.result.xqList[0].gradeList;
                vm.opt.campusId = data.result.xqList[0].id
                vm.opt.gradeId = data.result.xqList[0].gradeList[0].id
                vm.dataListFn();
            })
        },
        //后台查询是否有选修课占位
        dataListFn: function () {
            $.post(_path.dataList(this.opt.campusId, this.opt.gradeId), function (data) {
                vm._dataList = data.result.teacherCourse;
                vm.workDayIds = data.result.workDayIds;
            })
        },
        //添加选修课占位
        addFn: function (el) {
            if (this.opt.campusId == '') {
                layer.msg('校区不能为空!');
                return;
            }
            if (this.opt.gradeId == '') {
                layer.msg('年级不能为空!');
                return;
            }
            el.workDayId = el.$model.workDayCampusMap[vm.$model.opt.campusId];
            vm.workDayIds.push(el.$model.workDayId);
            el.editView = true;
            el.addView = false;
            el.courseName = "选修课占位";
            flag++;
        },
        clearFn: function (el) {
            vm.workDayIds.removeByValue(el.$model.workDayId);
            flag--;
            el.courseId = "";
            el.courseName = "";
            el.addView = true;
            el.editView = false;
        },
        submitFn: function () {
            var _index;
            $.ajax({
                url: _path.submitCheck(vm.opt.gradeId),
                type: 'POST',
                dataType: 'json',
                beforeSend: function () {
                    _index = layer.load(0, {shade: false});
                },
                data: {workDayIds: JSON.stringify(vm.$model.workDayIds)},
                success: function (data) {
                    layer.close(_index);
                    console.log(data);
                    if (data.code != '0') {
                        layer.alert(data.msg);
                    } else {
                        flag = 0;
                        vm.dataListFn();
                        layer.msg("保存成功!");
                    }


                }
            })
        }
    });
    vm.$watch("firstSelected", function (a) {
        if (a == '-1') {
            this.grade = [];
        } else {
            vm.grade = vm.campus[a].gradeList;
        }
    });
    window.setTimeout("vm.searchListFn()", 1);
</script>
</@page>