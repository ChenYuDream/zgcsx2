<div :controller="chooseCourseController">
    <!-- 查询条件 S-->
    <form class="form-inline">
        <div class="clearfix item-group">
            <ul class="clearfix pull-left">
                <li>
                    <span>课程定义：</span>
                    <input id="courseName" name="courseName" class="input" type="text" placeholder="请输入课程定义"
                           :duplex="@condition.courseName" style="width: 110px;">
                </li>
                <li>
                    <a :click="@search" class="item-btn" href="javascript:void(0);">
                        <i class="iconfont icon-chazhao"></i>查找
                    </a>
                </li>
                <li>
                    <a :click="@clear" class="item-btn" href="javascript:void(0);">
                        <i class="iconfont icon-qingkong"></i>清空
                    </a>
                </li>
            </ul>
        </div>
    </form>
    <!-- 查询条件 E-->
    <!-- table表单模块 S-->
    <div class="table-content">
        <table class="table">
            <thead>
            <tr>
                <th>课程定义</th>
                <th>课程层次</th>
                <th>课程类别</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr :for="(index,el) in @data.records">
                <td>{{el.courseName}}</p></td>
                <td>{{el.courseDefinition}}</td>
                <td>{{el.courseCategory}}</td>
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
    var chooseCourseCondition = {
        xxlb: "${xxlb}",
        courseName: ""
    };
    var chooseCourseVm = avalon.define({
        $id: "chooseCourseController",
        condition: chooseCourseCondition,
        data: {
            current: 1,
            size: 10,
            condition: chooseCourseCondition,
            records: [],
            total: 0,
            pages: 0
        },
        close: function () {
        },
        initialized: false,
        loadData: function () {
            msgFn.load(function (end) {
                var param = chooseCourseVm.$model.data;
                delete param.records;
                delete param.total;
                delete param.pages;
                chooseCourseVm.condition = param.condition;
                $.ajax({
                    url: "${ctx}/optional/baseCourse/list",
                    type: 'post',
                    data: param,
                    success: function (data) {
                        chooseCourseVm.initialized = true;
                        end();
                        chooseCourseVm.data.records = data.result.records;
                        chooseCourseVm.data.total = data.result.total;
                        chooseCourseVm.data.pages = data.result.pages;
                        $(window).resize();
                        mainVm.renderPage(data.result, function (current) {
                            chooseCourseVm.data.current = current;
                            chooseCourseVm.loadData();
                        });
                    }
                });
            });
        },
        clear: function () {
            chooseCourseVm.condition = chooseCourseCondition;
        },
        search: function () {
            chooseCourseVm.data.current = 1;
            chooseCourseVm.data.condition = chooseCourseVm.$model.condition;
            chooseCourseVm.loadData();
        },
        choose: function (el) {
            chooseCourseVm.close();
        }
    });
</script>