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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 请求类生成工厂
 *
 * @author zhulixin
 */
public class RequestServiceFactory {

    public static RequestService create(String type) {
        RequestService service = null;
        String fileName = null;
        if ("-a".equals(type)) {
            service = new LotteryRequestService();
            fileName = "a.txt";
        } else if ("-b".equals(type)) {
            service = new GoodsListRequestService();
            fileName = "b.txt";
        } else if ("-c".equals(type)) {
            service = new GoodsRequestService();
            fileName = "c.txt";
        } else if ("-d".equals(type)) {
            service = new BuyRequestService();
            fileName = "d.txt";
        }

        service.setConfig(getConfig(fileName));
        return service;
    }

    /**
     * 读取token信息
     *
     * @return token的信息
     */
    private static List<String> getConfig(String fileName) {
        BufferedReader reader = null;
        List<String> result = new ArrayList<String>();
        try {
            String line;
            reader = new BufferedReader(new FileReader(fileName));

            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }
}
