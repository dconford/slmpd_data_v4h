package org.launchcode.dataviz.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.launchcode.dataviz.web.rest.TestUtil;

public class NeighborhoodsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Neighborhoods.class);
        Neighborhoods neighborhoods1 = new Neighborhoods();
        neighborhoods1.setId(1L);
        Neighborhoods neighborhoods2 = new Neighborhoods();
        neighborhoods2.setId(neighborhoods1.getId());
        assertThat(neighborhoods1).isEqualTo(neighborhoods2);
        neighborhoods2.setId(2L);
        assertThat(neighborhoods1).isNotEqualTo(neighborhoods2);
        neighborhoods1.setId(null);
        assertThat(neighborhoods1).isNotEqualTo(neighborhoods2);
    }
}
