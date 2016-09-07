//非文本类输入框,屏蔽退格键回到首页
Ext.getDoc().on('keydown', function (e) {
    if (e.getKey() == 8 && (e.getTarget().type == 'text' || e.getTarget().type == 'password' || e.getTarget().type == 'textarea')&& !e.getTarget().readOnly) {

    } else if (e.getKey() == 8) {
        e.preventDefault();
    }
});
//验证用户权限，返回用户对象，返回验证的结果类型
//验证不通过，进行相应的跳转
//参数说明：para：权限编号
var checkPermission = function (usertype,para) {
    var res = false;
    Ext.Ajax.request({
        url: rootUrl + 'permission.do',
        method: 'post',
        params: { funId: para,type:usertype },
        async: false, //非异步执行
        success: function (response) {
            var result = Ext.decode(response.responseText);
            if (result.success) {
                //获取FeedBack对象中record属性记录（用户信息）
                userJson = result.data;
                permissions = result.dataList;
                res = true;
            } else {
                //跳转并返回登录错误的类型(Allowed：成功，Logout：未登录，Kicked：被踢出，Denied：没权限)
                pageJump(result.info.error);
            }
        },
        failure: function (response) {
            Ext.MessageBox.show({
                title: '错误',
                msg: response.responseText,
                buttons: Ext.MessageBox.OK,
                icon: Ext.MessageBox.ERROR
            });
        }
    });
    return res;
}


//验证用户权限，返回是或否
//参数说明：para：权限编号
var checkPopedom = function (usertype,para) {
    var res = false;
    Ext.Ajax.request({
        url: rootUrl + 'permission.do',
        method: 'post',
        params: { funId: para,type:usertype },
        async: false,
        success: function (response) {
            var result = Ext.decode(response.responseText);
            if (result.success) {
                res = true;
            } else
                res = false;
        },
        failure: function (response) {
            Ext.MessageBox.show({
                title: '错误',
                msg: response.responseText,
                buttons: Ext.MessageBox.OK,
                icon: Ext.MessageBox.ERROR
            });
        }
    });
    return res;
}


//验证后的跳转
//参数说明：code:错误代码
var pageJump = function (code) {
    //滚动条被刷新的次数  
    var count = 10;
    //进度百分比  
    var percentage = 1;
    var pageUrl = rootUrl + 'enter.do';
    var pageTitle = '登录页面';
    var jumpReason = '由于您<font color=red>未登录或长时间未操作</font>，需要重新登录本系统。';
    
    switch (code) {
        case '2':
            pageUrl = rootUrl + 'enter.do';
            pageTitle = '主页面';
            jumpReason = '由于<font color=red>权限</font>问题，您不能访问目标页面！';
            break;

//        case "Kicked":
//            jumpReason = '由于<font color=red>您的帐户在其它地方登录</font>，请重新登录本系统或及时修改密码！';
//            break;
    }

    var textMsg = "<div style='line-height:150%;text-align:center'>" + jumpReason + "</div>";

    var msgBox = Ext.MessageBox.show({
        title: '警告',
        msg: textMsg,
        progress: true,
        width: 400,
        buttons: Ext.Msg.OK,
        scope: document.body,
        fn: function () { top.location = pageUrl; }
    });

    var task = {
        interval: 1000,   //每秒更新
        run: function () {
            //刷新10次后关闭进度条，倒计时10秒  
            if (count < 1) {
                top.location = pageUrl;
                return;
            }
            //计算进度  
            percentage = count / 10;
            //显示内容  
            var processText = '本页将在 ' + count + ' 秒后跳转到 ' + pageTitle + '...';
            //更新进度条;  
            msgBox.updateProgress(percentage, processText, textMsg);
            count--;
        }
    };
    Ext.TaskManager.start(task);
}

var setCheckType = function(checkType){
	Ext.Ajax.request({
		url: rootUrl + 'setCheckType.do',
		method: 'post',
		async: false,
		params: { checkType: checkType }
	});
}

var getStateName = function(dicCode){
	Ext.Ajax.request({
	    url: rootUrl + 'dic/getStateName.do',
	    method: 'post',
	    async: false,
        params: { dicCode: dicCode }
	});
}

//检验招标公示的投标按钮是否显示
var canToubiao = function () {
    var res = false;
    Ext.Ajax.request({
        url: rootUrl + 'canToubiao.do',
        method: 'post',
        async: false,
        success: function (response) {
            var result = Ext.decode(response.responseText);
            if (result.success) {
                res = true;
            } else
                res = false;
        }
    });
    return res;
}

