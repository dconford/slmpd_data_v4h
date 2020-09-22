import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SlmpdDataV4HSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [SlmpdDataV4HSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent]
})
export class SlmpdDataV4HHomeModule {}
