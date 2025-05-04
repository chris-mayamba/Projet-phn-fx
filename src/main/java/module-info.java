module org.example.projet_phn_fx {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.projet_phn_fx to javafx.fxml;
    exports org.example.projet_phn_fx;
}