package com.vdoichev.javafxml.objects;

import javafx.beans.property.SimpleStringProperty;

public class Person {
    private SimpleStringProperty fio = new SimpleStringProperty("");
    private SimpleStringProperty phone = new SimpleStringProperty("");

    public Person(){

    }
    public Person(String name, String phone) {
        this.fio = new SimpleStringProperty(name);
        this.phone = new SimpleStringProperty(phone);
    }

    public String getFio() {
        return fio.get();
    }

    public void setFio(String fio) {
        this.fio.set(fio);
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    @Override
    public String toString() {
        return "Person{" + "fio='" + fio + '\'' + ", phone='" + phone + '\'' + '}';
    }

    public SimpleStringProperty fioProperty() {
        return fio;
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }
}
