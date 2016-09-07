//自定义Vtype验证
//--------------------------------------------------------------------------------------------------------------
Ext.apply(Ext.form.field.VTypes, {
    integer: function (v) {
        return /^\d+$/.test(v);
    },
    integerText: '只能输入整数！'
});

//验证小数点后面两位
Ext.apply(Ext.form.field.VTypes, {
    money: function (v) {
        return /^(([1-9]\d*)|0)(\.\d{1,2})?$|^\d+$/.test(v);
    },
    moneyText: '请输入正确的货币数额！'
});

Ext.apply(Ext.form.field.VTypes, {
    decimal: function (v) {
        return /^(([1-9]\d*)|0)(\.\d{1,2})?$|^\d+$/.test(v);
    },
    decimalText: '请输入正确的数量（小数点后最多两位数）！'
});


//验证小数点后面两位
Ext.apply(Ext.form.field.VTypes, {
    moneyTest: function (v) {
        return /^(-)?(\d)*(\.(\d){2})?$/.test(v);
    },
    moneyTestText: '请输入正确的货币数额！'
});
//验证银行卡号
Ext.apply(Ext.form.field.VTypes, {
    bankCard: function (v) {
        return /^\d{19}$/.test(v);
    },
    bankCardText: '请输入正确的19位银行卡号！'
});
//百分比验证
Ext.apply(Ext.form.field.VTypes, {
    percent: function (v) {
        return /^\d+\.?\%$/.test(v);
    },
    perText: '百分比！'
});

//定义vtype验证汉字
Ext.apply(Ext.form.field.VTypes, {
    china: function (v) {
        return /^[\u0391-\uFFE5]+$/.test(v);
    },
    chinaText: '只能输入汉字！'
});

//定义vtype验证座机号码
Ext.apply(Ext.form.field.VTypes, {
    tel: function (v) {
        return /^((\d{2,5}-)?\d{7,8}|(\d{2,5})?\d{7,8})$/.test(v);
    },
    telText: '请正确输入电话号码！'
});

//定义vtype验证传真
Ext.apply(Ext.form.field.VTypes, {
    fax: function (v) {
        return /^[0]{1}[1-9]{3}[-]{1}[1-9]{7,8}$/.test(v);
    },
    faxText: '请输入正确传真号！'
});
 
//定义vtype验证手机号码
Ext.apply(Ext.form.field.VTypes, {
    mobile: function (v) {
        return /^1[3|4|5|8][0-9]\d{4,8}$/.test(v);
    },
    mobileText: '不是完整的11位手机号或者正确的手机号前七位！'
});

//自定义邮编Vtype验证
Ext.apply(Ext.form.field.VTypes, {
    zip: function (v) {
        //规则邮编 6位数字
        return /^[1-9][0-9]{5}$/.test(v);
    },
    zipText: '请输入6位数字有效邮编！'
});



//定义vtype验证手机号码
Ext.apply(Ext.form.field.VTypes, {
    mobOrtel: function (v) {
        return /^(\d{2,5}-\d{7,8})$|^(1[3|4|5|8][0-9]\d{4,8})$/.test(v);
    },
    mobOrtelText: '请输入正确的手机或座机号码！'
});


//验证正实数
Ext.apply(Ext.form.field.VTypes, {
    int: function (v) {
        return /^(\d{1,10}.\d{1,10}|\d{1,10})$/.test(v);
    },
    intText:'只能输入正实数！'
});

//Vtype验证数字或小数//金额
//Ext.apply(Ext.form.field.VTypes, {
//    money: function (v) {
//        return /^(-?\d*)\.?\d+$|^(\d+)$/.test(v);
//    },
//    moneyText: '只能输入数字或小数点！'
//});

//验证身份证号
Ext.apply(Ext.form.field.VTypes, {
    card: function (v) {
        return /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/.test(v);
    },
    cardText:'请输入有效身份证号！'
});
//验证密码是否正确
Ext.apply(Ext.form.field.VTypes, {
	checkpwd : function(val, field) {
        if (!val) {
            return true;
        }
        var correct = false;
        Ext.Ajax.request({
			url : basepath+'/validate/checkpwd',// 获取面板的地址
			params : {
				
				val : val
				
			},
			async : false,//同步执行,为了争取返回exist，必须同步
			method:'post',
            success: function(response){
            	var requestSuccess = Ext.JSON.decode(response.responseText).success;
            	if(requestSuccess){
            		correct = requestSuccess;
            	}else{
            		correct = requestSuccess;
            		/*
            		 Ext.MessageBox.show({
                         title: '系统错误',
                         msg: Ext.decode(response.responseText).msg,
                         icon: Ext.MessageBox.ERROR,
                         buttons: Ext.Msg.OK
                     });
                     */
            	}
            	
            },
            failure: function(response){}
        });
        return correct;
    },
    checkpwdText:'密码不正确'  
});

