import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ReportedEventsService } from 'app/entities/reported-events/reported-events.service';
import { IReportedEvents, ReportedEvents } from 'app/shared/model/reported-events.model';

describe('Service Tests', () => {
  describe('ReportedEvents Service', () => {
    let injector: TestBed;
    let service: ReportedEventsService;
    let httpMock: HttpTestingController;
    let elemDefault: IReportedEvents;
    let expectedResult: IReportedEvents | IReportedEvents[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ReportedEventsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ReportedEvents(
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            newDateField: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ReportedEvents', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            newDateField: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            newDateField: currentDate
          },
          returnedFromService
        );

        service.create(new ReportedEvents()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ReportedEvents', () => {
        const returnedFromService = Object.assign(
          {
            complaintId: 'BBBBBB',
            codedMonth: 'BBBBBB',
            codedMonthAsInt: 1,
            eventOccurred: 'BBBBBB',
            newCrimeflag: 'BBBBBB',
            crimeUnfoundedFlag: 'BBBBBB',
            administrativeAdjustmentFlag: 'BBBBBB',
            count: 1,
            cleanupFlag: 'BBBBBB',
            crimeCode: 1,
            districtCode: 1,
            eventDescription: 'BBBBBB',
            ileadsAddressNumber: 'BBBBBB',
            ileadsStreetName: 'BBBBBB',
            neighborhoodCode: 1,
            eventLocationName: 'BBBBBB',
            eventLocationComment: 'BBBBBB',
            cadStreetNumber: 'BBBBBB',
            cadStreetName: 'BBBBBB',
            xCoordinates: 'BBBBBB',
            yCoordinates: 'BBBBBB',
            crimeCategoryShortened: 1,
            newDateField: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            newDateField: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ReportedEvents', () => {
        const returnedFromService = Object.assign(
          {
            complaintId: 'BBBBBB',
            codedMonth: 'BBBBBB',
            codedMonthAsInt: 1,
            eventOccurred: 'BBBBBB',
            newCrimeflag: 'BBBBBB',
            crimeUnfoundedFlag: 'BBBBBB',
            administrativeAdjustmentFlag: 'BBBBBB',
            count: 1,
            cleanupFlag: 'BBBBBB',
            crimeCode: 1,
            districtCode: 1,
            eventDescription: 'BBBBBB',
            ileadsAddressNumber: 'BBBBBB',
            ileadsStreetName: 'BBBBBB',
            neighborhoodCode: 1,
            eventLocationName: 'BBBBBB',
            eventLocationComment: 'BBBBBB',
            cadStreetNumber: 'BBBBBB',
            cadStreetName: 'BBBBBB',
            xCoordinates: 'BBBBBB',
            yCoordinates: 'BBBBBB',
            crimeCategoryShortened: 1,
            newDateField: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            newDateField: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ReportedEvents', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
