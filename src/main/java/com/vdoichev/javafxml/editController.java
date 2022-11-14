package com.vdoichev.javafxml;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class editController {
    @FXML
    public VBox leftPanel;
    @FXML
    public Label labelPhone;
    @FXML
    public Label labelName;
    @FXML
    public VBox centerPanel;
    @FXML
    public TextField textPhone;
    @FXML
    public TextField textName;
    @FXML
    public VBox rightPanel;
    @FXML
    public Button okButton;
    @FXML
    public Button cancelButton;
}
