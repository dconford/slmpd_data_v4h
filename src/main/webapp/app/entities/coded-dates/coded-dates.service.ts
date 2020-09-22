import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICodedDates } from 'app/shared/model/coded-dates.model';

type EntityResponseType = HttpResponse<ICodedDates>;
type EntityArrayResponseType = HttpResponse<ICodedDates[]>;

@Injectable({ providedIn: 'root' })
export class CodedDatesService {
  public resourceUrl = SERVER_API_URL + 'api/coded-dates';

  constructor(protected http: HttpClient) {}

  create(codedDates: ICodedDates): Observable<EntityResponseType> {
    return this.http.post<ICodedDates>(this.resourceUrl, codedDates, { observe: 'response' });
  }

  update(codedDates: ICodedDates): Observable<EntityResponseType> {
    return this.http.put<ICodedDates>(this.resourceUrl, codedDates, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICodedDates>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICodedDates[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
