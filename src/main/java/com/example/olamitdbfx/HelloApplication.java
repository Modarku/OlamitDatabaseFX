package com.example.olamitdbfx;

import com.example.olamitdbfx.Classes.AlertController;
import com.example.olamitdbfx.Classes.MySqlConnection;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    AlertController alrt;
    @Override
    public void start(Stage stage) throws IOException {

        MySqlConnection.getConnection();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        Text txtWelcome = new Text("The Libary");
        txtWelcome.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 50));
        txtWelcome.setTextAlignment(TextAlignment.CENTER);
        grid.add(txtWelcome, 0, 0, 3, 1);

        Label lbUsername = new Label("Username: ");
        lbUsername.setFont(Font.font(20));
        grid.add(lbUsername, 0, 1);

        TextField tfUsername = new TextField();
        grid.add(tfUsername, 1, 1);
        tfUsername.setFont(Font.font(20));
        tfUsername.setMaxWidth(300);

        Label lbEmail = new Label("Email: ");
        lbEmail.setFont(Font.font(20));
        grid.add(lbEmail, 0, 2);

        TextField tfEmail = new TextField();
        grid.add(tfEmail, 1, 2);
        tfEmail.setFont(Font.font(20));
        tfEmail.setMaxWidth(300);

        Button btnLogin = new Button("Log In");
        btnLogin.setFont(Font.font(20));
        grid.add(btnLogin, 0, 3, 2, 1);
        Button btnRegister = new Button("Register");
        btnRegister.setFont(Font.font(20));
        grid.add(btnRegister, 1, 3, 2, 1);

        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = tfUsername.getText();
                String email = tfEmail.getText();
                if(MySqlConnection.searchUserTable(name, email)) proceed(stage);
                else {
                    Alert loginError = new Alert(Alert.AlertType.ERROR);
                    alrt = new AlertController(loginError, "Login Error", "Invalid login credentials");
                    System.out.println("Invalid login credentials");
                }
            }
        });

        btnRegister.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = tfUsername.getText();
                String email = tfEmail.getText();
                MySqlConnection.insertUsersTable(name, email);
            }
        });

        Scene scene = new Scene(grid, 1400, 800);
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.show();
        txtWelcome.minWidth(grid.getWidth());
    }

    public void proceed(Stage stage){
        try {
            Parent p = FXMLLoader.load(getClass().getResource("homepage.fxml"));
            Scene s = new Scene(p);
            stage.setScene(s);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}