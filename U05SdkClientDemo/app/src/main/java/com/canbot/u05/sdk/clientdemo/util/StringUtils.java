package com.canbot.u05.sdk.clientdemo.util;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/16
 *     desc  : 字符串相关工具类.
 * </pre>
 */
public class StringUtils {

        private StringUtils() {
                throw new UnsupportedOperationException("u can't fuck me...");
        }

        /**
         * 判断字符串是否为null或长度为0.
         *
         * @param s 待校验字符串
         * @return {@code true}: 空<br>
         * {@code false}: 不为空
         */
        public static boolean isEmpty(String s) {
                if (s != null && !"".equalsIgnoreCase(s.trim())
                        && !"null".equalsIgnoreCase(s.trim())) {
                        return false;
                } else {
                        return true;
                }
        }

        /**
         * 判断字符串是否为null或全为空格.
         *
         * @param s 待校验字符串
         * @return {@code true}: null或全空格<br>
         * {@code false}: 不为null且不全空格
         */
        public static boolean isSpace(String s) {
                return (s == null || s.trim().length() == 0);
        }

        /**
         * null转为长度为0的字符串.
         *
         * @param s 待转字符串
         * @return s为null转为长度为0字符串，否则不改变
         */
        public static String null2Length0(String s) {
                return s == null ? "" : s;
        }

        /**
         * 返回字符串长度.
         *
         * @param s 字符串
         * @return null返回0，其他返回自身长度
         */
        public static int length(CharSequence s) {
                return s == null ? 0 : s.length();
        }

        /**
         * 首字母大写.
         *
         * @param s 待转字符串
         * @return 首字母大写字符串
         */
        public static String upperFirstLetter(String s) {
                if (isEmpty(s) || !Character.isLowerCase(s.charAt(0))) {
                        return s;
                }
                return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
        }

        /**
         * 首字母小写.
         *
         * @param s 待转字符串
         * @return 首字母小写字符串
         */
        public static String lowerFirstLetter(String s) {
                if (isEmpty(s) || !Character.isUpperCase(s.charAt(0))) {
                        return s;
                }
                return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
        }

        /**
         * 反转字符串.
         *
         * @param s 待反转字符串
         * @return 反转字符串
         */
        public static String reverse(String s) {
                int len = length(s);
                if (len <= 1) {
                        return s;
                }

                int mid = len >> 1;
                char[] chars = s.toCharArray();
                char c;
                for (int i = 0; i < mid; ++i) {
                        c = chars[i];
                        chars[i] = chars[len - i - 1];
                        chars[len - i - 1] = c;
                }
                return new String(chars);
        }

        /**
         * 转化为半角字符.
         *
         * @param s 待转字符串
         * @return 半角字符串
         */
        public static String toDBC(String s) {
                if (isEmpty(s)) {
                        return s;
                }
                char[] chars = s.toCharArray();
                for (int i = 0, len = chars.length; i < len; i++) {
                        if (chars[i] == 12288) {
                                chars[i] = ' ';
                        } else if (65281 <= chars[i] && chars[i] <= 65374) {
                                chars[i] = (char) (chars[i] - 65248);
                        } else {
                                chars[i] = chars[i];
                        }
                }
                return new String(chars);
        }

        /**
         * 转化为全角字符.
         *
         * @param s 待转字符串
         * @return 全角字符串
         */
        public static String toSBC(String s) {
                if (isEmpty(s)) {
                        return s;
                }
                char[] chars = s.toCharArray();
                for (int i = 0, len = chars.length; i < len; i++) {
                        if (chars[i] == ' ') {
                                chars[i] = (char) 12288;
                        } else if (33 <= chars[i] && chars[i] <= 126) {
                                chars[i] = (char) (chars[i] + 65248);
                        } else {
                                chars[i] = chars[i];
                        }
                }
                return new String(chars);
        }

