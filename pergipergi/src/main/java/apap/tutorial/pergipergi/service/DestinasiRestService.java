package apap.tutorial.pergipergi.service;

import apap.tutorial.pergipergi.model.DestinasiModel;
import reactor.core.publisher.Mono;

public interface DestinasiRestService {
    DestinasiModel findById(Long idDestinasi);

    public Mono<String> tebakUmur(String name);
}
