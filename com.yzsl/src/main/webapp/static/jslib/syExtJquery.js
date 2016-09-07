var sy = sy || {};
sy.data = sy.data || {};// 用于存放临时的数据或者对象

/**
 * 屏蔽右键
 * 
 * 
 * @requires jQuery
 */
$(document).bind('contextmenu', function() {
	// return false;
});

/**
 * 禁止复制
 * 
 * 
 * @requires jQuery
 */
$(document).bind('selectstart', function() {
	// return false;
});

/**
 * 
 * 增加命名空间功能
 * 
 * 使用方法：sy.ns('jQuery.bbb.ccc','jQuery.eee.fff');
 */
sy.ns = function() {
	var o = {}, d;
	for (var i = 0; i < arguments.length; i++) {
		d = arguments[i].split(".");
		o = window[d[0]] = window[d[0]] || {};
		for (var k = 0; k < d.slice(1).length; k++) {
			o = o[d[k + 1]] = o[d[k + 1]] || {};
		}
	}
	return o;
};

/**
 * 将form表单元素的值序列化成对象
 * 
 * @example sy.serializeObject($('#formId'))
 * 
 * 
 * @requires jQuery
 * 
 * @returns object
 */
sy.serializeObject = function(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (this['value'] != undefined && this['value'].length > 0) {// 如果表单项的值非空，才进行序列化操作
			if (o[this['name']]) {
				o[this['name']] = o[this['name']] + "," + this['value'];
			} else {
				o[this['name']] = this['value'];
			}
		}
	});
	return o;
};

/**
 * 增加formatString功能
 * 
 * 
 * @example sy.formatString('字符串{0}字符串{1}字符串','第一个变量','第二个变量');
 * 
 * @returns 格式化后的字符串
 */
sy.formatString = function(str) {
	for (var i = 0; i < arguments.length - 1; i++) {
		str = str.replace("{" + i + "}", arguments[i + 1]);
	}
	return str;
};

/**
 * 接收一个以逗号分割的字符串，返回List，list里每一项都是一个字符串
 * 
 * 
 * @returns list
 */
sy.stringToList = function(value) {
	if (value != undefined && value != '') {
		var values = [];
		var t = value.split(',');
		for (var i = 0; i < t.length; i++) {
			values.push('' + t[i]);/* 避免他将ID当成数字 */
		}
		return values;
	} else {
		return [];
	}
};

/**
 * JSON对象转换成String
 * 
 * @param o
 * @returns
 */
sy.jsonToString = function(o) {
	var r = [];
	if (typeof o == "string")
		return "\"" + o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n").replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\"";
	if (typeof o == "object") {
		if (!o.sort) {
			for ( var i in o)
				r.push(i + ":" + sy.jsonToString(o[i]));
			if (!!document.all && !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)) {
				r.push("toString:" + o.toString.toString());
			}
			r = "{" + r.join() + "}";
		} else {
			for (var i = 0; i < o.length; i++)
				r.push(sy.jsonToString(o[i]));
			r = "[" + r.join() + "]";
		}
		return r;
	}
	return o.toString();
};

/**
 * Create a cookie with the given key and value and other optional parameters.
 * 
 * @example sy.cookie('the_cookie', 'the_value');
 * @desc Set the value of a cookie.
 * @example sy.cookie('the_cookie', 'the_value', { expires: 7, path: '/', domain: 'jquery.com', secure: true });
 * @desc Create a cookie with all available options.
 * @example sy.cookie('the_cookie', 'the_value');
 * @desc Create a session cookie.
 * @example sy.cookie('the_cookie', null);
 * @desc Delete a cookie by passing null as value. Keep in mind that you have to use the same path and domain used when the cookie was set.
 * 
 * @param String
 *            key The key of the cookie.
 * @param String
 *            value The value of the cookie.
 * @param Object
 *            options An object literal containing key/value pairs to provide optional cookie attributes.
 * @option Number|Date expires Either an integer specifying the expiration date from now on in days or a Date object. If a negative value is specified (e.g. a date in the past), the cookie will be deleted. If set to null or omitted, the cookie will be a session cookie and will not be retained when the the browser exits.
 * @option String path The value of the path atribute of the cookie (default: path of page that created the cookie).
 * @option String domain The value of the domain attribute of the cookie (default: domain of page that created the cookie).
 * @option Boolean secure If true, the secure attribute of the cookie will be set and the cookie transmission will require a secure protocol (like HTTPS).
 * @type undefined
 * 
 * @name sy.cookie
 * @cat Plugins/Cookie
 * @author Klaus Hartl/klaus.hartl@stilbuero.de
 * 
 * Get the value of a cookie with the given key.
 * 
 * @example sy.cookie('the_cookie');
 * @desc Get the value of a cookie.
 * 
 * @param String
 *            key The key of the cookie.
 * @return The value of the cookie.
 * @type String
 * 
 * @name sy.cookie
 * @cat Plugins/Cookie
 * @author Klaus Hartl/klaus.hartl@stilbuero.de
 */
