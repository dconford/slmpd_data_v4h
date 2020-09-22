import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'crime-categories',
        loadChildren: () => import('./crime-categories/crime-categories.module').then(m => m.SlmpdDataV4HCrimeCategoriesModule)
      },
      {
        path: 'neighborhoods',
        loadChildren: () => import('./neighborhoods/neighborhoods.module').then(m => m.SlmpdDataV4HNeighborhoodsModule)
      },
      {
        path: 'coded-dates',
        loadChildren: () => import('./coded-dates/coded-dates.module').then(m => m.SlmpdDataV4HCodedDatesModule)
      },
      {
        path: 'reported-events',
        loadChildren: () => import('./reported-events/reported-events.module').then(m => m.SlmpdDataV4HReportedEventsModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class SlmpdDataV4HEntityModule {}
