package com.company;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Collection<Person> populationList = createPopulationCensus();
        printPopulationCensusResult(populationList);
    }

    private static void printPopulationCensusResult(Collection<Person> populationList) {
        long underagePersonsCount = underagePersonsCount(populationList);
        List<String> conscriptsPerson = findConscriptsPerson(populationList);
        List<String> possibleWorkMan = findPossibleWorkersMan(populationList);
        List<String> possibleWorkWoman = findPossibleWorkersWoman(populationList);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        System.out.println("Перепись населения: " + formatter.format(date)
                + "\nКоличество несовершеннолетних: " + underagePersonsCount
                + "\nКоличество призывников: " + conscriptsPerson.size()
                + "\nКоличество потенциально работоспособных мужчин: " + possibleWorkMan.size()
                + ", женщин: " + possibleWorkWoman.size()
                + "\nСписок фамилий призывников можно получить с помощью метода findConscriptsPerson,"
                + "\nСписок фамилий работоспособных можно получить с помощью методов possibleWorkMan, possibleWorkWoman");
    }

    private static Collection<Person> createPopulationCensus() {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        return persons;
    }

    private static List<String> findPossibleWorkersMan(Collection<Person> persons) {
        List<String> conscriptsPerson = persons.stream()
                .filter(person -> person.getEducation() == Education.valueOf("HIGHER"))
                .filter(person -> person.getAge() > 17 && person.getAge() < 66)
                .filter(person -> person.getSex() == Sex.valueOf("MAN"))
                .map(Person::getFamily)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
        return conscriptsPerson;
    }

    private static List<String> findPossibleWorkersWoman(Collection<Person> persons) {
        List<String> conscriptsPerson = persons.stream()
                .filter(person -> person.getEducation() == Education.valueOf("HIGHER"))
                .filter(person -> person.getAge() > 17 && person.getAge() < 61)
                .filter(person -> person.getSex() == Sex.valueOf("WOMAN"))
                .map(Person::getFamily)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
        return conscriptsPerson;
    }

    public static List<String> findConscriptsPerson(Collection<Person> persons) {
        List<String> conscriptsFamily = persons.stream()
                .filter(person -> person.getAge() > 17 && person.getAge() < 27)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        return conscriptsFamily;
    }


    public static long underagePersonsCount(Collection<Person> persons) {
        long numberOfUnderage = persons.stream()
                .filter(person -> person.getAge() < 19)
                .count();
        return numberOfUnderage;
    }
}