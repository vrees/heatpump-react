package de.vrees.heatpump.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.vrees.heatpump.web.rest.TestUtil;

public class FailureMessageTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FailureMessage.class);
        FailureMessage failureMessage1 = new FailureMessage();
        failureMessage1.setId(1L);
        FailureMessage failureMessage2 = new FailureMessage();
        failureMessage2.setId(failureMessage1.getId());
        assertThat(failureMessage1).isEqualTo(failureMessage2);
        failureMessage2.setId(2L);
        assertThat(failureMessage1).isNotEqualTo(failureMessage2);
        failureMessage1.setId(null);
        assertThat(failureMessage1).isNotEqualTo(failureMessage2);
    }
}
