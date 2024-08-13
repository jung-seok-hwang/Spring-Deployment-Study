package cloud.springdeploymentstudy;


import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Random;


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
                .flatMap(this::splitName)
                .log();
    }

    public Flux<String> splitName(String name) {
        String[] split = name.split("");
        return Flux.fromArray(split);
    }

    public Flux<String> fluxFlatMapAsynchronous(List<String> list, int length) {
        return Flux.fromIterable(list)
                .filter(str -> str.length() > length)
                .flatMap(this::splitNameDelay)
                .log();
    }

    public Flux<String> splitNameDelay(String name) {
        String[] split = name.split("");
        var delay = new Random().nextInt(1000);
        return Flux.fromArray(split).delayElements(Duration.ofMillis(delay));
    }

}
