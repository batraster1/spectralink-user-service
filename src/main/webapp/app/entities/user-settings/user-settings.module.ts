import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpectralinkUserServiceSharedModule } from 'app/shared/shared.module';
import { UserSettingsComponent } from './user-settings.component';
import { UserSettingsDetailComponent } from './user-settings-detail.component';
import { UserSettingsUpdateComponent } from './user-settings-update.component';
import { UserSettingsDeleteDialogComponent } from './user-settings-delete-dialog.component';
import { userSettingsRoute } from './user-settings.route';

@NgModule({
  imports: [SpectralinkUserServiceSharedModule, RouterModule.forChild(userSettingsRoute)],
  declarations: [UserSettingsComponent, UserSettingsDetailComponent, UserSettingsUpdateComponent, UserSettingsDeleteDialogComponent],
  entryComponents: [UserSettingsDeleteDialogComponent],
})
export class SpectralinkUserServiceUserSettingsModule {}
