<#include "/layout/layout.ftl"/>
<#assign baseName = {"2":"选修课","5":"课后一小时"}[xxlb]/>
<#assign baseModuleName = {"2":"拓展课程","5":"开放课程"}[xxlb]/>
<#assign prefix = {"2":"optional","5":"after"}[xxlb]/>
<#if readonly??>
    <#assign pageName=baseName+"详情"/>
<#elseif id??>
    <#assign pageName="编辑"+baseName/>
<#else>
    <#assign pageName="新增"+baseName/>
</#if>
<#if xxlb == "2">
    <#assign weeks = workDayWeeks>
<#elseif xxlb == "5">
    <#assign weeks = allWeeks>
</#if>
<@page title=pageName hasFooter=false breadWraps=[{"name":baseModuleName,"href":"javascript:void(0)"},{"name":pageName}]>
<style>
    td[class=first]:before {
        content: "";
        position: absolute;
        width: 1px;
        height: 222px;
        top: 0;
        left: 0;
        background-color: #ccc;
        display: block;
        transform: rotate(-82deg);
        transform-origin: top;
    }

</style>
<!-- 新增选课模块 S-->
<section class="addxk-wrap" :controller="optionalDetailController">
    <fieldset class="layui-elem-field">
        <legend>课程基本信息</legend>
        <div class="layui-field-box">
            <table class="detailTable">
                <tbody>
                <tr>
                    <th>课程定义:</th>
                    <td>
                        <input :attr="{value:@data.baseCourse.courseName||'',title:@data.baseCourse.courseName||''}"
                               class="form-control input-sm pull-left" style="width:75%;" type="text" placeholder="请选择课程定义"
                               readonly>
                        <button class="btn btn-danger btn-sm" type="button" :click="chooseCourse" :attr="{disabled:@readonly}">选择</button>
                    </td>
                    <th>课程名称:</th>
                    <td>
                        <input id="aliasName" name="aliasName" :duplex="@data.aliasName" class="form-control input-sm" type="text" placeholder="请输入课程名称"
                               :attr="{title:@data.aliasName,disabled:@readonly}" maxlength="30">
                    </td>
                    <th>所属校区:</th>
                    <td>
                        <select class="form-control input-sm" title="所属校区" :duplex="@data.campusId" :attr="{disabled:@readonly}">
                            <option value="">请选择</option>
                            <option :for="el in campuses" :attr="{value:@el.id}">{{el.name}}</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>课程层次:</th>
                    <td>
                        <select class="form-control input-sm" disabled title="课程层次">
                            <option :for="el in codeData.courseDefinitions" :attr="{value:@el.itemValue,selected:@el.itemValue == @data.baseCourse.kcdj}">
                                {{el.itemText}}
                            </option>
                        </select>
                    </td>
                    <th>课程类别:</th>
                    <td>
                        <select class="form-control input-sm" disabled title="课程类别">
                            <option :for="el in codeData.courseCategories" :attr="{value:@el.itemValue,selected:@el.itemValue == @data.baseCourse.kclb}">
                                {{el.itemText}}
                            </option>
                        </select>
                    </td>
                    <td colspan="2" rowspan="4" class="text-center" width="25%">
                        <img id="coverImg" :attr="{src:showFile()}" width="180" height="134" alt="">
                        <div class="clearfix" style="padding:20px 8px 0;">
                            <button id="coverId" class="btn btn-primary btn-sm" type="button" :attr="{disabled:@readonly}">上传封面</button>
                            <button class="btn btn-default btn-sm" type="button" :attr="{disabled:@readonly||!(@data.coverId||@coverImg)}" :click="clearCover">删除封面</button>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>课程简介:</th>
                    <td colspan="3">
                        <textarea id="description" name="description" :duplex="@data.description" class="form-control" rows="5" placeholder="请输入课程简介" :attr="{disabled:@readonly}"
                                  maxlength="1000"></textarea>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </fieldset>
    <fieldset class="layui-elem-field">
        <legend>课程日期设置</legend>
        <div class="layui-field-box">
            <table class="detailTable">
                <tbody>
                <tr>
                    <th>选课开始时间:</th>
                    <td>
                        <input id="chooseStartTime" name="chooseStartTime" :duplex="@data.chooseStartTime" readonly class="form-control input-sm" type="text" title="选课开始时间"
                               :attr="{disabled:@readonly}" :click="renderDateTime">
                    </td>
                    <th>选课结束时间:</th>
                    <td>
                        <input id="chooseEndTime" name="chooseEndTime" :duplex="@data.chooseEndTime" readonly class="form-control input-sm" type="text" title="选课结束时间"
                               :attr="{disabled:@readonly}" :click="renderDateTime">
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </fieldset>
    <fieldset class="layui-elem-field">
        <legend>详细时间设置</legend>
        <div class="layui-field-box">
            <div class="table-content">
                <table class="table">
                    <thead>
                    <tr>
                        <th :if="!@data.id">课程起始时间</th>
                        <#if xxlb == '2'>
                        <th :if="!@data.id">课程节次</th>
                        <#elseif xxlb == '5'>
                        <th :if="!@data.id">课程开始时间</th>
                        <th :if="!@data.id" style="min-width: 94px;">课程时长</th>
                        </#if>
                        <th :if="!@data.id"<#if xxlb == '5'> style="min-width: 314px;"</#if>>课程周次</th>
                        <th :if="!@data.id" style="min-width: 98px;">单双周</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td :if="!@data.id">
                            <input id="startDate" name="startDate" class="form-control input-sm" type="text" readonly :duplex="timeConfig[${xxlb}].startDate"
                                   :click="renderDate({elem:'#startDate'})">
                        </td>
                        <#if xxlb == '2'>
                        <td class="text-center" :if="!@data.id">
                            <label><input type="checkbox" name="period" value="5" :duplex-number="timeConfig[${xxlb}].period">第5节</label>
                            <label><input type="checkbox" name="period" value="6" :duplex-number="timeConfig[${xxlb}].period">第6节</label>
                        </td>
                        <#elseif xxlb == '5'>
                        <td :if="!@data.id">
                            <input id="startTime" name="startTime" class="form-control input-sm" type="text" readonly :duplex="timeConfig[${xxlb}].startTime"
                                   :click="renderTime($event)">
                        </td>
                        <td :if="!@data.id">
                            <input id="minutes" name="minutes" class="form-control input-sm" type="text" style="display:inline-block;display:inline;zoom:1;width: 40px;"
                                   :duplex="timeConfig[${xxlb}].minutes">&nbsp;/分钟
                        </td>
                        </#if>
                        <td :if="!@data.id">
                            <#list weeks as week>
                                <label><input type="checkbox" name="week" value="${week.code}" :duplex="timeConfig[${xxlb}].dayOfWeek">${week.text}</label>
                            </#list>
                        </td>
                        <td class="text-center" :if="!@data.id">
                            <label><input type="checkbox" name="oddEvenWeek" value="1" :duplex="timeConfig[${xxlb}].oddEvenWeek">单周</label>
                            <label><input type="checkbox" name="oddEvenWeek" value="2" :duplex="timeConfig[${xxlb}].oddEvenWeek">双周</label>
                        </td>
                        <td>
                            <button :if="!@timeConfig[${xxlb}].initialized" class="btn btn-default btn-xs" type="button" :click="openTimeDetail">初始化</button>
                            <button :if="@timeConfig[${xxlb}].initialized" class="btn btn-default btn-xs" type="button" :click="openTimeDetail">查看</button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </fieldset>
    <fieldset class="layui-elem-field">
        <legend>课程条件设置</legend>
        <div class="layui-field-box">
            <table class="detailTable">
                <tbody>
                <tr>
                    <th>适用班级:</th>
                    <td class="clearfix">
                        <input class="form-control input-sm pull-left" style="width:65%;" type="text"
                               :attr="{value:@formatClazzes(@data.clazzes)||'',title:@formatClazzes(@data.clazzes)||''}" placeholder="请输入适用班级..." disabled>
                        <button class="btn btn-danger btn-sm" type="button" :click="showClazzes" :attr="{disabled:@readonly}">添加</button>
                        <button class="btn btn-default btn-sm" type="button" :click="@data.clazzes=[]" :attr="{disabled:@readonly}">清空</button>
                    </td>
                    <th>上课地点:</th>
                    <td class="clearfix">
                        <input class="form-control input-sm pull-left" style="width:65%;" type="text"
                               :attr="{value:@formatSpace(@data.space)||'',title:@formatSpace(@data.space)||''}" placeholder="请添加上课地点..." disabled>
                        <button class="btn btn-danger btn-sm" type="button" :click="chooseSpace" :attr="{disabled:@readonly}">选择</button>
                    </td>
                </tr>
                <tr>
                    <th>任课教师:</th>
                    <td class="clearfix">
                        <input class="form-control input-sm pull-left" style="width:65%;" type="text"
                               :attr="{value:@formatArr(@data.teachers,'name'),title:@formatArr(@data.teachers,'name')}" placeholder="请添加任课教师..." disabled>
                        <button class="btn btn-danger btn-sm" type="button" :click="showTeachers" :attr="{disabled:@readonly}">添加</button>
                        <button class="btn btn-default btn-sm" type="button" :click="@data.teachers=[]" :attr="{disabled:@readonly}">清空</button>
                    </td>
                    <th>人数限制:</th>
                    <td class="clearfix">
                        <input id="limitCount" name="limitCount" :duplex="@data.limitCount" class="form-control input-sm pull-left" style="width:65%;" type="text"
                               placeholder="请输入限制人数..." :attr="{disabled:@readonly}">
                        <span style="line-height: 30px">&nbsp;&nbsp;/人</span>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </fieldset>
    <fieldset class="layui-elem-field" :visible="false">
        <legend>条件筛选</legend>
        <div class="layui-field-box">
            <table class="detailTable">
                <tbody>
                <tr>
                    <th width="20%">发送提醒:</th>
                    <td class="text-center" width="30%">
                        <label><input type="radio" name="courseRemind" value="1" :duplex="@data.courseRemind" :attr="{disabled:@readonly}">是</label>
                        <label><input type="radio" name="courseRemind" value="0" :duplex="@data.courseRemind" :attr="{disabled:@readonly}">否</label>
                    </td>
                    <th width="20%">日历通知等级:</th>
                    <td width="30%">
                        <select class="form-control input-sm" id="noticeLevel" name="noticeLevel" :duplex="@data.noticeLevel" title="日历通知等级" :attr="{disabled:@readonly}">
                            <option :for="(key,val) in noticeLevels" :attr="{value:@key}">{{val}}</option>
                        </select>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </fieldset>
    <#if xxlb == '2'>
    <fieldset class="layui-elem-field">
        <legend>学业评价量规</legend>
        <div class="layui-field-box">
            <table class="detailTable">
                <tbody>
                <tr>
                    <td class="first" style="position: relative;width: 220.98px;">
                        <span style="position: absolute;top: 10px;left: 12px;">评价要素</span>
                        <span style="position: absolute;top: 5px;right: 12px;">评价标准</span>
                    </td>
                    <td :visible="!@readonly">分级</td>
                    <td class="text-center" :for="el in @codeData.evaluateLevels" style="min-width: 50px;">{{el.itemText}}</td>
                    <td :visible="!@readonly">操作</td>
                </tr>
                <tr :for="el in @data.evaluateElements">
                    <th class="text-center">
                        <input :visible="!@readonly" class="form-control input-sm pull-left" style="width:65%;" type="text" :duplex="@el.element" :attr="{disabled:@readonly}"
                               maxlength="50" placeholder="请输入评价要素">
                        <button :visible="!@readonly" class="btn btn-danger btn-sm" type="button" :attr="{disabled:@readonly}" :click="chooseEvaluateElement(@el)">选择</button>
                        <p class="text-break text-left" :visible="@readonly" :attr="{title:@el.element}">{{el.element}}</p>
                    </th>
                    <td class="text-center" :visible="!@readonly">
                        <select class="form-control input-sm" :duplex="@el.type" :change="changeEvaluateElement($event,el)">
                            <option value="1">分级</option>
                            <option value="2">不分级</option>
                        </select>
                    </td>
                    <td :if="@el.type=='1'" class="text-center" :for="(levelIndex,evaluateLevel) in @codeData.evaluateLevels">
                        <input maxlength="200" :visible="!@readonly" class="form-control input-sm" :duplex="@el.evaluateElementValues[@levelIndex].content">
                        <p class="text-break text-left" :visible="@readonly" :attr="{title:@el.evaluateElementValues[levelIndex].content}">
                            {{el.evaluateElementValues[levelIndex].content}}</p>
                    </td>
                    <td :if="@el.type=='2'" class="text-center" :attr="{colspan:@codeData.evaluateLevels.length}">
                        <input maxlength="200" :visible="!@readonly" class="form-control input-sm" :duplex="@el.evaluateElementValues[0].content">
                        <p class="text-break text-left" :visible="@readonly" :attr="{title:@el.evaluateElementValues[0].content}">{{el.evaluateElementValues[0].content}}</p>
                    </td>
                    <td :visible="!@readonly" class="text-center">
                        <button class="btn btn-default btn-xs" type="button" :click="@data.evaluateElements.remove(el)">删除</button>
                    </td>
                </tr>
                <tr :visible="!@readonly">
                    <td colspan="6" class="text-center">
                        <button class="btn btn-default btn-xs" type="button" :click="addEvaluateElement">添加</button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </fieldset>
    </#if>
    <div class="text-center" :if="!@readonly">
        <button class="btn btn-primary" type="button" :click="save">保存</button>
    </div>
