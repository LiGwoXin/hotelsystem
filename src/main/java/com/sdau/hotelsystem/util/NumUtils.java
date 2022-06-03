package com.sdau.hotelsystem.util;

import java.util.Random;

public class NumUtils {
    public static Integer getNum(int n){
        char[] chars = ("1234567890").toCharArray();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < n; i++){
            //Random().nextInt()返回值为[0,n)
            char aChar = chars[new Random().nextInt(chars.length)];
            sb.append(aChar);
        }
        return Integer.parseInt(sb.toString());
    }
}
