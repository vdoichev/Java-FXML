package com.vdoichev.javafxml.interfaces;

import com.vdoichev.javafxml.objects.Person;
import javafx.collections.ObservableList;

public interface IAddressBook {
    boolean add(Person person);

    boolean update(Person person);

    boolean delete(Person person);

    ObservableList<Person> findAll();
    ObservableList<Person> find(String text);
}
