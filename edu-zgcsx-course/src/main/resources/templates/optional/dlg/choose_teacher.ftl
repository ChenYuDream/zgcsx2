<div class="dialogBox" :controller="chooseTeacherController">
    <div class="row">
        <!-- ztree S-->
        <div class="col-sm-3">
            <div class="zTreeWrap">
                <ul id="tree" class="ztree"></ul>
            </div>
        </div>
        <!-- ztree E-->
        <div class="col-sm-9">
            <!-- 条件搜索 S-->
            <form class="searchBox" onsubmit="return false;">
                <ul class="clearfix">
                    <li>
                        <input class="form-control input-sm" type="text" style="width: 158px;"
                               placeholder="请输入姓名" :duplex="@condition.name">
                    </li>
                    <li>
                        <input class="form-control input-sm" type="text" placeholder="请输入证件号"
                               :duplex="@condition.idCard">
                    </li>
                    <li>
                        <select class="form-control input-sm" :duplex="@condition.sex">
                            <option value="">性别</option>
                            <option value="1">男</option>
                            <option value="2">女</option>
                        </select>
                    </li>
                    <li>
                        <button :click="@search" class="btn btn-primary btn-sm">查询</button>
                    </li>
                    <li>
                        <button :click="@clear" class="btn btn-default btn-sm">重置</button>
                    </li>
                </ul>
            </form>
            <!-- 条件搜索 E-->
            <!-- 搜索列表 S-->
            <div class="tableWrap" style="height: 376px;">
                <table>
                    <thead>
                    <tr>
                        <td :if="!@single"><input type="checkbox" ms-duplex-checked="allChecked">全选</td>
                        <td>姓名</td>
                        <td>身份证</td>
                        <td>别名</td>
                        <td>性别</td>
                        <td>所在节点</td>
                        <td :if="@single">操作</td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr :for="el in @data.records">
                        <td :if="!@single">
                            <label>
                                <input :attr="{value:el.teacher.id,checked:avalon.Array.containsByField(@teachers,'id',@el.teacher.id)}" :click="@choose($event,@el)"
                                       type="checkbox">
                            </label>
                        </td>
                        <td>{{el.teacher.name}}</td>
                        <td>{{formatIdCard(el.teacher.idCard) | nvl}}</td>
                        <td>{{el.teacher.aliasName}}</td>
                        <td>{{sexes[el.teacher.sex]}}</td>
                        <td>{{formatNodeName(el.tree) | nvl}}</td>
                        <td :if="@single">
                            <button class="btn btn-danger btn-sm" type="button" :click="chooseOne(el)">选择</button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <!-- 搜索列表 E-->
            <!-- 分页操作 S-->
            <div id="chooseTeacherPage"></div>
            <!-- 分页操作 E-->
        </div>
    </div>
</div>
<script type="text/javascript">
    var chooseTeacherCondition = {treeId: '0', name: '', sex: '', idCard: ''};
    var chooseTeacherVm = avalon.define({
        $id: 'chooseTeacherController',
        sexes: mainVm.$model.sexes,
        single: false,
        formatIdCard: mainVm.formatIdCard,
        treeData: [],
        condition: chooseTeacherCondition,
        loadTreeData: function (callback) {
            $.post('${ctx}/api/tree', function (data) {
                for (var i = 0, j = data.length; i < j; i++) {
                    if (data[i].parentId === '0') {
                        data[i].open = true;
                        data.push({id: 'NON_TREE', name: '非通讯录', parentId: data[i].id, order: 9998});
                        data.push({id: 'NON_SCHOOL', name: '外聘教师', parentId: data[i].id, order: 9999});
                        break;
                    }
                }
                chooseTeacherVm.treeData = data;
                callback && callback();
            });
        },
        data: {
            current: 1,
            size: 10,
            condition: chooseTeacherCondition,
            records: [],
            total: 0,
            pages: 0
        },
        initialized: false,
        loadData: function () {
            msgFn.load(function (end) {
                var param = chooseTeacherVm.$model.data;
                delete param.records;
                delete param.total;
                delete param.pages;
                chooseTeacherVm.condition = param.condition;
                $.ajax({
                    url: "${ctx}/optional/teacher/list",
                    type: 'post',
                    data: param,
                    success: function (data) {
                        data = data.result;
                        chooseTeacherVm.initialized = true;
                        end();
                        chooseTeacherVm.data.records = data.records;
                        chooseTeacherVm.data.total = data.total;
                        chooseTeacherVm.data.pages = data.pages;
                        $(window).resize();
                        mainVm.renderPage('chooseTeacherPage', data, function (current) {
                            chooseTeacherVm.data.current = current;
                            chooseTeacherVm.loadData();
                        });
                    }
                });
            });
        },
        clear: function () {
            chooseTeacherVm.condition = chooseTeacherCondition;
        },
        search: function () {
            chooseTeacherVm.data.current = 1;
            chooseTeacherVm.data.condition = chooseTeacherVm.$model.condition;
            chooseTeacherVm.loadData();
        },
        teachers: [],
        teacher: {},
        chooseOne: function (el) {
        },
        choose: function (e, el) {
            if (e.target.checked) {
                this.teachers.push(el.teacher);
            } else {
                avalon.Array.removeByField(this.teachers, 'id', el.teacher.id);
            }
        },
        close: function () {
        },
        $computed: {
            allChecked: {
                get: function () {
                    var teachers = this.teachers, list = this.data.records;
                    if (!teachers.length) {
                        return false;
                    }
                    for (var i = 0; i < list.length; i++) {
                        if (!avalon.Array.containsByField(teachers, 'id', list[i].teacher.id)) {
                            return false;
                        }
                    }
                    return true;
                },
                set: function (value) {
                    var teachers = this.teachers, list = this.data.records;
                    for (var i = 0; i < list.length; i++) {
                        if (value && !avalon.Array.containsByField(teachers, 'id', list[i].teacher.id)) {
                            teachers.push(list[i].teacher);
                        }
                        if (!value && avalon.Array.containsByField(teachers, 'id', list[i].teacher.id)) {
                            avalon.Array.removeByField(teachers, 'id', list[i].teacher.id);
                        }
                    }
                }
            }
        },
        init: function () {
            chooseTeacherVm.loadTreeData(function () {
                var treeData = chooseTeacherVm.$model.treeData;
                var tree = $.fn.zTree.init($("#tree"), {
                    data: {
                        simpleData: {
                            enable: true,
                            pIdKey: "parentId",
                            rootPId: "0"
                        }
                    },
                    callback: {
                        onClick: function (event, treeId, el) {
                            chooseTeacherVm.changeTree(el);
                        }
                    },
                    view: {
                        selectedMulti: false
                    }
                }, treeData);
                var rootNode = tree.getNodeByParam("parentId", '0');
                $('#' + rootNode.tId + '>a').click();
            });
        },
        changeTree: function (el) {
            chooseTeacherCondition.treeId = el.id;
            chooseTeacherVm.clear();
            chooseTeacherVm.search();
        },
        formatNodeName: function (tree) {
            var treeList = tree.parents;
            var result = '';
            if (treeList && treeList.length) for (var i = 0, j = treeList.length; i < j; i++) {
                if (!i) continue;
                if (i - 1) result += '->';
                result += treeList[i].name;
            }
            else
                result = tree.name;
            return result;
        }
    });
</script>