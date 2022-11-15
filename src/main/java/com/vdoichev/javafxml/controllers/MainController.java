package com.vdoichev.javafxml.controllers;

import com.vdoichev.javafxml.HelloApplication;
import com.vdoichev.javafxml.interfaces.impls.CollectionAddressBook;
import com.vdoichev.javafxml.objects.Person;
import com.vdoichev.javafxml.utils.DialogManager;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.lang.reflect.Method;
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
    public CustomTextField txtSearch;
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
    private ObservableList<Person> backupList;

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    private void initListeners() {
        addressBook.getPersonList().addListener((ListChangeListener) (c) -> {
            updateCountLabel();
        });

        tblAddress.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                editDialogController.setPerson(tblAddress.getSelectionModel().getSelectedItem());
                showDialog();
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
        lblRowsCount.setText(resourceBundle.getString("key.count") + ": " + addressBook.getPersonList().size());
    }

    @FXML
    public void onShowDialog(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (!(source instanceof Button clickedButton)) {
            return;
        }

        Person selectedPerson = tblAddress.getSelectionModel().getSelectedItem();
        editDialogController.setPerson(selectedPerson);

        switch (clickedButton.getId()) {
            case "btnAdd" -> {
                editDialogController.setPerson(new Person());
                showDialog();
                addressBook.add(editDialogController.getPerson());
            }
            case "btnEdit" -> {
                if (!personIsSelected(selectedPerson)) {
                    return;
                }
                editDialogController.setPerson(tblAddress.getSelectionModel().getSelectedItem());
                showDialog();
            }
            case "btnDelete" -> {
                if (!personIsSelected(selectedPerson)) {
                    return;
                }
                addressBook.delete(tblAddress.getSelectionModel().getSelectedItem());
            }
        }
    }

    private boolean personIsSelected(Person selectedPerson) {
        if (selectedPerson == null){
            DialogManager.showErrorDialog(resourceBundle.getString("key.error"),
                    resourceBundle.getString("key.select.person"));
            return false;
        }
        return true;
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
        clmnName.setCellValueFactory(new PropertyValueFactory<>("fio"));
        clmnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        initListeners();

        addressBook.autoComplete();
        backupList = FXCollections.observableArrayList();
        backupList.addAll(addressBook.getPersonList());
        tblAddress.setItems(addressBook.getPersonList());

        initLoader();
        setupClearButtonField(txtSearch);
    }

    private void setupClearButtonField(CustomTextField customTextField) {
        try {
            Method m = TextFields.class.getDeclaredMethod("setupClearButtonField",
                    TextField.class, ObjectProperty.class);
            m.setAccessible(true);
            m.invoke(null,customTextField,customTextField.rightProperty());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void actionSearch(ActionEvent actionEvent) {
        addressBook.getPersonList().clear();
        for (Person person : backupList) {
            if (person.getFio().toLowerCase().contains(txtSearch.getText().toLowerCase()) ||
                    (person.getPhone().toLowerCase().contains(txtSearch.getText().toLowerCase()))
            ) {
                addressBook.getPersonList().add(person);
            }
        }
    }
}