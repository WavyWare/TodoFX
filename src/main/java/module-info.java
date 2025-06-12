module com.szkola.todo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.szkola.todo to javafx.fxml;
    exports com.szkola.todo;
}