        /**
         * The pyvalue.
         */
        private static int[] pyvalue = new int[]{-20319, -20317, -20304, -20295,
                -20292, -20283, -20265, -20257, -20242, -20230, -20051, -20036,
                -20032, -20026, -20002, -19990, -19986, -19982, -19976, -19805,
                -19784, -19775, -19774, -19763, -19756, -19751, -19746, -19741,
                -19739, -19728, -19725, -19715, -19540, -19531, -19525, -19515,
                -19500, -19484, -19479, -19467, -19289, -19288, -19281, -19275,
                -19270, -19263, -19261, -19249, -19243, -19242, -19238, -19235,
                -19227, -19224, -19218, -19212, -19038, -19023, -19018, -19006,
                -19003, -18996, -18977, -18961, -18952, -18783, -18774, -18773,
                -18763, -18756, -18741, -18735, -18731, -18722, -18710, -18697,
                -18696, -18526, -18518, -18501, -18490, -18478, -18463, -18448,
                -18447, -18446, -18239, -18237, -18231, -18220, -18211, -18201,
                -18184, -18183, -18181, -18012, -17997, -17988, -17970, -17964,
                -17961, -17950, -17947, -17931, -17928, -17922, -17759, -17752,
                -17733, -17730, -17721, -17703, -17701, -17697, -17692, -17683,
                -17676, -17496, -17487, -17482, -17468, -17454, -17433, -17427,
                -17417, -17202, -17185, -16983, -16970, -16942, -16915, -16733,
                -16708, -16706, -16689, -16664, -16657, -16647, -16474, -16470,
                -16465, -16459, -16452, -16448, -16433, -16429, -16427, -16423,
                -16419, -16412, -16407, -16403, -16401, -16393, -16220, -16216,
                -16212, -16205, -16202, -16187, -16180, -16171, -16169, -16158,
                -16155, -15959, -15958, -15944, -15933, -15920, -15915, -15903,
                -15889, -15878, -15707, -15701, -15681, -15667, -15661, -15659,
                -15652, -15640, -15631, -15625, -15454, -15448, -15436, -15435,
                -15419, -15416, -15408, -15394, -15385, -15377, -15375, -15369,
                -15363, -15362, -15183, -15180, -15165, -15158, -15153, -15150,
                -15149, -15144, -15143, -15141, -15140, -15139, -15128, -15121,
                -15119, -15117, -15110, -15109, -14941, -14937, -14933, -14930,
                -14929, -14928, -14926, -14922, -14921, -14914, -14908, -14902,
                -14894, -14889, -14882, -14873, -14871, -14857, -14678, -14674,
                -14670, -14668, -14663, -14654, -14645, -14630, -14594, -14429,
                -14407, -14399, -14384, -14379, -14368, -14355, -14353, -14345,
                -14170, -14159, -14151, -14149, -14145, -14140, -14137, -14135,
                -14125, -14123, -14122, -14112, -14109, -14099, -14097, -14094,
                -14092, -14090, -14087, -14083, -13917, -13914, -13910, -13907,
                -13906, -13905, -13896, -13894, -13878, -13870, -13859, -13847,
                -13831, -13658, -13611, -13601, -13406, -13404, -13400, -13398,
                -13395, -13391, -13387, -13383, -13367, -13359, -13356, -13343,
                -13340, -13329, -13326, -13318, -13147, -13138, -13120, -13107,
                -13096, -13095, -13091, -13076, -13068, -13063, -13060, -12888,
                -12875, -12871, -12860, -12858, -12852, -12849, -12838, -12831,
                -12829, -12812, -12802, -12607, -12597, -12594, -12585, -12556,
                -12359, -12346, -12320, -12300, -12120, -12099, -12089, -12074,
                -12067, -12058, -12039, -11867, -11861, -11847, -11831, -11798,
                -11781, -11604, -11589, -11536, -11358, -11340, -11339, -11324,
                -11303, -11097, -11077, -11067, -11055, -11052, -11045, -11041,
                -11038, -11024, -11020, -11019, -11018, -11014, -10838, -10832,
                -10815, -10800, -10790, -10780, -10764, -10587, -10544, -10533,
                -10519, -10331, -10329, -10328, -10322, -10315, -10309, -10307,
                -10296, -10281, -10274, -10270, -10262, -10260, -10256, -10254};

