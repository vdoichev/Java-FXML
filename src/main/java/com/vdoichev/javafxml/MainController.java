package com.vdoichev.javafxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainController {
    @FXML
    public Button addButton;
    @FXML
    public Label rowsCount;
    @FXML
    public AnchorPane panel4;
    @FXML
    public HBox panel1;
    @FXML
    public Button editButton;
    @FXML
    public Button deleteButton;
    @FXML
    public AnchorPane panel2;
    @FXML
    public TextField searchField;
    @FXML
    public Button searchButton;
    @FXML
    public AnchorPane panel3;
    @FXML
    public TableView tableView;
    @FXML
    public TableColumn column1;
    @FXML
    public TableColumn column2;

    @FXML
    public void onShowDialog(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("edit.fxml")));
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