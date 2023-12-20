package org.example.adapters;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Slf4j
public class ServerAdapterImpl implements ServerAdapter{
    private static final String HOST = "http://localhost:8081";
    private HttpResponse<String> send(HttpRequest request) throws IOException, InterruptedException {
        return HttpClient
                .newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Override
    public void error() {
        var request = HttpRequest
                .newBuilder()
                .uri(URI.create(HOST + "/message?id=0"))
                .GET()
                .build();
        try {
            var response = send(request);
            log.info(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void array() {
        var request = HttpRequest
                .newBuilder()
                .uri(URI.create(HOST + "/message"))
                .GET()
                .build();
        try {
            var response = send(request);
            log.info(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void single() {
        var request = HttpRequest
                .newBuilder()
                .uri(URI.create(HOST+"/message?id=1"))
                .GET()
                .build();
        try {
            var response = send(request);
            log.info(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
