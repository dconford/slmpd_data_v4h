import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IReportedEvents, ReportedEvents } from 'app/shared/model/reported-events.model';
import { ReportedEventsService } from './reported-events.service';
import { ReportedEventsComponent } from './reported-events.component';
import { ReportedEventsDetailComponent } from './reported-events-detail.component';
import { ReportedEventsUpdateComponent } from './reported-events-update.component';

@Injectable({ providedIn: 'root' })
export class ReportedEventsResolve implements Resolve<IReportedEvents> {
  constructor(private service: ReportedEventsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReportedEvents> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((reportedEvents: HttpResponse<ReportedEvents>) => {
          if (reportedEvents.body) {
            return of(reportedEvents.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ReportedEvents());
  }
}

export const reportedEventsRoute: Routes = [
  {
    path: '',
    component: ReportedEventsComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ReportedEvents'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ReportedEventsDetailComponent,
    resolve: {
      reportedEvents: ReportedEventsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ReportedEvents'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ReportedEventsUpdateComponent,
    resolve: {
      reportedEvents: ReportedEventsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ReportedEvents'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ReportedEventsUpdateComponent,
    resolve: {
      reportedEvents: ReportedEventsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ReportedEvents'
    },
    canActivate: [UserRouteAccessService]
  }
];
