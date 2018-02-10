<!-- */ 日历插件
       */ 注:文件先后顺序 S -->
<link rel="stylesheet" type="text/css" href="${ctx}/static/common/plug-in/fullcalendar-3.6.2/fullcalendar.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/common/plug-in/fullcalendar-3.6.2/fullcalendar.print.css" media="print">
<script type="text/javascript" src="${ctx}/static/lib/fullcalendar-3.6.2/lib/moment.min.js"></script>
<script type="text/javascript" src="${ctx}/static/lib/fullcalendar-3.6.2/fullcalendar.js"></script>
<script type="text/javascript" src="${ctx}/static/lib/fullcalendar-3.6.2/locale-all.js"></script>
<script type="text/javascript" src="${ctx}/static/js/calendar-init.js"></script>

<div id='calendar'></div>
<#include "../common-tpl/footer.ftl"/>
<#--
<script>
//此处写websocket
// 打开一个 web socket
    var ws =null;
    var host=window.location.host;
    var url="ws://"+host+"${ctx}/websocket/${user.userid}";
    if ('WebSocket' in window) {
        ws = new WebSocket(url);
    } else if ('MozWebSocket' in window) {
        ws = new MozWebSocket(url);
    } else {
        console.log('WebSocket is not supported by this browser.');
    }
    ws.onopen = function()
    {
        console.log("您的浏览器支持websocket，服务器连接成功");
    };
    ws.onmessage = function (evt)
    {
        var data=evt.data;
        console.log(data);
        if('refresh'==data){
            $("#calendar").fullCalendar('refetchEvents');
        }
    };
    ws.onclose = function()
    {
        console.log("与服务器连接断开");
    };

</script>-->
