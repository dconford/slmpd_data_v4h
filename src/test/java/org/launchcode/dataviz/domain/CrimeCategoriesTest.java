package org.launchcode.dataviz.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.launchcode.dataviz.web.rest.TestUtil;

public class CrimeCategoriesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CrimeCategories.class);
        CrimeCategories crimeCategories1 = new CrimeCategories();
        crimeCategories1.setId(1L);
        CrimeCategories crimeCategories2 = new CrimeCategories();
        crimeCategories2.setId(crimeCategories1.getId());
        assertThat(crimeCategories1).isEqualTo(crimeCategories2);
        crimeCategories2.setId(2L);
        assertThat(crimeCategories1).isNotEqualTo(crimeCategories2);
        crimeCategories1.setId(null);
        assertThat(crimeCategories1).isNotEqualTo(crimeCategories2);
    }
}
