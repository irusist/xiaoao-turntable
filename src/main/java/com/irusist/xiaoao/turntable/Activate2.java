package com.irusist.xiaoao.turntable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
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
public class Activate2 {

    public static void main(String[] args) {
        post();


    }

    private static void post() {
        HttpClient client = new DefaultHttpClient();
        HttpPost method = new HttpPost("http://passport.wanmei.com/oauth2/oauth/token");

        method.addHeader("Connection", "close");
        method.addHeader("Host", "passport.wanmei.com");
        method.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        method.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET4.0C; .NET4.0E)");

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("client_id", "3"));
        params.add(new BasicNameValuePair("client_secret", "df9df01421154016b905e2a21620bbd2"));
        params.add(new BasicNameValuePair("grant_type", "authorization_code"));
        params.add(new BasicNameValuePair("code", "yyqUu02YGb"));
        params.add(new BasicNameValuePair("redirect_uri", "http://wm.laohu.com/sign/jump/wanmei"));

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
