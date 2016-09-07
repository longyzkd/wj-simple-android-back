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

uc.cboPlanArea_Access = function (config) {
    var store = Ext.create('Ext.data.Store', {
        autoLoad: false,
        model: 'PlanAreaModel',
        proxy: {
            type: 'ajax',
            url: rootUrl + 'TDbbxInsurer/ReadNotAccessList',
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
    //    params: { ND: config.ND }
    //});

    return cbo;
}