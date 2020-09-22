import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SlmpdDataV4HSharedModule } from 'app/shared/shared.module';
import { CodedDatesComponent } from './coded-dates.component';
import { CodedDatesDetailComponent } from './coded-dates-detail.component';
import { CodedDatesUpdateComponent } from './coded-dates-update.component';
import { CodedDatesDeleteDialogComponent } from './coded-dates-delete-dialog.component';
import { codedDatesRoute } from './coded-dates.route';

@NgModule({
  imports: [SlmpdDataV4HSharedModule, RouterModule.forChild(codedDatesRoute)],
  declarations: [CodedDatesComponent, CodedDatesDetailComponent, CodedDatesUpdateComponent, CodedDatesDeleteDialogComponent],
  entryComponents: [CodedDatesDeleteDialogComponent]
})
export class SlmpdDataV4HCodedDatesModule {}
