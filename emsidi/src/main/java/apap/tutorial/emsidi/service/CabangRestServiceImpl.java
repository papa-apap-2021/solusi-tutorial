package apap.tutorial.emsidi.service;

import apap.tutorial.emsidi.model.CabangModel;
import apap.tutorial.emsidi.repository.CabangDb;
import apap.tutorial.emsidi.rest.CabangDetail;
import apap.tutorial.emsidi.rest.Setting;
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
public class CabangRestServiceImpl implements CabangRestService {
    private final WebClient webClient;

    @Autowired
    private CabangDb cabangDb;

    @Override
    public CabangModel createCabang(CabangModel cabang) { return cabangDb.save(cabang); }

    @Override
    public List<CabangModel> retrieveListCabang() { return cabangDb.findAll(); }

    @Override
    public CabangModel getCabangByNoCabang(Long noCabang) {
        Optional<CabangModel> cabang = cabangDb.findByNoCabang(noCabang);

        if (cabang.isPresent()) {
            return cabang.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public CabangModel updateCabang(Long noCabang, CabangModel cabangUpdate) {
        CabangModel cabang = getCabangByNoCabang(noCabang);
        cabang.setNamaCabang(cabangUpdate.getNamaCabang());
        cabang.setAlamatCabang(cabangUpdate.getAlamatCabang());
        cabang.setNoTeleponCabang(cabangUpdate.getNoTeleponCabang());
        cabang.setWaktuBuka(cabangUpdate.getWaktuBuka());
        cabang.setWaktuTutup(cabangUpdate.getWaktuTutup());

        return cabangDb.save(cabang);
    }

    @Override
    public void deleteCabang(Long noCabang) {
        LocalTime now = LocalTime.now();
        CabangModel cabang = getCabangByNoCabang(noCabang);

        if ((now.isBefore(cabang.getWaktuBuka()) || now.isAfter(cabang.getWaktuTutup()))
                && cabang.getListPegawai().isEmpty()) {
            cabangDb.delete(cabang);

        } else {
            throw new UnsupportedOperationException();
        }
    }

    public CabangRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(Setting.cabangUrl).build();
    }

    @Override
    public Mono<String> getStatus(Long noCabang) {
        return this.webClient.get().uri("/rest/cabang/" + noCabang + "/status")
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public Mono<CabangDetail> postStatus() {
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("namaCabang", "Cabang C");
        data.add("alamatCabang", "Jl. C");

        return this.webClient.post().uri("/rest/cabang/full")
                .syncBody(data)
                .retrieve()
                .bodyToMono(CabangDetail.class);
    }
}
