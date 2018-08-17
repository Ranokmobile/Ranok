package com.ranok.utils;

public class StringUtils {
    public static String repeat(String val, int count){
        StringBuilder buf = new StringBuilder(val.length() * count);
        while (count-- > 0) {
            buf.append(val);
        }
        return buf.toString();
    }

    public static String formatToLpn(String val){
        String result = "";
        if (val==null) return result;
        int inputLength = val.length();
        result = "L" + repeat("0", 9 - inputLength) + val;
        return result;
    }
}
/*
if (searchVM.getInputText() == null) return;
        String searchStr = "L";
        int inputLength = searchVM.getInputText().length();
        for (int i = inputLength; i < 9; i++) searchStr+="0";
        searchStr = searchStr + searchVM.getInputText();
 */
