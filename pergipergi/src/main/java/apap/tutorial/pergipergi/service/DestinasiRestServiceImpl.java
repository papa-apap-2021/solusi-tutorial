package apap.tutorial.pergipergi.service;


import apap.tutorial.pergipergi.model.DestinasiModel;
import apap.tutorial.pergipergi.repository.DestinasiDb;
import apap.tutorial.pergipergi.rest.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;

@Service
@Transactional
public class DestinasiRestServiceImpl implements DestinasiRestService {

    private final WebClient webClient;

    public DestinasiRestServiceImpl(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl(Setting.translateUrl).build();
    }

    @Autowired
    DestinasiDb destinasiDb;

    @Override
    public DestinasiModel findById(Long idDestinasi){
        return destinasiDb.findById(idDestinasi).get();
    }

    @Override
    public Mono<String> tebakUmur(String name){
        System.out.println("jalanin ini");
        System.out.println(this.webClient.get().uri("?name="+name));
        return this.webClient.get().uri("?name="+name)
                .retrieve()
                .bodyToMono(String.class);
    }

}
