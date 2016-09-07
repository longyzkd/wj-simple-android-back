
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE>
<html>
<head>
	<title></title>
	<jsp:include page="_LayoutCommonExtJS.jsp"/>
	<script type="text/javascript">
    Ext.require(['*']);

    Ext.onReady(function () {    
    	
        Ext.QuickTips.init();

        Ext.state.Manager.setProvider(Ext.create('Ext.state.CookieProvider'));

        Ext.define('MenuModel', {
            extend: 'Ext.data.Model',
            idProperty: 'MenuId',
            fields: [
                { name: 'MenuId', type: 'string' },
                { name: 'FMenuId', type: 'string' },
                { name: 'Name', type: 'string' }
            ]
        });

        var cmMenu = [{
            xtype: 'treecolumn',
            dataIndex: 'Name',
            width: 180,
            titleAlign: 'center',
            sortable: false
        }];

        var frmWest = new Ext.Panel({
            region: 'west',
            stateId: 'navigation-panel',
            id: 'west-panel', // see Ext.getCmp() below
            title: '功能菜单',
            iconCls:'menu_home',
            split: true,
            width: 200,
            minWidth: 175,
            maxWidth: 400,
            collapsible: true,
            animCollapse: true,
            margins: '0 0 0 5',
            layout: 'accordion',
            listeners: {
                'beforerender': function (m) {
                    loadMenu(m);
                }
            }
        });

        var frmSouth = new Ext.Component({
            height: 26,
            region: 'south',
            border: 1,
            html: "<table border='0' cellpadding='0' cellspacing='0' width='100%'><tr><td style='height:26px; color:white; text-align: center;'>©欢迎使用扬州闸后台管理系统 </td></tr></table>"
        });

        //-----------------------------------------------------------------------//
        var tabArray = new Array(); //tab中panel的id编号数组

        var frmCenter = Ext.create('Ext.tab.Panel', {
            region: 'center', // a center region is ALWAYS required for border layout
            id:'frmCenter',
            deferredRender: false,
            activeTab: 0
        });

        var viewport = Ext.create('Ext.Viewport', {
            id: 'border-example',
            layout: 'border',
            items: [
            // create instance immediately
                Ext.create('Ext.Component', {
                    region: 'north',
                    height: 55, // give north and south regions a height
                    xtype: "component",
                    layout: {
                        type: "hbox",
                        align: "middle"
                    },
                    html: '<table width="100%" style="margin-left:20px; color:white;" cellspacing="0" cellpadding="0">' +
                            '<tr style="height:20px;"><td width="400px" style="font-size:28px;" rowspan="2"><strong>扬州闸后台管理系统</strong></td></tr>' +
                            '<tr>' +
                            '<td style="text-align:right; padding-right:50px;">帐号：' + 'getUserId' + '&nbsp;&nbsp;名称：'+'getUserName'+'&nbsp;&nbsp;&nbsp;&nbsp;<img id="btnUpdatePWD" style="cursor:pointer;border:none;" alt="修改密码" src="<%=path%>/static/style/image/key.png" /><a id="aUpdatePWD" href="#" style="color:white;text-decoration:none;">修改密码</a>&nbsp;&nbsp;&nbsp;&nbsp;<img id="imgShutup" style="cursor:pointer;border:none;" alt="退出系统" src="<%=path%>/static/style/image/shut_down.png" /></td>' +
                            '</tr>' +
                          '</table>'
                }),
                frmWest,
                frmSouth,
                frmCenter
            ]
        });
        
        Ext.fly("imgShutup").on('click', function (btn) {
            Ext.Msg.confirm('提示', '您是否确定退出系统？', function (btn) {
                if (btn == 'yes') {
                    window.location = '${ctx}/logout';   
                }
            });
        });
        
        Ext.fly("btnUpdatePWD").on('click', function (btn) {
            UpdatePWD();
        });
        Ext.fly("aUpdatePWD").on('click', function (btn) {
            UpdatePWD();
        });

        function loadMenu(panel) {
            var menuList = [];
            Ext.Ajax.request({
                async: false,
                url: '<%=basePath%>base/tmanageruser!doNotNeedSecurity_getMainMenu.do',
                params: {
                    menutype: '2'
                },
                success: function (response, opts) {
                    menuList = Ext.decode(response.responseText);
                }
            });
            var panelItem;
            var treeItem;
            
            for (var i = 0; i < menuList.length; i++) {
                //panelItem = new Ext.panel.Panel(menuList[i]);
                panelItem = Ext.create('Ext.panel.Panel', {
                    id: 'menu' + menuList[i].id,
                    title: menuList[i].title,
                    iconCls: menuList[i].iconCls,
                    border: 0,
                    layout: 'fit'
                });
                treeItem = new Ext.tree.TreePanel({
                    rootVisible: false,
                    border: 0,
                    hideHeaders: true,
                    store: Ext.create('Ext.data.TreeStore',{ 
                        proxy: {
                            type: 'memory',
                            data: menuList[i].children,
                            reader: {
                                type: 'json'
                                
                            }
                        }
                    }),
                    listeners: {
                        itemclick: function (th, record) {
                            var str = record.data.id.split("|");
                            
                            if (str[1] != "") {
                                CreateTab(str[0], record.data.text, str[1]);
                            }
                            else {
                                if (record.childNodes.length == 0) {
                                    Ext.Msg.show({
                                        title: '提示',
                                        msg: "Sorry，功能尚在建设中！",
                                        buttons: Ext.Msg.CANCEL,
                                        icon: Ext.Msg.INFO
                                    });
                                }
                            }
                        }
                    }
                });
                
                panelItem.add(treeItem);
                panel.add(panelItem);
            }
        }

        //建立Tab页面(id：菜单项ID，text：菜单名称，href：链接地址)
        function CreateTab(id, text, href) {
            var tabId = 'tab' + id;
            var tab = frmCenter.getComponent(tabId);
            if (!tab) {
                var url = "<%=path%>/" + href; //链接地址
                tab = new Ext.panel.Panel({
                    id: tabId,
                    closable: true,
                    title: text,
                    titleAlgin: 'center',
                    closeText: '关闭' + text,
                    iconCls: 'tab',
                    autoScroll: false,
                    border: false,
                    layout: 'fit',
                    html: '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="' + url + '"> </iframe>',
                    autoDestroy: true,
                    listeners: {
                        render: function () {
                            tabArray.push(tabId);
                            //控制tab下的panel不超过5个
                            if (tabArray.length > 5) {
                                frmCenter.remove(frmCenter.getComponent(tabArray[0]));
                                tabArray.shift(); //删除第editDicData一个panel编号
                            }
                        },
                        close: function () {
                            frmCenter.remove(frmCenter.getComponent(tabId));
                            //tabArray.remove(tabId);

                        }
                    }
                });
                frmCenter.add(tab);
            }
            frmCenter.setActiveTab(tab);
        }

        //修改密码
        function UpdatePWD() {
            var winTitle = '修改密码'; 

            //编辑form
            if (form != null) {
                form.destroy(true);
                form = null;
            }
            if (!form) {
                var txtOldPwd = new Ext.form.field.Text({
                    name: 'OldPwd',
                    inputType: 'password',
                    fieldLabel: '<font style="color:red;">原始密码</font>',
                    regex: /^[A-Za-z0-9_]+$/,
                    regexText: '请输入有效的密码，含数字，字母，下划线',
                    allowBlank: false,
                    labelWidth: 60,
                    vtype:'checkpwd'
                });

                var txtNewPwd = new Ext.form.field.Text({
                    name: 'NewPwd',
                    inputType: 'password',
                    fieldLabel: '<font style="color:red;">新密码</font>',
                    regex: /^[A-Za-z0-9_]+$/,
                    regexText: '请输入有效的密码，含数字，字母，下划线',
                    allowBlank: false,
                    labelWidth: 60
                });

                var txtNewPwdAg = new Ext.form.field.Text({
                    name: 'NewPwdAg',
                    inputType: 'password',
                    fieldLabel: '<font style="color:red;">再次输入</font>',
                    regex: /^[A-Za-z0-9_]+$/,
                    regexText: '请输入有效的密码，含数字，	字母，下划线',
                    allowBlank: false,
                    labelWidth: 60,
                    validator: function (value) {
                        var pwd = this.previousSibling().value;
                        if (value != pwd) {
                            return '两次输入的密码不一致！';
                        }
                        else {
                            return true;
                        }
                    }
                });

                var form = new Ext.form.Panel({
                    fieldDefaults: {
                        labelAlign: 'right',
                        msgTarget: 'side',
                        labelWidth: 70
                    },
                    bodyStyle: 'padding:15px',
                    items: [txtOldPwd, txtNewPwd, txtNewPwdAg]
                });
            }
        //信息编辑Window
            if (win != null) {
                win.destroy(true);
                win = null;
            }
            if (!win) {
                var win = new Ext.window.Window({
                    title: winTitle,
                    width: 300,
                    height: 200,
                    modal: true,
                    resizeable: false,
                    collapsible: true,
                    closeAction: 'close',
                    bodyStyle: 'padding:5px;',
                    layout: 'fit',
                    buttonAlign: 'center',
                    items: [form],
                    buttons: [{
                        type: 'submit',
                        text: '确 定',
                        visible: false,
                        handler: function () {
                            if (form.isValid()) {
                                form.submit({
                                    submitEmptyText: false,
                                    clientValidation: true,
                                    url: '<%=path%>/user/updatePwd',
                                    method: 'POST',
                                    params: {
                                    	password: txtNewPwdAg.getValue()
                                    },
                                    success: function (form, action) {
                                        Ext.Msg.show({
                                            title: '提示',
                                            msg: action.result.msg,
                                            buttons: Ext.Msg.CANCEL,
                                            icon: Ext.Msg.INFO,
                                            fn: function () {
                                                win.close();
                                            }
                                        });
                                    },
                                    failure: function (form, action) {
                                        if (action.failureType == Ext.form.action.Action.SERVER_INVALID) {
                                            Ext.Msg.show({
                                                title: '提示',
                                                msg: action.result.msg,
                                                buttons: Ext.Msg.CANCEL,
                                                icon: Ext.Msg.ERROR
                                            });
                                        }
                                        else {
                                            Ext.Msg.show({
                                                title: '提示',
                                                msg: '无法访问后台！',
                                                buttons: Ext.Msg.CANCEL,
                                                icon: Ext.Msg.ERROR
                                            });
                                        }
                                    }
                                });
                            }
                            else {
                                Ext.Msg.show({ title: '提示', msg: '请检查输入项是否正确！', buttons: Ext.Msg.OK, icon: Ext.Msg.WARNING });
                            }
                        }
                    }, {
                        type: 'button',
                        text: '关 闭',
                        handler: function () { win.close(); }
                    }]
                });
            }
            win.show();

            //
            function SavePwd() {
                
                }
        }
    });
</script>

<html>
<body>
    <!-- use class="x-hide-display" to prevent a brief flicker of the content -->
    <div id="south" class="x-hide-display">
        <p>江苏金鑫信息技术有限公司</p>
    </div>
</body>
</html>