//重写Ext.data.proxy.Ajax
Ext.override(Ext.data.proxy.Ajax, {
    listeners: {
        exception: function (prox, response, operation) {
            if (response.status == '200') {
                var result = Ext.decode(response.responseText);
                if (result.info)
                    pageJump(result.info.error);
            }
            else {
                Ext.MessageBox.show({
                    title: '服务器异常',
                    msg: response.responseText,
                    buttons: Ext.MessageBox.OK,
                    icon: Ext.MessageBox.ERROR
                });
            }
        }
    }
});



//转换json时间
function formatJsonTime(v) {
    if (v == null || v == '') {
        return null;
    }
    var d = new Date();
    var str = v.toString();
    var str1 = str.replace("/Date(", "");
    var str2 = str1.replace(")/", "");
    var dd = parseInt(str2);
    d.setTime(dd);
    return d;
};


//四舍五入
function ForDight(Dight, How) {
    Dight = Math.round(Dight * Math.pow(10, How)) / Math.pow(10, How);
    return Dight;
}

//生成空格
function createSpace(n) {
    var space = '&nbsp;';
    for (var i = 0; i < n; i++) {
        space += '&nbsp;';
    }
    return space;
}

function CommonPrint(url,width,height)
{
    var winTitle = '打印';
    //信息编辑Window
    if (winTDicArea != null) {
        winTDicArea.destroy(true);
        winTDicArea = null;
    }
    if (!winTDicArea) {
        var winTDicArea = new Ext.window.Window({
            title: winTitle,
            html: '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="' + url + '"></iframe>',
            width: width,
            height: height,
            autoscroll:true,
            modal: true,
            resizeable: false,
            collapsible: true,
            closeAction: 'close',
            bodyStyle: 'padding:5px;',
            layout: 'fit'
        });
    }
    winTDicArea.show();
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

var isRelease = function(){
    var res = false;
    Ext.Ajax.request({
        url: rootUrl + 'credit/checkRelease.do',
        method: 'post',
        async: false,
        success: function (response) {
            var result = Ext.decode(response.responseText);
            if (result.success) {
                res = true;
            } else
                res = false;
        }
    });
    return res;
}


function ShowWindow(storeChannel, title, url, x, y) {
    var winTitle = title;
    var win = parent;
    homeWindow = win.Ext.getCmp('homeWindow');
    if (homeWindow != null) {
        homeWindow.destroy(true);
        homeWindow = null;
    }    
    if (!homeWindow) {
        var homeWindow = new win.Ext.window.Window({
            id:'homeWindow',
            title: winTitle,
            html: '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="' + url + '"></iframe>',
            width: x,
            height: y,
            autoscroll: true,
            modal: true,
            resizeable: false,
            collapsible: true,
            closeAction: 'close',
            bodyStyle: 'padding:5px;',
            layout: 'fit'
        });
    }
    homeWindow.on("close", function () {
        if (storeChannel != null && storeChannel != "undefined") {
            storeChannel.reload();
        }
    });
    homeWindow.show();
    return false;
    
}

function ShowWindows(storeChannels, title, url, x, y) {
    var winTitle = title;
    var win = parent;
    homeWindow = win.Ext.getCmp('homeWindow');
    if (homeWindow != null) {
        homeWindow.destroy(true);
        homeWindow = null;
    }
    if (!homeWindow) {
        var homeWindow = new win.Ext.window.Window({
            id: 'homeWindow',
            title: winTitle,
            html: '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="' + url + '"></iframe>',
            width: x,
            height: y,
            autoscroll: true,
            modal: true,
            resizeable: false,
            collapsible: true,
            closeAction: 'close',
            bodyStyle: 'padding:5px;',
            layout: 'fit'
        });
    }
    homeWindow.on("close", function () {
        if (storeChannels != null && storeChannels != "undefined") {
            for (var i = 0; i < storeChannels.length; i++) {
                var storeChannel = storeChannels[i];
                if (storeChannel != null && storeChannel != "undefined") {
                    storeChannel.reload();
                }
            }
            
        }
    });
    homeWindow.show();
    return false;

}

function CloseWindow() {
    var currentWin = window;
    while (top != currentWin) {
        var prentExt = currentWin.parent.Ext;
        var fElement = prentExt.get(currentWin.frameElement);
        var windowElement = fElement.up('div.x-window');
        if (windowElement) {
            var winId = windowElement.id;
            var extWin = prentExt.getCmp(winId);
            extWin.close();
            return true;
        } else {
            currentWin = currentWin.parent;
        }
    }
    alert('窗口关闭错误');
    return false;
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}