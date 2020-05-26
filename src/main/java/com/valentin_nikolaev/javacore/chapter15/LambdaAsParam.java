package com.valentin_nikolaev.javacore.chapter15;

interface StringFunctionForParam {
    String func(String s1, String s2);
}

public class LambdaAsParam {

    private static String stringOp(StringFunctionForParam sf, String s1, String s2) {
        return sf.func(s1, s2);
    }

    private static String toUpperCase(String s1, String s2) {
        return s1.toUpperCase() + "\n" + s2.toUpperCase();
    }

    public static void main(String[] args) {
        String inStr1 = "Is lambda a good chose?";
        String inStr2 = "I like lambda!";
        String outStr;

        outStr = stringOp(new StringFunctionForParam() {
            @Override
            public String func(String s1, String s2) {
                return s1 + "\n" + s2;
            }
        }, inStr1, inStr2);

        StringFunctionForParam concat = (s1, s2)->(s1 + "\n" + s2);
        outStr = stringOp(concat, inStr1, inStr2);

        outStr = stringOp(LambdaAsParam::toUpperCase, inStr1, inStr2);

    }
}
