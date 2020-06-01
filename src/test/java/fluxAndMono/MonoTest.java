package fluxAndMono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoTest {

    @Test
    public void monoTest(){

        Mono<String> mono =   Mono.just("Reactive Programming")
                                     .log();

        mono.subscribe(System.out :: println , (e) -> System.err.println("Exception is : " + e)
                , () -> System.out.println("Completed."));
    }

    @Test
    public void monoTest_noError(){

        Mono<String> mono =   Mono.just("Reactive Programming")
                .log();

        StepVerifier.create(mono)
                .expectNext("Reactive Programming")
                .verifyComplete();
    }

    @Test
    public void monoTest_withError(){

        StepVerifier.create(Mono.error(new RuntimeException("Exception Occurred")).log())
                .expectError(RuntimeException.class)
                .verify();
    }
}
