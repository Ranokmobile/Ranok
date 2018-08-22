package com.ranok.utils;

public class StringUtils {

    public static boolean isEmpty(String s){
        return s == null || s.isEmpty();
    }

    public static String repeat(String val, int count){
        if (isEmpty(val)) return "";
        if (count<=0) return "";
        StringBuilder buf = new StringBuilder(val.length() * count);
        while (count-- > 0) {
            buf.append(val);
        }
        return buf.toString();
    }

    public static String formatToLpn(String val){
        String result = "";
        if (isEmpty(val)) return result;
        int inputLength = val.length();
        result = "L" + repeat("0", 9 - inputLength) + val;
        return result;
    }

    public static String formatFromLpn(String val){
        String result = "";
        if (val==null) return result;
        result = val;
        if (val.substring(0,1).equals("L")) result = val.substring(1);
        int i = Integer.parseInt(result);
        return String.valueOf(i);
    }
}
/*
if (searchVM.getInputText() == null) return;
        String searchStr = "L";
        int inputLength = searchVM.getInputText().length();
        for (int i = inputLength; i < 9; i++) searchStr+="0";
        searchStr = searchStr + searchVM.getInputText();
 */
