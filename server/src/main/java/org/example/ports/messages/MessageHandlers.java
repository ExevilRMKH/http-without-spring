package org.example.ports.messages;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.config.exceptions.NotFoundException;
import org.example.config.exceptions.ResponseException;
import org.example.config.http.HttpHelpers;
import org.example.domain.Postman;
import org.example.domain.PostmanImpl;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

import static java.lang.invoke.VarHandle.AccessMode.GET;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_OK;

@Slf4j
public record MessageHandlers(Postman postman) implements HttpHandler {
    private Map<String, HttpHandler> handlers(){
        var handlers = new HashMap<String, HttpHandler>(){};
        handlers.put(
                GET.methodName().toUpperCase(),
                exchange -> {
                    log.info("handle request {}", exchange.getHttpContext().getPath());

                    var message = Arrays
                            .stream(HttpHelpers.queryToArray(exchange.getRequestURI().getQuery()))
                            .map(HttpHelpers::splitQueryParameters)
                            .filter(stringStringSimpleImmutableEntry -> stringStringSimpleImmutableEntry.getKey().equals("id"))
                            .map(AbstractMap.SimpleImmutableEntry::getValue)
                            .findAny()
                            .map(postman::getMessageById)
                            .orElse(postman.getMessages());

                    var response = exchange.getResponseBody();

                    if (message.length > 0){
                        exchange.sendResponseHeaders(HTTP_OK, message.length);
                        response.write(message);
                    }

                    throw new NotFoundException("messages couldn't find");
                });
        return handlers;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        var handlers = handlers();
        try {
            log.info("get request {} and method {}", exchange.getHttpContext().getPath(), exchange.getRequestMethod());
            var handle = handlers.get(exchange.getRequestMethod().toUpperCase());
            handle.handle(exchange);
            log.info(exchange.getHttpContext().getPath());
            var context = exchange.getHttpContext();
            log.info(context.getPath());
        } catch (NotFoundException e){
            var body = exchange.getResponseBody();
            var message =  HttpHelpers.objectToArray(new ResponseException(HTTP_NOT_FOUND, e.getMessage(), Date.from(Instant.now()).toString()));
            exchange.sendResponseHeaders(HTTP_NOT_FOUND, message.length);
            body.write(message);
        } catch (Exception e){
            log.error(e.getMessage());
        } finally {
            exchange.close();
        }
    }
}
