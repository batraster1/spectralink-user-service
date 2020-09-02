import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpectralinkUserServiceSharedModule } from 'app/shared/shared.module';
import { EndUserComponent } from './end-user.component';
import { EndUserDetailComponent } from './end-user-detail.component';
import { EndUserUpdateComponent } from './end-user-update.component';
import { EndUserDeleteDialogComponent } from './end-user-delete-dialog.component';
import { endUserRoute } from './end-user.route';

@NgModule({
  imports: [SpectralinkUserServiceSharedModule, RouterModule.forChild(endUserRoute)],
  declarations: [EndUserComponent, EndUserDetailComponent, EndUserUpdateComponent, EndUserDeleteDialogComponent],
  entryComponents: [EndUserDeleteDialogComponent],
})
export class SpectralinkUserServiceEndUserModule {}
