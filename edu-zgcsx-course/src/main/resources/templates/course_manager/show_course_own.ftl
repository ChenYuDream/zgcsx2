<#include "/layout/layout.ftl"/>
<@page title="我的已添加课程" breadWraps=[{"name":"课程管理","href":"javascript:void(0)"},{"name":"我的已添加课程"}]>
<div class="table-content ms-important" ms-important="viewTimeTable">
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
        <tr :for="(index,el) in @dataList.courseSections">
            <td :if="index == 0" rowspan="4" width="30"><b>上午</b></td>
            <td :if="index == 4" class="bg-info" rowspan="4" width="30"><b>下午</b></td>
            <td class="title" width="100">{{el.sectionIndex}}</td>
            <td :for="elem in el.courses">
                <div >
                    <h5>{{elem.courseName}}</h5>
                    <h6>{{elem.campusName}}</h6>
                    <h6>{{elem.className}}</h6>
                </div>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<script type="text/javascript">
    var vmTimeTable = avalon.define({
        $id: 'viewTimeTable',
        dataList: [],
        dataListFn: function () {
            $.ajax({
                url: "${ctx}/courseManager/query/37BCC70C9C9B4497B2EAEA429EA79B42/course/${onlineUser.id}",
                type: "POST",
                dataType: "json",
                success: function (data) {
                    vmTimeTable.dataList = data.result.teacherCourse;
                }
            });
        }
    });
    vmTimeTable.dataListFn();
</script>
</@page>