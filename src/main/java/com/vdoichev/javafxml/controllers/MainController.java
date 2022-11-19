package com.vdoichev.javafxml.controllers;

import com.vdoichev.javafxml.HelloApplication;
import com.vdoichev.javafxml.interfaces.impls.CollectionAddressBook;
import com.vdoichev.javafxml.interfaces.impls.DBAddressBook;
import com.vdoichev.javafxml.objects.Lang;
import com.vdoichev.javafxml.objects.Person;
import com.vdoichev.javafxml.utils.DialogManager;
import com.vdoichev.javafxml.utils.LocaleManager;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import java.util.Observable;
import java.util.ResourceBundle;

import static com.vdoichev.javafxml.HelloApplication.BUNDLES_FOLDER;

public class MainController extends Observable implements Initializable {
    private static final String FXML_EDIT = "edit.fxml";
    //private final CollectionAddressBook addressBook = new CollectionAddressBook();
    private final DBAddressBook addressBook = new DBAddressBook();
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
    @FXML
    public ComboBox<Lang> comboLocales;
    private final FXMLLoader fxmlLoader = new FXMLLoader();
    private Parent fxmlEdit;
    private EditController editDialogController;
    private Stage editDialogStage;
    private ResourceBundle resourceBundle;
    //private ObservableList<Person> backupList;
    private static final String RU_CODE = "ru";
    private static final String EN_CODE = "en";
    private static final String UK_CODE = "uk";

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

        comboLocales.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Lang selectedLang = (Lang) comboLocales.getSelectionModel().getSelectedItem();
                LocaleManager.setCurrentLang(selectedLang);
                setChanged();
                notifyObservers(selectedLang);
            }
        });
    }

    private void initLoader() {
        try {
            fxmlLoader.setLocation(HelloApplication.class.getResource(FXML_EDIT));
            fxmlLoader.setResources(ResourceBundle.getBundle(BUNDLES_FOLDER,
                    LocaleManager.getCurrentLang().getLocale()));
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
        if (selectedPerson == null) {
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

        setupClearButtonField(txtSearch);
        initListeners();

        //addressBook.autoComplete();
        ObservableList<Person> backupList = addressBook.findAll();
        tblAddress.setItems(backupList);
        fillLangComboBox();
        initLoader();


    }

    private void setupClearButtonField(CustomTextField customTextField) {
//        try {
//            Method m = TextFields.class.getDeclaredMethod("setupClearButtonField",
//                    TextField.class, ObjectProperty.class);
//            m.setAccessible(true);
//            m.invoke(null, customTextField, customTextField.rightProperty());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @FXML
    public void actionSearch(ActionEvent actionEvent) {

        if (txtSearch.getText().trim().length() == 0) {
            addressBook.findAll();
        }

        addressBook.find(txtSearch.getText());
    }

    private void fillLangComboBox() {
        Lang langUK = new Lang(0, UK_CODE, resourceBundle.getString("key.uk"), LocaleManager.UK_LOCALE);
        Lang langRU = new Lang(1, RU_CODE, resourceBundle.getString("key.ru"), LocaleManager.RU_LOCALE);
        Lang langEN = new Lang(2, EN_CODE, resourceBundle.getString("key.en"), LocaleManager.EN_LOCALE);

        comboLocales.getItems().add(langUK);
        comboLocales.getItems().add(langRU);
        comboLocales.getItems().add(langEN);

        if (LocaleManager.getCurrentLang() == null) {
            LocaleManager.setCurrentLang(langUK);
            comboLocales.getSelectionModel().select(0);
        } else {
            comboLocales.getSelectionModel().select(LocaleManager.getCurrentLang().getIndex());
        }
    }
}