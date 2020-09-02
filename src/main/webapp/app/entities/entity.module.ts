import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.SpectralinkUserServiceCustomerModule),
      },
      {
        path: 'organization',
        loadChildren: () => import('./organization/organization.module').then(m => m.SpectralinkUserServiceOrganizationModule),
      },
      {
        path: 'end-user',
        loadChildren: () => import('./end-user/end-user.module').then(m => m.SpectralinkUserServiceEndUserModule),
      },
      {
        path: 'contact',
        loadChildren: () => import('./contact/contact.module').then(m => m.SpectralinkUserServiceContactModule),
      },
      {
        path: 'user-settings',
        loadChildren: () => import('./user-settings/user-settings.module').then(m => m.SpectralinkUserServiceUserSettingsModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class SpectralinkUserServiceEntityModule {}
