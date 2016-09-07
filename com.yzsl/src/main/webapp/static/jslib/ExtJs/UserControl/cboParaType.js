/*大病保险参数类型*/

Ext.ns('uc');

Ext.define('TypeModel', {
    extend: 'Ext.data.Model',
    idProperty: 'value',
    fields: [
        { name: 'value', type: 'int' },
        { name: 'text', type: 'string' }
    ]
});

uc.cboParaType = function (config) {
    var data = [
        { 'value': 1, 'text': '整数' },
        { 'value': 2, 'text': '小数' },
        { 'value': 3, 'text': '日期' },
        { 'value': 4, 'text': '字符串' }
    ];

    var store = Ext.create('Ext.data.Store', {
        model: 'TypeModel',
        data: data
    });

    var cbo = new Ext.form.field.ComboBox(Ext.apply({
        store: store,
        triggerAction: 'all',
        queryMode: 'local',
        valueField: 'value',
        displayField: 'text',
        fieldLabel: '参数类型',
        labelAlign: 'right',
        editable: false
    }, config));

    return cbo;
}