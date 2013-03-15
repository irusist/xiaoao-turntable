/**
 * Copyright (C) 2013 The Xiaoao TurnTable Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.irusist.xiaoao.turntable.service;

import com.irusist.xiaoao.turntable.Constants;
import com.irusist.xiaoao.turntable.encrypt.Encrypt;
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
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.zip.InflaterInputStream;

/**
 * 请求服务抽象类
 *
 * @author zhulixin
 */
public abstract class RequestService implements Runnable {

    private static final ExecutorService pool = new ThreadPoolExecutor(5, 10,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>());

    private long duration;

    private Map<String, String> tokens = new HashMap<String, String>();

    private List<String> config;

    public abstract String getUrl();

    public abstract Map<String, String> getParams();

    public void run() {
        duration = (long) (Double.valueOf(config.get(0)) * 1000);
        for (int i = 1; i < config.size(); i++) {
            String[] line = config.get(i).split("\\|");
            tokens.put(line[0], line[1]);
        }

        while (true) {
            for (Map.Entry<String, String> entry : tokens.entrySet()) {
                String account = entry.getKey();
                String token = entry.getValue();
                execute(pool, account, token);
            }

            try {
                Thread.sleep(duration);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * http请求获取数据
     *
     * @param token
     *         oauth登陆后的token
     */
    public void execute(ExecutorService pool, final String account, final String token) {
        pool.execute(new Runnable() {
            @Override
            public void run() {
                request(account, token);
            }
        });
    }

    /**
     * 请求数据
     *
     * @param account
     *         用户名
     * @param token
     *         oauth登陆后的token
     */
    private void request(String account, String token) {
        HttpClient client = new DefaultHttpClient();
        HttpPost method = new HttpPost(getUrl());

        method.addHeader("Connection", "close");
        method.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        method.addHeader("User-Agent", Constants.USER_AGENT);

        Map<String, String> params = getCommonParams(token);
        params.putAll(getParams());

        List<NameValuePair> body = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            body.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        InputStream inputStream = null;
        try {
            HttpEntity entity = new UrlEncodedFormEntity(body, Constants.DEFAULT_ENCODING);
            method.setEntity(entity);

            HttpResponse response = client.execute(method);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                StringBuilder output = new StringBuilder();
                output.append("账号是: ").append(account).append(",请求结果是: ");
                inputStream = new InflaterInputStream(response.getEntity().getContent());
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

    /**
     * 获取请求公用参数
     *
     * @param token
     *         oauth登陆后的token
     * @return
     */
    protected Map<String, String> getCommonParams(String token) {
        Map<String, String> result = new HashMap<String, String>();

        result.put("client_id", Constants.CLIENT_ID);
        long time = System.currentTimeMillis() / 1000L;
        result.put("timestamp", String.valueOf(time));

        String iv = Encrypt.md5(Constants.DEFAULT_ENCODING, time + Constants.MD5_SUFFIX).substring(16);
        // 登陆后的访问授权码
        String accessToken = Encrypt.aes(token, Constants.AES_KEY, iv);
        result.put("access_token", accessToken);
        return result;
    }

    public void setConfig(List<String> config) {
        this.config = config;
    }
}
