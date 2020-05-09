package com.hai.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void fluxUsingArray()
	{
		String[] names = new String[]{"a","b","c"};
		Flux<String> namesFlux = Flux.fromArray(names);

		System.out.println(names);

		StepVerifier.create(namesFlux)
		.expectNext("a","b" );
	}

}
