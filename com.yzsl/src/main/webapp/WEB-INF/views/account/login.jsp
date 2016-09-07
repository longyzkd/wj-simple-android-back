<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE>
<html>
<head>
	<title>用户登录</title>
	<jsp:include page="../_LayoutCommonExtJS.jsp"/>
	<script type="text/javascript">
	
	
	
	
    Ext.require([
        'Ext.tab.*',
        'Ext.window.*',
        'Ext.tip.*',
        'Ext.layout.container.Border'
    ]);   

    Ext.onReady(function () {
        var txtUserId = Ext.create('Ext.form.field.Text', {
            id: 'txtUserId',
            fieldLabel: '帐号',
            width: 220,
            labelWidth: 40,
            maxLength: 20,
            allowBlank: false,
            x: 515,
            y: 260
        });

        var txtPassword = Ext.create('Ext.form.field.Text', {
            fieldLabel: '密码',
            width: 220,
            labelWidth: 40,
            allowBlank: false,
            maxLength: 20,
            inputType: 'password',
            x: 515,
            y: 300
        });
        
        
            
        var usertype="1";
        if(getQueryString("type")!=null)
        	usertype = getQueryString("type");
        
        

        var myMask = new Ext.LoadMask(Ext.getBody(), { msg: "登录验证中，请稍后..." });

        var btnLogin = Ext.create('Ext.button.Button', {
            text: '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;登&nbsp;&nbsp;录&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
            x: 540,
            y: 350,
            listeners: {
                click: login
            }
        });

        //页面回车响应事件
        new Ext.util.KeyMap(document, {
            key: 13, // or Ext.EventObject.ENTER
            fn: function () {
                login();
            },
            scope: this
        });

        var btnReset = Ext.create('Ext.button.Button', {
            text: '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;重&nbsp;&nbsp;置&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
            x: 630,
            y: 350,
            listeners: {
                click: {
                    fn: function () {
                        window.location = window.location;
                    }
                }
            }
        });

        var tbarUser = Ext.create('Ext.toolbar.Toolbar', {
            dock: 'bottom',
            items: [btnLogin, btnReset]
        });

        win = Ext.create('widget.window', {
            title: '系统登录',
            header: {
                titlePosition: 2,
                titleAlign: 'left'
            },
            layout: 'absolute',
            closable: false,
            html: '<img src="<%=path%>/static/style/image/Login.jpg">',
            closeAction: 'hide',
            width: 1011,
            minWidth: 800,
            height: 620,
            tools: [{ type: 'pin' }],
            items: [txtUserId, txtPassword, btnLogin,btnReset]
        });
        win.show();

        Ext.getCmp('txtUserId').focus(false, 300);

        function login() {
            if (!(txtUserId.validate() && txtPassword.validate()))
                return false;

            myMask.show();
            Ext.Ajax.request({
                url: '<%=basePath%>login',
                method: 'post', //filter handle post request
                params: {
                	username: txtUserId.getValue(),
                	password: txtPassword.getValue()
                },
                dataType:'html',
                loadMask: true,
                success: function (response) {
                	
                    myMask.hide();
                    var result = Ext.decode(response.responseText);
                    //console.log(result);
                    //alert(result.success);
                   if(result.success){
                   
                    self.location.href="${ctx}/login/welcome";
                   }else{
                   
                   	 Ext.Msg.show({
                        title: '失败',
                        msg: result.msg,
                        buttons: Ext.Msg.CANCEL,
                        icon: Ext.Msg.WARN
                    });
                   }
                    
                  
                   
                    
                },
                failure: function (response) {
                    myMask.hide();
                    Ext.Msg.show({
                        title: '错误',
                        msg: response.responseText,
                        buttons: Ext.Msg.CANCEL,
                        icon: Ext.Msg.ERROR
                    });
                }
            });
        }        
    });
</script>
</head>
<body style="background: #d7f1fe url('<%=path%>/static/style/image/LoginBg.jpg') repeat-x">
        <%
		String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		if(error != null){
		%>
				登录失败，请重试.
		<%
		}
		%>
</body>
</html>