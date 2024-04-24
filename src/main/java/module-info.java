module com.example.olamitdbfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.olamitdbfx to javafx.fxml;
    exports com.example.olamitdbfx;
    exports com.example.olamitdbfx.Classes;
}