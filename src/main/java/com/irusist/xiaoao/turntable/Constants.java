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

package com.irusist.xiaoao.turntable;

/**
 * 常量
 *
 * @author zhulixin
 */
public interface Constants {

    /**
     * 字符集 *
     */
    String DEFAULT_ENCODING = "UTF-8";

    /**
     * 应用的client_id *
     */
    String CLIENT_ID = "aa5da79f0961c878eb83676e5fbe5736";

    /**
     * 抽奖的请求地址 *
     */
    String LOTTERY_URL = "http://wm.laohu.com/xajh_v1/user/lottery";

    /**
     * 获取商品列表请求地址
     */
    String GOODS_LIST_URL = "http://wm.laohu.com/xajh_v1/store/goodslist";

    /**
     * 获取商品请求地址
     */
    String GOODS_URL = "http://wm.laohu.com/xajh_v1/store/goods";

    /**
     * 购买的请求地址
     */
    String BUY_URL = "http://wm.laohu.com/xajh_v1/store/buy";

    /**
     * user-agent *
     */
    String USER_AGENT = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET4.0C; .NET4.0E)";

    /**
     * MD5加密后缀 *
     */
    String MD5_SUFFIX = "@k#d5fb77d6e4e8077f48c37455c7960ae5";

    /**
     * AES加密私钥 *
     */
    String AES_KEY = "d5fb77d6e4e8077f48c37455c7960ae5";


}
