package com.senhome.web.interceptor;

import com.senhome.api.account.api.AccountServiceApi;
import com.senhome.service.account.dal.dataobject.Account;
import com.senhome.shell.common.enums.OS;
import com.senhome.shell.common.result.ViewResult;
import com.senhome.web.common.ThreadCacheUtils;
import com.senhome.web.common.exception.IncorrectAccountSignException;
import com.senhome.web.interceptor.request.AccountLogin;
import com.senhome.web.interceptor.request.RequestContext;
import com.senhome.web.interceptor.request.Terminal;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

@Component
@SuppressWarnings("SpringJavaAutowiringInspection")
public class AccountInterceptor extends HandlerInterceptorAdapter
{
    @Resource
    private AccountServiceApi accountServiceApi;

    private final static Logger logger = LoggerFactory.getLogger(AccountInterceptor.class);

    /** App端默认secretKey，当用户未登录时，accountId为0，使用该值 */
    private final static String DEFAULT_SECRET_KEY = "boxcityboxcity";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception
    {
        logger.info("request url: " + request.getRequestURI() + ", params : " + getParam(request));

        try
        {
            buildContextWithApp(request);
        }
        catch (Exception e)
        {
            ViewResult viewResult = ViewResult.ofSuccess();
            viewResult.setSuccess(false);
            viewResult.setMessage("verify sign fail");

            response.setContentType("application/json;charset=");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(viewResult.toJson());

            return false;
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //销毁RequestContext对象，防止内存泄露
        RequestContext.destroy();
    }

    private String getParam(HttpServletRequest request)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : request.getParameterMap().keySet())
        {
            stringBuilder.append(key).append(":");
            for (String str : request.getParameterMap().get(key))
            {
                stringBuilder.append(str).append(",");
            }
            stringBuilder.append(";");
        }
        return stringBuilder.toString();
    }

    /**
     * 为App请求构建Context
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    private RequestContext buildContextWithApp(HttpServletRequest request) throws UnsupportedEncodingException {

        String osParam = request.getParameter("os");
        String imei = request.getParameter("imei");
        Terminal terminal = new Terminal(OS.getByValue(osParam), imei);

        RequestContext cxt = null;

        String ip = getRemoteIpAddr(request);
        String accountIdParam = request.getParameter("accountId");
        if(StringUtils.isBlank(accountIdParam)){
            //部分接口客户端存在问题，没有传accountId，作兼容
            accountIdParam = "0";
        }
        String params = request.getParameter("params") == null ? "" : request.getParameter("params");
        String sign = request.getParameter("sign") == null ? "" : request.getParameter("sign");
        String secretKey = null;
        Account account = null;

        if(!StringUtils.isNumeric(accountIdParam)){
            throw new IncorrectAccountSignException();
        }

        int accountIdParamToInt = Integer.valueOf(accountIdParam);
        if(accountIdParamToInt == 0){
            secretKey = DEFAULT_SECRET_KEY;
        }else{
            ViewResult viewResult = accountServiceApi.findAccount(accountIdParamToInt);
            if(!viewResult.isSuccess()){
                String msg = String.format("Invoke accountServiceApi.findAccount fail, accountId=%d, result_msg=%s",accountIdParamToInt, viewResult.getMessage());
                throw new RuntimeException(msg);
            }

            account = (Account)viewResult.getModel();
            if (account == null) {
                String msg = "account not exit";
                throw new RuntimeException(msg);
            }
            secretKey = account.getSecretKey();
        }

        if(!sign.equals(strToMD5(params + secretKey)) ){
            logger.error("签名错误: sign={}", sign);
            throw new RuntimeException("签名错误");
        }

        if(accountIdParamToInt > 0){
            AccountLogin accountLogin = new AccountLogin(accountIdParamToInt, true);
            cxt = new RequestContext(terminal, ip, accountLogin);
            ThreadCacheUtils.put("collect", account);
        }else{
            cxt = new RequestContext(terminal,ip);
        }

        return cxt;
    }

    /**
     * 把字符串转换成md5
     *
     * @param str
     * @return
     */
    private static String strToMD5(String str)
        throws UnsupportedEncodingException
    {
        byte[] input;
        input = str.getBytes("UTF-8");
        return bytesToHex(bytesToMD5(input));
    }

    /**
     * 把字节数组转成16进位制数
     *
     * @param bytes
     * @return
     */
    private static String bytesToHex(byte[] bytes)
    {
        StringBuffer md5str = new StringBuffer();
        // 把数组每一字节换成16进制连成md5字符串
        int digital;
        for (int i = 4; i < bytes.length - 4; i++)
        {
            digital = bytes[i];
            if (digital < 0)
            {
                digital += 256;
            }
            if (digital < 16)
            {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString().toUpperCase();
    }

    /**
     * 把字节数组转换成md5
     *
     * @param input
     * @return
     */
    private static byte[] bytesToMD5(byte[] input)
    {
        // String md5str = null;
        byte[] buff = null;
        try
        {
            // 创建一个提供信息摘要算法的对象，初始化为md5算法对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算后获得字节数组
            buff = md.digest(input);
            // 把数组每一字节换成16进制连成md5字符串
            // md5str = bytesToHex(buff);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return buff;
    }

    private static String getRemoteIpAddr(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.length() > 15)
        {
            if (ip.indexOf(",") > 0)
            {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }
}
