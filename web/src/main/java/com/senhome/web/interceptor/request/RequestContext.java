package com.senhome.web.interceptor.request;

/**
 * 请求上下文
 * Created by luliru on 2017/2/23.
 */
public class RequestContext {

    private static ThreadLocal<RequestContext> contextThreadLocal = new ThreadLocal<>();

    private AccountLogin accountLogin;

    private String ip;

    private Terminal terminal;

    public static RequestContext get(){
        return contextThreadLocal.get();
    }

    public static void destroy(){
        contextThreadLocal.remove();
    }

    public RequestContext(Terminal terminal,String ip){
        this.terminal = terminal;
        this.ip = ip;
        contextThreadLocal.set(this);
    }

    public RequestContext(Terminal terminal,String ip,AccountLogin accountLogin){
        this.terminal = terminal;
        this.ip = ip;
        this.accountLogin = accountLogin;
        contextThreadLocal.set(this);
    }

    /**
     * 获取用户信息
     * @return
     */
    public AccountLogin getAccountLogin() {
        return accountLogin;
    }

    /**
     * 是否已登录
     * @return
     */
    public boolean isLogin(){
        if(accountLogin != null && accountLogin.isRegistered()){
            return true;
        }
        return false;
    }

    /**
     * 获取请求来源IP
     * @return
     */
    public String getIp() {
        return ip;
    }

    /**
     * 获取终端设备
     * @return
     */
    public Terminal getTerminal() {
        return terminal;
    }
}
