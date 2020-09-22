import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SlmpdDataV4HSharedModule } from 'app/shared/shared.module';
import { NeighborhoodsComponent } from './neighborhoods.component';
import { NeighborhoodsDetailComponent } from './neighborhoods-detail.component';
import { NeighborhoodsUpdateComponent } from './neighborhoods-update.component';
import { NeighborhoodsDeleteDialogComponent } from './neighborhoods-delete-dialog.component';
import { neighborhoodsRoute } from './neighborhoods.route';

@NgModule({
  imports: [SlmpdDataV4HSharedModule, RouterModule.forChild(neighborhoodsRoute)],
  declarations: [NeighborhoodsComponent, NeighborhoodsDetailComponent, NeighborhoodsUpdateComponent, NeighborhoodsDeleteDialogComponent],
  entryComponents: [NeighborhoodsDeleteDialogComponent]
})
export class SlmpdDataV4HNeighborhoodsModule {}
