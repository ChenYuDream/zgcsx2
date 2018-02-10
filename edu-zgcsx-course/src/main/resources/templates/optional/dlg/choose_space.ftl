<div :controller="chooseSpaceController">
    <!-- 查询条件 S-->
    <form class="form-inline">
        <div class="clearfix item-group">
            <ul class="clearfix pull-left">
                <li>
                    <span>地点名称：</span>
                    <input id="name" name="name" class="input" type="text" placeholder="请输入名称"
                           :duplex="@condition.name" style="width: 110px;">
                </li>
                <li>
                    <span>校区：</span>
                    <select id="campusCode" name="campusCode" class="select" :duplex="@condition.campusCode" title="所属校区">
                        <option value="">全部</option>
                        <option :for="el in @codeData['cims_campus']" :attr={value:@el.itemValue}>{{el.itemText}}</option>
                    </select>
                </li>
                <li>
                    <span>类型：</span>
                    <select id="typeCode" name="typeCode" class="select" :duplex="@condition.typeCode" title="类型">
                        <option value="">全部</option>
                        <option :for="el in @codeData['cims_sptype']" :attr={value:@el.itemValue}>{{el.itemText}}</option>
                    </select>
                </li>
                <li>
                    <span>楼层：</span>
                    <select id="floorCode" name="floorCode" class="select" :duplex="@condition.floorCode" title="楼层">
                        <option value="">全部</option>
                        <option :for="el in @codeData['cims_floor']" :attr={value:@el.itemValue}>{{el.itemText}}</option>
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
            <tr :for="(index,el) in @data.records">
                <td>{{el.campusName}}</td>
                <td>{{el.name}}</td>
                <td>{{el.volume}}</td>
                <td>{{el.address}}</td>
                <td>{{el.typeName}}</td>
                <td>{{el.floorName}}</td>
                <td>{{el.code}}</td>
                <td>
                    <button class="btn btn-danger btn-sm" type="button" :click="choose(el)">选择</button>
                </td>
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
    var chooseSpaceCondition = {
        name: '',
        campusCode: "",
        typeCode: "",
        floorCode: ""
    };
    var chooseSpaceVm = avalon.define({
        $id: "chooseSpaceController",
        condition: chooseSpaceCondition,
        codeData: {
            'cims_campus': [],
            'cims_arch': [],
            'cims_sptype': [],
            'cims_floor': []
        },
        data: {
            current: 1,
            size: 10,
            condition: chooseSpaceCondition,
            records: [],
            total: 0,
            pages: 0
        },
        initialized: false,
        loadData: function () {
            msgFn.load(function (end) {
                var param = chooseSpaceVm.$model.data;
                delete param.records;
                delete param.total;
                delete param.pages;
                chooseSpaceVm.condition = param.condition;
                $.ajax({
                    url: "${ctx}/api/spaces",
                    type: 'post',
                    data: param,
                    success: function (data) {
                        chooseSpaceVm.initialized = true;
                        end();
                        chooseSpaceVm.data.records = data.result.records;
                        chooseSpaceVm.data.total = data.result.total;
                        chooseSpaceVm.data.pages = data.result.pages;
                        $(window).resize();
                        mainVm.renderPage(data.result, function (current) {
                            chooseSpaceVm.data.current = current;
                            chooseSpaceVm.loadData();
                        });
                    }
                });
            });
        },
        loadCode: function () {
            mainVm.codeData.load('cims_campus', 'cims_arch', 'cims_sptype', 'cims_floor', function (data) {
                var result = {};
                for (var i = 0, len = data.length; i < len; i++) {
                    var obj = data[i];
                    if (result[obj.code] && result[obj.code].length)
                        result[obj.code].push(obj);
                    else
                        result[obj.code] = [obj];
                }
                chooseSpaceVm.codeData = result;
            });
        },
        search: function () {
            chooseSpaceVm.data.current = 1;
            chooseSpaceVm.data.condition = chooseSpaceVm.$model.condition;
            chooseSpaceVm.loadData();
        },
        clear: function () {
            chooseSpaceVm.condition = chooseSpaceCondition;
        },
        choose: function (el) {
            chooseSpaceVm.close();
        },
        close: function () {
        }
    });
    chooseSpaceVm.loadCode();
</script>