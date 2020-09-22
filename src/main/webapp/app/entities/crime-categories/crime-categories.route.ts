import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICrimeCategories, CrimeCategories } from 'app/shared/model/crime-categories.model';
import { CrimeCategoriesService } from './crime-categories.service';
import { CrimeCategoriesComponent } from './crime-categories.component';
import { CrimeCategoriesDetailComponent } from './crime-categories-detail.component';
import { CrimeCategoriesUpdateComponent } from './crime-categories-update.component';

@Injectable({ providedIn: 'root' })
export class CrimeCategoriesResolve implements Resolve<ICrimeCategories> {
  constructor(private service: CrimeCategoriesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICrimeCategories> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((crimeCategories: HttpResponse<CrimeCategories>) => {
          if (crimeCategories.body) {
            return of(crimeCategories.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CrimeCategories());
  }
}

export const crimeCategoriesRoute: Routes = [
  {
    path: '',
    component: CrimeCategoriesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CrimeCategories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CrimeCategoriesDetailComponent,
    resolve: {
      crimeCategories: CrimeCategoriesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CrimeCategories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CrimeCategoriesUpdateComponent,
    resolve: {
      crimeCategories: CrimeCategoriesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CrimeCategories'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CrimeCategoriesUpdateComponent,
    resolve: {
      crimeCategories: CrimeCategoriesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CrimeCategories'
    },
    canActivate: [UserRouteAccessService]
  }
];
