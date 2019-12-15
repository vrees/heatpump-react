package de.vrees.heatpump.domain;

import de.vrees.heatpump.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProcessdataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Processdata.class);
        Processdata processdata1 = new Processdata();
        processdata1.setId(1L);
        Processdata processdata2 = new Processdata();
        processdata2.setId(processdata1.getId());
        assertThat(processdata1).isEqualTo(processdata2);
        processdata2.setId(2L);
        assertThat(processdata1).isNotEqualTo(processdata2);
        processdata1.setId(null);
        assertThat(processdata1).isNotEqualTo(processdata2);
    }
}
