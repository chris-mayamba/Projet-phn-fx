module org.example.projet_phn_fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.sql;


    opens org.example.projet_phn_fx to javafx.fxml;
    exports org.example.projet_phn_fx;
}