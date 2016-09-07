/**
 * 弹出模式对话框
 * @param strUrl
 * @param winArgument
 * @param winWidth
 * @param winHeight
 */
function showMDialog(strUrl, winWidth, winHeight, winArgument) {
    var showx = ((window.screen.availWidth - winWidth) / 2 >= 0) ? (window.screen.availWidth - winWidth) / 2 : 0;
    var showy = ((window.screen.availHeight - winHeight) / 2 >= 0) ? (window.screen.availHeight - winHeight) / 2 : 0;
    var options =
			'dialogWidth:' + winWidth + 'px;'
			+ 'dialogHeight:' + winHeight + 'px;'
			+ 'dialogLeft:' + showx + 'px;'
			+ 'dialogTop:' + showy + 'px;'
			+ 'directories: no;'
			+ 'localtion: no;'
			+ 'menubar: no;'
			+ 'status: no;'
			+ 'toolbar: no;'
			+ 'Resizeable: no;'
			+ 'help: no;';
    returnValue = window.showModalDialog(strUrl, winArgument, options);
    return returnValue;
}

/**
 * 弹出对话框
 * @param strUrl
 * @param winWidth
 * @param winHeight
 * @param mode
 * @param winArgument
 * @returns
 */
function showDialog(strUrl, winWidth, winHeight, mode, winArgument) {
    var showx = ((window.screen.availWidth - winWidth) / 2 >= 0) ? (window.screen.availWidth - winWidth) / 2 : 0;
    var showy = ((window.screen.availHeight - winHeight) / 2 >= 0) ? (window.screen.availHeight - winHeight) / 2 : 0;
    var options =
			'dialogWidth:' + winWidth + 'px;'
			+ 'dialogHeight:' + winHeight + 'px;'
			+ 'dialogLeft:' + showx + 'px;'
			+ 'dialogTop:' + showy + 'px;'
			+ 'directories: no;'
			+ 'localtion: no;'
			+ 'menubar: no;'
			+ 'status: no;'
			+ 'toolbar: no;'
			+ 'Resizeable: no;'
			+ 'help: no;';
    if(mode)
    	returnValue = window.showModalDialog(strUrl, winArgument, options);
    else
    	returnValue = window.showModelessDialog(strUrl, winArgument, options);
    
    return returnValue;
}

/**
 * 打开新窗口
 * @param url
 * @param winWidth
 * @param winHeight
 */
function openWindow(url, winWidth, winHeight, name){
	var showx = ((window.screen.availWidth - winWidth) / 2 >= 0) ? (window.screen.availWidth - winWidth) / 2 : 0;
    var showy = ((window.screen.availHeight - winHeight) / 2 >= 0) ? (window.screen.availHeight - winHeight) / 2 : 0;
    
    var options = 'width=' + winWidth
		+ ', height=' + winHeight
		+ ', left=' + showx
		+ ', top=' + showy
		//+ ', resizable=no'
		+ ', scrollbars=1'
		+ ', status=no'
		+ ', menubar=no';
    
	var winHandle = document.open(url, name, options, false);
	return winHandle;
}

/**
 * 无提示关闭窗口
 */
function closeWindow(){ 
	window.open('','_parent','');
	window.close();
}