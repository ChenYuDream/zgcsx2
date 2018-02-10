<#include "/layout/layout.ftl"/>
<@page title="课程资源" hasFooter=false breadWraps=[{"name":"课程资源"}]>
<script type="text/javascript">
    var vmCourseResource = avalon.define({
        $id : "courseResource",
        opt : {},
        toPage : function(tab){
            var _url = "";
            if(this.opt.optionalCourseId){
                _url += "&optionalCourseId="+this.opt.optionalCourseId;
            }
            _url += "&clazzId="+this.opt.clazzId+"&gradeId="+this.opt.gradeId+"&courseId="+this.opt.courseId;
            window.location.href = "${ctx}/courseResource/page/resource/tab?tab="+tab+_url;
        }
    });

    vmCourseResource.opt = {
        tab : "${map.tab!'res'}",
        clazzId : "${map.clazzId!''}",
        gradeId : "${map.gradeId!''}",
        courseId: "${map.courseId!''}",
        optionalCourseId : "${map.optionalCourseId!''}"
    }
</script>
<div ms-controller="courseResource">
    <section class="tabChange">
        <ul class="clearfix">
            <li :class="@opt.tab == 'res' ? 'active' : ''"><a :click="toPage('res')" href="javascript:void(0);" title="我的资源">我的资源</a></li>
            <li :class="@opt.tab == 'student'  ? 'active' : ''" :if="@opt.optionalCourseId != ''"><a :click="toPage('student')"  href="javascript:void(0);" title="查看学生资源">查看学生资源</a></li>
            <li :class="@opt.tab == 'up'  ? 'active' : ''"><a :click="toPage('up')"  href="javascript:void(0);" title="资源上传">资源上传</a></li>
        </ul>
    </section>

    <#if tab == 'up'>
        <#include "/upload/tab/upload_tab.ftl">
    <#else>
        <#include "/upload/tab/resource_tab.ftl">
    </#if>
</div>
</@page>