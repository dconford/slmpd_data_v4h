import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INeighborhoods } from 'app/shared/model/neighborhoods.model';

type EntityResponseType = HttpResponse<INeighborhoods>;
type EntityArrayResponseType = HttpResponse<INeighborhoods[]>;

@Injectable({ providedIn: 'root' })
export class NeighborhoodsService {
  public resourceUrl = SERVER_API_URL + 'api/neighborhoods';

  constructor(protected http: HttpClient) {}

  create(neighborhoods: INeighborhoods): Observable<EntityResponseType> {
    return this.http.post<INeighborhoods>(this.resourceUrl, neighborhoods, { observe: 'response' });
  }

  update(neighborhoods: INeighborhoods): Observable<EntityResponseType> {
    return this.http.put<INeighborhoods>(this.resourceUrl, neighborhoods, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INeighborhoods>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INeighborhoods[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
