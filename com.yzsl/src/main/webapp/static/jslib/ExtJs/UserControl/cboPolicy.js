//可用于报销的年度

Ext.ns('uc');

Ext.define('PolicyModel', {
    extend: 'Ext.data.Model',
    idProperty: 'PolicyCode',
    fields: [
        { name: 'PolicyCode', type: 'string' },
        { name: 'PolicyName', type: 'string' }
    ]
});

uc.cboPolicy = function (config) {
    var store = Ext.create('Ext.data.Store', {
        autoLoad: false,
        model: 'PolicyModel',
        proxy: {
            type: 'ajax',
            url: rootUrl + 'TDbbxPolicy/GetPolicyList',
            reader: { type: 'json', root: 'dataList' }
        }
    });

    var cbo = new Ext.form.field.ComboBox(Ext.apply({
        name: 'PolicyCode',
        store: store,
        triggerAction: 'all',
        queryMode: 'local',
        valueField: 'PolicyCode',
        displayField: 'PolicyName',
        fieldLabel: '保险政策',
        labelAlign: 'right',
        editable: false
    }, config));

    //store.load({
    //    params: { ND: config.ND }
    //});

    return cbo;
}