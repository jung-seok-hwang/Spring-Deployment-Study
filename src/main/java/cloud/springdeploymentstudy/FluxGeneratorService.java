package cloud.springdeploymentstudy;


import reactor.core.publisher.Flux;

import java.util.List;



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

}
