package com.szkola.todo;

public record Task(String description) {

    @Override
    public String toString() {
        return description;
    }
}