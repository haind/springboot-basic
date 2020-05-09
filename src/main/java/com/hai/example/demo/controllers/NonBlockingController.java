package com.hai.example.demo.controllers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hai.example.demo.model.ItemBookmark;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class NonBlockingController {
	@GetMapping(value = "flux", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Integer> returnFlux() {
		return Flux.just(1, 2, 3, 4).delayElements(Duration.ofSeconds(2)).log();
	}

	@GetMapping("flux-mapping")
	public void returnFluxMapping() {
		Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring");
		stringFlux = stringFlux.map(s -> s.toUpperCase()).log();

		System.out.println("================================");
		stringFlux.subscribe(System.out::println);
		System.out.println("================================");
	}

	@GetMapping("mono")
	public Mono<Integer> returnMono() {
		return Mono.just(1).log();
	}

	@GetMapping("mono-mapping")
	public void returnMonoMapping() {
		Mono<String> stringMono = Mono.just("[{Duy hai}]");
		stringMono = stringMono.map(s -> s.toUpperCase());

		System.out.println("================================");
		stringMono.subscribe(System.out::println);
		System.out.println("================================");
	}

}