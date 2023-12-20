package org.example.config.exceptions;


public record ResponseException(int code, String message, String date) {}
