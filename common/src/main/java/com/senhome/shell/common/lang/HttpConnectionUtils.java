package com.senhome.shell.common.lang;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 *
 */
public class HttpConnectionUtils
{

    public static String sendGet(String reqUrl, String data)
            throws IOException {
        StringBuffer buffer = null;
        InputStream in = null;
        try {
            buffer = new StringBuffer();
            if (null != data && !"".equals(data)) {
                if (reqUrl.indexOf("?") > -1) {
                    reqUrl += data;
                } else {
                    reqUrl = reqUrl + "?" + data;
                }
            }
            URL url = new URL(reqUrl);
            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();

            httpUrlConnection.setConnectTimeout(30000); // 设置连接主机超时（单位：毫秒）
            httpUrlConnection.setReadTimeout(30000); // 设置从主机读取数据超时（单位：毫秒）

            httpUrlConnection.connect();
            in = httpUrlConnection.getInputStream(); // <===注意，实际发送请求的代码段就在这里
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "utf-8"));
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
        } catch (IOException e) {
            System.out.println("发送 POST 请求出现异常！");
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return buffer.toString();
    }


    public static String sendPost(String reqUrl, String data, Map<String, String> headers)
            throws IOException {
        StringBuffer buffer = null;
        OutputStream out = null;
        InputStream in = null;
        try {
            buffer = new StringBuffer();
            URL url = new URL(reqUrl);
            // 此处的urlConnection对象实际上是根据URL的请求协议(此处是http)生成的URLConnection类的子类HttpURLConnection,故此处最好将其转化为HttpURLConnection类型的对象,
            // 以便用到HttpURLConnection更多的API.如下:
            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();

            // 赋值请求头如 Content-Type、Cookie、User-Agent ...
            for (String key : headers.keySet()) {
                httpUrlConnection.setRequestProperty(key, headers.get(key));
            }
            // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false;
            httpUrlConnection.setDoOutput(true);
            // 设置是否从httpUrlConnection读入，默认情况下是true;
            httpUrlConnection.setDoInput(true);
            // Post 请求不能使用缓存
            httpUrlConnection.setUseCaches(false);

            // 设定请求的方法为"POST"，默认是GET
            httpUrlConnection.setRequestMethod("POST");

            httpUrlConnection.setRequestProperty("Content-Type","application/json");

            httpUrlConnection.setConnectTimeout(30000); // 设置连接主机超时（单位：毫秒）
            httpUrlConnection.setReadTimeout(30000); // 设置从主机读取数据超时（单位：毫秒）

            //connect()函数会根据HttpURLConnection对象的配置值生成http头部信息，因此在调用connect函数之前，就必须把所有的头部配置准备好
            //connect()函数，实际上只是建立了一个与服务器的tcp连接，并没有实际发送http请求
            httpUrlConnection.connect();

            //getOutputStream也会隐含的进行connect()
            out = httpUrlConnection.getOutputStream();
            out.write(data.getBytes()); //向对象输出流写出数据，这些数据将存到内存缓冲区中
            out.flush(); //刷新对象输出流，将任何字节都写入潜在的流中
            out.close();

            // 调用HttpURLConnection连接对象的getInputStream()函数, 将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端。
            in = httpUrlConnection.getInputStream(); // <===注意，实际发送请求的代码段就在这里

            /**
             *  在http头后面紧跟着的是http请求的正文，正文的内容是通过outputStream流写入的，
             实际上outputStream不是一个网络流，充其量是个字符串流，往里面写入的东西不会立即发送到网络，
             而是存在于内存缓冲区中，待outputStream流关闭时，根据输入的内容生成http正文。
             至此，http请求的东西已经全部准备就绪。在getInputStream()函数调用的时候，就会把准备好的http请求
             正式发送到服务器了，然后返回一个输入流，用于读取服务器对于此次http请求的返回信息。由于http
             请求在getInputStream的时候已经发送出去了（包括http头和正文），因此在getInputStream()函数
             之后对connection对象进行设置（对http头的信息进行修改）或者写入outputStream（对正文进行修改）
             都是没有意义的了，执行这些操作会导致异常的发生
             */

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "utf-8"));
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
        } catch (IOException e) {
            System.out.println("发送 POST 请求出现异常！");
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return buffer.toString();
    }
}
