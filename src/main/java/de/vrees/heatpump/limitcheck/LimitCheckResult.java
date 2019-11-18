package de.vrees.heatpump.limitcheck;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LimitCheckResult {
    private LimitCheckEnum  limitCheck;
    private String value;
}
