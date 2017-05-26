package com.common.util;

/**
 * Created by zhouhaiming on 2017-5-10 20:32
 * Email: dg_chow@163.com
 *
 * @Description: 字节数组与基本类型的转换
 */
public class TypeUtil {

    public static short byte2Short(byte[] b) {
        short s = 0;
        s |= (((char) b[1] & 0xff) << 8);
        s |= ((char) b[0] & 0xff);
        return s;
    }

    public static char byte2Char(byte[] b) {
        char c = 0;
        c |= (((char) b[1] & 0xff) << 8);
        c |= ((char) b[0] & 0xff);
        return c;
    }

    public static int byte2Int(byte[] b) {
        int i = 0;
        i |= (((int) b[3] & 0xff) << 24);
        i |= (((int) b[2] & 0xff) << 16);
        i |= (((int) b[1] & 0xff) << 8);
        i |= ((int) b[0] & 0xff);
        return i;
    }

    public static float byte2Float(byte[] b) {
        int i = byte2Int(b);
        return Float.intBitsToFloat(i);
    }

    public static long byte2Long(byte[] b) {
        long l = 0;
        l |= (((long) b[7] & 0xff) << 56);
        l |= (((long) b[6] & 0xff) << 48);
        l |= (((long) b[5] & 0xff) << 40);
        l |= (((long) b[4] & 0xff) << 32);
        l |= (((long) b[3] & 0xff) << 24);
        l |= (((long) b[2] & 0xff) << 16);
        l |= (((long) b[1] & 0xff) << 8);
        l |= ((long) b[0] & 0xff);
        return l;
    }

    public static double byte2Double(byte[] b) {
        long l = byte2Long(b);
        return Double.longBitsToDouble(l);
    }

    public static byte[] short2Byte(short s) {
        byte[] bytes = new byte[2];
        bytes[1] = (byte) (s & 0xff);
        bytes[0] = (byte) (s >> 8 & 0xff);
        return bytes;
    }

    public static byte[] char2Byte(char c) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (c & 0xff);
        bytes[1] = (byte) (c >> 8 & 0xff);
        return bytes;
    }

    public static byte[] int2Byte(int i) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (i & 0xff);
        bytes[1] = (byte) (i >> 8 & 0xff);
        bytes[2] = (byte) (i >> 16 & 0xff);
        bytes[3] = (byte) (i >> 24 & 0xff);
        return bytes;
    }

    public static byte[] float2Byte(float f) {
        int i = Float.floatToIntBits(f);
        return int2Byte(i);
    }

    public static byte[] long2Byte(long l) {
        byte[] bytes = new byte[8];
        bytes[0] = (byte) (l & 0xff);
        bytes[1] = (byte) (l >> 8 & 0xff);
        bytes[2] = (byte) (l >> 16 & 0xff);
        bytes[3] = (byte) (l >> 24 & 0xff);
        bytes[4] = (byte) (l >> 32 & 0xff);
        bytes[5] = (byte) (l >> 40 & 0xff);
        bytes[6] = (byte) (l >> 48 & 0xff);
        bytes[7] = (byte) (l >> 56 & 0xff);
        return bytes;
    }

    public static byte[] double2Byte(double d) {
        long l = Double.doubleToLongBits(d);
        return long2Byte(l);
    }
}
