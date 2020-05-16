package com.hai.example.demo.controllers;

import com.hai.example.demo.model.Session;
import com.hai.example.demo.views.ViewFluxMono;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
        Mono<String> monoFromCallable = Mono.fromCallable(() -> "This is mono from callable").log();
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
        List<Object> myList = new ArrayList<>();
        myList.add("ABC");
        Flux<Object> myFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring");

        //map() will be applied when onNext() is called.
        myFlux = myFlux.map(s -> {

            return (Object) s.toString().toUpperCase();
        }).log();

        //Consume this Flux by subscribe and assign to a list
        myFlux.subscribe(s -> myList.add(s));

        System.out.println("myList:");
        System.out.println(myList);

        Mono<List<Object>> Monolist = myFlux.collectList();

        Mono<Object> myMono = Mono.from(Monolist);
        myMono = myMono.map(x -> new ViewFluxMono("OK", "Status", x));

        return myMono;
    }

    @GetMapping("flux-flatmap")
    public void fluxFlatMap() {
        System.out.println("-- Mapping Flux elements --");
        Flux<Integer> myFlux = Flux.just(2, 3);
        myFlux = myFlux.flatMap(i -> {
            return Mono.just(i);
//            return Flux.range(i, i * 2);
        });
        myFlux.subscribe(System.out::println);


        System.out.println("-- Mapping Mono element --");
        Mono.just("supercalifragilisticexpialidocious")
                .flatMap(s -> Mono.just(s.length()))
                .subscribe(System.out::println);


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

    /**
     * Mono with array list
     * [{shop_id=55, shopimageurl=https://thumbnail.image.rakuten.co.jp/@0_mall/_kobo/logo/logo1.jpg, sbm_shop_id=3, shop_name=Shop (k_s), shop_url=_kobo, service_kbn=0, reg_ts=2015-08-19 14:48:10}]
     * [
     * {shop_id=22, item_id=10164456, item_url=https://item.rakuten.co.jp/_kobo/b1359927b43b499484288883f6cf50f5/, item_name=KOBO item (k_i) 1, raw_imagepath=, sbm_item_id=8515, item_type=1, shop_name=楽天kobo電子書籍ストア, shop_url=_kobo, review_url=, review_count=0, review_ave=null, price=490, inventory=1, postage_flg=, tax_flg=0, reg_ts=2020-03-02 17:06:23},
     * {shop_id=33, item_id=10164324, item_url=https://item.rakuten.co.jp/_kobo/b7e68ad1e43b3aefbe8381c72a5c51d3/, item_name=KOBO item (k_i) 2 TruthMy Story【電子書籍】[ Vicky Pat, raw_imagepath=@0_mall/_kobo/cabinet/5758/2000000125758.jpg, sbm_item_id=8435, item_type=1, shop_name=楽天kobo電子書籍ストア, shop_url=_kobo, review_url=, review_count=0, review_ave=null, price=1052, inventory=1, postage_flg=0, tax_flg=0, reg_ts=2020-01-28 14:38:54}
     * ]
     * [Session(id=1, uuid=UUID1, content=Connect1), Session(id=2, uuid=UUID2, content=Connect2)]
     */
    @GetMapping("mono-mapping2")
    public void returnMonoMapping2() {

        List<Object> list = new ArrayList<>();
        list.add("[{shop_id=11, item_id=10021082}]");
        list.add("[{shop_id=22, item_id=10164456}, {shop_id=33, item_id=10164324}]");
        list.add("[{shop_id=55, shop_name=Shop(k_s), shop_url=_kobo}]");
        list.add("[{shop_id=44, shop_name=Shop(b_s), shop_url=_book-stg}]");

        Mono<List<Object>> objMono = Mono.just(list);

/*        Flux<Object> objFlux = objMono.flatMapIterable(s -> {
            System.out.println("========Value of s =========");
            System.out.println(s);

            return s;
        }).map(m -> {
            System.out.println("========Value of m =========");
            System.out.println(m);

            return m;
        }).log();*/

        Flux<Object> objFlux = objMono.flatMapMany(s -> {
            System.out.println("========Value of s =========");
            System.out.println(s);
            System.out.println("========Value of s =========");
            return Flux.fromIterable(s);
        }).map(m -> {
            System.out.println("========Value of m =========");
            System.out.println(m);

            return m;
        }).log();


//        objMono = objMono.map(s -> s.toUpperCase());

        System.out.println("=============subscribe===================");
        objMono.subscribe();
        objFlux.subscribe();
        System.out.println("=============subscribe===================");
    }
}

@Data
@AllArgsConstructor
class ShopCacheModel implements Serializable
{
    private int id;
    private String shopName;
}

@Data
@AllArgsConstructor
class ItemCacheModel
{
    private int id;
    private String itemName;
}