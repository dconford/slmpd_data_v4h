import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SlmpdDataV4HSharedModule } from 'app/shared/shared.module';

import { DocsComponent } from './docs.component';

import { docsRoute } from './docs.route';

@NgModule({
  imports: [SlmpdDataV4HSharedModule, RouterModule.forChild([docsRoute])],
  declarations: [DocsComponent]
})
export class DocsModule {}
