<#include "/layout/layout.ftl"/>
<@page title="首页">
<div class="table-content ms-important"<#-- ms-important="viewTimeTable"-->>
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
        <#--<tr :for="(index,el) in @dataList">-->
            <#--<td :if="index == 0" rowspan="3" width="30"><b>上午</b></td>-->
            <#--<td :if="index == 3" class="bg-info" rowspan="4" width="30"><b>下午</b></td>-->
            <#--<td class="title" width="100">{{el.sectionIndex}}<h6>(<b>{{el.sectionTime}}</b>)</h6></td>-->
            <#--<td :for="elem in el.courses"-->
                <#--:class="[(elem.currentDay && elem.classId !='' ? 'bg-danger text-danger':'')]">-->
                <#--<div :if="elem.classId!=''">-->
                    <#--<h5>{{elem.teacherName}}</span></h5>-->
                    <#--<h5>{{elem.courseName}}</h5>-->
                    <#--<h6>{{elem.className}}</h6>-->
                <#--</div>-->
                <#--<i :if="elem.classId==''">-</i>-->
            <#--</td>-->
        <#--</tr>-->
        </tbody>
    </table>
</div>
<script type="text/javascript">
    <#--var vmTimeTable = avalon.define({-->
        <#--$id:'viewTimeTable',-->
        <#--dataList:[],-->
        <#--dataListFn:function(){-->
            <#--$.ajax({-->
                <#--url:"${ctx}/courseManager/query/37BCC70C9C9B4497B2EAEA429EA79B42/course/2c90a09853797045015379741bdc0204",-->
                <#--type:"POST",-->
                <#--dataType:"json",-->
                <#--success:function(data){-->
                    <#--vmTimeTable.dataList = data.result.courseSections;-->
                <#--}-->
            <#--});-->
        <#--}-->
    <#--});-->
    <#--vmTimeTable.dataListFn();-->
</script>
</@page>