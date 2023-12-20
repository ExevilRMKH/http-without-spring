package org.example;

import com.sun.net.httpserver.HttpServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.example.domain.Postman;
import org.example.domain.PostmanImpl;
import org.example.ports.messages.MessageHandlers;
import org.example.repository.MessageRepository;
import org.example.repository.MessageRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

@Slf4j
public class Server {
    public static final MessageRepository repository = new MessageRepositoryImpl();
    public static final Postman postman = new PostmanImpl(repository);
    public static final MessageHandlers messageHandler = new MessageHandlers(postman);

    private static void server() throws IOException {
        var server = HttpServer
                .create(new InetSocketAddress("localhost", 8081), 0)
                .createContext("/message", messageHandler)
                .getServer();
        server.setExecutor(Executors.newSingleThreadExecutor());
        server.start();
        log.info(" Server started on port 8001");
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        server();
    }
}