package com.vdoichev.javafxml.interfaces.impls;

import com.vdoichev.javafxml.db.MySQLConnection;
import com.vdoichev.javafxml.interfaces.IAddressBook;
import com.vdoichev.javafxml.objects.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DBAddressBook implements IAddressBook {
    private ObservableList<Person> personList = FXCollections.observableArrayList();

    @Override
    public boolean add(Person person) {
        try (Connection con = MySQLConnection.getConnection()) {
            String sql = "INSERT INTO addressbook.person(fio, phone) values (?, ?)";
            PreparedStatement statement = con.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, person.getFio());
            statement.setString(2, person.getPhone());
            int result = statement.executeUpdate();
            if (result > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    person.setId(id);
                    personList.add(person);
                    return true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Person person) {
        throw new UnsupportedOperationException("Not implemented, yet");
    }

    @Override
    public boolean delete(Person person) {
        throw new UnsupportedOperationException("Not implemented, yet");
    }

    @Override
    public ObservableList<Person> findAll() {
        try (Connection con = MySQLConnection.getConnection();
             Statement statement = con.createStatement();
             ResultSet rs = statement.executeQuery("select * from person");) {
            while (rs.next()) {
                Person person = new Person();
                person.setId(rs.getInt("id"));
                person.setFio(rs.getString("fio"));
                person.setPhone(rs.getString("phone"));
                personList.add(person);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return personList;
    }

    @Override
    public ObservableList<Person> find(String text) {
        throw new UnsupportedOperationException("Not implemented, yet");
    }

    public ObservableList<Person> getPersonList() {
        return personList;
    }
}
