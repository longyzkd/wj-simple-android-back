//数据字典下拉控件

Ext.ns('uc');

Ext.define('DicModel', {
    extend: 'Ext.data.Model',
    idProperty: 'code',
    fields: [
        { name: 'code', type: 'string' },
        { name: 'name', type: 'string' }
    ]
});

var _DicRecordEmpty = Ext.create('DicModel', {
    code: '',
    name: ''
});


uc.cboDic = function (config) {
    var storeDic = Ext.create('Ext.data.Store', {
        model: 'DicModel',
        proxy: {
            url: rootUrl + 'dic/dicListByDicAndFDic.do',
            type: 'ajax',
            reader: {
                type: 'json',
                root: 'dataList'
            }
        }
    });
    var cbo = new Ext.form.field.ComboBox(Ext.apply({
        store: storeDic,
        triggerAction: 'all',
        queryMode: 'local',
        emptyText: '—请选择—',
        valueField: 'code',
        displayField: 'name',
        editable: false
    }, config));

    /*
        字典索引类的表单域名以数据字典项命名为准
        当一个页面使用多个字典索引类时，用下划线加后缀区分
    */
    var _id = cbo.getId();
    var _name = cbo.getName();

    var indexStr = _id; //字典索引字符串
    if (_id.indexOf("_" + _name) < 0) {
        /*
            控件不设置Id，系统会自动生成Id串，
            判断id串中是否含有Name域名，如果有，则证明是自己设置的Id，是属于是否有无类的字典项；
            如没有，条件是true的情况，则取Name域值即可。
        */
        indexStr = _name;
        /*
            只有是否、有无字典需要设置Id属性，Is_DicIndex或Has_DicIndex；
            其余的字典项无须设置Id属性。
        */
    }

    var splitIndex = indexStr.indexOf('_');
    if (splitIndex >= 0) {
        /*
            取下划线分隔符之前的字符串，作为字典索引名称，分隔符后的仅作同类字典项在同一页面的区分符号。
        */
        indexStr = indexStr.substr(0, splitIndex);
    }
    //console.log(config);
    if (config.dicIndex) {
        indexStr = config.dicIndex;
    }
    var dicFcode='';
    if (config.dicFcode) {
    	dicFcode = config.dicFcode;
    }
    storeDic.load({
        params: { dicCode: indexStr,fCode:dicFcode },
        callback: function () {
            storeDic.insert(0, _DicRecordEmpty);
        }
    });
    return cbo;
}

uc.cbgDic = function (config) {
    Ext.Ajax.request({
			url: '<%=path%>/dic/dicListByDicAndFDic.do',
			method: 'post',
			async: false,
			params: { dicCode: 'S101-14' },
			success: function(response){
				items = [];
				var data = Ext.decode(response.responseText);
				var dataList = data.dataList;
				for(var i=0;i<dataList.length;i++){
					var chk = {
						boxLabel: dataList[i].name,
						name: 'tentBaseInfo.entTypeCode',
						width: 75,
						inputValue: dataList[i].code
					};
					items.push(chk);
				}
			}
		});
    var cbo = new Ext.form.field.ComboBox(Ext.apply({
        store: storeDic,
        triggerAction: 'all',
        queryMode: 'local',
        emptyText: '—请选择—',
        valueField: 'code',
        displayField: 'name',
        editable: false
    }, config));

    /*
        字典索引类的表单域名以数据字典项命名为准
        当一个页面使用多个字典索引类时，用下划线加后缀区分
    */
    var _id = cbo.getId();
    var _name = cbo.getName();

    var indexStr = _id; //字典索引字符串
    if (_id.indexOf("_" + _name) < 0) {
        /*
            控件不设置Id，系统会自动生成Id串，
            判断id串中是否含有Name域名，如果有，则证明是自己设置的Id，是属于是否有无类的字典项；
            如没有，条件是true的情况，则取Name域值即可。
        */
        indexStr = _name;
        /*
            只有是否、有无字典需要设置Id属性，Is_DicIndex或Has_DicIndex；
            其余的字典项无须设置Id属性。
        */
    }

    var splitIndex = indexStr.indexOf('_');
    if (splitIndex >= 0) {
        /*
            取下划线分隔符之前的字符串，作为字典索引名称，分隔符后的仅作同类字典项在同一页面的区分符号。
        */
        indexStr = indexStr.substr(0, splitIndex);
    }
    //console.log(config);
    if (config.dicIndex) {
        indexStr = config.dicIndex;
    }
    var dicFcode='';
    if (config.dicFcode) {
    	dicFcode = config.dicFcode;
    }
    storeDic.load({
        params: { dicCode: indexStr,fCode:dicFcode },
        callback: function () {
            storeDic.insert(0, _DicRecordEmpty);
        }
    });
    return cbo;
}