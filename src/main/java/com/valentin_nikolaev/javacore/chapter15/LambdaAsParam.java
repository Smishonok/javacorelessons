package com.valentin_nikolaev.javacore.chapter15;

interface StringFunc {
    String func(String s);
}

class StringTrimmer {
    public static String trimWhitSpace(String s) {
        String newString = "";
        char   trimVar   = ' ';
        char[] chars     = s.toCharArray();

        for (char ch : chars) {
            if (ch != trimVar) {
                newString += ch;
            }
        }

        return newString;
    }
}

public class LambdaAsParam {

    private static String stringOp(StringFunc sf, String s) {
        return sf.func(s);
    }

    public static void main(String[] args) {

        String inStr = "Is lambda a good chose?";
        String outStr;

        System.out.println(inStr);

        outStr = stringOp(new StringFunc() {
            @Override
            public String func(String s) {
                return s.toUpperCase();
            }
        }, inStr);
        System.out.println(outStr);

        outStr = stringOp(str->str.toUpperCase(), inStr);
        //the same - outStr = stringOp(String::toUpperCase, inStr);
        System.out.println(outStr);

        outStr = stringOp(StringTrimmer::trimWhitSpace, inStr);
        System.out.println(outStr);

        //The variable will become final if it will use in the body of the anonymous function and
        // will not be the parameter of that function.
        StringFunc stringFunc = new StringFunc() {
            @Override
            public String func(String s) {
                return s + inStr;
            }
        };

        //the same above
        StringFunc stringFunc1 = str->{
            return str + inStr;
        };

        
    }


}
