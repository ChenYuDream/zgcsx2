<div class="ms-controller" ms-controller="eventList">
    <!-- 查询条件 S-->
    <form class="form-inline">
        <div class="clearfix item-group">
            <ul class="clearfix pull-left">
                <li>
                    <input class="input" type="text" style="width: 120px" :duplex="@searchData.name"
                           placeholder="请输入空间名称...">
                </li>
                <li>
                    <select class="select" :duplex="@searchData.campusValue">
                        <option value="">== 选择校区 ==</option>
                        <#list xiaoQuList as xq>
                            <option value="${xq.itemValue}">${xq.itemText}</option>
                        </#list>
                    </select>
                </li>
                <li>
                    <select class="select" :duplex="@searchData.typeValue">
                        <option value="">== 选择类型 ==</option>
                        <#list leiXingList as lx>
                            <option value="${lx.itemValue}">${lx.itemText}</option>
                        </#list>
                    </select>
                </li>
                <li>
                    <select class="select" :duplex="@searchData.floorValue">
                        <option value="">== 选择楼层 ==</option>
                        <#list louCengList as lc>
                            <option value="${lc.itemValue}">${lc.itemText}</option>
                        </#list>
                    </select>
                </li>
            </ul>
            <div class="toolList pull-right">
                <ul class="clearfix">
                    <li>
                        <a :click="@search" class="item-btn" href="#this"><i class="iconfont icon-chazhao"></i>查找</a>
                    </li>
                    <li>
                        <a :click="@clearFn" class="item-btn" href="#this"><i class="iconfont icon-qingkong"></i>清空</a>
                    </li>
                    <li>
                        <a :click="@addFn" class="item-btn" href="#this"><i class="iconfont icon-xinzeng"></i>新增</a>
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
                <th>校区</th>
                <th>名称</th>
                <th>可容纳人数</th>
                <th>所在地点</th>
                <th>类型</th>
                <th>楼层</th>
                <th>编号</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr :for="(index,el) in @dataList">
                <td>{{el.campusTag}}</td>
                <td>{{el.mc}}</td>
                <td>{{el.volume}}</td>
                <td>{{el.addr}}</td>
                <td>{{el.sptypeTag}}</td>
                <td>{{el.flooridTag}}</td>
                <td>{{el.buildingid}}</td>
                <td>
                    <a class="btn btn-default btn-xs" :click="@toUpdate(el.id)" title="修改">修改</a>
                    <a class="btn btn-danger btn-xs" :click="@toDelete(el.id)" title="删除">删除</a>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <!-- table表单模块 E-->
    <!-- 分页+全选模块 S-->
    <div class="clearfix pagesBox">
        <div id="pages" class="pull-right"></div>
    </div>
    <!-- 分页+全选模块 E-->
</div>
<script type="text/javascript">
    var vmList = avalon.define({
        $id: 'eventList',
        searchData: {name: '', campusValue: '', typeValue: '', floorValue: '', current: 1, size: 20},
        searchData2: {name: '', campusValue: '', typeValue: '', floorValue: '', current: 1, size: 20},
        searchData3: {name: '', campusValue: '', typeValue: '', floorValue: '', current: 1, size: 20},
        dataList: [],
        dataListFn: function () {
            $.ajax({
                url: '${ctx}/space/query/page',
                type: 'POST',
                dataType: 'json',
                data: this.$model.searchData2,
                success: function (data) {
                    vmList.dataList = data.result.records;
                    layui.use('laypage', function () {
                        var laypage = layui.laypage;
                        laypage.render({
                            elem: 'pages',
                            limit: vmList.searchData2.size,
                            count: data.result.total,
                            groups: 4,
                            curr: data.result.current || 1,
                            jump: function (obj, first) {
                                if (!first) {
                                    vmList.searchData2.current = obj.curr;
                                    vmList.dataListFn();
                                }
                            }
                        });
                    });
                }
            });
        },
        clearFn: function () {
            this.searchData = vmList.$model.searchData3;
            this.search();
        },
        search: function () {
            this.searchData2 = vmList.$model.searchData;
            this.dataListFn();
        },
        addFn: function () {
            window.location.href = '${ctx}/space/to/add';
        },
        toUpdate: function (id) {
            window.location.href = '${ctx}/space/to/edit/' + id;
        },
        toDelete: function (id) {
            layer.confirm('<div class="text-center">您确认要删除该记录吗?</div>', {btnAlign: 'c'}, function () {
                $.ajax({
                    url: '${ctx}/space/delete/' + id,
                    type: 'GET',
                    dataType: 'json',
                    success: function (data) {
                        if (data.code == '0') {
                            layer.msg("删除成功!", {time: 500}, function () {
                                vmList.dataListFn();
                            });
                        } else {
                            layer.msg("删除失败")
                        }

                    }
                });
            });
        }
    });
    vmList.dataListFn();
</script>