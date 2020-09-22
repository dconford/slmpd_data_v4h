import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { SlmpdDataV4HSharedModule } from 'app/shared/shared.module';
import { SlmpdDataV4HCoreModule } from 'app/core/core.module';
import { SlmpdDataV4HAppRoutingModule } from './app-routing.module';
import { SlmpdDataV4HHomeModule } from './home/home.module';
import { SlmpdDataV4HEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    SlmpdDataV4HSharedModule,
    SlmpdDataV4HCoreModule,
    SlmpdDataV4HHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    SlmpdDataV4HEntityModule,
    SlmpdDataV4HAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class SlmpdDataV4HAppModule {}
