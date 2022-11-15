package com.vdoichev.javafxml;

import com.vdoichev.javafxml.controllers.MainController;
import com.vdoichev.javafxml.interfaces.impls.CollectionAddressBook;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("main.fxml"));
        Parent fxmlMain = fxmlLoader.load();
        MainController mainController = fxmlLoader.getController();
        mainController.setMainStage(stage);

        stage.setTitle("Адресная книга");
        stage.setMinHeight(440);
        stage.setMinWidth(340);
        stage.setScene(new Scene(fxmlMain, 340,440));
        stage.show();

        testData();
    }

    private void testData() {
        CollectionAddressBook addressBook = new CollectionAddressBook();
        addressBook.autoComplete();
        addressBook.print();
    }

    public static void main(String[] args) {
        launch();
    }
}