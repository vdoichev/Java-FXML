package com.vdoichev.javafxml;

import com.vdoichev.javafxml.controllers.MainController;
import com.vdoichev.javafxml.interfaces.impls.CollectionAddressBook;
import com.vdoichev.javafxml.objects.Lang;
import com.vdoichev.javafxml.utils.LocaleManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class HelloApplication extends Application implements Observer {
    private static final String FXML_MAIN = "main.fxml";
    public static final String BUNDLES_FOLDER = "com.vdoichev.javafxml.Locale";

    private Stage primaryStage;
    private  Parent fxmlMain;
    private MainController mainController;
    private FXMLLoader fxmlLoader;
    private VBox currentRoot;
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        createGUI(LocaleManager.RU_LOCALE);
    }

    private void createGUI(Locale locale) {
        currentRoot = loadFXML(locale);
        Scene scene = new Scene(currentRoot,340,440);
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(440);
        primaryStage.setMinWidth(340);
        primaryStage.show();
    }

    private void testData() {
        CollectionAddressBook addressBook = new CollectionAddressBook();
        addressBook.autoComplete();
        addressBook.print();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void update(Observable o, Object arg) {
        Lang lang = (Lang) arg;
        VBox newMode = loadFXML(lang.getLocale());
        currentRoot.getChildren().setAll(newMode.getChildren());
    }

    private VBox loadFXML(Locale locale){
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(FXML_MAIN));
        fxmlLoader.setResources(ResourceBundle.getBundle(BUNDLES_FOLDER, locale));

        VBox node = null;

        try {
            node = (VBox) fxmlLoader.load();
            mainController = fxmlLoader.getController();
            mainController.addObserver(this);
            primaryStage.setTitle(fxmlLoader.getResources().getString("key.address.book"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return node;
    }
}