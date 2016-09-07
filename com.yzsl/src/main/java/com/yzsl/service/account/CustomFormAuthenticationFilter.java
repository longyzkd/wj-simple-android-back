package com.yzsl.service.account;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {  
	private static final Logger log = LoggerFactory
			.getLogger(CustomFormAuthenticationFilter.class);
	
	 /** 
     * 所有请求都会经过的方法。 
     */  
    @Override  
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {  
    	 if (isLoginRequest(request, response)) {  
             if (isLoginSubmission(request, response)) {  
                 if (log.isTraceEnabled()) {  
                     log.trace("Login submission detected.  Attempting to execute login.");  
                 }  
                 //验证码暂不处理
//                 if ("XMLHttpRequest"  
//                         .equalsIgnoreCase(((HttpServletRequest) request)  
//                                 .getHeader("X-Requested-With"))) {// 不是ajax请求  
//                     String vcode = request.getParameter("vcode");  
//                     HttpServletRequest httpservletrequest = (HttpServletRequest) request;  
//                     String vvcode = (String) httpservletrequest  
//                             .getSession()  
//                             .getAttribute("com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY"  
//                                     );  
//                     if (vvcode == null || "".equals(vvcode)  
//                             || !vvcode.equals(vcode)) {  
//                         response.setCharacterEncoding("UTF-8");  
//                         PrintWriter out = response.getWriter();  
//                         out.println("{success:false,msg:'验证码错误'}");  
//                         out.flush();  
//                         out.close();  
//                         return false;  
//                     }  
//                 }  
                 return executeLogin(request, response);  
             } else {  
                 if (log.isTraceEnabled()) {  
                     log.trace("Login page view.");  
                 }  
                 // allow them to see the login page ;)  
                 return true;  
             }  
         } else {  
             if (log.isTraceEnabled()) {  
                 log.trace("Attempting to access a path which requires authentication.  Forwarding to the "  
                         + "Authentication url [" + getLoginUrl() + "]");  
             }  
             if (!"XMLHttpRequest"  
                     .equalsIgnoreCase(((HttpServletRequest) request)  
                             .getHeader("X-Requested-With"))) {// 不是ajax请求  
                 saveRequestAndRedirectToLogin(request, response);  
             } else {  
                 response.setCharacterEncoding("UTF-8");  
                 PrintWriter out = response.getWriter();  
                 out.println("{msg:'login'}");  
                 out.flush();  
                 out.close();  
             }  
             return false;  
         }
    }  
    
    /**
     * 覆盖默认实现，用sendRedirect直接跳出框架，以免造成js框架重复加载js出错。
     * @param token
     * @param subject
     * @param request
     * @param response
     * @return
     * @throws Exception  
     * @see org.apache.shiro.web.filter.authc.FormAuthenticationFilter#onLoginSuccess(org.apache.shiro.authc.AuthenticationToken, org.apache.shiro.subject.Subject, javax.servlet.ServletRequest, javax.servlet.ServletResponse)
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
            ServletRequest request, ServletResponse response) throws Exception {
        //issueSuccessRedirect(request, response);
        //we handled the success redirect directly, prevent the chain from continuing:
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
         
        if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("X-Requested-With")) 
                && request.getParameter("ajax") == null) {// 不是ajax请求 让他重定向到页面
        	issueSuccessRedirect(request, response);
        } else {//在ajax里面，用不了shiro配置的successurl重定向功能，只能在回调函数里重定向     wj 2016-9-7
        	httpServletResponse.setCharacterEncoding("UTF-8");
			PrintWriter out = httpServletResponse.getWriter();
			out.println("{success:true,msg:'登入成功'}");
			out.flush();
			out.close();
        }
         
        return false;
    }
    
    
    /** 
     * 主要是处理登入失败的方法 
     */  
    @Override  
    protected boolean onLoginFailure(AuthenticationToken token,  
            AuthenticationException e, ServletRequest request,  
            ServletResponse response) {  
        if (!"XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request)  
                .getHeader("X-Requested-With"))) {// 不是ajax请求  
            setFailureAttribute(request, e);  
            return true;  
        }  
        try {  
            response.setCharacterEncoding("UTF-8");  
            PrintWriter out = response.getWriter();  
            String msg = e.getClass().getSimpleName();  
            if ("IncorrectCredentialsException".equals(msg)) {  
                out.println("{success:false,msg:'密码错误'}");  
            } else if ("UnknownAccountException".equals(msg)) {  
                out.println("{success:false,msg:'账号不存在'}");  
            } else if ("LockedAccountException".equals(msg)) {  
                out.println("{success:false,msg:'账号被锁定'}");  
            } else {  
                out.println("{success:false,msg:'未知错误'}");  
            }  
            out.flush();  
            out.close();  
        } catch (IOException e1) {  
            // TODO Auto-generated catch block  
            e1.printStackTrace();  
        }  
        return false;  
    }  
  
}  