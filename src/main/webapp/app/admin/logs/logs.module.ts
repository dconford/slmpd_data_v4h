import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SlmpdDataV4HSharedModule } from 'app/shared/shared.module';

import { LogsComponent } from './logs.component';

import { logsRoute } from './logs.route';

@NgModule({
  imports: [SlmpdDataV4HSharedModule, RouterModule.forChild([logsRoute])],
  declarations: [LogsComponent]
})
export class LogsModule {}
