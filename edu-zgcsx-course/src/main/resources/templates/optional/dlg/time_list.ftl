<#if xxlb == "2">
    <#assign weeks = workDayWeeks>
<#elseif xxlb == "5">
    <#assign weeks = allWeeks>
</#if>
<div class="table-content" :controller="timeController">
    <table class="table">
        <thead>
        <tr>
            <th>课程<#if xxlb == '2'>详细</#if>时间</th>
            <th><#if xxlb == '2'>课程节次<#elseif xxlb == '5'>课程时长</#if></th>
            <th>第几周</th>
            <th>每周几</th>
            <th :if="!@readonly">操作</th>
        </tr>
        </thead>
        <tbody>
        <tr :for="el in @list">
            <#if xxlb == '2'>
            <td>
                <input class="form-control input-sm" type="text"
                       :attr="{id:@el.auto?'':@el.elem,value:@el.date,disabled:@el.auto}" :click="chooseDate($event,@el)">
            </td>
            <td class="text-center">
                <select class="form-control input-sm" :attr="{disabled:@el.auto}" :duplex-number="@el.period">
                    <option value="5" :attr="{selected:@el.period == 5}">第5节</option>
                    <option value="6" :attr="{selected:@el.period == 6}">第6节</option>
                </select>
            </td>
            <#elseif xxlb == '5'>
            <td>
                <input class="form-control input-sm" type="text"
                       :attr="{id:@el.auto?'':@el.elem,value:@el.datetime,disabled:@el.auto}"
                       :click="chooseDatetime($event,@el)" readonly>
            </td>
            <td>
                <input id="minutes" name="minutes" class="form-control input-sm" type="text"
                       style="display:inline-block;display:inline;zoom:1;width: 40px;"
                       :duplex="@el.minutes">&nbsp;/分钟
            </td>
            </#if>
            <td class="text-center">
                <div :if="@el.dayOfWeek || @el.dayOfWeek === 0">
                    第&nbsp;<input class="form-control input-sm text-center" style="display:inline-block;width:60px;" type="text" :attr="{value:@el.weekOfTerm,disabled:true}">&nbsp;周
                </div>
            </td>
            <td>
                <select class="form-control input-sm" :attr="{disabled:@el.auto}" :if="@el.dayOfWeek || @el.dayOfWeek === 0">
                <#list weeks as week>
                    <option value="${week.code}" :attr="{selected:@el.dayOfWeek=='${week.code}'}">${week.text}</option>
                </#list>
                </select>
            </td>
            <td :if="!@readonly">
                <button class="btn btn-default btn-xs" type="button" :click="@list.remove(el)">删除</button>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<script type="text/javascript">
    var timeVm = avalon.define({
        $id: "timeController",
        readonly: false,
        config: {
            "2": {
                index: 0,
                initialized: false,
                startDate: "",//开始时间
                period: [],//节次
                dayOfWeek: [],//周几
                oddEvenWeek: []//单双周
            }, "5": {
                index: 0,
                initialized: false,
                startDate: "",//开始时间
                startTime: "",//课程开始时间
                minutes: "60",//课程时长
                dayOfWeek: [],//周几
                oddEvenWeek: []//单双周
            }
        },
        list: [],
        init: function (config) {
            timeVm.config["${xxlb}"] = config;
            if (!config.initialized) {
                timeVm.formatTime();
            }
        },
        weeks: mainVm.$model.weeks,
        formatTime: function () {
            var config = timeVm.$model.config["${xxlb}"];
            config.dayOfWeek = config.dayOfWeek.sort(function (a, b) {
                return parseInt(a, 10) - parseInt(b, 10);
            });
            <#if xxlb == '2'>
            config.period = config.period.sort(function (a, b) {
                return parseInt(a, 10) - parseInt(b, 10);
            });
            </#if>
            var firstWeekOfTerm = mainVm.getWeekOfTerm(new Date(config.startDate));
            if (firstWeekOfTerm > mainVm.$model.xnxq.courseEndDate) {
                return;
            }
            var list = [];
            for (var i = firstWeekOfTerm; i <= mainVm.$model.xnxq.weekCount; i++) {
                var weekOfTerm = i;
                for (var j = 0; j < config.oddEvenWeek.length; j++) {
                    if (i % 2 === 1 && config.oddEvenWeek[j] !== '1') {//单周
                        continue;
                    }
                    if (i % 2 === 0 && config.oddEvenWeek[j] !== '2') {//双周
                        continue;
                    }
                    for (var x = 0; x < config.dayOfWeek.length; x++) {
                        var dayOfWeek = config.dayOfWeek[x];
                        var date = mainVm.getDate(weekOfTerm, dayOfWeek);
                        if (new Date(date).getTime() < new Date(config.startDate).getTime()) {
                            continue;
                        }
                        if (new Date(date).getTime() > new Date(mainVm.$model.xnxq.courseEndDate).getTime()) {
                            continue;
                        }
                        <#if xxlb == '2'>
                        for (var y = 0; y < config.period.length; y++) {
                            var period = config.period[y];
                            list.push({
                                date: date,
                                period: period,
                                weekOfTerm: weekOfTerm,
                                dayOfWeek: dayOfWeek,
                                auto: true
                            });
                        }
                        <#elseif xxlb == '5'>
                            list.push({
                                date: date,
                                datetime: date + " " + config.startTime,
                                startTime: config.startTime,
                                minutes: config.minutes,
                                endTime: '',
                                weekOfTerm: weekOfTerm,
                                dayOfWeek: dayOfWeek,
                                auto: true
                            });
                        </#if>
                    }
                }
            }
            timeVm.list = list;
        },
        add: function () {
            var elem = elemId + timeVm.config["${xxlb}"].index++;
            timeVm.list.push({
                elem: elem,
                date: '',
                <#if xxlb == '2'>
                period: 5,
                <#elseif xxlb == '5'>
                datetime: '',
                minutes: '60',
                startTime: '',
                endTime: '',
                </#if>
                weekOfTerm: '',
                dayOfWeek: '',
                auto: false
            });
        },
        chooseDate: function (e, el) {
            msgFn.date({
                elem: '#' + el.elem,
                done: function (value, date, endDate) {
                    if ($.isEmptyObject(value)) {
                        $('#' + el.elem).val("");
                        return;
                    }
                    var weekOfTerm = mainVm.getWeekOfTerm(new Date(value));
                    var dayOfWeek = new Date(value).getDay();
                    if (dayOfWeek === 0 || dayOfWeek === 6) {
                        msgFn.msg(false, '请选择周一至周五的日期');
                        $('#' + el.elem).val(el.date);
                    } else {
                        el.date = value;
                        el.weekOfTerm = weekOfTerm;
                        el.dayOfWeek = dayOfWeek;
                    }
                }
            });
        },
        chooseDatetime: function (e, el) {
            msgFn.datetime({
                elem: '#' + el.elem,
                format: 'yyyy-MM-dd HH:mm',
                done: function (value, date, endDate) {
                    if ($.isEmptyObject(value)) {
                        $('#' + el.elem).val("");
                        return;
                    }
                    var weekOfTerm = mainVm.getWeekOfTerm(new Date(avalon.filters.date(value, 'yyyy-MM-dd')));
                    var dayOfWeek = new Date(avalon.filters.date(value, 'yyyy-MM-dd')).getDay();
                    <#if xxlb == "2">
                    if (dayOfWeek === 0 || dayOfWeek === 6) {
                        msgFn.msg(false, '请选择周一至周五的日期');
                        $('#' + el.elem).val(el.date);
                        return;
                    }
                    </#if>
                    el.date = avalon.filters.date(value, 'yyyy-MM-dd');
                    el.datetime = avalon.filters.date(value, 'yyyy-MM-dd HH:mm');
                    el.startTime = avalon.filters.date(value, 'HH:mm');
                    el.weekOfTerm = weekOfTerm;
                    el.dayOfWeek = dayOfWeek.toString();
                }
            });
        }
    });
    var elemId = 'js_lay_date_';
</script>