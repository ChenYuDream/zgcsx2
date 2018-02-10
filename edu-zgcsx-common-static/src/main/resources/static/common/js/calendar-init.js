$(function(){
    $('#calendar').fullCalendar({
        locale: 'zh-cn',
        aspectRatio:1,
        header: {
            left: 'prev',
            center: 'title',
            right: 'next'
        },
        views: { 
            month: {
                titleFormat: 'YYYY年MMM ' 
            } 
        },
        buttonText:{
            prev: '上一月',
            next: '下一月',
        },
        firstDay:'7',
        editable: true,
        eventLimit: true,
        nextDayThreshold: '00:00:00', // 0am 设为0点，则过了0点的时间，就会显示在下一天
        events: function(start,end,timezone, callback) {
            var date = this.getDate().format('YYYY-MM');
            $.ajax({
                url: vmMain.sysPath+'/notice/calendar/data',
                dataType: 'json',
                data: {startTime:''},
                success: function(data) {
                    var events = [];
                    for(var i=0;i<data.result.length;i++){
                        events.push(data.result[i]);
                    }
                    callback(events);
                }
            });
        },
        eventClick:function (event, jsEvent, view) {
        //console.log(event.id);
            //判断事件是否存在
            $.ajax({
                url:vmMain.sysPath+"/notice/detail/exist/"+event.id,
                type:"post",
                success:function (data) {
                    if(data.code!='0'){
                        $("#calendar").fullCalendar('refetchEvents');
                        layer.msg(data.msg,function(){
                            return ;
                        });
                    }else{
                        location.href=vmMain.sysPath+"/notice/detail/"+event.id;
                    }
                }
            });
    }
    });
})