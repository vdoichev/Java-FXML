module com.vdoichev.javafxml {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens com.vdoichev.javafxml to javafx.fxml;
    exports com.vdoichev.javafxml;
}