package fluxAndMono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxAndMonoFilterTest {

    List<String> names = Arrays.asList("Jayant" , "Nandita" , "Divya" , "Anjali" , "Vineet");

    @Test
    public void filterTest(){
        Flux<String> flux =   Flux.fromIterable(names).filter( s -> s.startsWith("D")).log();

        StepVerifier.create(flux)
                .expectNext("Divya")
                .verifyComplete();
    }

    @Test
    public void filterLengthTest(){
        Flux<String> flux =   Flux.fromIterable(names).filter( s -> s.length() == 6).log();

        StepVerifier.create(flux)
                .expectNext("Jayant", "Anjali","Vineet")
                .verifyComplete();
    }
}
