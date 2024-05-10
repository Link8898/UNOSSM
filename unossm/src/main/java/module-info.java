module noaharnavrobert.unossm {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens noaharnavrobert.unossm to javafx.fxml;
    exports noaharnavrobert.unossm;
}