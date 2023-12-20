package org.example.domain;


import java.io.Serializable;


public record Message(String body, String auth) implements Serializable {}