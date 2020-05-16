package com.hai.example.demo.controllers;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

import java.util.*;

@RestController
@JsonSerialize
public class WebClientController {
    private final WebClient webClient;

    public WebClientController() {
        this.webClient = WebClient.builder()
                .baseUrl("https://reqres.in")
                .defaultHeader(HttpHeaders.CONTENT_TYPE)
                .defaultHeader(HttpHeaders.USER_AGENT)
                .build();

    }

    @GetMapping(value = "webclient/retrieve", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> retrieve() {
        Mono<String> stringMono = webClient
                .get()
                .uri("api/users?page=2")
                .retrieve()
                .bodyToMono(String.class);

        return stringMono;
    }

    @GetMapping(value = "webclient/exchange", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> exchange(@RequestParam(value = "path", defaultValue = "api/users") String apiPath, @RequestParam String page) {

        Mono<String> stringMono = webClient.get()
                .uri(apiPath + "?page=" + page)
                .exchange()
                .flatMap(m -> {
                    System.out.println(m.statusCode());
                    if (m.statusCode().is4xxClientError()) {
                        return Mono.just("404: Not Found");
                    }
                    return m.bodyToMono(String.class);

                }).onErrorReturn("sendSampleRequest2 Error");

        return stringMono;
    }


    public Mono<String> callAPI(String url) {
        System.out.println("Call..." + url);
        return WebClient.create(url).get().retrieve().bodyToMono(String.class);
    }

    @GetMapping(value = "webclient/parallel", produces = MediaType.APPLICATION_JSON_VALUE)
    public ParallelFlux<String> parallel() {
        List<String> urls = new ArrayList<>();
        urls.add("https://reqres.in/api/users?page=1");
        urls.add("https://reqres.in/api/users?page=2");

        ParallelFlux<String> myFlux = Flux.fromIterable(urls).parallel()
                .runOn(Schedulers.elastic())
                .flatMap(m -> this.callAPI(m))
                .map(m -> {
                    System.out.println(m);
                    return m;
                })
                .log();

        return myFlux;
    }
}
