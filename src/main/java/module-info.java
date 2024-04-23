module noaharnavrobert.unossm {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens noaharnavrobert.unossm to javafx.fxml;
    exports noaharnavrobert.unossm;
}