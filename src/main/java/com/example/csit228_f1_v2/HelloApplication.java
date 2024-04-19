package com.example.csit228_f1_v2;

import com.example.csit228_f1_v2.MySql.MySqlConnection;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        Text txtWelcome = new Text("Welcome to CIT");
        txtWelcome.setFont(Font.font("Chiller", FontWeight.EXTRA_BOLD, 69));
        txtWelcome.setFill(Color.RED);
        grid.setPadding(new Insets(20));
        txtWelcome.setTextAlignment(TextAlignment.CENTER);
        grid.add(txtWelcome, 0, 0, 3, 1);

        Label lbUsername = new Label("Username: ");
        lbUsername.setTextFill(Color.LIGHTSKYBLUE);
        lbUsername.setFont(Font.font(30));
        grid.add(lbUsername, 0, 1);

        TextField tfUsername = new TextField();
        grid.add(tfUsername, 1, 1);
        tfUsername.setFont(Font.font(30));
//        tfUsername.setMaxWidth(150);

        Label lbPassword = new Label("Email");
        lbPassword.setFont(Font.font(30));
        lbPassword.setTextFill(Color.CHARTREUSE);
        grid.add(lbPassword, 0, 2);

        TextField tfPassword = new TextField();
        grid.add(tfPassword, 1, 1);
        tfUsername.setFont(Font.font(30));

        Button btnLogin = new Button("Log In");
        btnLogin.setFont(Font.font(40));
        grid.add(btnLogin, 0, 3, 2, 1);
        Button btnRegister = new Button("Register");
        btnRegister.setFont(Font.font(40));
        grid.add(btnRegister, 1, 3, 2, 1);

        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Parent p = FXMLLoader.load(getClass().getResource("homepage.fxml"));
                    Scene s = new Scene(p);
                    stage.setScene(s);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnRegister.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try(Connection c = MySqlConnection.getConnection();
                    PreparedStatement statement = c.prepareStatement(
                            "INSERT INTO users (name, email) VALUE (?,?)"
                    )){
                    String name = tfUsername.getText();
                    String email = tfPassword.getText();
                    statement.setString(1, name);
                    statement.setString(2, email);
                    int row = statement.executeUpdate();
                    System.out.println("Row: " + row + " inserted successfully!!!!!!!!!!!!");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        Scene scene = new Scene(grid, 700, 500, Color.WHEAT);
        stage.setScene(scene);
        scene.setFill(Color.CORNFLOWERBLUE);
        stage.show();
        txtWelcome.minWidth(grid.getWidth());
    }



    public static void main(String[] args) {
        launch();
    }
}