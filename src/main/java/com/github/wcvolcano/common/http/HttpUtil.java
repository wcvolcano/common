package com.github.wcvolcano.common.http;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wencan on 2015/2/26.
 * 处理网络传输信息相关，由于没有新开线程，在调用时需要新开线程
 */
public class HttpUtil {

//    private static final int timeRequest = 60 * 1000;

    public static void getHttpRequest(final String address,
                                      final HttpCallbackListener listener,
                                      final int timeRequest) {

        HttpURLConnection connection = null;
        try {

            URL url = new URL(address);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(timeRequest);
            connection.setReadTimeout(timeRequest);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            InputStream in = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            if (listener != null) {
                //回调onFinish() 方法
                listener.onFinish(response.toString());
            }
        } catch (IOException e) {
            if (listener != null) {
                listener.onError(e);
            }
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

    }

    public static void postHttpRequest(final String address, final String uploadMessage,
                                       final HttpCallbackListener listener,
                                       final int timeRequest) {

        HttpURLConnection connection = null;
        try {


            URL url = new URL(address);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(timeRequest);
            connection.setReadTimeout(timeRequest);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            osw.write(uploadMessage);
            osw.flush();
            osw.close();


            InputStream in = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            if (listener != null) {
                //回调onFinish() 方法
                listener.onFinish(response.toString());
            }
        } catch (IOException e) {
            if (listener != null) {
                listener.onError(e);
            }
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

    }
}
