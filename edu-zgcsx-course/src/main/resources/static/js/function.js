var msgFn = {
    layer: function (callback) {
        layui.use('layer', function () {
            var layer = layui.layer;
            callback(layer);
        });
    },
    /**
     * 必须
     * @param opt.url 请求路径(返回html结构(不需要包含html,head,body等标签))
     * @param opt.title 弹窗标题
     * @param opt.yes 点击第一个按钮的回调,会自动传入关闭弹窗的回调,例:{yes:function(close){//这里调用close()关闭弹窗 }}
     * @param opt.success 弹窗加载完毕后的回调,会自动传入回调函数渲染弹窗内avalonjs和重新调整弹窗位置,例:{yes:function(callback){//这里调用callback()重新渲染弹窗 }}
     * 非必须
     * @param opt.btn 按钮(map格式，key为按钮名称,value为按钮回调，参数一为关闭方法)
     * @param opt.width 如果设置了宽度,则宽度固定
     * @param opt.height 如果要高度生效,必须传入宽度
     */
    dlg: function (opt) {
        msgFn.layer(function (layer) {
            msgFn.load(function (end) {
                $.ajax({
                    type: 'get',
                    url: opt.url,
                    success: function (str) {
                        var options = {
                            title: [opt.title, "font-weight:600;background-color:#f3f3f3"],
                            type: 1,
                            move: false,
                            btnAlign: 'c',
                            // maxWidth: 1200,
                            // maxHeight: 500,
                            scrollbar: false,
                            shadeClose: false,
                            content: str,
                            success: function (layDom, index) {
                                end();
                                var callback = function () {
                                    avalon.scan(document.body);
                                };
                                if (opt.success && typeof opt.success === "function") {
                                    switch (opt.success.length) {
                                        case 0:
                                            opt.success();
                                            break;
                                        case 1:
                                            opt.success(callback);
                                            break;
                                        case 2:
                                            opt.success(callback, function () {
                                                layer.close(index);
                                            });
                                    }
                                } else {
                                    callback();
                                    $(window).resize();
                                }
                            }
                        };
                        if (opt.width) {
                            options.area = opt.width;
                            if (opt.height) {
                                options.area = [opt.width, opt.height];
                            }
                        }
                        if (opt.offset) {
                            options.offset = opt.offset;
                        }
                        if (opt.btn) {
                            options.btn = [];
                            var arr = {};
                            for (var i = 0; i < opt.btn.length; i++) {
                                options.btn[i] = opt.btn[i].name;
                                var key = 'yes';
                                if (i !== 0) {
                                    key = 'btn' + (i + 1);
                                }
                                arr[key] = opt.btn[i].ok;
                                options[key] = (function (key) {
                                    return function (index, layero) {
                                        arr[key](function () {
                                            layer.close(index);
                                        });
                                        return false;
                                    };
                                })(key);
                            }
                        }
                        layer.open(options);
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        msgFn.msg(false, "网络错误,请刷新页面重试！");
                        end();
                    }
                });
            });
        });
    },
    msg: function () {
        var msg = '', success = false;
        if (arguments.length === 1 && Object.prototype.toString.call(arguments[0]) === '[object Object]') {
            msg = arguments[0].msg;
            success = arguments[0].success;
        } else {
            for (var i = 0; i < arguments.length; i++) {
                if (Object.prototype.toString.call(arguments[i]) === '[object Boolean]') {
                    success = arguments[i];
                } else if (Object.prototype.toString.call(arguments[i]) === '[object String]') {
                    msg = arguments[i];
                }
            }
        }
        msg = avalon.filters.escape(msg);
        msgFn.layer(function (layer) {
            layer.msg(msg, {icon: success ? 1 : 2});
        });
    },
    //msgFn.confirm({msg:'',title:'',callback:function(){doSomething();}})
    //msgFn.confirm([title,]msg,function(){doSomething});
    confirm: function () {
        var msg = '', title = '', callback, btn = ['确定', '取消'];
        if (arguments.length === 1 && Object.prototype.toString.call(arguments[0]) === '[object Object]') {
            msg = arguments[0].msg;
            callback = arguments[0].callback;
            title = arguments[0].title;
            if (arguments[0].btn) btn = arguments[0].btn;
        } else {
            for (var i = 0; i < arguments.length; i++) {
                if (Object.prototype.toString.call(arguments[i]) === '[object Function]') {
                    callback = arguments[i];
                } else if (Object.prototype.toString.call(arguments[i]) === '[object Array]') {
                    btn = arguments[i];
                } else if (Object.prototype.toString.call(arguments[i]) === '[object String]') {
                    if (arguments.length > 2 && !i) {
                        title = arguments[i];
                    } else {
                        msg = arguments[i];
                    }
                }
            }
        }
        msg = avalon.filters.escape(msg);
        var opts = {icon: 3, btn: btn};
        if (title) {
            opts.title = title;
        }
        opts.btnAlign = 'c';
        opts.move = false;
        msgFn.layer(function (layer) {
            layer.confirm(msg, opts, function (index, ele) {
                callback && callback(index, ele);
                layer.close(index);
            });
        });
    },
    load: function (callback) {
        msgFn.layer(function (layer) {
            var index = layer.load(1);
            var end = function () {
                layer.close(index);
            };
            callback && callback(end);
        });
    },
    tips: function (msg, e) {
        msgFn.layer(function (layer) {
            layer.tips(msg, e);
        });
    },
    datetime: function (data) {
        layui.use('laydate', function () {
            var options = {
                elem: data.elem,
                type: 'datetime',
                format: data.format || 'yyyy-MM-dd HH:mm:ss',
                theme: 'grid',
                show: true,
                closeStop: data.elem
            };
            if (data.done && typeof data.done === 'function') {
                options.done = data.done;
            }
            layui.laydate.render(options);
        });
    },
    time: function (data) {
        layui.use('laydate', function () {
            var options = {
                elem: data.elem,
                type: 'time',
                format: 'HH:mm',
                min: '07:00:00',
                max: '21:00:00',
                theme: 'grid',
                show: true,
                closeStop: data.elem
            };
            if (data.done && typeof data.done === 'function') {
                options.done = data.done;
            }
            layui.laydate.render(options);
        });
    },
    date: function (data) {
        layui.use('laydate', function () {
            var options = {
                elem: data.elem,
                type: 'date',
                calendar: true,
                theme: 'grid',
                show: true,
                closeStop: data.elem
            };
            if (data.done && typeof data.done === 'function') {
                options.done = data.done;
            }
            layui.laydate.render(options);
        });
    }
};

