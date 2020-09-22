package org.launchcode.dataviz.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CodedDates.
 */
@Entity
@Table(name = "coded_dates")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CodedDates implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "coded_date_string")
    private String codedDateString;

    @OneToMany(mappedBy = "codedDates")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ReportedEvents> reportedEvents = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodedDateString() {
        return codedDateString;
    }

    public CodedDates codedDateString(String codedDateString) {
        this.codedDateString = codedDateString;
        return this;
    }

    public void setCodedDateString(String codedDateString) {
        this.codedDateString = codedDateString;
    }

    public Set<ReportedEvents> getReportedEvents() {
        return reportedEvents;
    }

    public CodedDates reportedEvents(Set<ReportedEvents> reportedEvents) {
        this.reportedEvents = reportedEvents;
        return this;
    }

    public CodedDates addReportedEvents(ReportedEvents reportedEvents) {
        this.reportedEvents.add(reportedEvents);
        reportedEvents.setCodedDates(this);
        return this;
    }

    public CodedDates removeReportedEvents(ReportedEvents reportedEvents) {
        this.reportedEvents.remove(reportedEvents);
        reportedEvents.setCodedDates(null);
        return this;
    }

    public void setReportedEvents(Set<ReportedEvents> reportedEvents) {
        this.reportedEvents = reportedEvents;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CodedDates)) {
            return false;
        }
        return id != null && id.equals(((CodedDates) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CodedDates{" +
            "id=" + getId() +
            ", codedDateString='" + getCodedDateString() + "'" +
            "}";
    }
}
