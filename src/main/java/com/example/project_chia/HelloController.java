package com.example.project_chia;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloController {
    public Label welcomeText1;
    String windows_username = System.getProperty("user.name");
    @FXML
    private Label welcomeText;
    @FXML
    private TextField add_a_path_textfield;


    @FXML
    protected void onHelloButtonClick() throws IOException {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void removealine() throws IOException {
        new commands("RemoveALine", null);

    }

    @FXML
    protected void findPaths() throws IOException {
        new commands("findPaths", null);
    }

    @FXML
    protected void checkPlots() throws IOException {
        new commands("checkPlots", null);


    }

    @FXML
    protected void add_a_path() throws IOException {
        add_a_path_textfield.setVisible(true);
        new commands("add_a_path", add_a_path_textfield.getText());
    }


    @FXML
    protected void Show_Keys() throws IOException {
        new commands("Show_Keys", null);
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