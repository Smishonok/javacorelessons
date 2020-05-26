package com.valentin_nikolaev.javacore.chapter15;

interface StringFunc {
    String function(String s1, String s2);
}

public class LambdaFormat {
    private static String toUpperCase(String s1, String s2) {
        return s1.toUpperCase() + s2.toUpperCase();
    }

    public static void main(String[] args) {
        String inStr1 = "Is lambda a good chose?";
        String inStr2 = "I like lambda!";
        String outStr;

        StringFunc toUpperCase = new StringFunc() {
            @Override
            public String function(String s1, String s2) {
                return s1.toUpperCase() + s2.toUpperCase();
            }
        };

        StringFunc toUpperCase2 = (str1, str2)->(str1.toUpperCase() + str2.toUpperCase());

        StringFunc toUpperCase3 = LambdaFormat::toUpperCase;

        outStr = toUpperCase.function(inStr1, inStr2);
        outStr = toUpperCase2.function(inStr1, inStr2);
        outStr = toUpperCase3.function(inStr1, inStr2);
    }
}
