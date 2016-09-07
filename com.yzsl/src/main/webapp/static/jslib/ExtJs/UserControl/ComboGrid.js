//Ext.define('Ext.form.ComboGrid', {
//    extend: 'Ext.form.ComboBox',
//    alias: ['widget.Ext.form.ComboGrid'],
//    requires: ['Ext.grid.Panel'],
//    queryCaching: false,
//    matchFieldWidth: false,//使grid宽度可以不同步为下拉框宽度
//    queryParam: 'query',
//    queryMode: 'remote',
//    typeAhead: true,
//    typeAheadDelay: 1000,
//    minChars: 2,
//    checkChangeBuffer: 1000,
//    hideTrigger: true,//隐藏下拉按钮
//    createPicker: function () {
//        var me = this,
//        picker,
//        menuCls = Ext.baseCSSPrefix + 'menu',
//        opts = Ext.apply({
//            selModel: { mode: me.multiSelect ? 'SIMPLE' : 'SINGLE' },
//            columnLines: true,
//            floating: true,
//            //hidden: true,
//            ownerCt: me.ownerCt,
//            cls: me.el.up('.' + menuCls) ? menuCls : '',
//            store: me.store,
//            displayField: me.displayField,
//            focusOnToFront: false,
//            pageSize: me.pageSize,
//            width: me.panelWidth,
//            matchFieldWidth: false,
//            listeners: {
//                cellkeydown: function (td, cellIndex, record, tr, rowIndex, e, eOpts) {
//                    if (eOpts.keyCode == 13) {
//                        me.selectItem(tr);
//                    }
//                }
//            }
//        },
//            me.listConfig, me.defaultListConfig
//        );

//        picker = me.picker = new Ext.grid.Panel(opts);// Ext.create('Ext.grid.Panel', opts);

//        //picker.store.on("load", function () {
//        //    picker.getView().focus();
//        //});
//        picker.getNode = function () {
//            picker.setWidth(me.panelWidth);
//            picker.getView().getNode(arguments);
//            //picker.getView().focus();
//        };
//        me.mon(picker, {
//            itemclick: me.onItemClick,
//            refresh: me.onListRefresh,
//            Keypress: me.onPickerKeypress,
//            scope: me
//        });
//        me.mon(picker.getSelectionModel(), {
//            beforeselect: me.onBeforeSelect,
//            beforedeselect: me.onBeforeDeselect,
//            selectionChange: me.onListSelectionChange,
//            scope: me
//        });
//        me.mon(me.inputEl, 'click', me.onTriggerClick, me);


//        view = picker.getView();

//        return picker;
//    },
//    onViewRender: function (view) {
//        view.getEl().on('keypress', this.onPickerKeypress, this);
//    },
//    onTypeAhead: function () {
//        var me = this, df = me.displayField;
//        var st = me.store, rv = me.getRawValue();
//        var r = me.store.findRecord(df, rv);
//        if (r) {
//            var nv = r.get(df), ln = nv.length, ss = rv.length;
//            if (ss !== 0 && ss !== ln) {
//                me.setRawValue(nv);
//                me.selectText(ss, nv.length);
//            }
//        }
//    },
//    doTypeAhead: function () {
//        if (!this.typeAheadTask) {
//            this.typeAheadTask = Ext.create('Ext.util.DelayedTask', this.onTypeAhead, this);
//        }
//        if (this.lastKey != Ext.EventObject.BACKSPACE
//				&& this.lastKey != Ext.EventObject.DELETE) {
//            this.typeAheadTask.delay(this.typeAheadDelay);
//        }
//    },
//    alignPicker: function () {
//        var me = this,
//            picker;
//    },
//    onItemClick: function (view, record, node, rowIndex, e) {
//        this.selectItem(record);
//    },
//    onPickerKeypress: function (e, el) {
//        var key = e.getKey();

//        if (key === e.ENTER || (key === e.TAB && this.selectOnTab)) {
//            this.selectItem(this.picker.getSelectionModel().getSelection()[0]);
//        }
//    },
//    selectItem: function (record) {
//        var me = this;
//        me.setValue(record.get(this.valueField));
//        me.setRawValue(record.get(this.displayField));
//        if (me.picker) {
//            me.picker.hide();
//            me.inputEl.focus();
//            me.fireEvent('select', me, record);
//        }
//    },
//    onExpand: function () {
//        var me = this,
//            picker = me.picker,
//            store = picker.store,
//            value = me.value,
//            node;

