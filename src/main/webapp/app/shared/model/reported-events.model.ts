import { Moment } from 'moment';
import { ICrimeCategories } from 'app/shared/model/crime-categories.model';
import { INeighborhoods } from 'app/shared/model/neighborhoods.model';
import { ICodedDates } from 'app/shared/model/coded-dates.model';

export interface IReportedEvents {
  id?: number;
  complaintId?: string;
  codedMonth?: string;
  codedMonthAsInt?: number;
  eventOccurred?: string;
  newCrimeflag?: string;
  crimeUnfoundedFlag?: string;
  administrativeAdjustmentFlag?: string;
  count?: number;
  cleanupFlag?: string;
  crimeCode?: number;
  districtCode?: number;
  eventDescription?: string;
  ileadsAddressNumber?: string;
  ileadsStreetName?: string;
  neighborhoodCode?: number;
  eventLocationName?: string;
  eventLocationComment?: string;
  cadStreetNumber?: string;
  cadStreetName?: string;
  xCoordinates?: string;
  yCoordinates?: string;
  crimeCategoryShortened?: number;
  newDateField?: Moment;
  crimeCategories?: ICrimeCategories;
  neighborhoods?: INeighborhoods;
  codedDates?: ICodedDates;
}

export class ReportedEvents implements IReportedEvents {
  constructor(
    public id?: number,
    public complaintId?: string,
    public codedMonth?: string,
    public codedMonthAsInt?: number,
    public eventOccurred?: string,
    public newCrimeflag?: string,
    public crimeUnfoundedFlag?: string,
    public administrativeAdjustmentFlag?: string,
    public count?: number,
    public cleanupFlag?: string,
    public crimeCode?: number,
    public districtCode?: number,
    public eventDescription?: string,
    public ileadsAddressNumber?: string,
    public ileadsStreetName?: string,
    public neighborhoodCode?: number,
    public eventLocationName?: string,
    public eventLocationComment?: string,
    public cadStreetNumber?: string,
    public cadStreetName?: string,
    public xCoordinates?: string,
    public yCoordinates?: string,
    public crimeCategoryShortened?: number,
    public newDateField?: Moment,
    public crimeCategories?: ICrimeCategories,
    public neighborhoods?: INeighborhoods,
    public codedDates?: ICodedDates
  ) {}
}
