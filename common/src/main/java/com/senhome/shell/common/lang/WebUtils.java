/**
 * Copyright (c) 2017, Alex. All rights reserved.
 */
package com.senhome.shell.common.lang;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:zhongchao@gegejia.com">zhong</a>
 * @version 1.0 2017/10/27
 * @since 1.0
 */
public class WebUtils
{

    //默认连接超时3s
    private final static int CONNECT_TIMEOUT = 3;

    //默认读取超时3s
    private final static int READ_TIMEOUT = 3;

    //获取response的等待时间
    private final static int SOCKET_TIMEOUT = 15;

    //默认编码
    private final static String CHARSET = "UTF-8";

    /**
     * 创建httpClient
     *
     * @return httpclient
     */
    public HttpClient createHttpClient(boolean useSSL)
    {
        return createHttpClient(useSSL, CONNECT_TIMEOUT, READ_TIMEOUT);
    }

    /**
     * 创建httpClient
     *
     * @return httpclient
     */
    public static CloseableHttpClient createHttpClient(boolean useSSL, int connectTimeout, int readTimeout)
    {
        RequestConfig config =
            RequestConfig.custom().setConnectTimeout(connectTimeout * 1000).setSocketTimeout(SOCKET_TIMEOUT * 1000).setConnectionRequestTimeout(readTimeout * 1000).build();

        if(useSSL)
        {
            try
            {
                SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(new TrustAllStrategy()).build();
                return HttpClients.custom().setSSLContext(sslContext).setDefaultRequestConfig(config).build();
            }
            catch (Exception ex)
            {
                throw new RuntimeException("建立SSL连接出错");
            }

        } else {
            return HttpClients.custom().setDefaultRequestConfig(config).build();
        }
    }

    /**
     * 执行http POST 请求
     *
     * @param url       请求地址
     * @param params    请求参数
     * @param cookies   cookie
     * @return
     */
    public static String doPost(String url, Map<String, String> params, List<String> cookies)
        throws Exception
    {
        return doPost(url, params, cookies, CONNECT_TIMEOUT, READ_TIMEOUT);
    }

    /**
     * 执行http POST 请求
     *
     * @param url               请求地址
     * @param params            请求参数
     * @param connectTimeout    请求超时, 单位秒
     * @param readTimeout       读取超时, 单位秒
     * @return
     */
    public static String doPost(String url, Map<String, String> params, List<String> cookies, int connectTimeout, int readTimeout)
        throws Exception
    {
        HttpPost httpPost = new HttpPost(url);

        CloseableHttpClient httpClient = null;
        httpClient = createHttpClient(StringUtils.startsWith(url,"https"), connectTimeout, readTimeout);
        CloseableHttpResponse response = null;
        try
        {
            //封装参数
            if (params != null)
            {
                List<NameValuePair> parameters = params.entrySet().stream().map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue())).collect(Collectors.toList());

                HttpEntity entity = new UrlEncodedFormEntity(parameters, CHARSET);

                httpPost.setEntity(entity);
            }

            //封装cookie
            if(cookies != null && cookies.size() > 0) {
                String cookie = StringUtils.join(cookies, ";");
                httpPost.addHeader("Cookie", cookie);
            }

            response = httpClient.execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            if(status >= 200 && status < 300)
            {
                return EntityUtils.toString(response.getEntity());
            }
            throw new RuntimeException(
                "http request happen error, status=" + response.getStatusLine().getStatusCode() + "; data=" + EntityUtils.toString(response.getEntity()));
        }
        finally
        {
            close(httpClient, response);
        }
    }

    /**
     * 执行get请求
     *
     * @param url       请求地址
     * @return          请求数据
     * @throws Exception
     */
    public static String doGet(String url, List<String> cookies)
        throws Exception
    {
        return doGet(url, cookies, CONNECT_TIMEOUT, READ_TIMEOUT);
    }

    /**
     * 执行get请求
     *
     * @param url               请求地址
     * @param connectTimeout    请求超时, 单位: 秒
     * @param readTimeout       读取超时, 单位: 秒
     * @return
     * @throws Exception
     */
    public static String doGet(String url, List<String> cookies, int connectTimeout, int readTimeout)
        throws Exception
    {
        CloseableHttpClient httpClient = createHttpClient(StringUtils.startsWith(url,"https"), connectTimeout, readTimeout);
        CloseableHttpResponse response = null;

        try
        {
            HttpGet httpGet = new HttpGet(url);

            //封装cookie
            if(cookies != null && cookies.size() > 0) {
                String cookie = StringUtils.join(cookies, ";");
                httpGet.addHeader("Cookie", cookie);
            }

            response = httpClient.execute(httpGet);
            int status = response.getStatusLine().getStatusCode();
            if(status >= 200 && status < 300)
            {
                return EntityUtils.toString(response.getEntity());
            }

            throw new RuntimeException(
                "http request happen error, status=" + response.getStatusLine().getStatusCode() + "; data=" + EntityUtils.toString(response.getEntity()));
        } finally
        {
            close(httpClient, response);
        }
    }

    /**
     * close httpClient, httpResponse
     *
     * @param httpClient
     * @param httpResponse
     * @throws Exception
     */
    private static void close(CloseableHttpClient httpClient, CloseableHttpResponse httpResponse)
        throws Exception
    {
        if(httpClient != null)
            httpClient.close();

        if(httpResponse != null)
            httpResponse.close();
    }

    public static class TrustAllStrategy implements TrustStrategy {

        @Override
        public boolean isTrusted(X509Certificate[] chain, String authType)
            throws CertificateException
        {
            return true;
        }
    }
}