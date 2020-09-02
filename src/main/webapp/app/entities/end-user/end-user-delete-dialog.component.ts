import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEndUser } from 'app/shared/model/end-user.model';
import { EndUserService } from './end-user.service';

@Component({
  templateUrl: './end-user-delete-dialog.component.html',
})
export class EndUserDeleteDialogComponent {
  endUser?: IEndUser;

  constructor(protected endUserService: EndUserService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.endUserService.delete(id).subscribe(() => {
      this.eventManager.broadcast('endUserListModification');
      this.activeModal.close();
    });
  }
}