        /**
         * The pystr.
         */
        public static String[] pystr = new String[]{"a", "ai", "an", "ang", "ao",
                "ba", "bai", "ban", "bang", "bao", "bei", "ben", "beng", "bi",
                "bian", "biao", "bie", "bin", "bing", "bo", "bu", "ca", "cai",
                "can", "cang", "cao", "ce", "ceng", "cha", "chai", "chan", "chang",
                "chao", "che", "chen", "cheng", "chi", "chong", "chou", "chu",
                "chuai", "chuan", "chuang", "chui", "chun", "chuo", "ci", "cong",
                "cou", "cu", "cuan", "cui", "cun", "cuo", "da", "dai", "dan",
                "dang", "dao", "de", "deng", "di", "dian", "diao", "die", "ding",
                "diu", "dong", "dou", "du", "duan", "dui", "dun", "duo", "e", "en",
                "er", "fa", "fan", "fang", "fei", "fen", "feng", "fo", "fou", "fu",
                "ga", "gai", "gan", "gang", "gao", "ge", "gei", "gen", "geng",
                "gong", "gou", "gu", "gua", "guai", "guan", "guang", "gui", "gun",
                "guo", "ha", "hai", "han", "hang", "hao", "he", "hei", "hen",
                "heng", "hong", "hou", "hu", "hua", "huai", "huan", "huang", "hui",
                "hun", "huo", "ji", "jia", "jian", "jiang", "jiao", "jie", "jin",
                "jing", "jiong", "jiu", "ju", "juan", "jue", "jun", "ka", "kai",
                "kan", "kang", "kao", "ke", "ken", "keng", "kong", "kou", "ku",
                "kua", "kuai", "kuan", "kuang", "kui", "kun", "kuo", "la", "lai",
                "lan", "lang", "lao", "le", "lei", "leng", "li", "lia", "lian",
                "liang", "liao", "lie", "lin", "ling", "liu", "long", "lou", "lu",
                "lv", "luan", "lue", "lun", "luo", "ma", "mai", "man", "mang",
                "mao", "me", "mei", "men", "meng", "mi", "mian", "miao", "mie",
                "min", "ming", "miu", "mo", "mou", "mu", "na", "nai", "nan", "nang",
                "nao", "ne", "nei", "nen", "neng", "ni", "nian", "niang", "niao",
                "nie", "nin", "ning", "niu", "nong", "nu", "nv", "nuan", "nue",
                "nuo", "o", "ou", "pa", "pai", "pan", "pang", "pao", "pei", "pen",
                "peng", "pi", "pian", "piao", "pie", "pin", "ping", "po", "pu",
                "qi", "qia", "qian", "qiang", "qiao", "qie", "qin", "qing", "qiong",
                "qiu", "qu", "quan", "que", "qun", "ran", "rang", "rao", "re",
                "ren", "reng", "ri", "rong", "rou", "ru", "ruan", "rui", "run",
                "ruo", "sa", "sai", "san", "sang", "sao", "se", "sen", "seng",
                "sha", "shai", "shan", "shang", "shao", "she", "shen", "sheng",
                "shi", "shou", "shu", "shua", "shuai", "shuan", "shuang", "shui",
                "shun", "shuo", "si", "song", "sou", "su", "suan", "sui", "sun",
                "suo", "ta", "tai", "tan", "tang", "tao", "te", "teng", "ti",
                "tian", "tiao", "tie", "ting", "tong", "tou", "tu", "tuan", "tui",
                "tun", "tuo", "wa", "wai", "wan", "wang", "wei", "wen", "weng",
                "wo", "wu", "xi", "xia", "xian", "xiang", "xiao", "xie", "xin",
                "xing", "xiong", "xiu", "xu", "xuan", "xue", "xun", "ya", "yan",
                "yang", "yao", "ye", "yi", "yin", "ying", "yo", "yong", "you", "yu",
                "yuan", "yue", "yun", "za", "zai", "zan", "zang", "zao", "ze",
                "zei", "zen", "zeng", "zha", "zhai", "zhan", "zhang", "zhao", "zhe",
                "zhen", "zheng", "zhi", "zhong", "zhou", "zhu", "zhua", "zhuai",
                "zhuan", "zhuang", "zhui", "zhun", "zhuo", "zi", "zong", "zou",
                "zu", "zuan", "zui", "zun", "zuo"};

