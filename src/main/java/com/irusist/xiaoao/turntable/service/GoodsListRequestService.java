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

import java.util.HashMap;
import java.util.Map;

/**
 * 商品列表请求服务
 *
 * @author zhulixin
 */
public class GoodsListRequestService extends RequestService {

    @Override
    public String getUrl() {
        return Constants.GOODS_LIST_URL;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> result = new HashMap<String, String>();
        result.put("offset", "0");
        result.put("limit", "20");
        return result;
    }
}
