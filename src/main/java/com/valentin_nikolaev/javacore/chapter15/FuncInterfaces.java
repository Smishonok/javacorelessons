package com.valentin_nikolaev.javacore.chapter15;

import java.io.PrintWriter;

public class FuncInterfaces {

    interface Numeric {

        int getNum();

    }

    interface Parsable {
        int getInt(String number);
    }

    interface NumericTest {
        boolean test(int number1, int number2);
    }

    interface Printer {
        void print(int n1, int n2);
    }

    interface SomeFunc<T,P>{
        T func(P param);
    }

    public static void main(String[] args) {

        Numeric number = ()->125;

        String numberInStringFormat = "146";

        Parsable parsedInt = Integer::parseInt;

        System.out.println(parsedInt.getInt(numberInStringFormat) + number.getNum());

        NumericTest isFactor = (n1, n2)->(n1 % n2) == 0;

        Printer printer = (n1, n2)->{
            if (isFactor.test(n1, n2)) {
                System.out.println("The number "+n2+" is the factor of "+n1+" number");
            } else {
                System.out.println("The number "+n2+" is not the factor of "+n1+" number");
            }
        };

        int number1 = 10;
        int number2 = 2;

        Runnable function = new Runnable() {
            @Override
            public void run() {
                printer.print(number1,number2);
            }
        };

        Runnable function2 =()->{
            printer.print(number1,number2);
        };

        function.run();

        SomeFunc<Integer, String> parseInt = Integer::parseInt;





    }


}
