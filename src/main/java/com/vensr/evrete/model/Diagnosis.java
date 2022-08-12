package com.vensr.evrete.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Diagnosis {

    private Patient patient;

    private boolean hasFever;

    private boolean hasCough;
    
}
