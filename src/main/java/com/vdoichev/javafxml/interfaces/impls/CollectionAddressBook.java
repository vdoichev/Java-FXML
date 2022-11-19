package com.vdoichev.javafxml.interfaces.impls;

import com.vdoichev.javafxml.interfaces.IAddressBook;
import com.vdoichev.javafxml.objects.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class CollectionAddressBook implements IAddressBook {
    private ObservableList<Person> personList = FXCollections.observableArrayList();

    @Override
    public boolean add(Person person) {
        personList.add(person);
        return true;
    }

    @Override
    public boolean update(Person person) {
        return true;
    }

    @Override
    public boolean delete(Person person) {
        personList.remove(person);
        return true;
    }

    @Override
    public ObservableList<Person> findAll() {
        return null;
    }

    @Override
    public ObservableList<Person> find(String text) {
        return null;
    }

    public ObservableList<Person> getPersonList() {
        return personList;
    }

    public void print() {
        int number = 0;
        for (Person person : personList) {
            number++;
            System.out.println(number + ") fio " + person.getFio() + "; phone = " + person.getPhone());
        }
    }

    public void autoComplete() {
        personList.add(new Person(1,"Дойчев В.И.", "+380762323224"));
        personList.add(new Person(2,"Смоков С.И.", "+380765636542"));
        personList.add(new Person(3,"Степанов В.А.", "+380763452352"));
        personList.add(new Person(4,"Иванов С.С.", "+380762423422"));
    }
}
