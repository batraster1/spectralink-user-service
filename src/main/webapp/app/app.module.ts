import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { SpectralinkUserServiceSharedModule } from 'app/shared/shared.module';
import { SpectralinkUserServiceCoreModule } from 'app/core/core.module';
import { SpectralinkUserServiceAppRoutingModule } from './app-routing.module';
import { SpectralinkUserServiceHomeModule } from './home/home.module';
import { SpectralinkUserServiceEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    SpectralinkUserServiceSharedModule,
    SpectralinkUserServiceCoreModule,
    SpectralinkUserServiceHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    SpectralinkUserServiceEntityModule,
    SpectralinkUserServiceAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class SpectralinkUserServiceAppModule {}