avalon.filters.nvl = function (str) {
    return str || "—";
};


avalon.filters.empty = function (str) {
    return str || "";
};

avalon.filters.idCard = function (str) {
    if (!str) {
        return "";
    }
    return str.substring(0, 3) + "******" + str.substring(str.length - 4, str.length);
};

var dateUtils = {
    dateDiff: function (startTime, endTime, diffType) {
        startTime = startTime.replace(/-/g, "/");
        endTime = endTime.replace(/-/g, "/");
        //将计算间隔类性字符转换为小写
        diffType = diffType ? diffType.toLowerCase() : "day";
        var _startTime = new Date(startTime).getTime(); //开始时间
        var _endTime = new Date(endTime).getTime(); //结束时间
        //作为除数的数字
        var timeType = 1;
        switch (diffType) {
            case "second":
                timeType = 1000;
                break;
            case "minute":
                timeType = 1000 * 60;
                break;
            case "hour":
                timeType = 1000 * 3600;
                break;
            case "day":
                timeType = 1000 * 3600 * 24;
                break;
        }
        return (_endTime - _startTime) / timeType;
    },
    getDays: function (year) {
        return dateUtils.dateDiff(year + "-01-01 00:00:00", (year + 1) + "-01-01 00:00:00");
    },
    getHours: function (startTime, endTime) {//HH:mm
        return dateUtils.dateDiff("2000-01-01 " + startTime + ":00", "2000-01-01 " + endTime + ":00", "hour");
    },
    getDate: function (str) {
        var reg = /(\d{4})-(\d{2})-(\d{2})/;
        var date = str.replace(reg, "$1/$2/$3");
        return new Date(date);
    }
};

var numberUtils = {
    /**
     * @return {string}
     */
    PrefixInteger: function (num, length) {
        return (Array(length).join('0') + num).slice(-length);
    }
};

avalon.Array.containsByField = function (arr, key, value) {
    for (var i = 0; i < arr.length; i++) {
        if (arr[i][key] === value) {
            return true;
        }
    }
    return false;
};

avalon.Array.removeByField = function (arr, key, value) {
    for (var i = 0, j = arr.length; i < j; i++) {
        if (arr[i][key] === value) {
            arr.splice(i--, 1);
            j--;
        }
    }
};

avalon.Array.findById = function (arr, id) {
    for (var i = 0; i < arr.length; i++) {
        if (arr[i].id === id) {
            return arr[i];
        }
    }
    return null;
};

/**
 * 为数组定义一个根据值移除元素的函数
 * @param val
 */
Array.prototype.removeByValue = function (val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) {
            this.splice(i, 1);
            break;
        }
    }
}


var dataFn = {
    isString: function (data) {
        return Object.prototype.toString.call(data) === "[object String]";
    },
    isArray: function (data) {
        return Object.prototype.toString.call(data) === "[object Array]";
    },
    isObject: function (data) {
        return Object.prototype.toString.call(data) === "[object Object]";
    },
    getType: function (data) {
        return Object.prototype.toString.call(data);
    }
};