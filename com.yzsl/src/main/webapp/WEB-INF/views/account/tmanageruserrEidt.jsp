<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<jsp:include page="../_LayoutCommonExtJS.jsp"/>
      <script type="text/javascript" src="<%=path%>/static/jslib/ExtJs/UserControl/sys_Dic.js" ></script>
	<script type="text/javascript">
		Ext.onReady(function(){
			
			Ext.QuickTips.init();

			/*
			var action = getQueryString("action");
            var _UserID = getQueryString("userId");
            */
            var action = '${action}';
            var _UserID = '${userId}'; //后台null，这里就是空串
            
            Ext.define('RightsModel', {
                extend: 'Ext.data.Model',
                idProperty: 'funID',
                fields: [
	    			{ name: 'funID', type: 'string' },
                    { name: 'ffunID', type: 'string' },
                    { name: 'funName', type: 'string' },
                    { name: 'note', type: 'string' }
	    		]
            });
            
            var storeRights = Ext.create('Ext.data.TreeStore', {
                autoLoad: true,
                model: 'RightsModel',
                folderSort: false,
                proxy: {
                    type: 'ajax',
                    url: '<%=basePath%>permission/getRights',
                    reader: { type: 'json' }
                   // reader: { type: 'json' , root: 'obj' }
                },
               	listeners: {
                    beforeload: function (store, options) {
                        var params = { action: action, userId: _UserID };
                        Ext.apply(store.proxy.extraParams, params);
                    }
                }
            });
            
            var cmRights = [{
                xtype: 'treecolumn',
                text: '名称',
                sortable: false,
                dataIndex: 'funName',
                width: 250,
                titleAlign: 'center'
            }, {
                text: '编号',
                dataIndex: 'funID',
                width: 100,
                titleAlign: 'center'
            }, {
                text: '备注',
                sortable: false,
                flex: 1,
                dataIndex: 'note',
                titleAlign: 'center'
            }];
            
            var treeRights = Ext.create('Ext.tree.Panel', {
                title: '权限',
                store: storeRights,
                rootVisible: false,
                frame: false,
                columnLines: true,
                checkModel: "parentCascade",
                multiSelect: false,
                singleExpand: true,
                columns: cmRights,
                height: 390,
                listeners: {
                    "checkchange": function (node, state) {
                        //-------------------------------------------------
                        //级联勾选多个父级节点
                        checkedParentNode(node, state)
                        //-------------------------------------------------
                        //级联勾选子级节点
                        node.cascadeBy(function (child) {
                            child.set("checked", state);
                        });
                        //-------------------------------------------------
                    }

                }
            });
            
            function checkedParentNode(node, state) {
                if (node.parentNode != null) {
                    if (state) {
                        node.parentNode.set('checked', state);
                    }
                    checkedParentNode(node.parentNode, state)
                }
            }
            
            //-----------------------------------------------------------------------
            
            var storeType = Ext.create('Ext.data.Store', {
	            autoLoad: false,
	            fields: [
	                    'Code',
	                    'Name'
	                ],
	            data: [
	                    {Code: '1', Name: '管理员'},
	                    {Code: '2', Name: '办事处'},
	                    {Code: '3', Name: '安装工'},
	                    {Code: '4', Name: '客户'},
	                    {Code: '5', Name: '销售人员'}
	                    ]
	        });
	        
	        var cboUserType = Ext.create('Ext.form.ComboBox', {
	            name: 'userType',
	            fieldLabel: '用户类型',
	            emptyText: '请选择',
	            store: storeType,
	            displayField: 'Name',
	            valueField: 'Code',
	            allowBlank: false,
	            labelAlign: 'right'
	        });
	        
            var txtUserID = new Ext.form.field.Text({
                name: 'userId',
                fieldLabel: '帐号',
                maxLength: 20,
                readOnly: ((action == 'update') ? true : false),
                allowBlank: false
              
            });
            var txtUserName = new Ext.form.field.Text({
                name: 'username',
                fieldLabel: '名称',
                maxLength: 20,
                allowBlank: false
              
            });
            var txtPwd =  {
                xtype: 'textfield',
                inputType: 'password',
                name : 'plainPassword',
                fieldLabel: '密码',
                maxLength :10,
                id:'plainPassword',
                hidden:action=='update'?true:false
            };
            var txtPwdConfirm =   {
                xtype: 'textfield',
                inputType: 'password',
                fieldLabel: '确认密码',
                name : 'password',
                maxLength :10,
                validator: function (value) {
                        var pwd = this.previousSibling().value;
                        if (value != pwd) {
                            return '两次输入的密码不一致！';
                        }
                        else {
                            return true;
                        }
                    },
                     hidden:action=='update'?true:false
            };    

            

            var cboIsStop = new Ext.form.field.ComboBox({
                name: 'isStoped',
                fieldLabel: '停用',
                store: new Ext.data.Store({
                    fields: ['Text', 'Code'],
                    data: [
                                {Text: '否', Code: false},
                                {Text: '是', Code: true}
                            ]
                }),
                displayField: 'Text',
                valueField: 'Code',
                labelAlign: 'right',
                editable: false,
                allowBlank: false
               
            });

            cboIsStop.setValue(false);

           //function createForm(){
           
            var formRight = new Ext.form.Panel({
                region: 'center',
                title: '用户信息&nbsp;&nbsp;' + titleName(action),
                width: '100%',
                bodyPadding: 10,
                buttonAlign: 'center',
                items: [{
                    xtype: 'fieldcontainer',
                    layout: { type: 'hbox', align: 'middle' },
                    fieldDefaults: { labelAlign: 'right', labelWidth: 65, width: 245 },
                    items: [cboUserType, cboIsStop]
                }, {
                    xtype: 'fieldcontainer',
                    layout: { type: 'hbox', align: 'middle' },
                    fieldDefaults: { labelAlign: 'right', labelWidth: 65, width: 245 },
                    items: [txtUserID, txtUserName]
                } ],

                buttons: [{
                    type: 'submit',
                    text: '确 定',
                    handler: handleProcess
                }, {
                    type: 'button',
                    text: '关 闭',
                    handler: function () { CloseWindow(); }
                }]
            });
           if(action=='insert'){
           		var container = {
                    xtype: 'fieldcontainer',
                    layout: { type: 'hbox', align: 'middle' },
                    fieldDefaults: { labelAlign: 'right', labelWidth: 65, width: 245 },
                    items: [txtPwd, txtPwdConfirm]
                };
           		formRight.add(container);
           }
           //保证顺序之用
           formRight.add(treeRights);
           
           /*
           return formRight;
           		
           }
           */
            
          //用户Model
            
            <%=com.yzsl.util.JsonHelperJS.FormExtJsModel("com.yzsl.bean.User", "UserModel", "","")%>
            //用户列表Store
            var storeUser = Ext.create('Ext.data.Store', {
                autoLoad: false,
                model: 'UserModel',
                proxy: {
                    type: 'ajax',
                    url: '<%=basePath%>user/one',
                    reader: { type: 'json'}
                    
                },
                listeners: {
                    load: function (store, options) {
                       
                        formRight.getForm().loadRecord(store.getAt(0));
                    }
                }
        	});
            
            

            //表单自动加载数据 -----------------------------------------------------------------------------------------------------
            if (action != 'insert') {
            	storeUser.load({
                    params: {
                    	'userId': _UserID
                    }
                });
            }

            new Ext.container.Viewport({
                layout: 'border',
                items: [formRight]
            });

            //函数区
            //----------------------------------------------------------------------------------------------------------------------
            //标题名称
            function titleName(action) {
                var title = '[查看]';
                switch (action) {
                    case 'insert': title = '[新增]'; break;
                    case 'update': title = '[修改]'; break;
                }
                return title;
            }

            //关闭窗口刷新
            function closeRefresh() {
            	CloseWindow();
            }

            function handleProcess() {
                //编辑--------------------------------------------------------------------------
                if (action == 'insert' || action == 'update') {
                    var records = treeRights.getChecked();
                    if ( records.length < 1) {
                        Ext.Msg.alert('提示', '请选择权限！');
                        return false;
                    }
                    var RightsIdArray = [];
                    Ext.each(records, function (record) {
                        RightsIdArray.push(record.data['funID']);
                    });
                    var action_url= '<%=basePath%>user/saveUser';
                    if(action == 'update'){
                    	action_url= '<%=basePath%>user/updateUser';
                    }
                    
                    var form = this.up('form').getForm();
                    if (form.isValid()) {
                        form.submit({
                            submitEmptyText: false,
                            clientValidation: true,
                            url: action_url,
                            method: 'POST',
                            params: { action: action, funidvalue: RightsIdArray },
                            success: function (form, action) {
                                Ext.Msg.show({
                                    title: '提示',
                                    msg: action.result.msg,
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.Msg.INFO,
                                    fn: CloseWindow
                                });
                            },
                            failure: function (form, action) {
                                if (action.failureType == Ext.form.action.Action.SERVER_INVALID) {
                                    Ext.Msg.show({
                                        title: '提示',
                                        msg: action.result.msg,
                                        buttons: Ext.Msg.OK,
                                        icon: Ext.Msg.ERROR
                                    });
                                }
                                else {
                                    Ext.Msg.show({
                                        title: '提示',
                                        msg: '无法访问后台！',
                                        buttons: Ext.Msg.OK,
                                        icon: Ext.Msg.ERROR
                                    });
                                }
                            }
                        });
                    }
                    else {
                        Ext.Msg.show({ title: '提示', msg: '信息验证不通过！', buttons: Ext.Msg.CANCEL, icon: Ext.Msg.WARNING });
                    }
                }

            }
//--------------------------------------------------------------------------------------
		});
	</script>
  </head>
  
  <body>
  </body>
</html>
