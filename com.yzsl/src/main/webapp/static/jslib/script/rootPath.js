/**
 * 是否虚拟目录
 */
var isVirtualRoot = true;

/**
 * 网站根目录
 */
var rootUrl = getSiteRoot(isVirtualRoot);

/**
 * 获取网站根地址，如果是虚拟目录则带有虚拟目录名
 * @param isVirtual 是否虚拟目录
 * @returns {String}
 */
function getSiteRoot(isVirtual) {
	var siteRoot = window.location.protocol + "//" + window.location.host + "/";
	if (!isVirtual) return siteRoot;

	var relativePath = window.location.pathname;
	if (relativePath != "" && relativePath.substring(0, 1) == "/") {
		//此处重要，不同的浏览器可能返回的relativePath不一样
		relativePath = relativePath.substring(1);
	}
	var virtualPath = (relativePath == "") ? "" : relativePath.substring(0, relativePath.indexOf("/") + 1);

	return siteRoot + virtualPath;
}


