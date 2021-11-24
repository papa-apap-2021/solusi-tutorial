package apap.tutorial.cineplux.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BioskopDetail {
    private String status;

    @JsonProperty("bioskop-license")
    private Integer bioskopLicense;

    @JsonProperty("valid-until")
    private Date validUntil;
}
