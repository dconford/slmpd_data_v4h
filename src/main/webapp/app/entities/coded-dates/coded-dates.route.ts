import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICodedDates, CodedDates } from 'app/shared/model/coded-dates.model';
import { CodedDatesService } from './coded-dates.service';
import { CodedDatesComponent } from './coded-dates.component';
import { CodedDatesDetailComponent } from './coded-dates-detail.component';
import { CodedDatesUpdateComponent } from './coded-dates-update.component';

@Injectable({ providedIn: 'root' })
export class CodedDatesResolve implements Resolve<ICodedDates> {
  constructor(private service: CodedDatesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICodedDates> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((codedDates: HttpResponse<CodedDates>) => {
          if (codedDates.body) {
            return of(codedDates.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CodedDates());
  }
}

export const codedDatesRoute: Routes = [
  {
    path: '',
    component: CodedDatesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CodedDates'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CodedDatesDetailComponent,
    resolve: {
      codedDates: CodedDatesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CodedDates'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CodedDatesUpdateComponent,
    resolve: {
      codedDates: CodedDatesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CodedDates'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CodedDatesUpdateComponent,
    resolve: {
      codedDates: CodedDatesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CodedDates'
    },
    canActivate: [UserRouteAccessService]
  }
];
