<div :controller="chooseEvaluateElementController">
    <!-- table表单模块 S-->
    <div class="table-content">
        <table class="table">
            <thead>
            <tr>
                <th>评价要素</p></th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr :for="el in @list">
                <td>{{el.itemText}}</p></td>
                <td>
                    <button class="btn btn-danger btn-sm" type="button" :click="@choose(el)">选择</button>
                </td>
            </tr>
            <tr :if="!@list.length">
                <td colspan="2"><@spring.message code="course.page.empty_result"/></td>
            </tr>
            </tbody>
        </table>
    </div>
    <!-- table表单模块 E-->
</div>
<script type="text/javascript">
    var chooseEvaluateElementVm = avalon.define({
        $id: "chooseEvaluateElementController",
        list: [],
        close: function () {
        },
        choose: function (el) {
            this.close();
        }
    });
</script>