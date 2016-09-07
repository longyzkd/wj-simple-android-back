
function CheckSFZID(strId) {
    strId = strId.toLowerCase();

    if (strId.length == 18)								//如果是18位代码
    {
        if (!Check18s(strId))								//检查18位代码格式
        {
            alert("身份证号码代码格式错误！");
            return false;
        }

        if (!CheckArea(strId))								//检查地区代码
        {
            alert("身份证号码地区代码错误！");
            return false;
        }

        if (!CheckDate(strId.substr(6, 8)))		//检查日期有效性
        {
            alert("身份证号码日期格式错误！");
            return false;
        }

        if (!CheckLastBit(strId)) {
            alert("身份证号码校验位错误！");
            return false;
        }
    }
    else if (strId.length == 15)						//如果是15位代码
    {
        if (!Check15s(strId))								//检查15位代码格式
        {
            alert("身份证号码代码格式错误！");
            return false;
        }

        if (!CheckArea(strId))								//检查地区代码
        {
            alert("身份证号码地区代码错误！");
            return false;
        }

        if (!CheckDate(strId.substr(6, 6)))		//检查日期有效性
        {
            alert("身份证号码日期格式错误！");
            return false;
        }
    }
    else {
        alert("身份证号码位数错误！");
        return false;
    }

    return true;
}

function CheckArea(strId) {
    var isOk = false;

    var CityList = {
        11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古"
		, 21: "辽宁", 22: "吉林", 23: "黑龙江"
		, 31: "上海", 32: "江苏", 33: "浙江", 34: "安徽", 35: "福建", 36: "江西", 37: "山东"
		, 41: "河南", 42: "湖北", 43: "湖南", 44: "广东", 45: "广西", 46: "海南", 50: "重庆"
		, 51: "四川", 52: "贵州", 53: "云南", 54: "西藏"
		, 61: "陕西", 62: "甘肃", 63: "青海", 64: "宁夏", 65: "新疆"
		, 71: "台湾"
		, 81: "香港", 82: "澳门"
		, 91: "国外"
    };

    if (CityList[parseInt(strId.substr(0, 2))] != null)
        isOk = true;

    return isOk;
}

function Check18s(strId) {
    var isOk = false;
    var patrn = /^\d{17}(\d|x)$/;
    if (patrn.exec(strId))
        isOk = true;

    return isOk;
}

function Check15s(strId) {
    var isOk = false;
    var patrn = /^\d{15}$/;
    if (patrn.exec(strId))
        isOk = true;

    return isOk;
}

function CheckDate(strDate) {
    if (strDate.length == 6)
        strDate = "19" + strDate;

    if (isNaN(Date.UTC(strDate.substr(0, 4), strDate.substr(4, 2), strDate.substr(6, 2))))
        return false;
    else
        return true;

    //	strDate = strDate.substr(0,4) +"/"+ strDate.substr(4,2) +"/"+ strDate.substr(6,2);
    //	alert( strDate );
    //	var dateTest = new Date( strDate );
    //	var strTest = dateTest.getFullYear() +"/"+ (dateTest.getMonth()+1) +"/"+ dateTest.getDate();
    //	if( strDate == strTest )
    //		isOk = true;

    //	return isOk;
}

function CheckLastBit(strId) {
    var isOk = false;

    var wi = { 1: 7, 2: 9, 3: 10, 4: 5, 5: 8, 6: 4, 7: 2, 8: 1, 9: 6, 10: 3, 11: 7, 12: 9, 13: 10, 14: 5, 15: 8, 16: 4, 17: 2 };
    var bits = new Array("1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2");
    var sum = 0;
    for (var i = 0; i < strId.length - 1; i++) {
        sum += parseInt(strId.substr(i, 1)) * wi[i + 1];
    }

    var mod11 = sum % 11;

    if (bits[mod11] == strId.substr(17, 1))
        isOk = true;

    return isOk;
}