</section>
<!-- 新增选课模块 E-->
<script>
    var evaluateElementModel = {
        element: '',
        type: '1',
        evaluateElementValues: []
    }, evaluateElementValueModel = {
        evaluateLevel: '',
        content: ''
    };
    var optionalDetailVm = avalon.define({
        $id: 'optionalDetailController',
        initialized: false,//初始化
        data: {//选修课数据集
            id: '${id!}',
            courseId: '',
            campusId: '',
            aliasName: '',
            description: '',
            chooseStartTime: '',
            chooseEndTime: '',
            courseType: '${xxlb}',
            baseCourse: {},
            teachers: [
                {id: '${onlineUser.id}', name: '${onlineUser.userName!}'}
            ],
            evaluateElements: [],
            coverId: '',
            workDays: [],
            clazzes: [],
            space: {},
            limitCount: '',
            courseRemind: '1',
            noticeLevel: '0001'
        },
        coverImg: '',
        init: function () {
            var id = '${id!}';
            if (id) {
                $.post('${ctx}/optional/detail', {id: id}, function (data) {
                    if (data.result) {
                        data.result.description = data.result.description || '';
                        data.result.coverId = data.result.coverId || '';
                        optionalDetailVm.data = data.result;
                        optionalDetailVm.timeConfig[${xxlb}].initialized = true;
                        optionalDetailVm.initialized = true;
                    } else
                        location.href = '${ctx}/${prefix}/${showType!}_${prefix}';
                });
            } else {
                optionalDetailVm.initialized = true;
            }
            if (this.readonly) {
                return;
            }
            uploader = new plupload.Uploader({
                browse_button: 'coverId', //触发文件选择对话框的按钮，为那个元素id
                url: '${ctx}/file/file_upload', //服务器端的上传页面地址
                filters: {
                    max_file_size: '10mb',
                    mime_types: [
                        {title: "Image files", extensions: "jpg,jpeg,gif,png"}
                    ]
                },
                file_data_name: "file",
                resize: {
                    width: 180,
                    height: 134,
                    crop: false,
                    // quality: 60,
                    preserve_headers: false
                },
                multi_selection: false,
                flash_swf_url: '${ctx}/static/common/plug-in/plupload-2.3.6/js/Moxie.swf',//swf文件，当需要使用swf方式进行上传时需要配置该参数
                silverlight_xap_url: '${ctx}/static/common/plug-in/plupload-2.3.6/js/Moxie.xap',//silverlight文件，当需要使用silverlight方式进行上传时需要配置该参数
                init: {
                    FilesAdded: function (uploader, files) {
                        for (var i = uploader.files.length - 2; i >= 0; i--) {
                            uploader.removeFile(uploader.files[i]);
                        }
                        // console.log(uploader);
                        // console.log(files);
                        // uploader.start();
                        var fr = new moxie.file.FileReader();
                        fr.onload = function () {
                            optionalDetailVm.coverImg = fr.result;
                            fr.destroy();
                            fr = null;
                        };
                        fr.readAsDataURL(files[0].getSource());
                    },
                    Error: function (uploader, errObject) {
                        if (errObject.code === plupload.FILE_SIZE_ERROR) {
                            msgFn.msg(false, '请上传10M以内大小的文件')
                        }
                    },
                    FileFiltered: function (uploader, file) {
                        if (file.name.length > 50) {
                            msgFn.msg(false, "文件名称不能超过50字符");
                            uploader.removeFile(file);
                        }
                    },
                    FileUploaded: function (uploader, file, responseObject) {
                        var data = eval('(' + responseObject.response + ')');
                        // console.log(data);
                        optionalDetailVm.data.coverId = data.result.id;
                        uploader.removeFile(file);
                        optionalDetailVm.doSave();
                    }
                }
            });
            uploader.init();
        },
        clearCover: function () {
            optionalDetailVm.data.coverId = '';
            optionalDetailVm.coverImg = '';
            if (uploader.files.length) {
                uploader.removeFile(uploader.files[0]);
            }
        },
        showFile: function () {
            if (this.coverImg) {
                return this.coverImg;
            }
            if (this.data.coverId) {
                return mainVm.showFile(this.data.coverId);
            }
            return mainVm.defaultImg["${prefix}"].edit;
        },
        readonly:${(readonly!false)?string('true','false')},
        timeConfig: {
            "2": {
                index: 0,
                initialized: false,
                startDate: "",//开始时间
                period: [],//节次
                dayOfWeek: [],//周几
                oddEvenWeek: []//单双周
            },
            "5": {
                index: 0,
                initialized: false,
                startDate: "",//开始时间
                startTime: "",//课程开始时间
                minutes: "60",//课程时长
                dayOfWeek: [],//周几
                oddEvenWeek: []//单双周
            }
        },
        checkTimeConfig: function () {
            var timeConfig = optionalDetailVm.$model.timeConfig[${xxlb}];
            if (!timeConfig.startDate) {
                msgFn.msg(false, "请选择课程起始时间");
                return false;
            }
            if (!timeConfig.dayOfWeek.length) {
                msgFn.msg(false, "请选择周几");
                return false;
            }
            if (!timeConfig.oddEvenWeek.length) {
                msgFn.msg(false, "请选择单双周");
                return false;
            }
            <#if xxlb == '2'>
            if (!timeConfig.period.length) {
                msgFn.msg(false, "请选择节次");
                return false;
            }
            <#elseif xxlb == '5'>
            if (!timeConfig.startTime) {
                msgFn.msg(false, "请选择课程开始时间");
                return false;
            }
            if (!timeConfig.minutes) {
                msgFn.msg(false, "请输入课程时长");
                return false;
            }
            if (!/^[1-9]*[1-9][0-9]*$/.test(timeConfig.minutes)) {
                msgFn.msg(false, "课程时长必须是正整数");
                return false;
            }
            if (parseInt(timeConfig.minutes, 10) > 99) {
                msgFn.msg(false, "课程时长不能超过99分钟");
                return false;
            }
            </#if>
            return true;
        },
        formatSpace: function (space) {
            if (space.typeName && space.name && space.floorName && space.campusName) {
                return '(' + space.typeName + ')' + space.name + '[' + space.floorName + ']-' + space.campusName;
            } else {
                return '';
            }
        },
        openTimeDetail: function () {
            var timeConfig = optionalDetailVm.$model.timeConfig[${xxlb}], xnxq = mainVm.$model.xnxq;
            if (!(timeConfig.initialized || optionalDetailVm.checkTimeConfig())) {
                return;
            }
            <#if !id??>
            if (timeConfig.startDate < xnxq.startDate) {
                return msgFn.msg(false, "<@spring.message code="course.optional.merge-start-time-xnxq-start-time"/>");
            }
            </#if>
            msgFn.dlg({
                title: "详细时间",
                url: "${ctx}/optional/time_list?xxlb=${xxlb}",
                width: '600px',
                height: '500px',
                success: function (callback, close) {
                    var list = optionalDetailVm.$model.data.workDays;
                    <#if xxlb=='5'>
                    for (var i = 0; i < list.length; i++) {
                        var workDay = list[i];
                        if (!workDay.datetime) {
                            workDay.datetime = workDay.date + " " + workDay.startTime;
                        }
                        if (!workDay.minutes) {
                            var sh = parseInt(workDay.startTime.split(":")[0], 10);
                            var sm = parseInt(workDay.startTime.split(":")[1], 10);
                            var eh = parseInt(workDay.endTime.split(":")[0], 10);
                            var em = parseInt(workDay.endTime.split(":")[1], 10);
                            workDay.minutes = (eh - sh) * 60 + (em - sm);
                        }
                    }
                    </#if>
                    timeVm.list = list;
                    timeVm.init(timeConfig);
                    timeVm.readonly = optionalDetailVm.readonly;
                    callback();
                },
                btn: optionalDetailVm.readonly ? '' : [
                    <#if !id??>
                    {
                        name: '重置', ok: function (close) {
                            if (!optionalDetailVm.checkTimeConfig()) {
                                return;
                            }
                            timeVm.formatTime();
                        }
                    },</#if> {
                        name: '添加', ok: function (close) {
                            timeVm.add();
                        }
                    }, {
                        name: '确定', ok: function (close) {
                            var list = timeVm.$model.list;
                            var checkMap = {};
                            for (var i = 0; i < list.length; i++) {
                                var workDay = list[i];
                            <#if xxlb=='2'>
                            if (!workDay.date) {
                                return msgFn.msg(false, '请选择时间！');
                            }
                            if (!workDay.period) {
                                return msgFn.msg(false, '请选择节次');
                            }
                            var periods = checkMap[workDay.date];
                            if (periods) {
                                if (periods.indexOf(workDay.period) !== -1) {
                                    return msgFn.msg(false, workDay.date + '第' + workDay.period + '节课数据重复！');
                                }
                                periods.push(workDay.period);
                            } else {
                                checkMap[workDay.date] = [workDay.period];
                            }
                            <#elseif xxlb =='5'>
                            if (!workDay.date || !workDay.datetime || !workDay.startTime) {
                                return msgFn.msg(false, '请选择详细时间！');
                            }
                            if (!workDay.minutes) {
                                return msgFn.msg(false, '请输入课程时长');
                            }
                            var endHour = parseInt(workDay.startTime.split(":")[0], 10);
                            if (endHour < 7) {
                                return msgFn.msg(false, "<@spring.message code="course.optional.between-course-time"/>");
                            }
                            var endMinutes = parseInt(workDay.startTime.split(":")[1], 10);
                            endMinutes += parseInt(workDay.minutes, 10);
                            while (endMinutes >= 60) {
                                endMinutes -= 60;
                                endHour++;
                            }
                            if (endHour + endMinutes / 100 > 21) {
                                return msgFn.msg(false, "<@spring.message code="course.optional.between-course-time"/>");
                            }
                            if (endHour < 10) {
                                endHour = "0" + endHour;
                            }
                            if (endMinutes < 10) {
                                endMinutes = "0" + endMinutes;
                            }
                            workDay.endTime = endHour + ":" + endMinutes;
                            </#if>
                            }
                            optionalDetailVm.timeConfig[${xxlb}].initialized = true;
                            optionalDetailVm.timeConfig[${xxlb}].index = timeVm.config[${xxlb}].index;
                            optionalDetailVm.data.workDays = list;
                            close();
                        }
                    }, {
                        name: '取消', ok: function (close) {
                            close();
                        }
                    }
                ]
            });
        },
        space: {},
        chooseCourse: function () {
            msgFn.dlg({
                title: "选择课程",
                url: "${ctx}/optional/choose_course?xxlb=${xxlb}",
                width: '600px',
                success: function (callback, close) {
                    chooseCourseVm.close = close;
                    chooseCourseVm.choose = function (el) {
                        optionalDetailVm.data.courseId = el.id;
                        optionalDetailVm.data.baseCourse = el;
                        close();
                    };
                    chooseCourseVm.search();
                    callback();
                }
            });
        },
        chooseSpace: function () {
            msgFn.dlg({
                title: '选择上课地点',
                url: '${ctx}/optional/choose_space',
                width: '800px',
                success: function (callback, close) {
                    chooseSpaceVm.close = close;
                    chooseSpaceVm.choose = function (el) {
                        optionalDetailVm.data.space = el;
                        close();
                    };
                    chooseSpaceVm.search();
                    callback();
                }
            });
        },
        <#--chooseSpace: function () {-->
            <#--msgFn.dlg({-->
                <#--title: '选择上课地点',-->
                <#--url: '${ctx}/optional/choose_space',-->
                <#--width: '800px',-->
                <#--success: function (end) {-->
                    <#--end();-->
                    <#--var space = optionalDetailVm.$model.data.space;-->
                    <#--chooseSpaceVm.loadData(space.id ? space : null);-->
                <#--},-->
                <#--btn: [{-->
                    <#--name: '确定', ok: function (close) {-->
                        <#--optionalDetailVm.data.space = chooseSpaceVm.$model.space;-->
                        <#--close();-->
                    <#--}-->
                <#--}, {-->
                    <#--name: '取消', ok: function (close) {-->
                        <#--close();-->
                    <#--}-->
                <#--}]-->
            <#--});-->
        <#--},-->
        showTeachers: function () {
            msgFn.dlg({
                title: '选择任课教师',
                url: '${ctx}/optional/choose_teacher',
                width: '800px',
                success: function (end) {
                    end();
                    chooseTeacherVm.init();
                    chooseTeacherVm.teachers = optionalDetailVm.$model.data.teachers;
                },
                btn: [{
                    name: '确定', ok: function (close) {
                        optionalDetailVm.data.teachers = chooseTeacherVm.$model.teachers;
                        close();
                    }
                }, {
                    name: '取消', ok: function (close) {
                        close();
                    }
                }]
            });
        },
        showClazzes: function () {
            var campusId = this.data.campusId;
            if (!campusId) {
                return msgFn.msg(false, "请先选择校区！");
            }
            msgFn.dlg({
                title: '适用班级',
                url: '${ctx}/optional/choose_clazz?xxlb=${xxlb}&campusId=' + campusId,
                width: '200px',
                height: '400px',
                success: function (end) {
                    end();
                    chooseClazzVm.init(optionalDetailVm.$model.data.clazzes);
                },
                btn: [{
                    name: '确定', ok: function (close) {
                        optionalDetailVm.data.clazzes = chooseClazzVm.getClazzes();
                        close();
                    }
                }, {
                    name: '取消', ok: function (close) {
                        close();
                    }
                }]
            });
        },
        save: function () {
            if (!this.data.chooseStartTime) {
                return msgFn.msg(false, "<@spring.message code="course.optional.non-choose-start-time"/>");
            }
            if (!this.data.chooseEndTime) {
                return msgFn.msg(false, "<@spring.message code="course.optional.non-choose-end-time"/>");
            }
            if (!this.data.limitCount) {
                return msgFn.msg(false, "<@spring.message code="course.optional.non-limit-count"/>");
            }
            if (!/^[1-9]*[1-9][0-9]*$/.test(this.data.limitCount)) {
                return msgFn.msg(false, "<@spring.message code="course.optional.min-limit-count"/>");
            }
            if (this.data.limitCount > 1000) {
                return msgFn.msg(false, "<@spring.message code="course.optional.max-limit-count"/>");
            }
            if (this.data.chooseEndTime < '${.now?string("yyyy-MM-dd HH:mm:ss")}') {
                return msgFn.msg(false, "<@spring.message code="course.optional.merge-choose-end-time-sysdate"/>");
            }
            msgFn.load(function (end) {
                window.end = end;
                if (uploader.files && uploader.files.length) {
                    uploader.start();
                } else {
                    optionalDetailVm.doSave();
                }
            });
        },
        doSave: function () {
            $.ajax({
                url: '${ctx}/optional/save',
                type: 'post',
                contentType: 'application/json',
                data: JSON.stringify(optionalDetailVm.$model.data),
                success: function (data) {
                    msgFn.msg(data);
                    location.href = "${ctx}/${prefix}/${showType!}_${prefix}";
                    end();
                }
            });
        },
        addEvaluateElement: function () {
            this.data.evaluateElements.push(clone(evaluateElementModel));
        },
        changeEvaluateElement: function (e, el) {
            var evaluateElementValues = [];
            if (e.target.value === '1') {
                var evaluateLevels = mainVm.$model.codeData.evaluateLevels;
                for (var i = 0; i < evaluateLevels.length; i++) {
                    var evaluateElementValue = clone(evaluateElementValueModel);
                    evaluateElementValue.evaluateLevel = evaluateLevels[i].itemValue;
                    evaluateElementValues.push(evaluateElementValue);
                }
            } else if (e.target.value === '2') {
                var evaluateElementValue2 = clone(evaluateElementValueModel);
                evaluateElementValues.push(evaluateElementValue2);
            }
            el.evaluateElementValues = evaluateElementValues;
        },
        chooseEvaluateElement: function (el) {
            msgFn.dlg({
                title: "选择评价要素",
                url: "${ctx}/optional/choose_evaluate_element",
                width: '600px',
                success: function (callback, close) {
                    chooseEvaluateElementVm.close = close;
                    chooseEvaluateElementVm.list = mainVm.$model.codeData.evaluateElements;
                    chooseEvaluateElementVm.choose = function (data) {
                        el.element = data.itemText;
                        close();
                    };
                    callback();
                }
            });
        },
        refreshTimeConfig: function () {
            optionalDetailVm.data.workDays = [];
            optionalDetailVm.timeConfig[${xxlb}].initialized = false;
        }
    });
    $(function () {
        mainVm.loadCampuses();
        mainVm.codeData.loadCourseDefinitions();
        mainVm.codeData.loadCourseCategories();
        mainVm.codeData.loadEvaluateLevels(function (data) {
            for (var i = 0; i < data.length; i++) {
                var evaluateElementValue = clone(evaluateElementValueModel);
                evaluateElementValue.evaluateLevel = data[i].itemValue;
                evaluateElementModel.evaluateElementValues.push(evaluateElementValue);
            }
        });
        mainVm.codeData.loadEvaluateElements();
        optionalDetailVm.$watch("data.campusId", function (newValue, oldValue) {
            if (!optionalDetailVm.initialized) {
                return;
            }
            if (newValue !== oldValue) {
                optionalDetailVm.data.clazzes = [];
            }
        });
        optionalDetailVm.$watch("timeConfig[${xxlb}].startDate", function (newValue, oldValue) {
            optionalDetailVm.refreshTimeConfig();
        });
        optionalDetailVm.$watch("timeConfig[${xxlb}].dayOfWeek", function (newValue, oldValue) {
            optionalDetailVm.refreshTimeConfig();
        });
        optionalDetailVm.$watch("timeConfig[${xxlb}].oddEvenWeek", function (newValue, oldValue) {
            optionalDetailVm.refreshTimeConfig();
        });
        <#if xxlb == "2">
        optionalDetailVm.$watch("timeConfig[${xxlb}].period", function (newValue, oldValue) {
            optionalDetailVm.refreshTimeConfig();
        });
        <#elseif xxlb == "5">
        optionalDetailVm.$watch("timeConfig[${xxlb}].startTime", function (newValue, oldValue) {
            optionalDetailVm.refreshTimeConfig();
        });
        optionalDetailVm.$watch("timeConfig[${xxlb}].minutes", function (newValue, oldValue) {
            optionalDetailVm.refreshTimeConfig();
        });
        </#if>
        optionalDetailVm.init();
    });
</script>
</@page>