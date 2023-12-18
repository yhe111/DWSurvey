package net.diaowen.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * 向指定URL发送GET和POST请求
 */
public class HttpRequest {
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        //初始化result为空字符串
        String result = "";
        //初始化输入流in为null
        BufferedReader in = null;
        try {
            //urlNameString是发送请求的url加上它请求的参数，即完整的请求网址
            String urlNameString = url + "?" + param;
            //以字符串urlNameString创建一个URL实体realUrl
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            // 设置通用的请求属性
            connection.setRequestProperty("connection", "Keep-Alive");
            // 设置通用的请求属性
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();

            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            //初始化字符串line，用于存储输入流中的一行
            String line;
            while ((line = in.readLine()) != null) {
                //如果输入流这一行非空，就在result加上这一行，直至result获取url所有内容
                result += line;
            }
        } catch (Exception e) {
            //抛出异常
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    //关闭输入流
                    in.close();
                }
            } catch (Exception e2) {
                //抛出异常
                e2.printStackTrace();
            }
        }
        //返回获取的URL响应
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        //初始化打印流out为null
        PrintWriter out = null;
        //初始化输入流in为null
        BufferedReader in = null;
        //初始化字符串result为空字符串
        String result = "";
        try {
            //以发送请求的URL创建一个URL实体
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            // 设置通用的请求属性
            conn.setRequestProperty("connection", "Keep-Alive");
            // 设置通用的请求属性
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            //初始化字符串line，存储输入流的一行
            String line;
            while ((line = in.readLine()) != null) {
                //若输入流此行非空，就写入result中，直至写完URL的响应
                result += line;
            }
        } catch (Exception e) {
            //抛出异常
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    //关闭输出流
                    out.close();
                }
                if(in!=null){
                    //关闭输入流
                    in.close();
                }
            }
            catch(IOException ex){
                //抛出异常
                ex.printStackTrace();
            }
        }
        //返回获取的URL响应
        return result;
    }
}
