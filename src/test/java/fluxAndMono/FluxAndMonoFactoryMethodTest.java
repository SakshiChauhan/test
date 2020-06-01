package fluxAndMono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class FluxAndMonoFactoryMethodTest {

    List<String> names = Arrays.asList("Jayant" , "Nandita" , "Divya" , "Anjali" , "Vineet");

    @Test
    public void fluxUsingIterable(){
        Flux<String> flux =   Flux.fromIterable(names).log();

        StepVerifier.create(flux)
                .expectNext("Jayant")
                .expectNext("Nandita")
                .expectNext("Divya")
                .expectNext("Anjali")
                .expectNext("Vineet")
                .verifyComplete();
    }

    @Test
    public void fluxUsingArray(){
        String[] names = new String[]{"Jayant" , "Nandita" , "Divya" , "Anjali" , "Vineet"};
        Flux<String> flux =   Flux.fromArray(names).log();

        StepVerifier.create(flux)
                .expectNext("Jayant")
                .expectNext("Nandita")
                .expectNext("Divya")
                .expectNext("Anjali")
                .expectNext("Vineet")
                .verifyComplete();
    }


    @Test
    public void fluxUsingStreams(){
        Flux<String> streamFlux =   Flux.fromStream(names.stream());

        StepVerifier.create(streamFlux)
                .expectNext("Jayant")
                .expectNext("Nandita")
                .expectNext("Divya")
                .expectNext("Anjali")
                .expectNext("Vineet")
                .verifyComplete();
    }

    @Test
    public void monoUsingJustOrEmpty(){
        Mono<String> mono = Mono.justOrEmpty(null); //Empty mono

        StepVerifier.create(mono.log())
                               .verifyComplete();
    }

    @Test
    public void monoUsingSupplier(){

        Supplier<String> supplier = () -> "Jayant";
        Mono<String> mono = Mono.fromSupplier(supplier);
        StepVerifier.create(mono.log()).expectNext("Jayant")
                .verifyComplete();
    }

    @Test
    public void fluxUsingRange(){
        Flux<Integer> integerFlux =   Flux.range(3,10);

        StepVerifier.create(integerFlux.log())
                .expectNext(3,4,5,6,7,8,9,10,11,12)
                .verifyComplete();
    }
}
