import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SlmpdDataV4HSharedModule } from 'app/shared/shared.module';

import { AuditsComponent } from './audits.component';

import { auditsRoute } from './audits.route';

@NgModule({
  imports: [SlmpdDataV4HSharedModule, RouterModule.forChild([auditsRoute])],
  declarations: [AuditsComponent]
})
export class AuditsModule {}
