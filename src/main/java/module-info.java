module org.example.testjavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens org.example.testjavafx to javafx.fxml;
    exports org.example.testjavafx;
}