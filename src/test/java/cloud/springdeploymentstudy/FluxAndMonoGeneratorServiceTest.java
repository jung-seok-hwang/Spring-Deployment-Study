package cloud.springdeploymentstudy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;


class FluxAndMonoGeneratorServiceTest {

    FluxGeneratorService flux = new FluxGeneratorService();

    @Test
    @DisplayName("Validate Flux fromIterable Methods")
    public void flux_fromIterable() {
        //given
        List<String> list = List.of("A", "B", "C", "D");

        //when
        var nameFlux = flux.flux(list);

        //then
        StepVerifier.create(nameFlux)
                .expectNext("A")
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    @DisplayName("Test Flux Immutability with Map Transformation")
    public void flux_map() {
        //given
        List<String> list = List.of("alex", "job", "hep", "ben");

        //when
        Flux<String> fluxImmutability = flux.fluxImmutability(list);

        //then
        StepVerifier.create(fluxImmutability)
                .expectNext("alex")
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void nameFluxToUpperCase() {
        //given
        List<String> list = List.of("alex", "job", "hep", "ben");

        //when
        var namedFluxToUpperCase = flux.fluxToUpperCase(list);

        //then
        StepVerifier.create(namedFluxToUpperCase)
                .expectNext("ALEX", "JOB", "HEP", "BEN")
                .verifyComplete();
    }

    @Test
    void fluxFilter() {
        //given
        List<String> list = List.of("ale", "job", "deadpool", "hep", "ben", "venom");

        //when
        var namedFluxToUpperCase = flux.fluxFilter(list, 3);

        Flux<String> log = namedFluxToUpperCase.filter(str -> str.length() == 8).log();

        //then
        StepVerifier.create(log)
                .expectNext("DEADPOOL")
                .verifyComplete();
    }

    @Test
    @DisplayName("")
    void fluxFlatMap() {
        //given
        List<String> list = List.of("ale", "job","liam", "hep", "ben", "venom");

        //when
        var namedFluxToUpperCase = flux.fluxFlatMap(list, 3);

        //then
        StepVerifier.create(namedFluxToUpperCase)
                .expectNext("l", "i", "a", "m", "v", "e", "n", "o","m")
                .verifyComplete();
    }

    @Test
    @DisplayName("")
    void fluxFlatMapAsync() {
        //given
        List<String> list = List.of("ale", "job", "liam", "hep", "ben", "venom");

        //when
        var namedFluxToUpperCase = flux.fluxFlatMapAsynchronous(list, 3);

        //then
        StepVerifier.create(namedFluxToUpperCase)
                .expectNextCount(9)
                .verifyComplete();
    }

}