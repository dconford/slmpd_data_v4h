import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IReportedEvents } from 'app/shared/model/reported-events.model';

type EntityResponseType = HttpResponse<IReportedEvents>;
type EntityArrayResponseType = HttpResponse<IReportedEvents[]>;

@Injectable({ providedIn: 'root' })
export class ReportedEventsService {
  public resourceUrl = SERVER_API_URL + 'api/reported-events';

  constructor(protected http: HttpClient) {}

  create(reportedEvents: IReportedEvents): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reportedEvents);
    return this.http
      .post<IReportedEvents>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(reportedEvents: IReportedEvents): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reportedEvents);
    return this.http
      .put<IReportedEvents>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IReportedEvents>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IReportedEvents[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(reportedEvents: IReportedEvents): IReportedEvents {
    const copy: IReportedEvents = Object.assign({}, reportedEvents, {
      newDateField:
        reportedEvents.newDateField && reportedEvents.newDateField.isValid() ? reportedEvents.newDateField.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.newDateField = res.body.newDateField ? moment(res.body.newDateField) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((reportedEvents: IReportedEvents) => {
        reportedEvents.newDateField = reportedEvents.newDateField ? moment(reportedEvents.newDateField) : undefined;
      });
    }
    return res;
  }
}
