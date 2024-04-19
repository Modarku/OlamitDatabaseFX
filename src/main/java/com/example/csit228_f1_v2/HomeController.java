package com.example.csit228_f1_v2;

import com.example.csit228_f1_v2.MySql.MySqlConnection;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeController {

    public ToggleButton tbNight;
    public ProgressIndicator piProgress;
    public Slider slSlider;
    public ProgressBar pbProgress;
    public Button btnShowTableClick;
    public TextArea txtTable;
    public void onShowTableClick() {
        try(Connection c = MySqlConnection.getConnection();
            PreparedStatement statement = c.prepareStatement("SELECT * FROM users")){
            ResultSet s = statement.executeQuery();
            StringBuilder str = new StringBuilder();
            while(s.next()){
                int id = s.getInt("id");
                String name = s.getString("name");
                String email = s.getString("email");
                System.out.println("id: " + id + "\nname: " + name + "\nemail: " + email);
                str.append("id: " + id + "\nname: " + name + "\nemail: " + email + "\n");
            }
            System.out.println("Data read successfully!!!!!!!!!!!!!!!!!!");
            txtTable.setText(str.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void onSliderChange() {
        double val = slSlider.getValue();
        piProgress.setProgress(val/100);
        pbProgress.setProgress(val/100);
    }

    public void onNightModeClick() {
        if (tbNight.isSelected()) {
            tbNight.getParent().setStyle("-fx-background-color: DARKSLATEBLUE");
            tbNight.setText("DAY");
        } else {
            tbNight.getParent().setStyle("-fx-background-color: WHITE");
            tbNight.setText("NIGHT");
        }
    }
}
