package com.example.project_chia;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloController {
    String windows_username = System.getProperty("user.name");
    @FXML
    private Label welcomeText;
    @FXML
    private TextField add_a_path_textfield;
    @FXML
    private TextField Check_Number;
    @FXML
    private ListView doneList;







    @FXML
    protected void onHelloButtonClick() throws IOException {
        welcomeText.setText("Welcome to JavaFX Application!");
    }



    @FXML
    protected void removealine() throws IOException {
        new com.example.project_chia.commands("RemoveALine", null);

    }
    @FXML
    protected void clear_AddtoParhText() throws IOException {
        add_a_path_textfield.setText("");
    }

    @FXML
    protected void findPaths() throws IOException {
        new com.example.project_chia.commands("findPaths", null);
    }

    @FXML
    protected void checkPlots() throws IOException {
        new com.example.project_chia.commands("checkPlots", Check_Number.getText());
    }

    @FXML
    protected void add_a_path() throws IOException {
        add_a_path_textfield.setVisible(true);
        new com.example.project_chia.commands("add_a_path", add_a_path_textfield.getText());
    }


    @FXML
    protected void Show_Keys() throws IOException {
        new com.example.project_chia.commands("Show_Keys", null);
    }
    private Stage stage;
    private Scene scene;
    @FXML
    protected void passphrase(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("passphrase_Page.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}