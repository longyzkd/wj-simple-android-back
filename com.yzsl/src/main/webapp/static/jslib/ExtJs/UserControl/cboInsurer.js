//可用于报销的年度

Ext.ns('uc');

Ext.define('InsurerModel', {
    extend: 'Ext.data.Model',
    idProperty: 'InsurerCode',
    fields: [
        { name: 'InsurerCode', type: 'string' },
        { name: 'InsurerName', type: 'string' }
    ]
});

uc.cboInsurer = function (config) {
    var store = Ext.create('Ext.data.Store', {
        model: 'InsurerModel',
        proxy: {
            type: 'ajax',
            url: rootUrl + 'TDbbxInsurer/GetInsurerList',
            reader: { type: 'json', root: 'dataList' }
        }
    });

    var cbo = new Ext.form.field.ComboBox(Ext.apply({
        name: 'InsurerCode',
        store: store,
        triggerAction: 'all',
        queryMode: 'local',
        valueField: 'InsurerCode',
        displayField: 'InsurerName',
        fieldLabel: '承保单位',
        labelAlign: 'right',
        editable: false
    }, config));

    store.load();
    return cbo;
}