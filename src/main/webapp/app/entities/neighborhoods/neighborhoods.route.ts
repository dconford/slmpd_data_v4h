import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INeighborhoods, Neighborhoods } from 'app/shared/model/neighborhoods.model';
import { NeighborhoodsService } from './neighborhoods.service';
import { NeighborhoodsComponent } from './neighborhoods.component';
import { NeighborhoodsDetailComponent } from './neighborhoods-detail.component';
import { NeighborhoodsUpdateComponent } from './neighborhoods-update.component';

@Injectable({ providedIn: 'root' })
export class NeighborhoodsResolve implements Resolve<INeighborhoods> {
  constructor(private service: NeighborhoodsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INeighborhoods> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((neighborhoods: HttpResponse<Neighborhoods>) => {
          if (neighborhoods.body) {
            return of(neighborhoods.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Neighborhoods());
  }
}

export const neighborhoodsRoute: Routes = [
  {
    path: '',
    component: NeighborhoodsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Neighborhoods'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NeighborhoodsDetailComponent,
    resolve: {
      neighborhoods: NeighborhoodsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Neighborhoods'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NeighborhoodsUpdateComponent,
    resolve: {
      neighborhoods: NeighborhoodsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Neighborhoods'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NeighborhoodsUpdateComponent,
    resolve: {
      neighborhoods: NeighborhoodsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Neighborhoods'
    },
    canActivate: [UserRouteAccessService]
  }
];
