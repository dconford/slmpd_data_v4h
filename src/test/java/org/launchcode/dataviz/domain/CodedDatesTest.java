package org.launchcode.dataviz.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.launchcode.dataviz.web.rest.TestUtil;

public class CodedDatesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CodedDates.class);
        CodedDates codedDates1 = new CodedDates();
        codedDates1.setId(1L);
        CodedDates codedDates2 = new CodedDates();
        codedDates2.setId(codedDates1.getId());
        assertThat(codedDates1).isEqualTo(codedDates2);
        codedDates2.setId(2L);
        assertThat(codedDates1).isNotEqualTo(codedDates2);
        codedDates1.setId(null);
        assertThat(codedDates1).isNotEqualTo(codedDates2);
    }
}
