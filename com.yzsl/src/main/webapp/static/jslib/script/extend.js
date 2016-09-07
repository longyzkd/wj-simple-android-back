Array.prototype.del = function (n) {
    if (n < 0) {
        return this;
    } else {
        return this.slice(0, n).concat(this.slice(n + 1, this.length));
    }
}

Array.prototype.indexOf = function (obj) {
    for (var i = 0; i < this.length; i++) {
        if (obj != null && this[i] == obj) {
            return i;
        }
    }
    return -1;
}

Array.prototype.remove = function (val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};

Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(), //day
        "h+": this.getHours(), //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
        "S": this.getMilliseconds() //millisecond
    }
    if (/(y+)/.test(format))
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o) if (new RegExp("(" + k + ")").test(format))
        format = format.replace(RegExp.$1,
        RegExp.$1.length == 1 ? o[k] :
        ("00" + o[k]).substr(("" + o[k]).length));
    return format;
}


//四舍五入
//Dight：数字，How：小数点后位数
function ForDight(Dight, How) {
    Dight = Math.round(Dight * Math.pow(10, How)) / Math.pow(10, How);
    return Dight;
}

//生成空格
function createSpace(n) {
    var space = '&nbsp;';
    for (var i = 0; i < n; i++) {
        space += '&nbsp;';
    }
    return space;
}


//数字格式化
function numberFormat(number, decimals, dec_point, thousands_sep) {
    number = (number + '').replace(/[^0-9+-Ee.]/g, '');
    var n = !isFinite(+number) ? 0 : +number,
        prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
        sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
        dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
        s = '',
        toFixedFix = function (n, prec) {
            var k = Math.pow(10, prec);
            return '' + Math.round(n * k) / k;
        };

    s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
    if (s[0].length > 3) {
        s[0] = s[0].replace(/B(?=(?:d{3})+(?!d))/g, sep);
    }

    if ((s[1] || '').length < prec) {
        s[1] = s[1] || '';
        s[1] += new Array(prec - s[1].length + 1).join('0');
    }
    return s.join(dec);
}




//转换json时间
function formatJsonTime(v) {
    if (v == null || v == '') {
        return null;
    }
    var d = new Date();
    var str = v.toString();
    var str1 = str.replace("/Date(", "");
    var str2 = str1.replace(")/", "");
    var dd = parseInt(str2);
    d.setTime(dd);
    return d;
};