package com.szkola.todo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TaskApp extends Application {

    private final TaskManager taskManager = new TaskManager("tasks.txt");

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("todoFX");

        ListView<Task> taskListView = new ListView<>();
        taskListView.setItems(taskManager.getTasks());

        TextField taskInputField = new TextField();
        taskInputField.setPromptText("Wpisz nowe zadanie...");

        Button addButton = new Button("Dodaj");
        Button removeButton = new Button("Usuń");

        HBox buttonBox = new HBox(10, addButton, removeButton);

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(taskListView, taskInputField, buttonBox);
        VBox.setVgrow(taskListView, Priority.ALWAYS);

        addButton.setOnAction(_ -> {
            taskManager.addTask(taskInputField.getText());
            taskInputField.clear();
        });

        taskInputField.setOnAction(_ -> {
            taskManager.addTask(taskInputField.getText());
            taskInputField.clear();
        });

        removeButton.setOnAction(_ -> {
            Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                taskManager.removeTask(selectedTask);
            }
        });

        taskManager.loadTasksFromFile();

        primaryStage.setOnCloseRequest(_ -> taskManager.saveTasksToFile());

        Scene scene = new Scene(root, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}