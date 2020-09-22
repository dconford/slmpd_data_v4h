package org.launchcode.dataviz.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.launchcode.dataviz.web.rest.TestUtil;

public class ReportedEventsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportedEvents.class);
        ReportedEvents reportedEvents1 = new ReportedEvents();
        reportedEvents1.setId(1L);
        ReportedEvents reportedEvents2 = new ReportedEvents();
        reportedEvents2.setId(reportedEvents1.getId());
        assertThat(reportedEvents1).isEqualTo(reportedEvents2);
        reportedEvents2.setId(2L);
        assertThat(reportedEvents1).isNotEqualTo(reportedEvents2);
        reportedEvents1.setId(null);
        assertThat(reportedEvents1).isNotEqualTo(reportedEvents2);
    }
}
