package cloud.springdeploymentstudy;


import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;

@Slf4j
public class FluxGeneratorService {

    public Flux<String> flux(List<String> list) {
        return Flux.fromIterable(list).log();
    }

    public Flux<String> fluxToUpperCase(List<String> list) {
        return Flux.fromIterable(list).map(String::toUpperCase).log();
    }

    public Flux<String> fluxImmutability(List<String> list) {
        var flux = Flux.fromIterable(list);
        flux.map(String::toUpperCase);
        return flux;
    }

    public Flux<String> fluxFilter(List<String> list, int length) {
        return Flux.fromIterable(list)
                .filter(str -> str.length() > length)
                .map(String::toUpperCase)
                .log();
    }

    public Flux<String> fluxFlatMap(List<String> list, int length) {
        return Flux.fromIterable(list)
                .filter(str -> str.length() > length)
                .flatMap(name -> splitNameIntoCharacters(name))
                .log();
    }

    public Flux<String> fluxFlatMapAsynchronous(List<String> list, int length) {
        return Flux.fromIterable(list)
                .flatMap(str -> nameLength(str, length)
                        .filter(ignored -> str.length() > length)
                ).doOnNext(n -> log.info("flatMap Done {}", n));
    }

    public Flux<String> fluxConcatMapAsynchronous(List<String> list, int length) {
        return Flux.fromIterable(list)
                .concatMap(str -> nameLength(str , length)
                        .filter(ignored -> ignored.length() > length))
                .doOnNext(n -> log.info("concatMap Done {}", n));
    }

    public Flux<String> fluxFlatMapSequentialAsynchronous(List<String> list, int length) {
        return Flux.fromIterable(list)
                .flatMapSequential(name -> nameLength(name , length)
                        .filter(ignored -> ignored.length() > length))
                .doOnNext(n -> log.info("flatMapSequential Done {}", n));
    }

    public Flux<String> splitNameIntoCharacters(String name) {
        String[] split = name.split("");
        return Flux.fromArray(split);
    }

    public Flux<String> splitNameIntoCharactersWithDelay(String name) {
        String[] split = name.split("");
        var delay = new Random().nextInt(1000);

        return Flux.fromArray(split)
                .delayElements(Duration.ofMillis(delay))
                .doOnNext(n -> log.info("Executing {}", n));
    }

    public Mono<String> nameLength(String name ,Integer length) {

        var delay = new Random().nextInt(10000);

        return name.length() > length ?
                Mono.just(name).doOnNext(n -> log.info("Delayed {}", n)).delayElement(Duration.ofMillis(delay))
                : Mono.just(name).doOnNext(n -> log.info("Executing {}", n));
    }
}
