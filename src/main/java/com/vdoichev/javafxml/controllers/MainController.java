package com.vdoichev.javafxml.controllers;

import com.vdoichev.javafxml.HelloApplication;
import com.vdoichev.javafxml.interfaces.impls.CollectionAddressBook;
import com.vdoichev.javafxml.objects.Person;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private final CollectionAddressBook addressBook = new CollectionAddressBook();
    private Stage mainStage;
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
    private final FXMLLoader fxmlLoader = new FXMLLoader();
    private Parent fxmlEdit;
    private EditController editDialogController;
    private Stage editDialogStage;
    private ResourceBundle resourceBundle;

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    private void initListeners() {
        addressBook.getPersonList().addListener((ListChangeListener) (c) -> {
            updateCountLabel();
        });

        tblAddress.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    editDialogController.setPerson((Person) tblAddress.getSelectionModel().getSelectedItem());
                    showDialog();
                }
            }
        });
    }

    private void initLoader() {
        try {
            fxmlLoader.setLocation(HelloApplication.class.getResource("edit.fxml"));
            fxmlLoader.setResources(ResourceBundle.getBundle("com.vdoichev.javafxml.Locale",
                    new Locale("uk")));
            fxmlEdit = fxmlLoader.load();
            editDialogController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateCountLabel() {
        lblRowsCount.setText(resourceBundle.getString("key.count")+": " + addressBook.getPersonList().size());
    }

    @FXML
    public void onShowDialog(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (!(source instanceof Button)) {
            return;
        }

        Button clickedButton = (Button) source;
        Person selectedPerson = (Person) tblAddress.getSelectionModel().getSelectedItem();
        editDialogController.setPerson(selectedPerson);

        switch (clickedButton.getId()) {
            case "btnAdd":
                editDialogController.setPerson(new Person());
                showDialog();
                addressBook.add(editDialogController.getPerson());
                break;
            case "btnEdit":
                editDialogController.setPerson((Person) tblAddress.getSelectionModel().getSelectedItem());
                showDialog();
                break;
            case "btnDelete":
                addressBook.delete((Person) tblAddress.getSelectionModel().getSelectedItem());
                break;
        }
    }

    private void showDialog() {
        if (editDialogStage == null) {
            editDialogStage = new Stage();
            editDialogStage.setTitle(resourceBundle.getString("key.titleEdit"));
            editDialogStage.setMinHeight(150);
            editDialogStage.setMinWidth(300);
            editDialogStage.setResizable(false);
            editDialogStage.setScene(new Scene(fxmlEdit));
            editDialogStage.initModality(Modality.WINDOW_MODAL);
            editDialogStage.initOwner(mainStage);
        }
        editDialogStage.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        clmnName.setCellValueFactory(new PropertyValueFactory<Person, String>("fio"));
        clmnPhone.setCellValueFactory(new PropertyValueFactory<Person, String>("phone"));

        initListeners();

        addressBook.autoComplete();
        tblAddress.setItems(addressBook.getPersonList());

        initLoader();
    }
}