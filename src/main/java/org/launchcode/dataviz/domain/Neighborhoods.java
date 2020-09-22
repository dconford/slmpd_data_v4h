package org.launchcode.dataviz.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Neighborhoods.
 */
@Entity
@Table(name = "neighborhoods")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Neighborhoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "neighborhood_name")
    private String neighborhoodName;

    @OneToMany(mappedBy = "neighborhoods")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ReportedEvents> reportedEvents = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNeighborhoodName() {
        return neighborhoodName;
    }

    public Neighborhoods neighborhoodName(String neighborhoodName) {
        this.neighborhoodName = neighborhoodName;
        return this;
    }

    public void setNeighborhoodName(String neighborhoodName) {
        this.neighborhoodName = neighborhoodName;
    }

    public Set<ReportedEvents> getReportedEvents() {
        return reportedEvents;
    }

    public Neighborhoods reportedEvents(Set<ReportedEvents> reportedEvents) {
        this.reportedEvents = reportedEvents;
        return this;
    }

    public Neighborhoods addReportedEvents(ReportedEvents reportedEvents) {
        this.reportedEvents.add(reportedEvents);
        reportedEvents.setNeighborhoods(this);
        return this;
    }

    public Neighborhoods removeReportedEvents(ReportedEvents reportedEvents) {
        this.reportedEvents.remove(reportedEvents);
        reportedEvents.setNeighborhoods(null);
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
        if (!(o instanceof Neighborhoods)) {
            return false;
        }
        return id != null && id.equals(((Neighborhoods) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Neighborhoods{" +
            "id=" + getId() +
            ", neighborhoodName='" + getNeighborhoodName() + "'" +
            "}";
    }
}