//        Ext.defer(function () {
//            //picker.getView().focus();
//        }, 1);
//    },
//    setValue: function (value) {
//        var me = this,
//            record;

//        me.value = value;

//        if (me.store.loading) {
//            return me;
//        }
//        me.setRawValue(record ? record.get(me.displayField) : '');

//        return me;
//    },
//    setFieldValue: function (value, display) {
//        this.setValue(value);
//        this.setRawValue(display);
//    },

//    getSubmitValue: function () {
//        return this.value;
//    },
//    getValue: function () {
//        return this.value;
//    },
//    onLoad: function () {
//        var value = this.value;

//        if (value) {
//            this.setValue(value);
//        }
//    },
//    onUpdate: function (store, rec, type, modifiedFieldNames) {
//        var display = this.displayField;

//        if (type === 'edit' && modifiedFieldNames && Ext.Array.contains(modifiedFieldNames, display) && this.value === rec.getId()) {
//            this.setRawValue(rec.get(display));
//        }
//    }
//});


Ext.define('Ext.form.ComboGrid', {
    extend: 'Ext.form.ComboBox',
    alias: ['widget.Ext.form.ComboGrid'],
    requires: ['Ext.grid.Panel'],
    queryCaching: false,
    typeAhead: true,
    typeAheadDelay: 1000,
    matchFieldWidth: false,//使grid宽度可以不同步为下拉框宽度
    queryParam: 'query',
    queryMode: 'remote',
    typeAhead: true,
    minChars: 2,
    checkChangeBuffer: 1000,
    hideTrigger: true,//隐藏下拉按钮
    createPicker: function () {
        var me = this,
        picker,
        menuCls = Ext.baseCSSPrefix + 'menu',
        opts = Ext.apply({
            selModel: { mode: me.multiSelect ? 'SIMPLE' : 'SINGLE' },
            columnLines: true,
            floating: true,
            //hidden: true,
            ownerCt: me.ownerCt,
            cls: me.el.up('.' + menuCls) ? menuCls : '',
            store: me.store,
            displayField: me.displayField,
            focusOnToFront: false,
            pageSize: me.pageSize,
            width: me.panelWidth,
            matchFieldWidth: false,
            listeners: {
                cellkeydown: function (td, cellIndex, record, tr, rowIndex, e, eOpts) {
                    if (eOpts.keyCode == 13) {
                        me.selectItem(tr);
                    }
                }
            }
        },
            me.listConfig, me.defaultListConfig
        );
        picker = me.picker = Ext.create('Ext.grid.Panel', opts);

        me.picker.on({
            'selectItem': function (view, rec) {
                me.setValue(rec.get(me.valueField));
                me.picker.hide();
                me.inputEl.focus();
                me.fireEvent('select', me, rec)
            }
        });
        picker.getNode = function () {
            picker.setWidth(me.panelWidth);
            picker.getView().getNode(arguments);
            //picker.getView().focus();
            //me.inputEl.focus();
        }; 
        me.mon(picker, {
            itemclick: me.onItemClick,
            refresh: me.onListRefresh,
            scope: me
        });
        me.mon(picker.getSelectionModel(), {
            beforeselect: me.onBeforeSelect,
            beforedeselect: me.onBeforeDeselect,
            selectionChange: me.onListSelectionChange,
            scope: me
        });
        me.mon(me.inputEl, 'click', me.onTriggerClick, me);
        view = picker.getView();
        return picker;
    },
    onTriggerClick: function () {
        var me = this;
        if (!me.readOnly && !me.disabled) {
            if (me.isExpanded) {
                me.collapse();
            } else {
                me.onFocus({});
                if (me.triggerAction === 'all') {
                    me.doQuery(me.allQuery, true);
                } else if (me.triggerAction === 'last') {
                    me.doQuery(me.lastQuery, true);
                } else {
                    me.doQuery(me.getRawValue(), false, true);
                }
            }
            me.inputEl.focus();
        }
    },
    onListSelectionChange: function (list, selectedRecords) {
        var me = this;
        if (!me.ignoreSelection && me.isExpanded) {
            if (!me.multiSelect) {
                Ext.defer(me.collapse, 1, me);
            }
            me.setValue(selectedRecords, false);
            if (selectedRecords.length > 0) {
                me.fireEvent('select', me, selectedRecords);
            }
            me.inputEl.focus();
        }
    },
    setValue: function (value) {
        var me = this,
            record;

        me.value = value;

        if (me.store.loading) {
            return me;
        }
        me.setRawValue(record ? record.get(me.displayField) : '');

        return me;
    },
    setFieldValue: function (value, display) {
        this.setValue(value);
        this.setRawValue(display);
    },
    onItemClick: function (view, record, node, rowIndex, e) {
        this.selectItem(record);
    },
    getSubmitValue: function () {
        return this.value;
    },
    getValue: function () {
        return this.value;
    },
    defaultHandlers: {
        down: function () {
            this.picker.getView().focus();
        }
    },
    selectItem: function (record) {
        var me = this;
        me.setValue(record.get(this.valueField));
        me.setRawValue(record.get(this.displayField));
        if (me.picker) {
            me.picker.hide();
            me.inputEl.focus();
            me.fireEvent('select', me, record);
        }
    },
    onExpand: function () {
        var me = this, keyNav = me.listKeyNav;
        var selectOnTab = me.selectOnTab, picker = me.getPicker();

        if (keyNav) {
            keyNav.enable();
        } else {
            keyNav = me.listKeyNav = Ext.create('Ext.view.BoundListKeyNav', this.inputEl, {
                boundList: picker,
                forceKeyDown: true,
                tab: function (e) {
                    if (selectOnTab) {
                        this.selectHighlighted(e);
                        me.triggerBlur();
                    }
                    return true;
                }
            });
        }
        if (selectOnTab) {
            me.ignoreMonitorTab = true;
        }

        var me = this,
            picker = me.picker,
            store = picker.store,
            value = me.value,
            node;

        Ext.defer(function () {
            picker.getView().focus(200);
        }, 1);
        Ext.defer(keyNav.enable, 1, keyNav);
        me.inputEl.focus();
        me.syncSelection();
    },
    syncSelection: function () {
        var me = this, picker = me.picker;
        if (picker && picker.grid) {
            var EA = Ext.Array, gd = picker.grid, st = gd.store;
            var cs = [];
            var sv = this.getSubmitValue();
            EA.each(st.data.items, function (r) {
                if (EA.contains(sv, r.data[me.valueField])) {
                    cs.push(r);
                }
            });
            gd.getSelectionModel().select(cs, false, true);
        }
    },
    onTypeAhead: function () {
        var me = this, df = me.displayField;
        var st = me.store, rv = me.getRawValue();
        var r = me.store.findRecord(df, rv);
        if (r) {
            var nv = r.get(df), ln = nv.length, ss = rv.length;
            if (ss !== 0 && ss !== ln) {
                me.setRawValue(nv);
                me.selectText(ss, nv.length);
            }
        }
    },
    doTypeAhead: function () {
        if (!this.typeAheadTask) {
            this.typeAheadTask = Ext.create('Ext.util.DelayedTask', this.onTypeAhead, this);
        }
        if (this.lastKey != Ext.EventObject.BACKSPACE
    			&& this.lastKey != Ext.EventObject.DELETE) {
            this.typeAheadTask.delay(this.typeAheadDelay);
        }
    },
    doAutoSelect: function () {
        var me = this,
            picker = me.picker,
            lastSelected, itemNode;
        if (picker && me.autoSelect && me.store.getCount() > 0) {
            // Highlight the last selected item and scroll it into view
            lastSelected = picker.getSelectionModel().lastSelected;
            itemNode = picker.getNode(lastSelected || 0);
            if (itemNode) {
                picker.highlightItem(itemNode);
                picker.listEl.scrollChildIntoView(itemNode, false);
            }
        }
    },
    onAdded: function () {
        var me = this;
        me.callParent(arguments);
        if (me.picker) {
            me.picker.ownerCt = me.up('[floating]');
            me.picker.registerWithOwnerCt();
        }
    },
    onLoad: function () {
        var me = this, value = me.value;
        me.syncSelection();
    },
    onUpdate: function (store, rec, type, modifiedFieldNames) {
        var display = this.displayField;

        if (type === 'edit' && modifiedFieldNames && Ext.Array.contains(modifiedFieldNames, display) && this.value === rec.getId()) {
            this.setRawValue(rec.get(display));
        }
    },
    onEditorTab: function (e) {
        var keyNav = this.listKeyNav;

        if (this.selectOnTab && keyNav) {
            keyNav.selectHighlighted(e);
        }
    }
});