sy.cookie = function(key, value, options) {
	if (arguments.length > 1 && (value === null || typeof value !== "object")) {
		options = $.extend({}, options);
		if (value === null) {
			options.expires = -1;
		}
		if (typeof options.expires === 'number') {
			var days = options.expires, t = options.expires = new Date();
			t.setDate(t.getDate() + days);
		}
		return (document.cookie = [ encodeURIComponent(key), '=', options.raw ? String(value) : encodeURIComponent(String(value)), options.expires ? '; expires=' + options.expires.toUTCString() : '', options.path ? '; path=' + options.path : '', options.domain ? '; domain=' + options.domain : '', options.secure ? '; secure' : '' ].join(''));
	}
	options = value || {};
	var result, decode = options.raw ? function(s) {
		return s;
	} : decodeURIComponent;
	return (result = new RegExp('(?:^|; )' + encodeURIComponent(key) + '=([^;]*)').exec(document.cookie)) ? decode(result[1]) : null;
};

/**
 * 改变jQuery的AJAX默认属性和方法
 * 
 * 
 * @requires jQuery
 * 
 */
$.ajaxSetup({
	type : 'POST',
	error : function(XMLHttpRequest, textStatus, errorThrown) {
		try {
			parent.$.messager.progress('close');
			parent.$.messager.alert('错误', XMLHttpRequest.responseText);
		} catch (e) {
			alert(XMLHttpRequest.responseText);
		}
	}
});

/**
 * 解决class="iconImg"的img标记，没有src的时候，会出现边框问题
 * 
 * 
 * @requires jQuery
 */
$(function() {
	$('.iconImg').attr('src', sy.pixel_0);
});





