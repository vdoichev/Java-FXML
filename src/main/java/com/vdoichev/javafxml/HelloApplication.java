package com.vdoichev.javafxml;

import com.vdoichev.javafxml.interfaces.impls.CollectionAddressBook;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 340, 440);
        //scene.getStylesheets().add(HelloApplication.class.getResource("css/my.css").toExternalForm());
        stage.setTitle("Hello!");
        stage.setMinHeight(440);
        stage.setMinWidth(340);
        stage.setScene(scene);
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