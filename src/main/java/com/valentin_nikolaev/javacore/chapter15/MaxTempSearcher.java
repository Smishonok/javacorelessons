package com.valentin_nikolaev.javacore.chapter15;

interface GenericInterface<T> {
    boolean func(T v1, T v2);
}

public class MaxTempSearcher {
    int maxTemp;

    public MaxTempSearcher(int maxTemperature) {
        this.maxTemp = maxTemperature;
    }

    public boolean isTheSame(MaxTempSearcher weatherSearcher) {
        return this.maxTemp == weatherSearcher.maxTemp;
    }

    public boolean isLessThen(MaxTempSearcher weatherSearcher) {
        return this.maxTemp < weatherSearcher.maxTemp;
    }

}

class WeatherSearcher2 {

    static <T> int counter(T[] array, GenericInterface<T> f, T value) {
        int result = 0;

        for (T av : array) {
            if (f.func(av, value)) {
                result++;
            }
        }
        return result;
    }

    public static void main(String[] args) {

        MaxTempSearcher[] weatherData = {new MaxTempSearcher(83), new MaxTempSearcher(42),
                new MaxTempSearcher(58), new MaxTempSearcher(83), new MaxTempSearcher(71),
                new MaxTempSearcher(64)};

        int count;
        count = counter(weatherData, MaxTempSearcher::isLessThen, new MaxTempSearcher(64));


    }

}
