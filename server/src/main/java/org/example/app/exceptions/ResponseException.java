package org.example.app.exceptions;


public record ResponseException(int code, String message, String date) {}
