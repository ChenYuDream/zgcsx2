<div class="dialogBox" :controller="chooseClazzController">
    <ul id="clazzTree" class="ztree"></ul>
</div>
<script type="text/javascript">
    var chooseClazzVm = avalon.define({
        $id: 'chooseClazzController',
        data: [],
        loadData: function (callback) {
            $.post('${ctx}/optional/clazz/list', {xxlb: "${xxlb}", campusId: "${campusId}"}, function (data) {
                var list = data.result;
                list = chooseClazzVm.buildTree(list);
                chooseClazzVm.data = list;
                callback && callback();
            });
        },
        buildTree: function (list) {
            var result = [];
            for (var i = 0; i < list.length; i++) {
                var campus = list[i], gradeList = campus.gradeList, _second = [];
                for (var j = 0; j < gradeList.length; j++) {
                    var grade = gradeList[j], clazzList = grade.clazzList, _third = [];
                    for (var k = 0; k < clazzList.length; k++) {
                        var clazz = clazzList[k];
                        _third.push(clazz);
                    }
                    if (_third.length) {
                        _second.push({id: grade.id, name: grade.name, children: _third});
                    }
                }
                if (_second.length) {
                    result.push({id: campus.id, name: campus.name, children: _second, open: true});
                }
            }
            return result;
        },
        clazzes: [],
        init: function (clazzes) {
            chooseClazzVm.loadData(function () {
                var data = chooseClazzVm.$model.data;
                if (clazzes && clazzes.length) {
                    chooseClazzVm.clazzes = clazzes;
                }
                chooseClazzVm.$tree = $.fn.zTree.init($("#clazzTree"), {
                    data: {},
                    check: {
                        enable: true
                    },
                    callback: {
                        onClick: function (event, treeId, el) {
                            chooseClazzVm.changeTree(el);
                        }
                    },
                    view: {
                        selectedMulti: false
                    }
                }, data);
                $(window).resize();
                if (clazzes && clazzes.length) {
                    chooseClazzVm.setCheckClazzs();
                }
            });
        },
        $tree: {},
        getClazzes: function () {
            var list = this.$tree.getCheckedNodes(true);
            for (var i = 0, j = list.length; i < j; i++) {
                if (list[i].level !== 2) {
                    list.splice(i--, 1);
                    j--;
                }
            }
            return list;
        },
        setCheckClazzs: function () { //设置选中
            var clazzs = this.clazzes;
            $.each(clazzs, function (index, value) {
                var node = chooseClazzVm.$tree.getNodeByParam("id", value.id);
                chooseClazzVm.$tree.checkNode(node, true, true);
            })
        }
    });
</script>