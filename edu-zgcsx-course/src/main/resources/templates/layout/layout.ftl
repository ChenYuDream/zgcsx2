<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#include "/layout/spring.ftl"/>
<#include "/layout/frame/head.ftl"/>
<#include "/layout/frame/top.ftl"/>
<#include "/layout/frame/right.ftl"/>
<#include "/layout/frame/left.ftl"/>
<#include "/layout/frame/footer.ftl"/>
<#include "/layout/frame/nav_show.ftl"/>
<#macro page title breadWraps=[{"name":"首页"}] hasFooter=true>
<!DOCTYPE html>
<html lang="zh-CN">
    <@head title=title></@head>
<body :class="@skin" class="skin ms-controller" :controller="main">
    <@right></@right>
<div class="container" :controller="base">
    <@left></@left>
    <!-- 主内容区域 S-->
    <div class="main">
        <@top title=title></@top>
        <!-- table切换 E-->
        <section class="main-wrap">
            <div class="content">
                <@navShow breadWraps=breadWraps></@navShow>
            <#--模板页面自定义引入 start-->
                <#nested>
            <#--模板页面自定义引入 end-->
                <@footer></@footer>
            </div>
        </section>
    </div>
    <!-- 主内容区域 E-->
</div>
<script type="text/javascript">
    var littleGrades = ['一', '二', '三', '四', '五', '六'];
    var condition = {};
    var mainVm = avalon.define({
        $id: "base",
        toPage: function (href) {
            location.href = href;
        },
        condition: {},
        data: {
            current: 1,
            size: 20,
            condition: {},
            records: [],
            total: 0,
            pages: 0
        },
        hasFooter:${hasFooter?c},
        toolBtnList: [{name: '返回', href: 'javascript:history.go(-1);', icon: 'icon'}],
        dataUrl: "",
        initialized: false,
        loadData: function () {
            msgFn.load(function (end) {
                var param = mainVm.$model.data;
                delete param.records;
                delete param.total;
                delete param.pages;
                mainVm.condition = param.condition;
                $.ajax({
                    url: mainVm.$model.dataUrl,
                    type: 'post',
                    data: param,
                    success: function (data) {
                        mainVm.initialized = true;
                        end();
                        mainVm.data.records = data.result.records;
                        mainVm.data.total = data.result.total;
                        mainVm.data.pages = data.result.pages;
                        mainVm.renderPage(data.result, function (current) {
                            mainVm.data.current = current;
                            mainVm.loadData();
                        });
                        mainVm.hasFooter = data.result.pages <= 1;
                    }
                });
            });
        },
        renderPage: function (elem, data, callback) {
            if (typeof callback === 'undefined') {
                callback = data;
                data = elem;
                elem = undefined;
            }
            layui.use('laypage', function () {
                layui.laypage.render({
                    elem: elem || "pagination",//div的ID
                    count: data.total,//数据量
                    limit: data.size || 15, //每页数据量
                    curr: data.current || 1,
                    theme: '#237bc4',
                    jump: function (obj, first) {//跳转页面时的回调,obj:配置项参数,first:是否为首次加载
                        if (!first)
                            callback && callback(obj.curr);
                    }
                });
            });
        },
        clear: function () {
            mainVm.condition = condition;
        },
        search: function () {
            mainVm.data.current = 1;
            mainVm.data.condition = mainVm.$model.condition;
            mainVm.loadData();
        },
        initPage: function (url, _condition) {
            condition = _condition;
            mainVm.condition = condition;
            mainVm.dataUrl = url;
            mainVm.data.condition = condition;
        },
//        initList: function (url, _condition) {
//            condition = _condition;
//            mainVm.condition = condition;
//            mainVm.dataUrl = url;
//            mainVm.data.condition = condition;
//        },
        defaultImg: {
            optional: {
                show: "${ctx}/static/common/images/xuanxiuke-big.png",
                edit: "${ctx}/static/common/images/xk-default.jpg"
            },
            after: {
                edit: "${ctx}/static/common/images/khyxs-default.jpg",
                show: "${ctx}/static/common/images/kehouyixiaoshi-big.png"
            }
        },
        campuses: [],
        grades: [],
        clazzes: [],
        loadCampuses: function () {
            $.post("${ctx}/api/campus", function (data) {
                mainVm.campuses = data.result;
            });
        },
        loadGrades: function (campusId) {
            $.post("${ctx}/api/grade", {campusId: campusId}, function (data) {
                mainVm.grades = data.result;
            });
        },
        loadClazzes: function (gradeId) {
            $.post("${ctx}/api/clazz", {gradeId: gradeId}, function (data) {
                mainVm.clazzes = data.result;
            });
        },
        watchClazz: function () {
            mainVm.$watch("condition.campusId", function (newValue, oldValue) {
                if (newValue) {
                    mainVm.loadGrades(newValue);
                }
                mainVm.condition.gradeId = "";

            });
            mainVm.$watch("condition.gradeId", function (newValue, oldValue) {
                if (newValue) {
                    mainVm.loadClazzes(newValue);
                }
                mainVm.condition.clazzId = "";

            });
        },
        codeData: {
            courseCategories: [],//六类定义
            courseDefinitions: [],//三层定义
            courseLevels: [],//课程层次：国家、地方、校本、自主
            evaluateLevels: [],
            evaluateElements: [],
            CONSTANTS: {
                COURSE_CATEGORY: 'cims_kcly_ll',
                COURSE_DEFINITION: 'cims_kcly_sc',
                COURSE_LEVEL: 'cims_kc_cc',
                EVALUATE_LEVEL: 'kc_eva_level',
                EVALUATE_ELEMENT: 'kc_eva_element'
            },
            load: function () {
                var len = arguments.length;
                if (len < 2) {
                    return;
                }
                var callback = arguments[len - 1];
                var codes = [];
                for (var i = 0; i < len - 1; i++) {
                    codes.push(arguments[i]);
                }
                $.post("${ctx}/api/codemap", {code: codes.join()}, function (data) {
                    callback(data);
                });
            },
            loadCourseCategories: function () {
                var _this = this;
                this.load(this.CONSTANTS.COURSE_CATEGORY, function (data) {
                    _this.courseCategories = data;
                });
            },
            loadCourseDefinitions: function () {
                var _this = this;
                this.load(this.CONSTANTS.COURSE_DEFINITION, function (data) {
                    _this.courseDefinitions = data;
                });
            },
            loadCourseLevels: function () {
                var _this = this;
                this.load(this.CONSTANTS.COURSE_LEVEL, function (data) {
                    _this.courseLevels = data;
                });
            },
            loadEvaluateLevels: function (callback) {
                var _this = this;
                this.load(this.CONSTANTS.EVALUATE_LEVEL, function (data) {
                    _this.evaluateLevels = data;
                    callback && callback(data);
                });
            },
            loadEvaluateElements: function () {
                var _this = this;
                this.load(this.CONSTANTS.EVALUATE_ELEMENT, function (data) {
                    _this.evaluateElements = data;
                });
            },
            formatCourseDefinition: function (val) {
                var list = this.courseDefinitions;
                for (var i = 0; i < list.length; i++) {
                    if (list[i].itemValue === val) {
                        return list[i].itemText;
                    }
                }
            }
        },
        xnxq: {},
        xnxqs: [],
        loadXnxq: function (callback) {
            $.post("${ctx}/api/xnxq", function (data) {
                mainVm.xnxq = data;
                callback && callback();
            });
        },
        loadXnxqs: function () {
            $.post("${ctx}/api/xnxqs", function (data) {
                mainVm.xnxqs = data;
            });
        },
        courseStatuses: {'1': '已录入(未发布)', '2': '已发布'},
        xxlbs: {'2': '选修课', '5': '课后一小时'},
        sexes: {'1': '男', '2': '女'},
        firstDayOfWeek: ${firstDayOfWeek},
        weeks: {'1': '周一', '2': '周二', '3': '周三', '4': '周四', '5': '周五'},
        noticeLevels: {'0001': '一般', '0002': '重要', '0003': '紧急'},
        formatArr: function (arr, key) {
            var result = "";
            for (var i = 0; i < arr.length; i++) {
                if (i !== 0) {
                    result += ",";
                }
                result += arr[i][key];
            }
            return result;
        },
        formatClazzes: function (clazzes) {
            var result = "";
            for (var i = 0; i < clazzes.length; i++) {
                if (i !== 0) {
                    result += "，";
                }
                var clazz = clazzes[i];
                var gradeName = '';
                for (var x = 0; x < littleGrades.length; x++) {
                    if (clazz.gradeName.indexOf(littleGrades[x]) !== -1) {
                        gradeName = littleGrades[x];
                    }
                }
                result += gradeName + '.' + clazz.name;
            }
            return result;
        },
        formatIdCard: function (idCard) {
            if (!idCard) {
                return "";
            } else {
                return idCard.substr(0, 3) + '****' + idCard.substr(idCard.length - 4);
            }
        },
        formatSpace: function (space) {
            return "[" + space.typeName + "]" + space.name;
        },
        getWeekOfTerm: function (date) {//日期字符串
            var xnxq = mainVm.$model.xnxq;
            var startDate = new Date(xnxq.startDate);
            if (date.getTime() < startDate.getTime()) {
                return 0;
            }
            while (date.getDay() !== mainVm.$model.firstDayOfWeek) {
                date.setTime(date.getTime() - (1000 * 60 * 60 * 24));
            }
            while (startDate.getDay() !== mainVm.$model.firstDayOfWeek) {
                startDate.setTime(startDate.getTime() - (1000 * 60 * 60 * 24));
            }
            var result = parseInt((date.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24 * 7)) + 1;
            // console.log("日期：[",date ,"]是第[" + result + "]周");
            return result;
        },
        getDate: function (weekOfTerm, dayOfWeek) {
            // console.log(weekOfTerm, dayOfWeek);
            var xnxq = mainVm.$model.xnxq;
            var startDate = new Date(xnxq.startDate);
            while (startDate.getDay() !== mainVm.$model.firstDayOfWeek) {
                startDate.setTime(startDate.getTime() - (1000 * 60 * 60 * 24));
            }
            startDate.setTime(startDate.getTime() + (1000 * 60 * 60 * 24 * 7 * (weekOfTerm - 1)));
            while (startDate.getDay() !== parseInt(dayOfWeek)) {
                startDate.setTime(startDate.getTime() + (1000 * 60 * 60 * 24));
            }
            var result = avalon.filters.date(startDate, 'yyyy-MM-dd');
            // console.log("第[" + weekOfTerm + "]周周[" + dayOfWeek + "]是[" + result + "]");
            return result;
        },
        renderDate: function (data) {
            msgFn.date(data);
        },
        renderTime: function (e, data) {
            if (!data) {
                data = {};
            }
            data.elem = e.target;
            msgFn.time(data);
        },
        renderDateTime: function (e, data) {
            if (!data) {
                data = {};
            }
            data.elem = e.target;
            msgFn.datetime(data);
        },
        showFile: function (attId) {
            if (attId) {
                return '${ctx}/file/path/' + attId;
            } else {
                return null;
            }
        },
        formatChooseCourseName: function (courseName, aliasName) {
            if (courseName && aliasName) {
                return courseName + "(" + aliasName + ")";
            }
            return "";
        }
    });
    mainVm.loadXnxq();
    $(document).ajaxError(function (event, xhr, options, exc) {
        if (xhr.status === 403) {
            location.href = "${ctx}/logout";
        }
        if (xhr.responseJSON && JSON.stringify(xhr.responseJSON) !== '{}') {
            msgFn.msg(xhr.responseJSON);
            layer.closeAll('loading');
        }
        // console.log(xhr.responseJSON);
    });
</script>
</body>
</html>
</#macro>