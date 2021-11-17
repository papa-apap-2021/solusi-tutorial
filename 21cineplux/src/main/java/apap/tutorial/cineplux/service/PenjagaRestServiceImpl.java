package apap.tutorial.cineplux.service;

import apap.tutorial.cineplux.model.BioskopModel;
import apap.tutorial.cineplux.model.PenjagaModel;
import apap.tutorial.cineplux.repository.PenjagaDB;
import apap.tutorial.cineplux.rest.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.*;

@Service
@Transactional
public class PenjagaRestServiceImpl implements PenjagaRestService{
    private final WebClient webClient;

    @Autowired
    private PenjagaDB penjagaDB;

    @Override
    public PenjagaModel createPenjaga(PenjagaModel penjagaModel){
        return penjagaDB.save(penjagaModel);
    }

    @Override
    public List<PenjagaModel> retrieveListPenjaga(){
        return penjagaDB.findAll();
    }

    @Override
    public PenjagaModel getPenjagaByNoPenjaga(Long noPenjaga){
        Optional<PenjagaModel> penjaga = penjagaDB.findByNoPenjaga(noPenjaga);

        if(penjaga.isPresent()){
            return penjaga.get();
        }else{
            throw new NoSuchElementException();
        }
    }

    @Override
    public PenjagaModel updatePenjaga(Long noPenjaga, PenjagaModel penjagaUpdate){
        LocalTime now = LocalTime.now();
        PenjagaModel penjaga = getPenjagaByNoPenjaga(noPenjaga);
        BioskopModel bioskop = penjaga.getBioskop();

        if((now.isBefore(bioskop.getWaktuBuka()) || now.isAfter(bioskop.getWaktuTutup()))){
            penjaga.setNamaPenjaga(penjagaUpdate.getNamaPenjaga());
            penjaga.setJenisKelamin(penjagaUpdate.getJenisKelamin());
            penjaga.setBioskop(penjagaUpdate.getBioskop());

            return penjagaDB.save(penjaga);
        }else{
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public void deletePenjaga(Long noPenjaga){
        LocalTime now = LocalTime.now();
        PenjagaModel penjaga = getPenjagaByNoPenjaga(noPenjaga);
        BioskopModel bioskop = penjaga.getBioskop();

        if((now.isBefore(bioskop.getWaktuBuka()) || now.isAfter(bioskop.getWaktuTutup()))){
            penjagaDB.delete(penjaga);
        }else{
            throw new UnsupportedOperationException();
        }
    }

    public PenjagaRestServiceImpl(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl(Setting.prediksiUmur).build();
    }

    @Override
    public PenjagaModel predictAge(Long noPenjaga){
        LocalTime now = LocalTime.now();
        PenjagaModel penjaga = getPenjagaByNoPenjaga(noPenjaga);
        BioskopModel bioskop = penjaga.getBioskop();

        if((now.isBefore(bioskop.getWaktuBuka()) || now.isAfter(bioskop.getWaktuTutup()))){
            String namaPenjaga = penjaga.getNamaPenjaga();
            String  response = this.webClient.get().uri("/?name=" + namaPenjaga)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            response = response.substring(1, response.length()-1);    //remove curly brackets
            String[] keyValuePairs = response.split(",");      //split the string to create key-value pairs
            Map<String,String> map = new HashMap<>();

            //iterate over the pairs
            for (String pair : keyValuePairs) {
                //split the pairs to get key and value
                String[] entry = pair.split(":");

                //add them to the hashmap and trim whitespaces
                map.put(entry[0].trim().replace("\"", ""),
                        entry[1].trim().replace("\"", ""));
            }

            penjaga.setUmur(map.get("age"));
            return penjagaDB.save(penjaga);
        }else{
            throw new UnsupportedOperationException();
        }
    }
}
