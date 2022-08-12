package com.vensr.evrete.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Report {
    
    private Patient patient;

    private Integer temperature;

    private boolean hasCough;

}
