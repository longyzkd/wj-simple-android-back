//数据字典下拉控件

Ext.ns('uc');

Ext.define('DicModel', {
    extend: 'Ext.data.Model',
    idProperty: 'Code',
    fields: [
        { name: 'Code', type: 'string' },
        { name: 'Name', type: 'string' }
    ]
});

uc.cboTrueFalse = function (config) {
    var data = [
        { Code: '', Name: '' },
        { Code: 'true', Name: '是' },
        { Code: 'false', Name: '否' }
    ];

    var storeDic = Ext.create('Ext.data.Store', {
        model: 'DicModel',
        data: data
    });

    var cbo = new Ext.form.field.ComboBox(Ext.apply({
        store: storeDic,
        triggerAction: 'all',
        queryMode: 'local',
        emptyText: '—请选择—',
        valueField: 'Code',
        displayField: 'Name',
        editable: false
    }, config));

    return cbo;
}