package com.szkola.todo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class TaskManager {

    private final ObservableList<Task> tasks;
    private final Path filePath;

    public TaskManager(String filename) {
        this.tasks = FXCollections.observableArrayList();
        this.filePath = Paths.get(filename);
    }

    public ObservableList<Task> getTasks() {
        return tasks;
    }

    public void addTask(String description) {
        if (description != null && !description.trim().isEmpty()) {
            tasks.add(new Task(description));
        }
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public void loadTasksFromFile()  {
        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            Files.lines(filePath)
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .forEach(this::addTask);
        } catch (IOException e) {
            System.err.println("Błąd podczas wczytywania zadań: " + e.getMessage());
        }
    }

    public void saveTasksToFile() {
        java.util.List<String> taskDescriptions = tasks.stream()
                .map(Task::description)
                .collect(Collectors.toList());
        try {
            Files.write(filePath, taskDescriptions);
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisywania zadań: " + e.getMessage());
        }
    }
}