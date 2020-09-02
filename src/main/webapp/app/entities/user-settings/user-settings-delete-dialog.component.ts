import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserSettings } from 'app/shared/model/user-settings.model';
import { UserSettingsService } from './user-settings.service';

@Component({
  templateUrl: './user-settings-delete-dialog.component.html',
})
export class UserSettingsDeleteDialogComponent {
  userSettings?: IUserSettings;

  constructor(
    protected userSettingsService: UserSettingsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.userSettingsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userSettingsListModification');
      this.activeModal.close();
    });
  }
}
