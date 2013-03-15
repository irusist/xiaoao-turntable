package com.irusist.xiaoao.turntable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * description
 *
 * @author zhulixin
 */
public class Activate {

    public static void main(String[] args) {
        String account = args[0];

        for (int i= 0; i < 10; i++) {
            String activationCode = args[1].replaceFirst("\\*", "#");
            String value = String.valueOf(i);
            activationCode = activationCode.replace("#", value);
            for (int j = 0; j < 10; j++) {
                String code = activationCode;
                String value2 = String.valueOf(j);
                code = code.replace("*", value2);
                System.out.println("dest: " + code);
                post(account, code);
            }
        }


    }

    private static void post(String account, String activationCode) {
        HttpClient client = new DefaultHttpClient();
        HttpPost method = new HttpPost("http://passport.wanmei.com/activationAction.do");

        method.addHeader("Connection", "close");
        method.addHeader("Host", "passport.wanmei.com");
        method.addHeader("Content-Type", "application/x-www-form-urlencoded");
        method.addHeader("Origin", "http://passport.wanmei.com");
        method.addHeader("Referer", "http://passport.wanmei.com/jsp/active/xiao.jsp");
       // method.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.52 Safari/537.17");

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("activegame", "8"));
        params.add(new BasicNameValuePair("topage", "/jsp/active/xiaook.htm"));
        params.add(new BasicNameValuePair("errorpage", "/jsp/active/xiaoerror.jsp"));
        params.add(new BasicNameValuePair("username", account));
        params.add(new BasicNameValuePair("activationCode", activationCode));

        InputStream inputStream = null;
        try {
            HttpEntity entity = new UrlEncodedFormEntity(params, Constants.DEFAULT_ENCODING);
            method.setEntity(entity);

            HttpResponse response = client.execute(method);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                StringBuilder output = new StringBuilder();
                output.append("请求结果是： ");
//                inputStream = new InflaterInputStream(response.getEntity().getContent());
                inputStream = response.getEntity().getContent();
                byte[] data = new byte[1024];
                int len;
                while ((len = inputStream.read(data)) != -1) {
                    output.append(new String(data, 0, len));
                }

                System.out.println(output.toString());
            } else {
                System.out.println("请求失败");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
