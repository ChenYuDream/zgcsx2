<!--公共课表弹出框-->
<div id="testName" class="detailTable dialog-kcxq" ms-controller="publicTimetableDetail" class="ms-controller">
    <table>
        <tbody>
        <tr>
            <td class="text-center" rowspan="3">
                <img src="${ctx}/static/common/images/gonggongke.png" alt="">
            </td>
            <th width="15%">课程名称:</th>
            <td width="20%">{{data.courseName | empty}}</td>
            <th width="15%">课程层次:</th>
            <td width="20%">{{data.kcdj | empty}}</td>
        </tr>
        <tr>
            <th>课程等级:</th>
            <td>{{data.kccc | empty}}</td>
            <th>课程类别:</th>
            <td>{{data.kclb | empty}}</td>
        </tr>
        <tr>
            <th>学习类别:</th>
            <td>{{data.xxlb | empty}}</td>
        </tr>
        <tr>
            <td colspan="5">
                <p class="text-break"><b>课程简介：</b>
                    <span :if="data.description != undefined">{{data.description}}</span>
                    <span :if="data.description == undefined">该课程暂无简介！</span>
                </p>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<script type="text/javascript">
    var vmPublicTimetableDetail = avalon.define({
        $id:"publicTimetableDetail",
        data:{}
    });
</script>
