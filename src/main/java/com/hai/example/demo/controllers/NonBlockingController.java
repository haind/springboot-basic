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
	public Flux<Integer> returnFlux()
	{
		return Flux.just(1,2,3,4)
				.delayElements(Duration.ofSeconds(2))
				.log();
	}

	@GetMapping("mono")
	public Mono<Integer> returnMono()
	{
		return Mono.just(1).log();
	}
}