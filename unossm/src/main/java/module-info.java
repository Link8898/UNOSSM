module noaharnavrobert.unossm {
    requires javafx.controls;
    requires javafx.fxml;


    opens noaharnavrobert.unossm to javafx.fxml;
    exports noaharnavrobert.unossm;
}