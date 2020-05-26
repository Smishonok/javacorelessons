package com.valentin_nikolaev.javacore.chapter15;

interface GenericInterface<T> {
    boolean func(T v1, T v2);
}

public class WeatherSearcher {
    int maxTemp;

    public WeatherSearcher(int maxTemperature) {
        this.maxTemp = maxTemperature;
    }

    public boolean isTheSame(WeatherSearcher weatherSearcher) {
        return this.maxTemp == weatherSearcher.maxTemp;
    }

    public boolean isLessThen(WeatherSearcher weatherSearcher) {
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

        WeatherSearcher[] weatherData = {new WeatherSearcher(83), new WeatherSearcher(42),
                new WeatherSearcher(58), new WeatherSearcher(83), new WeatherSearcher(71),
                new WeatherSearcher(64)};

        int count;
        count = counter(weatherData, WeatherSearcher::isLessThen, new WeatherSearcher(64));


    }

}
