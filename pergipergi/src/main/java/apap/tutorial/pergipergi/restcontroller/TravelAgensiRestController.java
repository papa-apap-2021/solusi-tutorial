package apap.tutorial.pergipergi.restcontroller;


import apap.tutorial.pergipergi.model.TravelAgensiModel;
import apap.tutorial.pergipergi.rest.AgensiDetail;
import apap.tutorial.pergipergi.service.TravelAgensiRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1")
public class TravelAgensiRestController {

    @Autowired
    private TravelAgensiRestService travelAgensiRestService;

    @PostMapping(value = "/agensi")
    private TravelAgensiModel createTravelAgensi(@Valid @RequestBody TravelAgensiModel agensi, BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field."
            );
        }else{
            return travelAgensiRestService.createAgensi(agensi);
        }
    }

    @GetMapping(value = "/agensi/{noAgensi}")
    private TravelAgensiModel retrieveAgensi(@PathVariable("noAgensi") Long noAgensi){
        try{
            return travelAgensiRestService.getAgensiByNoAgensi(noAgensi);
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No Agensi " + String.valueOf(noAgensi) + " Not Found."
            );
        }
    }

    @DeleteMapping(value = "/agensi/{noAgensi}")
    private ResponseEntity deleteAgensi(@PathVariable("noAgensi") Long noAgensi){
        try{
            travelAgensiRestService.deleteAgensi(noAgensi);
            return ResponseEntity.ok("Travel Agensi with No Agensi " + String.valueOf(noAgensi) + " Deleted!");
        }catch (NoSuchElementException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Travel Agensi with No Agensi " + String.valueOf(noAgensi) + " Not Found."
            );
        }catch (UnsupportedOperationException e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Travel Agensi is still open or has Tour Guide!"
            );
        }
    }

    @PutMapping(value = "/agensi/{noAgensi}")
    private TravelAgensiModel updateAgensi(@PathVariable("noAgensi") Long noAgensi, @RequestBody TravelAgensiModel agensi){
        try{
            return travelAgensiRestService.updateAgensi(noAgensi, agensi);
        }catch (NoSuchElementException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Agensi with No Agensi " + String.valueOf(noAgensi) + " Not Found."
            );
        }
    }

    @GetMapping(value = "/list-agensi")
    private List<TravelAgensiModel> retrieveListBioskop(){
        return travelAgensiRestService.retrieveListAgensi();
    }

    @GetMapping(value = "/agensi/{noAgensi}/status")
    private Mono<String> getStatus(@PathVariable("noAgensi") Long noAgensi){
        return travelAgensiRestService.getStatus(noAgensi);
    }

    @GetMapping(value = "/full")
    private Mono<AgensiDetail> postStatus(){
        return travelAgensiRestService.postStatus();
    }
}
