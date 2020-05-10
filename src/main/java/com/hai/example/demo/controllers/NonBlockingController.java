package com.hai.example.demo.controllers;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import com.hai.example.demo.views.ViewFluxMono;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class NonBlockingController {

    @GetMapping(value = "create-flux")
    public void createFlux() {
        Flux<Integer> intFlux = Flux.just(1, 2, 3, 4).delayElements(Duration.ofSeconds(2)).log();
        intFlux.subscribe(System.out::println);

        //Create a flux contains A, B, C
        Flux<String> strFlux = Flux.just("A", "B", "C").log();
        strFlux.subscribe(System.out::println);

        //Create a flux from already iterable, ex a list
        List<String> list = Arrays.asList("list A", "list B", "list C");
        Flux<String> fluxFromList = Flux.fromIterable(list).log();
        fluxFromList.subscribe(System.out::println);

        //Create a flux on a range
        Flux<Integer> rangeFlux = Flux.range(1, 5);
        rangeFlux.subscribe(System.out::println);

        // Creates a flux that generates a new value every 100 ms.
        // The value is incremental, starting at 1.
        //This code will run forever...
        Flux<Long> genFlux = Flux.interval(Duration.ofMillis(500)).log();
        genFlux.subscribe(System.out::println);

        // You can also create a Flux from another one, or from a Mono.
        Flux<String> clonedFlux = Flux.from(fluxFromList);
        clonedFlux.subscribe(System.out::println);
    }

    @GetMapping(value = "create-mono")
    public void createMono() {
        Mono<String> strMono = Mono.just("Hello World").log();
        strMono.subscribe(System.out::println);

        // Creating an empty Mono
        Mono<Object> emptyMono = Mono.empty().log();
        emptyMono.subscribe(System.out::println);

        // Creating a mono from a Callable
        Mono<String> monoFromCallable = Mono.fromCallable( () -> "This is mono from callable" ).log();
        monoFromCallable.subscribe(System.out::println);
        // Same with Java 8 method reference
//        Mono<User> user = Mono.fromCallable(UserService::fetchAnyUser);

        // Creating a mono from a Future
//        CompletableFuture<String> helloWorldFuture = MyApi.getHelloWorldAsync();
//        Mono<String> monoFromFuture = Mono.fromFuture(helloWorldFuture);

        // Creating a mono from a supplier
        Random rnd = new Random();
        Mono<Double> monoFromSupplier = Mono.fromSupplier(rnd::nextDouble).log();
        monoFromSupplier.subscribe(System.out::println);

        // You can also create a mono from another one.
        Mono<Double> clondedMono = Mono.from(monoFromSupplier).log();
        clondedMono.subscribe(System.out::println);

        // Or from a Flux.
        Mono<Integer> monoFromFlux1 = Mono.from(Flux.range(90, 5)).log();
        monoFromFlux1.subscribe(System.out::println);   //output first element: 90

        Mono<List<Integer>> monoFromFlux = Mono.from(Flux.range(90, 5).collectList()).log();
        monoFromFlux.subscribe(System.out::println);    //output array [90, 91, 92, 93, 94]
    }

    @GetMapping(value = "flux", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Integer> returnFlux() {
        return Flux.just(1, 2, 3, 4).delayElements(Duration.ofSeconds(2)).log();
    }

    /**
     * Flux of object
     * Convert object to string and transform uppercase
     * Convert Flux -> Mono
     * Output Mono into a view response
     *
     * @return
     */
    @GetMapping("flux-mapping")
    public Mono<Object> returnFluxMapping() {
        Flux<Object> myFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring");
        myFlux = myFlux.map(s -> {
            return (Object) s.toString().toUpperCase();
        }).log();

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