package fluxAndMono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxTest {

    @Test
    public void fluxTest(){

      Flux<String> flux =   Flux.just("Welcome" , "SpringBoot" , "Reactive" , "World")
        //      .concatWith(Flux.error(new RuntimeException("Exception Occurred in flux")))
              .concatWith(Flux.just("After Error"))
              .log();

      flux.subscribe(System.out :: println , (e) -> System.err.println("Exception is : " + e)
      , () -> System.out.println("Completed."));
    }


    @Test
    public void fluxTest_noError1(){
        Flux<String> flux =   Flux.just("Welcome" , "SpringBoot" , "Reactive" , "World")
                .log();

        StepVerifier.create(flux)
                .expectNext("Welcome")
                .expectNext("SpringBoot")
                .expectNext("Reactive")
                .expectNext("World")
                .verifyComplete();
    }

    @Test
    public void fluxTest_noError2(){
        Flux<String> flux =   Flux.just("Welcome" , "SpringBoot" , "Reactive" , "World")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred in flux")))
                .concatWith(Flux.just("After Error"))
                .log();

        StepVerifier.create(flux)
                .expectNext("Welcome" , "SpringBoot" , "Reactive" , "World")
                .expectError(RuntimeException.class);
        //   .verifyComplete();
    }

    @Test
    public void fluxTest_withError(){
        Flux<String> flux =   Flux.just("Welcome" , "SpringBoot" , "Reactive" , "World")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred in flux")))
                .concatWith(Flux.just("After Error"))
                .log();

        StepVerifier.create(flux)
                .expectNext("Welcome")
                .expectNext("SpringBoot")
                .expectNext("Reactive")
                .expectNext("World")
           //     .verifyError();
                .expectError(RuntimeException.class).verify();
    }

    @Test
    public void fluxTest_elementCount(){
        Flux<String> flux =   Flux.just("Welcome" , "SpringBoot" , "Reactive" , "World")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred in flux")))
                .concatWith(Flux.just("After Error"))
                .log();

        StepVerifier.create(flux)
                .expectNextCount(4)
               .expectError(RuntimeException.class);
             //   .verifyComplete();
    }
}
