
Ext.define("Ext.ux.AreaTree", {
    extend: "Ext.form.field.Picker",
    alias: 'widget.combotree',
    requires: ["Ext.tree.Panel"],
    _idValue: '',
    _txtValue: '',
    initComponent: function () {
        Ext.define('AreaModel', {
            extend: 'Ext.data.Model',
            idProperty: 'AreaCode',
            fields: [
                { name: 'AreaCode', type: 'string' },
                { name: 'AreaName', type: 'string' }
            ]
        });
        this.callParent();
        var self = this;
        var store = Ext.create('Ext.data.TreeStore', {
            autoLoad: true,
            model: 'AreaModel',
            proxy: {
                type: 'ajax',
                url: self.storeUrl,
                reader: { type: 'json', root: 'dataList' }
            },
            root: {
                expanded: self.expanded
            },
            listeners: {
                //在展开前调用，发送请求，请求中应有节点信息
                beforeexpand: function (node, eOpts) {
                    this.proxy.extraParams.FAreaCode = node.get('AreaCode') == 'root' ? '' : node.get('AreaCode');
                }
            }
        });
        self.picker = new Ext.tree.Panel({
            id: Ext.id(),
            height: self.treeHeight || 300,
            resizable: true, minWidth: 100, maxWidth: 400, minHeight: 200, maxHeight: 500,
            autoScroll: true,
            floating: true,
            focusOnToFront: false,
            shadow: true,
            lines: true,
            editable: self.editable,
            ownerCt: this.ownerCt,
            displayField: self.displayField,
            valueField: self.valueField,
            collapsible: true,
            columnLines: true,
            preventHeader: true,
            split: true,
            rootVisible: false,
            useArrows: self.useArrows || true,
            store: store
        });
        self.picker.on({
            'itemclick': function (view, rec) {
                if (rec && ((self.selectMode == 'leaf' && rec.isLeaf() == true) || self.selectMode == 'all')) {
                    self._idValue = rec.get('AreaCode');
                    self.setValue(self._txtValue = rec.get('AreaName')); // 显示值  
                    self.collapse();
                    self.fireEvent('select', self, rec);
                }
            }
        });
    },
    getValue: function () {//获取id值  
        return this._idValue;
    },
    getTextValue: function () {//获取text值  
        return this._txtValue;
    },
    giveValue: function (id, v) {
        this._idValue = id;
        this.setValue(this._txtValue = v);
    },
    reLoad: function (id, url) {
        var store = this.picker.getStore();
        var root = this.picker.getRootNode();
        store.proxy.url = url;
        root.set('id', id);
        store.load();
    },
    alignPicker: function () {
        var me = this, picker, isAbove, aboveSfx = '-above';
        if (this.isExpanded) {
            picker = me.getPicker();
            if (me.matchFieldWidth) {
                picker.setWidth(me.bodyEl.getWidth());
            }
            if (picker.isFloating()) {
                picker.alignTo(me.inputEl, "", me.pickerOffset);
                isAbove = picker.el.getY() < me.inputEl.getY();
                me.bodyEl[isAbove ? 'addCls' : 'removeCls'](me.openCls + aboveSfx);
                picker.el[isAbove ? 'addCls' : 'removeCls'](picker.baseCls + aboveSfx);
            }
        }
    }
});