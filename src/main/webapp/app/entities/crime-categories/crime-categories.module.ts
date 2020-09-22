import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SlmpdDataV4HSharedModule } from 'app/shared/shared.module';
import { CrimeCategoriesComponent } from './crime-categories.component';
import { CrimeCategoriesDetailComponent } from './crime-categories-detail.component';
import { CrimeCategoriesUpdateComponent } from './crime-categories-update.component';
import { CrimeCategoriesDeleteDialogComponent } from './crime-categories-delete-dialog.component';
import { crimeCategoriesRoute } from './crime-categories.route';

@NgModule({
  imports: [SlmpdDataV4HSharedModule, RouterModule.forChild(crimeCategoriesRoute)],
  declarations: [
    CrimeCategoriesComponent,
    CrimeCategoriesDetailComponent,
    CrimeCategoriesUpdateComponent,
    CrimeCategoriesDeleteDialogComponent
  ],
  entryComponents: [CrimeCategoriesDeleteDialogComponent]
})
export class SlmpdDataV4HCrimeCategoriesModule {}
