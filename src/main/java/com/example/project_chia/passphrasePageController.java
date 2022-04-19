package com.example.project_chia;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class passphrasePageController {
    @FXML
    private TextField passphraseTextField;
    private Stage stage;
    private Scene scene;
    @FXML
    protected void savePassphrase(ActionEvent event) throws IOException {
        new com.example.project_chia.commands("savePassphrase", passphraseTextField.getText());
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
