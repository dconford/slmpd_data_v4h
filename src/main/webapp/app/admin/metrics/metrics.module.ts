import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SlmpdDataV4HSharedModule } from 'app/shared/shared.module';

import { MetricsComponent } from './metrics.component';

import { metricsRoute } from './metrics.route';

@NgModule({
  imports: [SlmpdDataV4HSharedModule, RouterModule.forChild([metricsRoute])],
  declarations: [MetricsComponent]
})
export class MetricsModule {}
