import { Injectable } from '@angular/core';

import { HttpClient, HttpResponse } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { BasicCountComponent } from './basic-count.component';

@Injectable({
  providedIn: 'root'
})
export class BasicCountService {
  constructor() {}
}
