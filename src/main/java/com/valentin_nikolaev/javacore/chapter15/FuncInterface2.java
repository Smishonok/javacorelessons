package com.valentin_nikolaev.javacore.chapter15;

interface SomeFunc<T, P> {
    T func(P param);
}

public class FuncInterface2 {

    private static String revers(String s) {
        String result    = "";
        char[] charArray = s.toCharArray();

        for (int i = charArray.length - 1; i > - 1; i--) {
            result += charArray[i];
        }

        return result;
    }

    public static void main(String[] args) {
        String str = "I use lambda!";

        SomeFunc<String, String> stringFunc;
        stringFunc = s->{
            String result    = "";
            char[] charArray = s.toCharArray();

            for (int i = charArray.length - 1; i > - 1; i--) {
                result += charArray[i];
            }

            return result;
        };

        // The same stringFunc = FuncInterface2::revers;
        //The same stringFunc = (s)->revers(s);
        String outStr = stringFunc.func(str);

        System.out.println(outStr);
    }
}
