package de.vrees.heatpump.limitcheck;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FailureMessage {
    private FailureLevel failureLevel;

    private String msg;

    private String parameter;
}
