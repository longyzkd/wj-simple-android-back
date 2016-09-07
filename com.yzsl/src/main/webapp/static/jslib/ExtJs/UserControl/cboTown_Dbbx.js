Ext.ns('uc');

Ext.define('TownModel', {
    extend: 'Ext.data.Model',
    idProperty: 'TownCode',
    fields: [
        { name: 'TownCode', type: 'string' },
        { name: 'TownName', type: 'string' }
    ]
});

uc.cboTown_Dbbx = function (config) {
    var store = Ext.create('Ext.data.Store', {
        model: 'TownModel',
        proxy: {
            type: 'ajax',
            url: rootUrl + 'Dbbx/GetAreaTown',
            reader: { type: 'json', root: 'dataList' }
        }
    });

    var cbo = new Ext.form.field.ComboBox(Ext.apply({
        store: store,
        triggerAction: 'all',
        queryMode: 'local',
        emptyText: '请选择',
        valueField: 'TownCode',
        displayField: 'TownName',
        fieldLabel: '乡镇',
        labelAlign: 'right',
        editable: false
    }, config));

    return cbo;
}