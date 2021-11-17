package apap.tutorial.emsidi.restcontroller;

import apap.tutorial.emsidi.model.CabangModel;
import apap.tutorial.emsidi.rest.CabangDetail;
import apap.tutorial.emsidi.service.CabangRestService;
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
public class CabangRestController {
    @Autowired
    private CabangRestService cabangRestService;

    @PostMapping(value = "/cabang")
    private CabangModel createCabang(@Valid @RequestBody CabangModel cabang, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field."
            );

        } else {
            return cabangRestService.createCabang(cabang);
        }
    }

    @GetMapping(value = "/cabang/{noCabang}")
    private CabangModel retrieveCabang(@PathVariable("noCabang") Long noCabang) {
        try {
            return cabangRestService.getCabangByNoCabang(noCabang);

        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "ID Cabang " + String.valueOf(noCabang) + " Not Found."
            );
        }
    }

    @DeleteMapping(value = "/cabang/{noCabang}")
    private ResponseEntity deleteCabang(@PathVariable("noCabang") Long noCabang) {
        try {
            cabangRestService.deleteCabang(noCabang);
            return ResponseEntity.ok("Cabang with ID " + String.valueOf(noCabang) + " Deleted!");

        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cabang with ID " + String.valueOf(noCabang) + " Not Found!"
            );

        } catch (UnsupportedOperationException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Cabang is still open or has pegawai!"
            );
        }
    }

    @PutMapping(value = "/cabang/{noCabang}")
    private CabangModel updateCabang(@PathVariable("noCabang") Long noCabang, @RequestBody CabangModel cabang) {
        try {
            return cabangRestService.updateCabang(noCabang, cabang);

        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cabang with ID " + String.valueOf(noCabang) + " Not Found!"
            );
        }
    }

    @GetMapping(value = "/list-cabang")
    private List<CabangModel> retrieveListCabang() { return cabangRestService.retrieveListCabang(); }

    @GetMapping(value = "/cabang/{noCabang}/status")
    private Mono<String> getStatus(@PathVariable("noCabang") Long noCabang) { return cabangRestService.getStatus(noCabang); }

    @GetMapping(value = "/full")
    private Mono<CabangDetail> postStatus() { return cabangRestService.postStatus(); }
}
