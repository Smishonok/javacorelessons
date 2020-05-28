package com.valentin_nikolaev.javacore.chapter15;

import java.util.*;
import java.util.function.Supplier;

public class ReferenceToConstructor {

    private static <T, SOURCE extends Collection<T>, DESTINATION extends Collection<T>> DESTINATION transferCollection(
            SOURCE sourceCollection, Supplier<DESTINATION> collectionFactory) {

        DESTINATION result = collectionFactory.get();
        for (T element : sourceCollection) {
            result.add(element);
        }

        return result;
    }

    public static void main(String[] args) {

        List<String> names = new ArrayList<>();
        names.add("Veronika");
        names.add("Igor");
        names.add("Sevastian");
        names.add("Gregory");
        names.add("Igor");
        names.add("Veronika");

        Set<String> uniqNames = ReferenceToConstructor.transferCollection(names, HashSet::new);
        for (String name : uniqNames) {
            System.out.println(name);
        }
    }

}
