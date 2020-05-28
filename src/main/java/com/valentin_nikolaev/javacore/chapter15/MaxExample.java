package com.valentin_nikolaev.javacore.chapter15;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age  = age;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    int compareByName(Person another) {
        return this.getName().length() - another.getName().length();
    }
}

class PersonComparator {
    static int compareByAge(Person p1, Person p2) {
        return p1.getAge() - p2.getAge();
    }
}

public class MaxExample {

    public static void main(String[] args) {

        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Valentin", 34));
        persons.add(new Person("Svetlana", 45));
        persons.add(new Person("Vsevolod", 18));
        persons.add(new Person("Igor", 55));

        Person oldestPerson = Collections.max(persons, PersonComparator::compareByAge);
        System.out.println("The oldest person is: "+oldestPerson.getName());

        Person youngestPerson = Collections.min(persons, (p1, p2)->p1.getAge() - p2.getAge());
        System.out.println("The youngest person is: "+youngestPerson.getName());

        Person withShortestName = Collections.min(persons, Person::compareByName);
        System.out.println("The person with shortest name is: "+withShortestName.getName());
    }

}
