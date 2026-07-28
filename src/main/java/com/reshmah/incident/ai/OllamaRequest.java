package com.reshmah.incident.ai;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OllamaRequest {

    private String model;
    private String prompt;
    private boolean stream;
    private String format;

}