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

import com.irusist.xiaoao.turntable.service.RequestService;
import com.irusist.xiaoao.turntable.service.RequestServiceFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 笑傲江湖游戏转盘积分获取
 *
 * @author zhulixin
 */
public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage : java -jar xiaoao-turntable-{version}.jar -a -b ...");
            System.exit(0);
        }

        List<RequestService> services = new ArrayList<RequestService>();
        for (String option : args) {
            services.add(RequestServiceFactory.create(option));
        }

        new Dispatcher(services).dispatch();
    }
}
