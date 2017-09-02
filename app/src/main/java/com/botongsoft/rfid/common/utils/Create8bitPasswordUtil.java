package com.botongsoft.rfid.common.utils;

import java.util.Random;

/**  随机密码8位数，满足0-9 A-Z a-z
 * Created by pc on 2017/9/1.
 */

public class Create8bitPasswordUtil {
    public String create8p() {
        StringBuffer password = new StringBuffer();
        while (password.length() < 8) {
            int x = getRandomNumber();
            if (x >= 0 && x <= 9) {
                String n = "" + x;
                password.append(n);
            }
            if (x >= 65 && x <= 90) {
                char c = (char) x;
                password.append(c);
            }
            if((x>=97 && x<=122)){
                char d = (char) x;
                password.append(d);
            }
        }
        return new String(password);
    }

    public int getRandomNumber() {

        Random rand = new Random();
        int n = rand.nextInt(122);
        return n;
    }

    public static void main(String args[]) {

        Create8bitPasswordUtil ct = new Create8bitPasswordUtil();
        System.out.println(ct.create8p());
    }
}
