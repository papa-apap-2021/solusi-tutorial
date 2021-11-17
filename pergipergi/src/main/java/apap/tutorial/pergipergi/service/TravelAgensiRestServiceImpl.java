package apap.tutorial.pergipergi.service;

import apap.tutorial.pergipergi.model.TravelAgensiModel;
import apap.tutorial.pergipergi.repository.TravelAgensiDb;
import apap.tutorial.pergipergi.rest.AgensiDetail;
import apap.tutorial.pergipergi.rest.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@Transactional
public class TravelAgensiRestServiceImpl implements TravelAgensiRestService {

    @Autowired
    private TravelAgensiDb travelAgensiDb;

    private final WebClient webClient;

    public TravelAgensiRestServiceImpl(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl(Setting.agensiUrl).build();
    }

    @Override
    public TravelAgensiModel createAgensi(TravelAgensiModel agensi){
        return travelAgensiDb.save(agensi);
    }

    @Override
    public List<TravelAgensiModel> retrieveListAgensi(){
        return travelAgensiDb.findAll();
    }

    @Override
    public TravelAgensiModel getAgensiByNoAgensi(Long noAgensi){
        Optional<TravelAgensiModel> agensi = travelAgensiDb.findByNoAgensi(noAgensi);

        if(agensi.isPresent()){
            return agensi.get();
        }else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public TravelAgensiModel updateAgensi(Long noAgensi, TravelAgensiModel AgensiUpdate){
        TravelAgensiModel Agensi = getAgensiByNoAgensi(noAgensi);
        Agensi.setNamaAgensi(AgensiUpdate.getNamaAgensi());
        Agensi.setAlamatAgensi(AgensiUpdate.getAlamatAgensi());
        Agensi.setNoTeleponAgensi(AgensiUpdate.getNoTeleponAgensi());
        Agensi.setWaktuBuka(AgensiUpdate.getWaktuBuka());
        Agensi.setWaktuTutup(AgensiUpdate.getWaktuTutup());

        return travelAgensiDb.save(Agensi);
    }

    @Override
    public void deleteAgensi(Long noAgensi){
        LocalTime now = LocalTime.now();
        TravelAgensiModel Agensi = getAgensiByNoAgensi(noAgensi);

        if((now.isBefore(Agensi.getWaktuBuka()) || now.isAfter(Agensi.getWaktuTutup()))
                && Agensi.getListTourGuide().isEmpty()){
            travelAgensiDb.delete(Agensi);
        }else{
            throw new UnsupportedOperationException("Agensi still open!");
        }
    }



    @Override
    public Mono<String> getStatus(Long noAgensi){
        return this.webClient.get().uri("/rest/agensi/" + noAgensi + "/status")
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public Mono<AgensiDetail> postStatus(){
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("namaAgensi", "Agensi Mock Server");
        data.add("alamatAgensi", "Di hatimu");
        return this.webClient.post().uri("/rest/agensi/full")
                .syncBody(data)
                .retrieve()
                .bodyToMono(AgensiDetail.class);
    }
}
