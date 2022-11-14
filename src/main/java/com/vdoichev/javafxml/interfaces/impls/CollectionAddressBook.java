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
    public void add(Person person) {
        personList.add(person);
    }

    @Override
    public void edit(Person person) {

    }

    @Override
    public void delete(Person person) {
        personList.remove(person);
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
        personList.add(new Person("Дойчев В.И.", "+380762323224"));
        personList.add(new Person("Смоков С.И.", "+380765636542"));
        personList.add(new Person("Степанов В.А.", "+380763452352"));
        personList.add(new Person("Иванов С.С.", "+380762423422"));
    }
}
