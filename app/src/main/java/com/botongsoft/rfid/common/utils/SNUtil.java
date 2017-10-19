package com.botongsoft.rfid.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class SNUtil {
    public static void main(String[] args) {
        for (int i = 0; i < 3600; i++) {
            System.out.println("Date()" + new Date().getTime());
            System.out.println("System" + System.currentTimeMillis());
            System.out.println("123456" + nextSid());
        }

    }

    private static long lastTimestamp = -1L;
    private static int sequence = 0;
    private static final long MAX_SEQUENCE = 1000;
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");


    public static synchronized Long nextSid() {
        long now = timeGen();
        if (now == lastTimestamp) {
            if (sequence++ > MAX_SEQUENCE) {
                now = tilNextMillis(lastTimestamp);
                sequence = 0;
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = now;
        long s = new Date(now).getTime();
        String sssString = String.valueOf(s) + String.valueOf(sequence);
        //
        return Long.valueOf(sssString);
    }

    private static long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private static long timeGen() {
        return System.currentTimeMillis();
    }


}
