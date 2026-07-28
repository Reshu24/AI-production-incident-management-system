package com.reshmah.incident.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChartData {

    private String label;
    private long count;
}
