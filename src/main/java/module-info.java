module com.pvetec.inspectra {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires cn.hutool;
    requires org.slf4j;
    requires lombok;
    requires usb.api;

    opens com.pvetec.inspectra to javafx.fxml;
    opens com.pvetec.inspectra.controller to javafx.fxml;

    exports com.pvetec.inspectra;
    exports com.pvetec.inspectra.controller to javafx.fxml;
}
