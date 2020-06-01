package fluxAndMono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static reactor.core.scheduler.Schedulers.parallel;

public class FluxAndMonoTransformerTest {

    List<String> names = Arrays.asList("Jayant", "Nandita", "Divya", "Anjali", "Vineet");

    @Test
    public void transformUsingMap() {
        Flux<String> flux = Flux.fromIterable(names).map(s -> s.toUpperCase()).log();

        StepVerifier.create(flux)
                .expectNext("JAYANT", "NANDITA", "DIVYA", "ANJALI", "VINEET")
                .verifyComplete();
    }


    @Test
    public void transformUsingMap_Length() {
        Flux<Integer> flux = Flux.fromIterable(names).map(s -> s.length()).log();

        StepVerifier.create(flux)
                .expectNext(6, 7, 5, 6, 6)
                .verifyComplete();
    }

    @Test
    public void transformUsingMap_Length_Repeat() {
        Flux<Integer> flux = Flux.fromIterable(names)
                .map(s -> s.length())
                .repeat(1)
                .log();

        StepVerifier.create(flux)
                .expectNext(6, 7, 5, 6, 6, 6, 7, 5, 6, 6)
                .verifyComplete();
    }

    @Test
    public void transformUsingMap_Filter() {
        Flux<String> flux = Flux.fromIterable(names)
                .filter(s -> s.length() > 5)
                .map(s -> s.toUpperCase())
                .log();

        StepVerifier.create(flux)
                .expectNext("JAYANT", "NANDITA", "ANJALI", "VINEET")
                .verifyComplete();
    }

    @Test
    public void transformUsingFlatmap() {
        Flux<String> flux = Flux.fromIterable(names)
                .flatMap(s ->  {
                        return Flux.fromIterable(convertToList(s));
        }).log(); //for each elements if you have to call db or call some external service

        StepVerifier.create(flux)
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    public void transformUsingFlatmap_usingParallel() {
        Flux<String> flux = Flux.fromIterable(names)
                .window(2)
                .flatMap((s) ->
                    s.map(this :: convertToList).subscribeOn(parallel()))
                            .flatMap(s -> Flux.fromIterable(s))
                .log(); //for each elements if you have to call db or call some external service

        StepVerifier.create(flux)
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    public void transformUsingFlatmap_parallel_maintain_order() {
        Flux<String> flux = Flux.fromIterable(names)
                .window(2)
                // .concatMap
                .flatMapSequential((s) ->
                        s.map(this :: convertToList).subscribeOn(parallel()))
                .flatMap(s -> Flux.fromIterable(s))
                .log(); //for each elements if you have to call db or call some external service

        StepVerifier.create(flux)
                .expectNextCount(10)
                .verifyComplete();
    }

    private List<String> convertToList(String s) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
           e.printStackTrace();
        }
        return Arrays.asList( s, "new value");
    }

    @Test
    public void test() {

      List list = Arrays.asList(null);

      System.out.println(list.get(0));

    }
}
