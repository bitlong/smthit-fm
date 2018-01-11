package com.smthit.lang.utils;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class TextUtils {

    private static final char[] chs = "0123456789abcdefghijklmnopqrstuv".toCharArray();

    private static long MINUTE = 60000;

    private static long HOUR = 3600000;
    
    private final static char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
        '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    private TextUtils(){
    }

    /**
     * 将日期（时间）格式化成指定格式的字符串
     * @param date 日期(时间)对象
     * @param pattern 格式化模式
     * @return
     */
    public static String formatTime(Date date, String pattern){
        return new SimpleDateFormat(pattern).format(date);
    }
    
    /**
     * 将日期（时间）字符串解析成日期时间对象
     * @param value 日期(时间)字符串
     * @param pattern 时间模式
     * @return 返回日期对象，如果解析失败，则返回null
     */
    public static Date parseTime(String value, String pattern){
        try {
            return new SimpleDateFormat(pattern).parse(value);
        } catch (ParseException e) {
            return null;
        }
    }
    
    /**
     * 根据给定的key和seed，生成唯一的有效文件路径。
     * 有效文件路径需要满足：
     * 1、每个文件夹下不超过10000个文件；
     * 2、可以根据文件路径和seed还原key
     * @param key 用来生成文件路径的一个唯一数值，一般为文件的ID
     * @param seed 文件夹最大数量，有效取值为小于10000的质数
     * @param deep 生成文件路径的有效深度，实际生成的路径深度为deep+1
     * @return
     */
    public static String filePath(long key, long seed, int deep){
        StringBuilder buf = new StringBuilder(32);
        for (int i = 0; i < deep; i++) {
            buf.append(key % seed).append(File.separatorChar);
            key = key / seed;
        }
        return buf.append(key).toString();
    }
    
    /**
     * 对数值进行32进制编码处理，32进制的基准为[0-9a-v]
     * @param value 要进行编码的数值
     * @return
     */
    public static String base32(long value){
        StringBuilder buf = new StringBuilder(16);
        while (value > 0) {
            buf.append(chs[(int) (value & 31)]);
            value = value >> 5;
        }
        return buf.toString();
    }
    
    /**
     * 对数值和随机数进行32进制编码处理，32进制的基准为[0-9a-v]。
     * 随机数的编码会追加到数值的编码后面
     * @param value 要进行编码的数值
     * @param seed 随机数
     * @return
     */
    public static String base32(long value, long seed){
        StringBuilder buf = new StringBuilder(32);
        while (value > 0) {
            buf.append(chs[(int) (value & 31)]);
            value = value >> 5;
        }
        while (seed > 0) {
            buf.append(chs[(int) (seed & 31)]);
            seed = seed >> 5;
        }
        return buf.toString();
    }

    /**
     * MD5摘要
     * 
     * @param value 要进行md5摘要处理的字符串
     * @deprecated
     * @return
     */
    public static String md5(String value){
        byte[] code = null;
        try {
            code = MessageDigest.getInstance("md5").digest(value.getBytes());
        } catch (NoSuchAlgorithmException e) {
            code = value.getBytes();
        }
        BigInteger bi = new BigInteger(code);
        return bi.abs().toString(32).toUpperCase();
    }

    /**
     * 获得指定时间相对当前时间的流逝信息（即时间间隔），该方法用于处理单个时间：
     * 1、1分钟以内显示“刚刚”
     * 2、1小时以内显示“xx分钟前”
     * 3、当天显示“今天 HH:mm”
     * 4、当年显示“M月d日 HH:mm”
     * 4、其他显示“YYYY年M月d日 HH:mm”
     * 
     * @param currentTime 当前时间
     * @param time 要对时的日期时间对象
     * @return 
     */
    public static String lapsedTime(Date time){
        return lapsedTime(Calendar.getInstance(), time);
    }
    
    /**
     * 获得指定时间相对当前时间的流逝信息（即时间间隔），该方法用于批量处理多个时间：
     * 1、1分钟以内显示“刚刚”
     * 2、1小时以内显示“xx分钟前”
     * 3、当天显示“今天 HH:mm”
     * 4、当年显示“M月d日 HH:mm”
     * 5、其他显示“YYYY年M月d日 HH:mm”
     * 
     * @param currentTime 当前时间
     * @param time 要对时的日期时间对象
     * @return 
     */
    public static String lapsedTime(Calendar currentTime, Date time){
        Calendar c = Calendar.getInstance();
        c.setTime(time);

        long offset = currentTime.getTimeInMillis() - c.getTimeInMillis();

        if (offset <= MINUTE) {
            return "刚刚"; // 时间间隔
        } else if (offset <= HOUR) {
            return (offset / MINUTE) + " 分钟前";
        } else if(currentTime.get(Calendar.YEAR) != c.get(Calendar.YEAR)){
            return TextUtils.formatTime(time, "yyyy年M月d日 HH:mm");
        } else if(currentTime.get(Calendar.MONTH) != c.get(Calendar.MONTH) || currentTime.get(Calendar.DATE) != c.get(Calendar.DATE)){
            return TextUtils.formatTime(time, "M月d日 HH:mm");
        }
        return "今天 " + TextUtils.formatTime(time, "HH:mm");
    }
    
    /**
     * 获得指定时间相对当前时间的流逝信息（即时间间隔），该方法用于批量处理多个时间：
     * 1、当天显示“今天HH:mm”
     * 2、当年显示“M月d日”
     * 3、其他显示“YY年M月”
     * 
     * @param currentTime 当前时间
     * @param time 要对时的日期时间对象
     * @return 
     */
    public static String shortLapsedTime(Calendar currentTime, Date time){
        Calendar c = Calendar.getInstance();
        c.setTime(time);

        if(currentTime.get(Calendar.YEAR) != c.get(Calendar.YEAR)){
            return TextUtils.formatTime(time, "yyyy年");
        } else if(currentTime.get(Calendar.MONTH) != c.get(Calendar.MONTH) || currentTime.get(Calendar.DATE) != c.get(Calendar.DATE)){
            return TextUtils.formatTime(time, "M月d日");
        }
        return "今天" + TextUtils.formatTime(time, "HH:mm");
    }
    
    /**
     * 转换字节数组为16进制字串
     * @param b 字节数组
     * @return 16进制字串
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuilder buf = new StringBuilder();
        int n = 0;
        for (int i = 0; i < b.length; i++) {
            n = b[i];
            if(n < 0){
                n += 256;
            }
            buf.append(HEX_DIGITS[n / 16]).append(HEX_DIGITS[n % 16]);
        }
        return buf.toString();
    }

    /**
     * 对字符串进行MD5编码后转为16进制字符串
     * @param src
     * @return
     */
    public static String MD5Encode(String src) {
        String result = new String(src);
        try {
            result = byteArrayToHexString(MessageDigest.getInstance("MD5").digest(result.getBytes()));
        } catch (Exception e) {
        }
        return result;
    }
    
    /** 
     * 去除字符串首尾出现的某个字符. 
     * @param source 源字符串. 
     * @param element 需要去除的字符. 
     * @return String. 
     */  
    public static String trimFirstAndLastChar(String source,char element){  
        boolean beginIndexFlag = true;  
        boolean endIndexFlag = true;  
        do{  
            int beginIndex = source.indexOf(element) == 0 ? 1 : 0;  
            int endIndex = source.lastIndexOf(element) + 1 == source.length() ? source.lastIndexOf(element) : source.length();  
            source = source.substring(beginIndex, endIndex);  
            beginIndexFlag = (source.indexOf(element) == 0);  
            endIndexFlag = (source.lastIndexOf(element) + 1 == source.length());  
        } while (beginIndexFlag || endIndexFlag);  
        return source;  
    }  
    /**
    * @param length 随机字符串长度
    * @return 随机字符串
    */
    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(chs[random.nextInt(chs.length)]);
        }
        return sb.toString();
    }
}
