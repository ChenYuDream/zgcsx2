<script type="text/javascript">
    var flag = 0;
    var userId = '${onlineUser.id}';
    var _path = {
        clazzList: "${ctx}/course/dlg/classes",
        search: "${ctx}/courseManager/query/search/all",
        dataList: function (campusId, teacherId) {
            return "${ctx}/courseManager/query/" + campusId + "/course/" + teacherId;
        },
        submitCheck: function (teacherId) {
            return "${ctx}/courseManager/check/course/" + teacherId;
        },
        submitSave: function (teacherId) {
            return "${ctx}/courseManager/save/course/" + teacherId;
        }
    };
    var vmAddTimeTable = avalon.define({
        $id: 'addTimeTable',
        _data: {},
        opt: {
            campusName: '',//校区
            campusId: '',//校区ID
            gradeId: '',//年级
            gradeName: '',//年级
            courseId: '',//学科
            courseName: '',//学科
            teacherId: '',//教师
            teacherName: '',//教师
        },
        firstSelected: '-1',
        secondSelected: '-1',
        campus: [],//校区
        grade: [],//年级
        class: [],//班级
        teachers: [],//教师
        subject: [],//科目
        _dataList: [],
        clazzTimeTables: [],
        teacherChangeFn: function () {
            if (flag > 0) {
                //询问框
                layer.confirm('你还有更改没有保存，确定保存吗？', {
                    btn: ['保存', '不保存'] //按钮
                }, function () {
                    vmAddTimeTable.submitFn();
                }, function () {
                    flag = 0;
                    vmAddTimeTable.dataListFn();
                });
            } else {
                vmAddTimeTable.dataListFn();
                return true;
            }

        },
        duplexFn: function (e, elem) {
            switch (elem) {
                case 'campus':
                    this.firstSelected = $(e.target).find("option:selected").attr("_index");
                    vmAddTimeTable.grade = vmAddTimeTable.$model.campus[this.firstSelected].gradeList;
                    vmAddTimeTable.opt.campusName = vmAddTimeTable.$model.campus[this.firstSelected].name
                    vmAddTimeTable.opt.gradeId = vmAddTimeTable.$model.grade[0].id
                    vmAddTimeTable.opt.gradeName = vmAddTimeTable.$model.grade[0].name
                    vmAddTimeTable.class = vmAddTimeTable.$model.grade[0].clazzList.concat();
                    break;
                case 'grade':
                    this.secondSelected = $(e.target).find("option:selected").attr("_index");
                    vmAddTimeTable.opt.gradeName = vmAddTimeTable.$model.grade[this.secondSelected].name
                    vmAddTimeTable.class = vmAddTimeTable.$model.grade[this.secondSelected].clazzList.concat();
                    break;
                case 'subject':
                    this.opt.courseId = $(e.target).find("option:selected").attr("courseId");
                    break;
            }
        },
        searchListFn: function () {
            $.post(_path.search, function (data) {
                vmAddTimeTable.campus = data.result.xqList;
                vmAddTimeTable.subject = data.result.xkList;
                vmAddTimeTable.teachers = data.result.jsList;
                vmAddTimeTable.grade = data.result.xqList[0].gradeList;
                vmAddTimeTable.class = data.result.xqList[0].gradeList[0].clazzList;
                vmAddTimeTable.opt.campusId = data.result.xqList[0].id
                vmAddTimeTable.opt.campusName = data.result.xqList[0].name
                vmAddTimeTable.opt.gradeId = data.result.xqList[0].gradeList[0].id
                vmAddTimeTable.opt.gradeName = data.result.xqList[0].gradeList[0].name
                if (vmAddTimeTable.opt.teacherId == '') {
                    vmAddTimeTable.opt.teacherId = userId;
                }
                vmAddTimeTable.dataListFn();
            })
        },
        dataListFn: function () {
            $.post(_path.dataList(this.opt.campusId, this.opt.teacherId), function (data) {
                vmAddTimeTable._dataList = data.result.teacherCourse;
                vmAddTimeTable.clazzTimeTables = data.result.clazzTimeTables;
                vmAddTimeTable.opt.teacherId = data.result.teacherCourse.teacherId;
                vmAddTimeTable.opt.teacherName = data.result.teacherCourse.teacherName;
                return true;
            })
        },
        addFn: function (el, type) {
            if (this.opt.campusId == '') {
                layer.msg('校区不能为空!');
                return;
            }
            if (this.opt.gradeId == '') {
                layer.msg('年级不能为空!');
                return;
            }
            if (this.opt.teacherName == '') {
                layer.msg('老师不能为空!');
                return;
            }
            if (this.opt.courseName == '') {
                layer.msg('学科不能为空!');
                return;
            }
            $.post(_path.clazzList, function (str) {
                layer.open({
                    type: 1,
                    title: '班级选择',
                    area: ['400px', '400px'],
                    content: str,
                    btn: ['确定', '取消'],
                    btnAlign: 'c',
                    scrollbar: false,
                    success: function () {
                        vmClasses.dataList = vmAddTimeTable.$model.class;
                        avalon.scan(document.body);
                    },
                    yes: function (index) {
                        var classId = avalon.vmodels.classes.$model.opt.data.id
                        if (!classId) {
                            layer.msg("你还没有选择班级!");
                            return;
                        }
                        flag++;
                        el.courseName = vmAddTimeTable.$model.opt.courseName;
                        el.courseId = vmAddTimeTable.$model.opt.courseId;
                        el.className = vmAddTimeTable.$model.opt.gradeName + "(" + avalon.vmodels.classes.$model.opt.data.name + ")班";
                        el.classId = avalon.vmodels.classes.$model.opt.data.id;
                        el.campusName = vmAddTimeTable.$model.opt.campusName;
                        el.teacherName = vmAddTimeTable.$model.opt.teacherName;
                        el.addView = false;
                        el.editView = true;
                        if (type && true) {
                            //表明是edit操作 需要移除
                            avalon.Array.removeByField(vmAddTimeTable.clazzTimeTables, "workDayId", el.$model.workDayId);
                        }
                        el.workDayId = el.$model.workDayCampusMap[vmAddTimeTable.$model.opt.campusId];
                        var obj = {
                            clazzId: el.$model.classId,
                            workDayId: el.$model.workDayId,
                            teacherId: vmAddTimeTable.$model.opt.teacherId,
                            courseId: el.$model.courseId
                        }
                        console.log(obj);
                        vmAddTimeTable.clazzTimeTables.push(obj);
                        layer.close(index);
                    }
                });
            })
        },
        clearFn: function (el) {
            flag--;
            el.classId = "";
            el.className = "";
            el.courseId = "";
            el.courseName = "";
            el.teacherName = "";
            el.campusName = "";
            el.addView = true;
            el.editView = false;
            avalon.Array.removeByField(vmAddTimeTable.clazzTimeTables, "workDayId", el.$model.workDayId);
        },
        submitFn: function () {
            var _index;
            $.ajax({
                url: _path.submitCheck(vmAddTimeTable.opt.teacherId),
                type: 'POST',
                dataType: 'json',
                beforeSend: function () {
                    _index = layer.load(0, {shade: false});
                },
                data: {courseData: JSON.stringify(vmAddTimeTable.$model.clazzTimeTables)},
                success: function (data) {
                    layer.close(_index);
                    if (data.code == -1) {
                        var index2 = layer.confirm(data.msg, function () {
                            flag = 0;
                            $.ajax({
                                url: _path.submitSave(vmAddTimeTable.opt.teacherId),
                                type: 'POST',
                                dataType: 'json',
                                data: {courseData: JSON.stringify(vmAddTimeTable.$model.clazzTimeTables)},
                                success: function (res) {
                                    if (res.code == 0) {
                                        layer.close(index2);
                                        layer.msg("保存成功");
                                        vmAddTimeTable.dataListFn();
                                    } else {
                                        layer.msg(res.msg);
                                    }
                                }
                            })
                        });
                    } else if (data.code == -2) {
                        layer.confirm(data.msg, {
                            area: '400x',
                            btn: ['确定'], //按钮
                        });
                    } else if (data.code == 0) {
                        flag = 0;
                        vmAddTimeTable.dataListFn();
                        layer.msg("保存成功!");
                    }
                }
            })
        }
    });
</script>