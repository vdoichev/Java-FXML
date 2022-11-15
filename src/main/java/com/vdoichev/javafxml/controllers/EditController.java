package com.vdoichev.javafxml.controllers;

import com.vdoichev.javafxml.objects.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditController {
    @FXML
    public Label lblPhone;
    @FXML
    public Label lblName;
    @FXML
    public TextField txtPhone;
    @FXML
    public TextField txtName;
    @FXML
    public Button btnConfirm;
    @FXML
    public Button btnCancel;

    private Person person;

    @FXML
    public void actionCancel(ActionEvent actionEvent) {
        Node sourse = (Node) actionEvent.getSource();
        Stage stage = (Stage) sourse.getScene().getWindow();
        stage.hide();
    }

    public void setPerson(Person person) {
        if (person == null) {
            return;
        }
        this.person = person;
        txtName.setText(person.getFio());
        txtPhone.setText(person.getPhone());
    }

    @FXML
    public void actionSave(ActionEvent actionEvent) {
        person.setFio(txtName.getText());
        person.setPhone(txtPhone.getText());
        actionCancel(actionEvent);
    }

    public Person getPerson() {
        return person;
    }
}
