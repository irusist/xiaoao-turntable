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

package com.irusist.xiaoao.turntable.encrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密类
 *
 * @author zhulixin
 */
public class Encrypt {

    /**
     * MD5加密
     *
     * @param encoding
     *         字符集
     * @param value
     *         被加密的数据
     * @return 加密后的数据
     */
    public static String md5(String encoding, String value) {
        // 被加密数据的字节数
        byte[] bytes;
        StringBuilder result;
        int i = 0;
        try {
            bytes = MessageDigest.getInstance("MD5").digest(value.getBytes("UTF-8"));
            result = new StringBuilder(2 * bytes.length);
            i = bytes.length;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm is not supported", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(encoding + " is not supported", e);
        }

        for (int j = 0; j < i; j++) {
            int k = bytes[j];
            if ((k & 0xFF) < 16) {
                result.append("0");
            }

            result.append(Integer.toHexString(k & 0xFF));
        }

        return result.toString();
    }

    /**
     * AES/CBC/NOPADDING加密
     *
     * @param data
     *         被加密的数据
     * @param key
     *         加密的私钥
     * @param iv
     *         initialization vector
     * @return 加密后的数据
     */
    public static String cipher(String data, String key, String iv) {
        Cipher a = null;
        try {
            a = Cipher.getInstance("AES/CBC/NOPADDING");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        int i = 16 - data.getBytes().length % 16;
        StringBuilder dataBuilder = new StringBuilder(data);

        for (int j = 0; j < i; j++) {
            dataBuilder.append((char) i);
        }

        byte[] bytes = dataBuilder.toString().getBytes();
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec parameterSpec = new IvParameterSpec(iv.getBytes());
        String result = null;
        try {
            a.init(1, keySpec, parameterSpec);
            result = Base64.encodeToString(a.doFinal(bytes), 18);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            return null;
        } catch (BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }
}
