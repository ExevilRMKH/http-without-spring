package org.example;

import org.apache.log4j.BasicConfigurator;
import org.example.adapters.ServerAdapterImpl;

public class Client {
    public static void main(String[] args) {
        BasicConfigurator.configure();

        var adapter = new ServerAdapterImpl();
        adapter.array();
        adapter.error();
        adapter.single();
    }
}