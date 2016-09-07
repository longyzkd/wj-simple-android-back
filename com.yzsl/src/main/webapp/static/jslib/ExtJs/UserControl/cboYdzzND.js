
Ext.ns('uc');

Ext.define('YearModel', {
    extend: 'Ext.data.Model',
    idProperty: 'ND',
    fields: [
        { name: 'ND', type: 'string' }
    ]
});



uc.cboYdzzND = function (config) {
    var store = Ext.create('Ext.data.Store', {
        model: 'YearModel',
        proxy: {
            type: 'ajax',
            url: rootUrl + 'Ydzz/GetND',
            reader: { type: 'json', root: 'data' }
        }
    });

    var cbo = new Ext.form.field.ComboBox(Ext.apply({
        store: store,
        triggerAction: 'all',
        queryMode: 'local',
        valueField: 'ND',
        displayField: 'ND',
        fieldLabel: '年度',
        labelAlign: 'right',
        editable: false
    }, config));

    store.load({
        callback: function (records, operation, success) {
            if (success) {
                var fullYear = new Date().getFullYear();
                var isNow = false;
                for (var i = 0; i < records.length; i++) {
                    if (fullYear == records[i].data.ND) {
                        isNow = true;
                    }
                }
                if (isNow)
                    cbo.setValue(fullYear);
                else
                    cbo.setValue(records[0].data.ND);
            }
        }
    });

    return cbo;
}