        /**
         * 汉字转成ASCII码.
         *
         * @param chs 中文字符
         * @return 转换结果
         */
        public static int getChsAscii(String chs) {
                int asc = 0;
                try {
                        byte[] bytes = chs.getBytes("gb2312");
            /*
             * if (bytes == null || bytes.length > 2 || bytes.length <= 0) {
             * throw new RuntimeException("illegal resource string"); }
             */
                        if (bytes.length == 1) {
                                asc = bytes[0];
                        }
                        if (bytes.length == 2) {
                                int hightByte = 256 + bytes[0];
                                int lowByte = 256 + bytes[1];
                                asc = (256 * hightByte + lowByte) - 256 * 256;
                        }
                } catch (UnsupportedEncodingException e) {
                        System.out.println(
                                "ERROR:ChineseSpelling.class-getChsAscii(String chs)" + e);
                }
                return asc;
        }

        /**
         * 单字解析.
         *
         * @param str 输入字符
         * @return 解析结果
         */
        public static String convert(String str) {
                String result = null;
                int ascii = getChsAscii(str);
                if (ascii > 0 && ascii < 160) {
                        result = String.valueOf((char) ascii);
                } else {
                        for (int i = (pyvalue.length - 1); i >= 0; i--) {
                                if (pyvalue[i] <= ascii) {
                                        result = pystr[i];
                                        break;
                                }
                        }
                }
                return result;
        }

        /**
         * 词组解析.
         *
         * @param chs 输入字符
         * @return 解析结果
         */
        public String getSelling(String chs) {
                String key, value;
                StringBuilder buffer = new StringBuilder();
                for (int i = 0; i < chs.length(); i++) {
                        key = chs.substring(i, i + 1);
                        if (key.getBytes().length >= 2) {
                                value = convert(key);
                                if (value == null) {
                                        value = "unknown";
                                }
                        } else {
                                value = key;
                        }
                        buffer.append(value);
                }
                return buffer.toString();
        }

        /**
         * 中文长度.
         *
         * @param str 输入字符
         * @return 长度结果
         */
        public static int chineseLength(String str) {
                int valueLength = 0;
                String chinese = "[\u0391-\uFFE5]";
                if (!isEmpty(str)) {
                        for (int i = 0; i < str.length(); i++) {
                                String temp = str.substring(i, i + 1);
                                if (temp.matches(chinese)) {
                                        valueLength += 2;
                                }
                        }
                }
                return valueLength;
        }

        /**
         * 字符串长度.
         *
         * @param str 输入字符
         * @return 字符串长度
         */
        public static int strLength(String str) {
                int valueLength = 0;
                String chinese = "[\u0391-\uFFE5]";
                if (!isEmpty(str)) {
                        for (int i = 0; i < str.length(); i++) {
                                String temp = str.substring(i, i + 1);
                                if (temp.matches(chinese)) {
                                        valueLength += 2;
                                } else {
                                        valueLength += 1;
                                }
                        }
                }
                return valueLength;
        }

        /**
         * 获取指定长度的字符所在位置.
         *
         * @param str  字符串
         * @param maxL
         * @return
         */
        public static int subStringLength(String str, int maxL) {
                int currentIndex = 0;
                int valueLength = 0;
                String chinese = "[\u0391-\uFFE5]";
                for (int i = 0; i < str.length(); i++) {
                        String temp = str.substring(i, i + 1);
                        if (temp.matches(chinese)) {
                                valueLength += 2;
                        } else {
                                valueLength += 1;
                        }
                        if (valueLength >= maxL) {
                                currentIndex = i;
                                break;
                        }
                }
                return currentIndex;
        }

        /**
         * 是否是中文.
         *
         * @param str 输入字符串
         * @return 是否是中文字符
         */
        public static Boolean isChinese(String str) {
                Boolean isChinese = true;
                String chinese = "[\u0391-\uFFE5]";
                if (!isEmpty(str)) {
                        for (int i = 0; i < str.length(); i++) {
                                String temp = str.substring(i, i + 1);
                                isChinese = temp.matches(chinese);
                        }
                }
                return isChinese;
        }

        /**
         * 是否包含中文.
         *
         * @param str 字符串
         * @return 是否包含中文
         */
        public static Boolean isContainChinese(String str) {
                Boolean isChinese = false;
                String chinese = "[\u0391-\uFFE5]";
                if (!isEmpty(str)) {
                        for (int i = 0; i < str.length(); i++) {
                                String temp = str.substring(i, i + 1);
                                isChinese = temp.matches(chinese);
                        }
                }
                return isChinese;
        }

