package com.hai.example.demo.controllers;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.hai.example.demo.views.ViewFluxMono;
import org.reactivestreams.Publisher;
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
    public Mono<Object> returnFluxMapping() {
        Flux<Object> myFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring");
		myFlux = myFlux.map(s -> {
            return (Object) s.toString().toUpperCase();
        }).log();

        System.out.println("================================");

        Mono<List<Object>> Monolist = myFlux.collectList();

        Mono<Object> myMono = Mono.from(Monolist);
        myMono = myMono.map(x -> new ViewFluxMono("OK", "Status", x));

        return myMono;
    }

    @GetMapping("mono")
    public void returnMono() {
//		Mono<T> e = Mono.empty();
//		CompletableFuture
        System.out.println("Start Debugging.... ===============================");
        Mono<Integer> intMono = Mono.just(1).log();

        //Manipulate element inside Mono
        intMono = intMono.map(n -> {
            n = multiple(n);
            n = n + 8;
            return n;
        }).map(m -> m + 10).log();

        //Consume Mono by subscribe it
        intMono.subscribe(n -> {
            System.out.println("intMono.subscribe():" + n);
        });

        //Block func make it NO reactive anymore
        int stupid = intMono.block();
        System.out.println("stupid:" + stupid);
    }

    private Integer multiple(int x) {
        return x * 12;
    }

/*	@GetMapping("mono-view")
	public Publisher<Object> monoView() {
		Mono<Object> mono = Mono.just(1);
//		return mono;
		return mono.map(x -> new ViewFluxMono("OK", "Status", x));
	}*/

    @GetMapping("mono-mapping")
    public void returnMonoMapping() {
        Mono<String> stringMono = Mono.just("[{Duy hai}]");
        stringMono = stringMono.map(s -> s.toUpperCase());

        System.out.println("================================");
        stringMono.subscribe(System.out::println);
        System.out.println("================================");
    }

}