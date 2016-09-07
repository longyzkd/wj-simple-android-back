
Ext.ns('uc');

Ext.define('PlanAreaModel', {
    extend: 'Ext.data.Model',
    idProperty: 'PlanAreaCode',
    fields: [
        { name: 'PlanAreaCode', type: 'string' },
        { name: 'PlanAreaName', type: 'string' },
        { name: 'SubjectionCode', type: 'string' }
    ]
});

//var _DicRecordEmpty = Ext.create('PlanAreaModel', {
//    PlanAreaCode: '',
//    PlanAreaName: '全部',
//    SubjectionCode: ''
//});

uc.cboPlanArea_XnhNotF = function (config) {
    var store = Ext.create('Ext.data.Store', {
        autoLoad: false,
        model: 'PlanAreaModel',
        proxy: {
            type: 'ajax',
            url: rootUrl + 'Base/GetPlanAreaListNotF',
            reader: { type: 'json', root: 'dataList' }
        }
    });

    var cbo = new Ext.form.field.ComboBox(Ext.apply({
        name: 'PlanAreaCode',
        store: store,
        triggerAction: 'all',
        queryMode: 'local',
        valueField: 'PlanAreaCode',
        displayField: 'PlanAreaName',
        fieldLabel: '统筹区',
        labelAlign: 'right',
        editable: false
    }, config));

    //store.load({
    //    callback: function () {
    //        store.insert(0, _DicRecordEmpty);
    //    }
    //});

    return cbo;
}