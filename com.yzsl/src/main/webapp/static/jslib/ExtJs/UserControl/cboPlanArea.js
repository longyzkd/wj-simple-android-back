//可用于报销的年度

Ext.ns('uc');

Ext.define('PlanAreaModel', {
    extend: 'Ext.data.Model',
    idProperty: 'PlanAreaCode',
    fields: [
        { name: 'PlanAreaCode', type: 'string' },
        { name: 'PlanAreaName', type: 'string' }
    ]
});

uc.cboPlanArea = function (config) {
    var store = Ext.create('Ext.data.Store', {
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

    store.load({
        callback: function (records, operation, success) {
            
        }
    });

    return cbo;
}