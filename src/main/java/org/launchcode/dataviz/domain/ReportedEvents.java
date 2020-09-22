package org.launchcode.dataviz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ReportedEvents.
 */
@Entity
@Table(name = "reported_events")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ReportedEvents implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "complaint_id")
    private String complaintId;

    @Column(name = "coded_month")
    private String codedMonth;

    @Column(name = "coded_month_as_int")
    private Integer codedMonthAsInt;

    @Column(name = "event_occurred")
    private String eventOccurred;

    @Column(name = "new_crimeflag")
    private String newCrimeflag;

    @Column(name = "crime_unfounded_flag")
    private String crimeUnfoundedFlag;

    @Column(name = "administrative_adjustment_flag")
    private String administrativeAdjustmentFlag;

    @Column(name = "count")
    private Integer count;

    @Column(name = "cleanup_flag")
    private String cleanupFlag;

    @Column(name = "crime_code")
    private Integer crimeCode;

    @Column(name = "district_code")
    private Integer districtCode;

    @Column(name = "event_description")
    private String eventDescription;

    @Column(name = "ileads_address_number")
    private String ileadsAddressNumber;

    @Column(name = "ileads_street_name")
    private String ileadsStreetName;

    @Column(name = "neighborhood_code")
    private Integer neighborhoodCode;

    @Column(name = "event_location_name")
    private String eventLocationName;

    @Column(name = "event_location_comment")
    private String eventLocationComment;

    @Column(name = "cad_street_number")
    private String cadStreetNumber;

    @Column(name = "cad_street_name")
    private String cadStreetName;

    @Column(name = "x_coordinates")
    private String xCoordinates;

    @Column(name = "y_coordinates")
    private String yCoordinates;

    @Column(name = "crime_category_shortened")
    private Integer crimeCategoryShortened;

    @Column(name = "new_date_field")
    private LocalDate newDateField;

    @ManyToOne
    @JsonIgnoreProperties("reportedEvents")
    private CrimeCategories crimeCategories;

    @ManyToOne
    @JsonIgnoreProperties("reportedEvents")
    private Neighborhoods neighborhoods;

    @ManyToOne
    @JsonIgnoreProperties("reportedEvents")
    private CodedDates codedDates;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComplaintId() {
        return complaintId;
    }

    public ReportedEvents complaintId(String complaintId) {
        this.complaintId = complaintId;
        return this;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public String getCodedMonth() {
        return codedMonth;
    }

    public ReportedEvents codedMonth(String codedMonth) {
        this.codedMonth = codedMonth;
        return this;
    }

    public void setCodedMonth(String codedMonth) {
        this.codedMonth = codedMonth;
    }

    public Integer getCodedMonthAsInt() {
        return codedMonthAsInt;
    }

    public ReportedEvents codedMonthAsInt(Integer codedMonthAsInt) {
        this.codedMonthAsInt = codedMonthAsInt;
        return this;
    }

    public void setCodedMonthAsInt(Integer codedMonthAsInt) {
        this.codedMonthAsInt = codedMonthAsInt;
    }

    public String getEventOccurred() {
        return eventOccurred;
    }

    public ReportedEvents eventOccurred(String eventOccurred) {
        this.eventOccurred = eventOccurred;
        return this;
    }

    public void setEventOccurred(String eventOccurred) {
        this.eventOccurred = eventOccurred;
    }

    public String getNewCrimeflag() {
        return newCrimeflag;
    }

    public ReportedEvents newCrimeflag(String newCrimeflag) {
        this.newCrimeflag = newCrimeflag;
        return this;
    }

    public void setNewCrimeflag(String newCrimeflag) {
        this.newCrimeflag = newCrimeflag;
    }

    public String getCrimeUnfoundedFlag() {
        return crimeUnfoundedFlag;
    }

    public ReportedEvents crimeUnfoundedFlag(String crimeUnfoundedFlag) {
        this.crimeUnfoundedFlag = crimeUnfoundedFlag;
        return this;
    }

    public void setCrimeUnfoundedFlag(String crimeUnfoundedFlag) {
        this.crimeUnfoundedFlag = crimeUnfoundedFlag;
    }

    public String getAdministrativeAdjustmentFlag() {
        return administrativeAdjustmentFlag;
    }

    public ReportedEvents administrativeAdjustmentFlag(String administrativeAdjustmentFlag) {
        this.administrativeAdjustmentFlag = administrativeAdjustmentFlag;
        return this;
    }

    public void setAdministrativeAdjustmentFlag(String administrativeAdjustmentFlag) {
        this.administrativeAdjustmentFlag = administrativeAdjustmentFlag;
    }

    public Integer getCount() {
        return count;
    }

    public ReportedEvents count(Integer count) {
        this.count = count;
        return this;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getCleanupFlag() {
        return cleanupFlag;
    }

    public ReportedEvents cleanupFlag(String cleanupFlag) {
        this.cleanupFlag = cleanupFlag;
        return this;
    }

    public void setCleanupFlag(String cleanupFlag) {
        this.cleanupFlag = cleanupFlag;
    }

    public Integer getCrimeCode() {
        return crimeCode;
    }

    public ReportedEvents crimeCode(Integer crimeCode) {
        this.crimeCode = crimeCode;
        return this;
    }

    public void setCrimeCode(Integer crimeCode) {
        this.crimeCode = crimeCode;
    }

    public Integer getDistrictCode() {
        return districtCode;
    }

    public ReportedEvents districtCode(Integer districtCode) {
        this.districtCode = districtCode;
        return this;
    }

    public void setDistrictCode(Integer districtCode) {
        this.districtCode = districtCode;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public ReportedEvents eventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
        return this;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getIleadsAddressNumber() {
        return ileadsAddressNumber;
    }

    public ReportedEvents ileadsAddressNumber(String ileadsAddressNumber) {
        this.ileadsAddressNumber = ileadsAddressNumber;
        return this;
    }

    public void setIleadsAddressNumber(String ileadsAddressNumber) {
        this.ileadsAddressNumber = ileadsAddressNumber;
    }

    public String getIleadsStreetName() {
        return ileadsStreetName;
    }

    public ReportedEvents ileadsStreetName(String ileadsStreetName) {
        this.ileadsStreetName = ileadsStreetName;
        return this;
    }

    public void setIleadsStreetName(String ileadsStreetName) {
        this.ileadsStreetName = ileadsStreetName;
    }

    public Integer getNeighborhoodCode() {
        return neighborhoodCode;
    }

    public ReportedEvents neighborhoodCode(Integer neighborhoodCode) {
        this.neighborhoodCode = neighborhoodCode;
        return this;
    }

    public void setNeighborhoodCode(Integer neighborhoodCode) {
        this.neighborhoodCode = neighborhoodCode;
    }

    public String getEventLocationName() {
        return eventLocationName;
    }

    public ReportedEvents eventLocationName(String eventLocationName) {
        this.eventLocationName = eventLocationName;
        return this;
    }

    public void setEventLocationName(String eventLocationName) {
        this.eventLocationName = eventLocationName;
    }

    public String getEventLocationComment() {
        return eventLocationComment;
    }

    public ReportedEvents eventLocationComment(String eventLocationComment) {
        this.eventLocationComment = eventLocationComment;
        return this;
    }

    public void setEventLocationComment(String eventLocationComment) {
        this.eventLocationComment = eventLocationComment;
    }

    public String getCadStreetNumber() {
        return cadStreetNumber;
    }

    public ReportedEvents cadStreetNumber(String cadStreetNumber) {
        this.cadStreetNumber = cadStreetNumber;
        return this;
    }

    public void setCadStreetNumber(String cadStreetNumber) {
        this.cadStreetNumber = cadStreetNumber;
    }

    public String getCadStreetName() {
        return cadStreetName;
    }

    public ReportedEvents cadStreetName(String cadStreetName) {
        this.cadStreetName = cadStreetName;
        return this;
    }

    public void setCadStreetName(String cadStreetName) {
        this.cadStreetName = cadStreetName;
    }

    public String getxCoordinates() {
        return xCoordinates;
    }

    public ReportedEvents xCoordinates(String xCoordinates) {
        this.xCoordinates = xCoordinates;
        return this;
    }

    public void setxCoordinates(String xCoordinates) {
        this.xCoordinates = xCoordinates;
    }

    public String getyCoordinates() {
        return yCoordinates;
    }

    public ReportedEvents yCoordinates(String yCoordinates) {
        this.yCoordinates = yCoordinates;
        return this;
    }

    public void setyCoordinates(String yCoordinates) {
        this.yCoordinates = yCoordinates;
    }

    public Integer getCrimeCategoryShortened() {
        return crimeCategoryShortened;
    }

    public ReportedEvents crimeCategoryShortened(Integer crimeCategoryShortened) {
        this.crimeCategoryShortened = crimeCategoryShortened;
        return this;
    }

    public void setCrimeCategoryShortened(Integer crimeCategoryShortened) {
        this.crimeCategoryShortened = crimeCategoryShortened;
    }

    public LocalDate getNewDateField() {
        return newDateField;
    }

    public ReportedEvents newDateField(LocalDate newDateField) {
        this.newDateField = newDateField;
        return this;
    }

    public void setNewDateField(LocalDate newDateField) {
        this.newDateField = newDateField;
    }

    public CrimeCategories getCrimeCategories() {
        return crimeCategories;
    }

    public ReportedEvents crimeCategories(CrimeCategories crimeCategories) {
        this.crimeCategories = crimeCategories;
        return this;
    }

    public void setCrimeCategories(CrimeCategories crimeCategories) {
        this.crimeCategories = crimeCategories;
    }

    public Neighborhoods getNeighborhoods() {
        return neighborhoods;
    }

    public ReportedEvents neighborhoods(Neighborhoods neighborhoods) {
        this.neighborhoods = neighborhoods;
        return this;
    }

    public void setNeighborhoods(Neighborhoods neighborhoods) {
        this.neighborhoods = neighborhoods;
    }

    public CodedDates getCodedDates() {
        return codedDates;
    }

    public ReportedEvents codedDates(CodedDates codedDates) {
        this.codedDates = codedDates;
        return this;
    }

    public void setCodedDates(CodedDates codedDates) {
        this.codedDates = codedDates;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportedEvents)) {
            return false;
        }
        return id != null && id.equals(((ReportedEvents) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ReportedEvents{" +
            "id=" + getId() +
            ", complaintId='" + getComplaintId() + "'" +
            ", codedMonth='" + getCodedMonth() + "'" +
            ", codedMonthAsInt=" + getCodedMonthAsInt() +
            ", eventOccurred='" + getEventOccurred() + "'" +
            ", newCrimeflag='" + getNewCrimeflag() + "'" +
            ", crimeUnfoundedFlag='" + getCrimeUnfoundedFlag() + "'" +
            ", administrativeAdjustmentFlag='" + getAdministrativeAdjustmentFlag() + "'" +
            ", count=" + getCount() +
            ", cleanupFlag='" + getCleanupFlag() + "'" +
            ", crimeCode=" + getCrimeCode() +
            ", districtCode=" + getDistrictCode() +
            ", eventDescription='" + getEventDescription() + "'" +
            ", ileadsAddressNumber='" + getIleadsAddressNumber() + "'" +
            ", ileadsStreetName='" + getIleadsStreetName() + "'" +
            ", neighborhoodCode=" + getNeighborhoodCode() +
            ", eventLocationName='" + getEventLocationName() + "'" +
            ", eventLocationComment='" + getEventLocationComment() + "'" +
            ", cadStreetNumber='" + getCadStreetNumber() + "'" +
            ", cadStreetName='" + getCadStreetName() + "'" +
            ", xCoordinates='" + getxCoordinates() + "'" +
            ", yCoordinates='" + getyCoordinates() + "'" +
            ", crimeCategoryShortened=" + getCrimeCategoryShortened() +
            ", newDateField='" + getNewDateField() + "'" +
            "}";
    }
}
