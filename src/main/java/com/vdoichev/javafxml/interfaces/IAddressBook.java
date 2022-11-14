package com.vdoichev.javafxml.interfaces;

import com.vdoichev.javafxml.objects.Person;

public interface IAddressBook {
    void add(Person person);

    void edit(Person person);

    void delete(Person person);
}
