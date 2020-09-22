package org.launchcode.dataviz.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CrimeCategories.
 */
@Entity
@Table(name = "crime_categories")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CrimeCategories implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "crime_category")
    private String crimeCategory;

    @OneToMany(mappedBy = "crimeCategories")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ReportedEvents> reportedEvents = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCrimeCategory() {
        return crimeCategory;
    }

    public CrimeCategories crimeCategory(String crimeCategory) {
        this.crimeCategory = crimeCategory;
        return this;
    }

    public void setCrimeCategory(String crimeCategory) {
        this.crimeCategory = crimeCategory;
    }

    public Set<ReportedEvents> getReportedEvents() {
        return reportedEvents;
    }

    public CrimeCategories reportedEvents(Set<ReportedEvents> reportedEvents) {
        this.reportedEvents = reportedEvents;
        return this;
    }

    public CrimeCategories addReportedEvents(ReportedEvents reportedEvents) {
        this.reportedEvents.add(reportedEvents);
        reportedEvents.setCrimeCategories(this);
        return this;
    }

    public CrimeCategories removeReportedEvents(ReportedEvents reportedEvents) {
        this.reportedEvents.remove(reportedEvents);
        reportedEvents.setCrimeCategories(null);
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
        if (!(o instanceof CrimeCategories)) {
            return false;
        }
        return id != null && id.equals(((CrimeCategories) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CrimeCategories{" +
            "id=" + getId() +
            ", crimeCategory='" + getCrimeCategory() + "'" +
            "}";
    }
}
