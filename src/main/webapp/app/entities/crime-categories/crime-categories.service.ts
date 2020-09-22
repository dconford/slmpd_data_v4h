import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICrimeCategories } from 'app/shared/model/crime-categories.model';

type EntityResponseType = HttpResponse<ICrimeCategories>;
type EntityArrayResponseType = HttpResponse<ICrimeCategories[]>;

@Injectable({ providedIn: 'root' })
export class CrimeCategoriesService {
  public resourceUrl = SERVER_API_URL + 'api/crime-categories';

  constructor(protected http: HttpClient) {}

  create(crimeCategories: ICrimeCategories): Observable<EntityResponseType> {
    return this.http.post<ICrimeCategories>(this.resourceUrl, crimeCategories, { observe: 'response' });
  }

  update(crimeCategories: ICrimeCategories): Observable<EntityResponseType> {
    return this.http.put<ICrimeCategories>(this.resourceUrl, crimeCategories, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICrimeCategories>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICrimeCategories[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