        /**
         * 不足2位前面补0.
         *
         * @param str 输入字符
         * @return 补0结果
         */
        public static String strFormat2(String str) {
                if (str.length() <= 1) {
                        str = "0" + str;
                }
                return str;
        }

        /**
         * int类型安全转换.
         *
         * @param value        转换对象
         * @param defaultValue 默认数值
         * @return 转换结果
         */
        public static int convert2Int(Object value, int defaultValue) {
                if (value == null || "".equals(value.toString().trim())) {
                        return defaultValue;
                }
                try {
                        return Double.valueOf(value.toString()).intValue();
                } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return defaultValue;
                }
        }


        public static String decimalFormat(String s, String format) {
                DecimalFormat decimalFormat = new DecimalFormat(format);
                return decimalFormat.format(s);
        }


        public static boolean isEquals(String... agrs) {
                String last = null;
                for (int i = 0; i < agrs.length; i++) {
                        String str = agrs[i];
                        if (isEmpty(str)) {
                                return false;
                        }
                        if (last != null && !str.equalsIgnoreCase(last)) {
                                return false;
                        }
                        last = str;
                }
                return true;
        }


        public static CharSequence getHighLightText(String content, int color,
                                                    int start, int end) {
                if (TextUtils.isEmpty(content)) {
                        return "";
                }
                start = start >= 0 ? start : 0;
                end = end <= content.length() ? end : content.length();
                SpannableString spannable = new SpannableString(content);
                CharacterStyle span = new ForegroundColorSpan(color);
                spannable.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                return spannable;
        }


        public static String formatFileSize(long len) {
                return formatFileSize(len, false);
        }

        public static String formatFileSize(long len, boolean keepZero) {
                String size;
                DecimalFormat formatKeepTwoZero = new DecimalFormat("#.00");
                DecimalFormat formatKeepOneZero = new DecimalFormat("#.0");
                if (len < 1024) {
                        size = String.valueOf(len + "B");
                } else if (len < 10 * 1024) {
                        // [0, 10KB)锛屼繚鐣欎袱浣嶅皬鏁�
                        size = String.valueOf(len * 100 / 1024 / (float) 100) + "KB";
                } else if (len < 100 * 1024) {
                        // [10KB, 100KB)锛屼繚鐣欎竴浣嶅皬鏁�
                        size = String.valueOf(len * 10 / 1024 / (float) 10) + "KB";
                } else if (len < 1024 * 1024) {
                        // [100KB, 1MB)锛屼釜浣嶅洓鑸嶄簲鍏�
                        size = String.valueOf(len / 1024) + "KB";
                } else if (len < 10 * 1024 * 1024) {
                        // [1MB, 10MB)锛屼繚鐣欎袱浣嶅皬鏁�
                        if (keepZero) {
                                size = String.valueOf(formatKeepTwoZero.format(len * 100 / 1024
                                        / 1024 / (float) 100))
                                        + "MB";
                        } else {
                                size = String.valueOf(len * 100 / 1024 / 1024 / (float) 100)
                                        + "MB";
                        }
                } else if (len < 100 * 1024 * 1024) {
                        // [10MB, 100MB)锛屼繚鐣欎竴浣嶅皬鏁�
                        if (keepZero) {
                                size = String.valueOf(formatKeepOneZero.format(len * 10 / 1024
                                        / 1024 / (float) 10))
                                        + "MB";
                        } else {
                                size = String.valueOf(len * 10 / 1024 / 1024 / (float) 10)
                                        + "MB";
                        }
                } else if (len < 1024 * 1024 * 1024) {
                        // [100MB, 1GB)锛屼釜浣嶅洓鑸嶄簲鍏�
                        size = String.valueOf(len / 1024 / 1024) + "MB";
                } else {
                        // [1GB, ...)锛屼繚鐣欎袱浣嶅皬鏁�
                        size = String.valueOf(len * 100 / 1024 / 1024 / 1024 / (float) 100)
                                + "GB";
                }
                return size;
        }
}