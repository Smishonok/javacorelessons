package com.valentin_nikolaev.javacore.chapter15;

interface MyFunc<T>{
    int func(T[] vals, T v);
}

class MyArrayOps{
    public static <T> int countMatching(T[] vals, T v) {
        int result = 0;

        for (T el : vals) {
            if (el == v) {
                result++;
            }
        }

        return result;
    }
}

public class GenericInFuncInterface {

    static <T> int myOp(MyFunc<T> f, T[] vals, T v) {
        return f.func(vals, v);
    }

    public static void main(String[] args) {

        Integer[] intArray = {3, 4, 5, 3, 2, 6, 4, 5, 3, 2, 2, 3, 4};
        String[] stringArray = {"One", "Two", "One", "Three", "Four", "Two"};

        int matching = myOp(MyArrayOps::countMatching, intArray, 4);
        int marching2 = myOp(MyArrayOps::countMatching, stringArray, "Two");
    }
}
