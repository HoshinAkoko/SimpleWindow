package moe.moti.simplewindow.util;

import org.springframework.lang.Nullable;

import java.security.MessageDigest;

public class ValueUtil {
    public static String valueOf(@Nullable Object obj) {
        if (obj == null) {
            return "";
        } else {
            return String.valueOf(obj);
        }
    }

    public static int parseInt(@Nullable Object obj) {
        String str = valueOf(obj);
        if (str.matches("\\d+")) {
            return Integer.parseInt(str);
        } else {
            return 0;
        }
    }

    public static double parseDouble(@Nullable Object obj) {
        String str = valueOf(obj);
        if (str.matches("\\d+(\\.\\d+)?")) {
            return Double.parseDouble(str);
        } else {
            return 0;
        }
    }

    public static String MD5(String s) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将对象字符串转换为json字符串
     *
     * @param str
     * @return
     */
    public static String ObjectStrToJson(String str) {
        return str.replaceAll("(\\w+)=([^,}]*)", "\"$1\": \"$2\"");
    }

    public static String printMapHtml(String string) {
        return "<pre>" + printMapNew(string) + "</pre>";
    }

    public static String printHtml(String string) {
        return "<pre>" + string + "</pre>";
    }

    public static String printMapNew(String str) {
        StringBuilder sb = new StringBuilder();
        boolean f = true;
        int t = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '\\') {
                sb.append(c);
                i++;
                c = str.charAt(i);
                sb.append(c);
                continue;
            }
            if (c == '\"') {
                f = !f;
                sb.append(c);
                continue;
            }
            if (f && c == ',') {
                sb.append(c);
                sb.append("\n");
                fillTab(sb, t);
                continue;
            }
            if (f && (c == '{' || c == '[')) {
                if (sb.length() > 0 && sb.charAt(sb.length() - 1) != '\t') {
                    sb.append("\n");
                    for (int n = 0; n < t; n++) {
                        sb.append("\t");
                    }
                }
                sb.append(c);
                sb.append("\n");
                if (c == '{') t++;
                fillTab(sb, t);
                continue;
            }
            if (f && (c == '}' || c == ']')) {
                if (sb.length() > 0 && sb.charAt(sb.length() - 1) != '\t') {
                    sb.append("\n");
                    if (c == '}') t--;
                    fillTab(sb, t);
                } else {
                    if (c == '}') {
                        t--;
                        sb.deleteCharAt(sb.length() - 1);
                    }
                }
                sb.append(c);
                if (nextNotSpace(str, i) != ',') {
                    sb.append("\n");
                    fillTab(sb, t);
                }
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    private static void fillTab(StringBuilder sb, int tab) {
        for (int n = 0; n < tab; n ++) {
            sb.append("\t");
        }
    }

    private static char nextNotSpace(String str, int s) {
        for (int i = s + 1; i < str.length(); i++) {
            if (str.charAt(i) == ' ') {
                continue;
            }
            return str.charAt(i);
        }
        return ',';
    }

    public static String[] subArr(String[] strArr, int l, int r) {
        if (l < 0 || r < 0 || l > r || r > strArr.length) {
            String[] sub = {};
            return sub;
        }
        String[] res = new String[r - l];
        System.arraycopy(strArr, l, res, 0, r - l);
        return res;
    }
}
