
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

uc.cboPlanArea_Xnh = function (config) {
    var store = Ext.create('Ext.data.Store', {
        autoLoad: false,
        model: 'PlanAreaModel',
        proxy: {
            type: 'ajax',
            url: rootUrl + 'Base/ReadTBaseOrganListByUserOrgan',
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

    return cbo;
}