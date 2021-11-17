package apap.tutorial.pergipergi.restcontroller;


import apap.tutorial.pergipergi.model.DestinasiModel;
import apap.tutorial.pergipergi.model.TourGuideModel;
import apap.tutorial.pergipergi.repository.DestinasiDb;
import apap.tutorial.pergipergi.repository.TourGuideDb;
import apap.tutorial.pergipergi.service.DestinasiRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class TourGuideRestController {

    @Autowired
    DestinasiRestService destinasiRestService;

    @Autowired
    TourGuideDb tourGuideDb;


    @GetMapping(value = "/tour/umur/{idTourGuide}")
    private TourGuideModel gantiUmurTourGuide(@PathVariable(value = "idTourGuide") Long idTourGuide){
        TourGuideModel tourGuide = tourGuideDb.findByNoTourGuide(idTourGuide).get();

        String nama = tourGuide.getNamaTourGuide();

        String translationDetailMono = destinasiRestService.tebakUmur(nama).block();

        String value = translationDetailMono;

        value = value.substring(1, value.length()-1);           //remove curly brackets
        String[] keyValuePairs = value.split(",");              //split the string to creat key-value pairs
        Map<String,String> map = new HashMap<>();

        for(String pair : keyValuePairs)                        //iterate over the pairs
        {
            String[] entry = pair.split(":");                   //split the pairs to get key and value
            map.put(entry[0].trim().replace("\"", ""), entry[1].trim().replace("\"", ""));          //add them to the hashmap and trim whitespaces
        }

        tourGuide.setUmur(map.get("age"));
        return tourGuideDb.save(tourGuide);
    }
}
