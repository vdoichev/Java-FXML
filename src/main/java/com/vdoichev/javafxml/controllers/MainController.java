package com.vdoichev.javafxml.controllers;

import com.vdoichev.javafxml.HelloApplication;
import com.vdoichev.javafxml.interfaces.impls.CollectionAddressBook;
import com.vdoichev.javafxml.objects.Person;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    public Button btnAdd;
    @FXML
    public Label lblRowsCount;
    @FXML
    public Button btnEdit;
    @FXML
    public Button btnDelete;
    @FXML
    public TextField txtSearch;
    @FXML
    public Button btnSearch;
    @FXML
    public TableView<Person> tblAddress;
    @FXML
    public TableColumn<Person, String> clmnName;
    @FXML
    public TableColumn<Person, String> clmnPhone;

    private final CollectionAddressBook addressBook = new CollectionAddressBook();

    @FXML
    private void initialize() {
        clmnName.setCellValueFactory(new PropertyValueFactory<Person, String>("fio"));
        clmnPhone.setCellValueFactory(new PropertyValueFactory<Person, String>("phone"));

        addressBook.getPersonList().addListener((ListChangeListener) (c) ->{
            updateCountLabel();
        });
        addressBook.autoComplete();
        tblAddress.setItems(addressBook.getPersonList());
    }

    private void updateCountLabel() {
        lblRowsCount.setText("Количество записей: " + addressBook.getPersonList().size());
    }

    @FXML
    public void onShowDialog(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(HelloApplication.class.getResource("edit.fxml"));
            stage.setTitle("Редактирование записи");
            stage.setMinHeight(150);
            stage.setMinWidth(300);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}