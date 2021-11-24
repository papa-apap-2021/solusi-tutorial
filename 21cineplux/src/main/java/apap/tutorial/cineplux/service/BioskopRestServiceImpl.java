package apap.tutorial.cineplux.service;

import apap.tutorial.cineplux.model.BioskopModel;
import apap.tutorial.cineplux.repository.BioskopDB;
import apap.tutorial.cineplux.rest.BioskopDetail;
import apap.tutorial.cineplux.rest.Setting;
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
public class BioskopRestServiceImpl implements BioskopRestService{
    private final WebClient webClient;

    @Autowired
    private BioskopDB bioskopDB;

    @Override
    public BioskopModel createBioskop(BioskopModel bioskop){
        return bioskopDB.save(bioskop);
    }

    @Override
    public List<BioskopModel> retrieveListBioskop(){
        return bioskopDB.findAll();
    }

    @Override
    public BioskopModel getBioskopByNoBioskop(Long noBioskop){
        Optional<BioskopModel> bioskop = bioskopDB.findByNoBioskop(noBioskop);

        if(bioskop.isPresent()){
            return bioskop.get();
        }else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public BioskopModel updateBioskop(Long noBioskop, BioskopModel bioskopUpdate){
        BioskopModel bioskop = getBioskopByNoBioskop(noBioskop);
        bioskop.setNamaBioskop(bioskopUpdate.getNamaBioskop());
        bioskop.setAlamatBioskop(bioskopUpdate.getAlamatBioskop());
        bioskop.setJumlahStudio(bioskopUpdate.getJumlahStudio());
        bioskop.setWaktuBuka(bioskopUpdate.getWaktuBuka());
        bioskop.setWaktuTutup(bioskopUpdate.getWaktuTutup());

        return bioskopDB.save(bioskop);
    }

    @Override
    public void deleteBioskop(Long noBioskop){
        LocalTime now = LocalTime.now();
        BioskopModel bioskop = getBioskopByNoBioskop(noBioskop);

        if((now.isBefore(bioskop.getWaktuBuka()) || now.isAfter(bioskop.getWaktuTutup()))
                && bioskop.getListPenjaga().isEmpty()){
            bioskopDB.delete(bioskop);
        }else{
            throw new UnsupportedOperationException("Bioskop still open!");
        }
    }

    public BioskopRestServiceImpl(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl(Setting.bioskopUrl).build();
    }

    @Override
    public Mono<String> getStatus(Long noBioskop){
        return this.webClient.get().uri("/rest/bioskop/" + noBioskop + "/status")
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public Mono<BioskopDetail> postStatus(){
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("namaBioskop", "Bioskop Mock Server");
        data.add("alamatBioskop", "Depok");
        System.out.println("masuk");
        return this.webClient.post().uri("/rest/bioskop/full")
                .syncBody(data)
                .retrieve()
                .bodyToMono(BioskopDetail.class);
    }
}