package com.shishic.cb.util;

import android.text.TextUtils;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类，用于各种字符串的转化。
 *
 * @author Dongyue
 */
public class RegexStringUtils {
    // 将matchString中包含的关键词进行标记
    public static String markString(List<String> keyWordsList,
                                    String matchString) {
        String replaceRegStr = createReplaceReg(keyWordsList);
        Pattern p = Pattern.compile(replaceRegStr);
        Matcher m = p.matcher(matchString);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "<font color='#ff0000'>" + m.group()
                    + "</font>");
            System.out.println(m.group());
        }
        m.appendTail(sb);
        return sb.toString();
    }

    // 创建替换文字所需的正则表达式
    private static String createReplaceReg(List<String> keyWordsList2) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < keyWordsList2.size(); i++) {
            sb.append("(").append(keyWordsList2.get(i)).append(")|");
        }
        return sb.substring(0, sb.length() - 1);
    }

    // 将文本框中输入的关键词字符串以“特殊符号”切分为关键词List
    public static List<String> splitKeyWordsStr(String rawStr) {
        List<String> keyWordsList = new ArrayList<String>();
        String regex = "\\b[\\u4e00-\\u9fa5\\w]+\\b";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(rawStr);
        while (m.find()) {
            // System.out.println(m.group());
            keyWordsList.add(m.group());
        }
        return keyWordsList;
    }

    // 邮箱验证
    public static boolean EmailFormat(String str) {
        Pattern pattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher mc = pattern.matcher(str);
        return mc.matches();
    }

    /**
     * 验证手机格式
     */
    public static boolean IsMobileFormat(String mobiles) {
         /*
         * 移动：134/135/136/137/138/139/150/151/152/157/158/159/182/183/184/187/188/147/178
		 * 联通：130/131/132/152/155/156/185/186/145/176
		 * 电信号段:133/153/180/181/189/177
		 *
		 */
        String telRegex = "^[1](3|4|5|7|8|9)\\d{9}$"; // "^"代表开始符，"$"代表结束符，[1]"代表第1位为数字1，"[^4,\D]"代表除4以外的数字，"\\d{8}"代表后面是可以是0～9的数字，有8位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    public static boolean isNicknameLegal(String nickname) {
        int m = 0;
        char arr[] = nickname.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            if ((c >= 0x0391 && c <= 0xFFE5))  //中文字符
            {
                m = m + 2;
            } else if ((c >= 0x0000 && c <= 0x00FF)) //英文字符
            {
                m = m + 1;
            }
        }
        //不超过10个字符
        return m <= 10;
    }

    /**
     * 获取统计匹配下载的字符串
     * @param str
     * @return
     */
    public static String getMatchDownloadStatistics(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        Pattern pattern = Pattern.compile("[=][\\-_\\w\\d]{22}[~]");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

}
