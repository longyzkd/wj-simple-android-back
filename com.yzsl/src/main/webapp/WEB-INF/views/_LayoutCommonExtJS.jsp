<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<link href="<%=path%>/static/style/style.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/static/jslib/ExtJs/resources/css/ext-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/static/jslib/ExtJs/resources/css/ext-all-rtl.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/static/jslib/ExtJs/resources/css/ext-patch.css" rel="stylesheet" type="text/css" />

<script src="<%=path%>/static/jslib/ExtJs/bootstrap.js" type="text/javascript"></script>
<script src="<%=path%>/static/jslib/ExtJs/locale/ext-lang-zh_CN.js" type="text/javascript"></script>    
<script src="<%=path%>/static/jslib/script/rootPath.js?v=1.1" type="text/javascript"></script>
<script src="<%=path%>/static/jslib/script/function.js" type="text/javascript"></script>
<script src="<%=path%>/static/jslib/script/extend.js" type="text/javascript"></script>



<script type="text/javascript">

		var basepath = '${ctx}';
		
    	curUser = '<shiro:principal property="userId"/>';
    	//TODO 从后台带过来
    	pageSize=2;
		
        Ext.grid.PageRowNumberer = Ext.extend(Ext.grid.RowNumberer, {
            width: 40,
            renderer: function (value, cellmeta, record, rowIndex, columnIndex, store) {
                if (store.lastOptions.params != null) {
                    var pageindex = store.lastOptions.params.start;
                    return pageindex + rowIndex + 1;
                } else {
                    return rowIndex + 1;
                }
            }
        });
        
        function titleName(action) {
            var title = '[æ¥ç]';
            switch (action) {
                case 'insert': title = '[æ°å¢]'; break;
                case 'update': title = '[ä¿®æ¹]'; break;
            }
            return title;
        }
        


        function closeRefresh() {
        	CloseWindow();
        }
        
        
    </script>
    <script src="<%=path%>/static/jslib/ExtJs/vTypes.js" type="text/javascript"></script>