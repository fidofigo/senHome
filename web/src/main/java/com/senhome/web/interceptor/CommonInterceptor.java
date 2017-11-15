package com.senhome.web.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommonInterceptor extends HandlerInterceptorAdapter
{
//    @Autowired
//    private HqbsAccountService hqbsAccountService;

    /**
     * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕
     * 再执行被拦截的Controller 然后进入拦截器链, 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion()
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception
    {
//        try
//        {
//            HttpSession session = request.getSession();
//            QqbsAccountEntity accountSession = SessionUtil.getQqbsAccountFromSession(session);
//            QqbsAccountEntity userInfo;
//            //优先取cookie
//            Cookie cookie = hqbsAccountService.getLoginCookieUUID(request);
//            if (cookie != null)
//            {
//                userInfo = getUserInfoCahche(cookie.getValue());
//                if (userInfo != null && userInfo.getAccountId() > 0)
//                {
//                    if (accountSession == null)
//                    {
//                        hqbsAccountService.setLoginSession(session, userInfo);
//                    }
//                    else if (userInfo.getAccountId() != accountSession.getAccountId())
//                    {
//                        //用户在其他域名下账户切换了，session重新载入
//                        hqbsAccountService.setLoginSession(session, userInfo);
//                    }
//                    return true;
//                }
//            }
//
//            if (accountSession != null)
//            {
//                return true;
//            }
//
//            String uid = request.getParameter(LOGIN_JUMP_KEY);
//            if (uid != null && uid.length() > 0)
//            {
//                userInfo = getUserInfoCahche(uid);
//                if (userInfo != null && userInfo.getAccountId() > 0)
//                {
//                    hqbsAccountService.setLoginSessionCookie(request, response, userInfo, uid);
//                    return true;
//                }
//            }
//
//            String url = "https://";//request.getScheme() + "s://";
//            url += request.getHeader("host");
//            url += request.getRequestURI();
//            if (request.getQueryString() != null)
//            {
//                url += "?" + request.getQueryString();
//            }
//
//            String accountId = hqbsAccountService.getCookieLoginAccountId(request);
//            boolean login = !StringUtils.isBlank(accountId) && (url.contains("/account/getUserInfo") || url.contains("/account/setTid"));
//            if (login)
//            {
//                //cookie存在当前登陆accountId，输入推荐人页面使用,完成输入推荐人后清空该cookie,时间原因先临时写死，该页面调用2个接口getUserInfo，setTid
//                return true;
//            }
//
//            response.sendRedirect(loginAddress + "/account/checkLoginStatus?url=" + EncodeUtil.encode(url));
//            return false;
//        }
//        catch (Exception e)
//        {
//            logger.error("拦截器处理出错CommonInterceptorMobile", e);
//        }
        return true;
    }

    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作 可在modelAndView中加入数据，比如当前时间
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
        throws Exception
    {
        super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception
    {
        super.afterCompletion(request, response, handler, ex);
    }
}
