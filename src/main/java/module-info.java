module org.default.unossm {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.default.unossm to javafx.fxml;
    exports org.default.unossm;
}