var MyDateUtil = function () {

    /**
     * 判断闰年
     * @param date Date日期对象
     * @return boolean true 或false
     */
    this.isLeapYear = function (date) {
        return (0 == date.getYear() % 4 && ((date.getYear() % 100 != 0) || (date.getYear() % 400 == 0)));
    }

    /**
     * 日期对象转换为指定格式的字符串
     * @param f 日期格式,格式定义如下 yyyy-MM-dd HH:mm:ss
     * @param date Date日期对象, 如果缺省，则为当前时间
     *
     * YYYY/yyyy/YY/yy 表示年份 
     * MM/M 月份 
     * W/w 星期 
     * dd/DD/d/D 日期 
     * hh/HH/h/H 时间 
     * mm/m 分钟 
     * ss/SS/s/S 秒 
     * @return string 指定格式的时间字符串
     */
    this.dateToStr = function (formatStr, date) {
        formatStr = arguments[0] || "yyyy-MM-dd HH:mm:ss";
        date = arguments[1] || new Date();
        var str = formatStr;
        var Week = ['日', '一', '二', '三', '四', '五', '六'];
        str = str.replace(/yyyy|YYYY/, date.getFullYear());
        str = str.replace(/yy|YY/, (date.getYear() % 100) > 9 ? (date.getYear() % 100).toString() : '0' + (date.getYear() % 100));
        str = str.replace(/MM/, date.getMonth() > 9 ? (date.getMonth() + 1) : '0' + (date.getMonth() + 1));
        str = str.replace(/M/g, date.getMonth());
        str = str.replace(/w|W/g, Week[date.getDay()]);

        str = str.replace(/dd|DD/, date.getDate() > 9 ? date.getDate().toString() : '0' + date.getDate());
        str = str.replace(/d|D/g, date.getDate());

        str = str.replace(/hh|HH/, date.getHours() > 9 ? date.getHours().toString() : '0' + date.getHours());
        str = str.replace(/h|H/g, date.getHours());
        str = str.replace(/mm/, date.getMinutes() > 9 ? date.getMinutes().toString() : '0' + date.getMinutes());
        str = str.replace(/m/g, date.getMinutes());

        str = str.replace(/ss|SS/, date.getSeconds() > 9 ? date.getSeconds().toString() : '0' + date.getSeconds());
        str = str.replace(/s|S/g, date.getSeconds());

        return str;
    }


    /**
    * 日期计算 
    * @param strInterval string  可选值 y 年 m月 d日 w星期 ww周 h时 n分 s秒 
    * @param num int
    * @param date Date 日期对象
    * @return Date 返回日期对象
    */
    this.dateAdd = function (strInterval, num, date) {
        date = arguments[2] || new Date();
        switch (strInterval) {
            case 's': return new Date(date.getTime() + (1000 * num));
            case 'n': return new Date(date.getTime() + (60000 * num));
            case 'h': return new Date(date.getTime() + (3600000 * num));
            case 'd': return new Date(date.getTime() + (86400000 * num));
            case 'w': return new Date(date.getTime() + ((86400000 * 7) * num));
            case 'm': return new Date(date.getFullYear(), (date.getMonth()) + num, date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds());
            case 'y': return new Date((date.getFullYear() + num), date.getMonth(), date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds());
        }
    }

    /**
    * 比较日期差 dtEnd 格式为日期型或者有效日期格式字符串
    * @param strInterval string  可选值 y 年 m月 d日 w星期 ww周 h时 n分 s秒 
    * @param dtStart Date  可选值 y 年 m月 d日 w星期 ww周 h时 n分 s秒
    * @param dtEnd Date  可选值 y 年 m月 d日 w星期 ww周 h时 n分 s秒
    */
    this.dateDiff = function (strInterval, dtStart, dtEnd) {
        switch (strInterval) {
            case 's': return parseInt((dtEnd - dtStart) / 1000);
            case 'n': return parseInt((dtEnd - dtStart) / 60000);
            case 'h': return parseInt((dtEnd - dtStart) / 3600000);
            case 'd': return parseInt((dtEnd - dtStart) / 86400000);
            case 'w': return parseInt((dtEnd - dtStart) / (86400000 * 7));
            case 'm': return (dtEnd.getMonth() + 1) + ((dtEnd.getFullYear() - dtStart.getFullYear()) * 12) - (dtStart.getMonth() + 1);
            case 'y': return dtEnd.getFullYear() - dtStart.getFullYear();
        }
    }

    /**
    * 字符串转换为日期对象
    * @param date Date 格式为yyyy-MM-dd HH:mm:ss，必须按年月日时分秒的顺序，中间分隔符不限制
    */
    this.strToDate = function (dateStr) {
        var data = dateStr;
        var reCat = /(\d{1,4})/gm;
        var t = data.match(reCat);
        t[1] = t[1] - 1;
        eval('var d = new Date(' + t.join(',') + ');');
        return d;
    }

    /**
    * 把指定格式的字符串转换为日期对象yyyy-MM-dd HH:mm:ss
    *
    */
    this.strFormatToDate = function (formatStr, dateStr) {
        var year = 0;
        var start = -1;
        var len = dateStr.length;
        if ((start = formatStr.indexOf('yyyy')) > -1 && start < len) {
            year = dateStr.substr(start, 4);
        }
        var month = 0;
        if ((start = formatStr.indexOf('MM')) > -1 && start < len) {
            month = parseInt(dateStr.substr(start, 2)) - 1;
        }
        var day = 0;
        if ((start = formatStr.indexOf('dd')) > -1 && start < len) {
            day = parseInt(dateStr.substr(start, 2));
        }
        var hour = 0;
        if (((start = formatStr.indexOf('HH')) > -1 || (start = formatStr.indexOf('hh')) > 1) && start < len) {
            hour = parseInt(dateStr.substr(start, 2));
        }
        var minute = 0;
        if ((start = formatStr.indexOf('mm')) > -1 && start < len) {
            minute = dateStr.substr(start, 2);
        }
        var second = 0;
        if ((start = formatStr.indexOf('ss')) > -1 && start < len) {
            second = dateStr.substr(start, 2);
        }
        return new Date(year, month, day, hour, minute, second);
    }


    /**
    * 日期对象转换为毫秒数
    */
    this.dateToLong = function (date) {
        return date.getTime();
    }

    /**
    * 毫秒转换为日期对象
    * @param dateVal number 日期的毫秒数
    */
    this.longToDate = function (dateVal) {
        return new Date(dateVal);
    }

    /**
    * 判断字符串是否为日期格式
    * @param str string 字符串
    * @param formatStr string 日期格式， 如下 yyyy-MM-dd
    */
    this.isDate = function (str, formatStr) {
        if (formatStr == null) {
            formatStr = "yyyyMMdd";
        }
        var yIndex = formatStr.indexOf("yyyy");
        if (yIndex == -1) {
            return false;
        }
        var year = str.substring(yIndex, yIndex + 4);
        var mIndex = formatStr.indexOf("MM");
        if (mIndex == -1) {
            return false;
        }
        var month = str.substring(mIndex, mIndex + 2);
        var dIndex = formatStr.indexOf("dd");
        if (dIndex == -1) {
            return false;
        }
        var day = str.substring(dIndex, dIndex + 2);
        if (!isNumber(year) || year > "2100" || year < "1900") {
            return false;
        }
        if (!isNumber(month) || month > "12" || month < "01") {
            return false;
        }
        if (day > getMaxDay(year, month) || day < "01") {
            return false;
        }
        return true;
    }

    this.getMaxDay = function (year, month) {
        if (month == 4 || month == 6 || month == 9 || month == 11)
            return "30";
        if (month == 2)
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
                return "29";
            else
                return "28";
        return "31";
    }
    /**
    *   变量是否为数字
    */
    this.isNumber = function (str) {
        var regExp = /^\d+$/g;
        return regExp.test(str);
    }

    /**
    * 把日期分割成数组 [年、月、日、时、分、秒]
    */
    this.toArray = function (myDate) {
        myDate = arguments[0] || new Date();
        var myArray = Array();
        myArray[0] = myDate.getFullYear();
        myArray[1] = myDate.getMonth();
        myArray[2] = myDate.getDate();
        myArray[3] = myDate.getHours();
        myArray[4] = myDate.getMinutes();
        myArray[5] = myDate.getSeconds();
        return myArray;
    }

    /**
    * 取得日期数据信息 
    * 参数 interval 表示数据类型 
    * y 年 M月 d日 w星期 ww周 h时 n分 s秒 
    */
    this.datePart = function (interval, myDate) {
        myDate = arguments[1] || new Date();
        var partStr = '';
        var Week = ['日', '一', '二', '三', '四', '五', '六'];
        switch (interval) {
            case 'y': partStr = myDate.getFullYear(); break;
            case 'M': partStr = myDate.getMonth() + 1; break;
            case 'd': partStr = myDate.getDate(); break;
            case 'w': partStr = Week[myDate.getDay()]; break;
            case 'ww': partStr = myDate.WeekNumOfYear(); break;
            case 'h': partStr = myDate.getHours(); break;
            case 'm': partStr = myDate.getMinutes(); break;
            case 's': partStr = myDate.getSeconds(); break;
        }
        return partStr;
    }

    /**
    * 取得当前日期所在月的最大天数 
    */
    this.maxDayOfDate = function (date) {
        date = arguments[0] || new Date();
        date.setDate(1);
        date.setMonth(date.getMonth() + 1);
        var time = date.getTime() - 24 * 60 * 60 * 1000;
        var newDate = new Date(time);
        return newDate.getDate();
    }

    return this;
}();