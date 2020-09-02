import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IContact, Contact } from 'app/shared/model/contact.model';
import { ContactService } from './contact.service';
import { IEndUser } from 'app/shared/model/end-user.model';
import { EndUserService } from 'app/entities/end-user/end-user.service';

@Component({
  selector: 'jhi-contact-update',
  templateUrl: './contact-update.component.html',
})
export class ContactUpdateComponent implements OnInit {
  isSaving = false;
  endusers: IEndUser[] = [];

  editForm = this.fb.group({
    id: [],
    firstName: [null, [Validators.required]],
    lastName: [],
    email: [],
    phoneNumber: [],
    sipExtension: [],
    created: [],
    contactIcon: [],
    endUser: [],
  });

  constructor(
    protected contactService: ContactService,
    protected endUserService: EndUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contact }) => {
      if (!contact.id) {
        const today = moment().startOf('day');
        contact.created = today;
      }

      this.updateForm(contact);

      this.endUserService.query().subscribe((res: HttpResponse<IEndUser[]>) => (this.endusers = res.body || []));
    });
  }

  updateForm(contact: IContact): void {
    this.editForm.patchValue({
      id: contact.id,
      firstName: contact.firstName,
      lastName: contact.lastName,
      email: contact.email,
      phoneNumber: contact.phoneNumber,
      sipExtension: contact.sipExtension,
      created: contact.created ? contact.created.format(DATE_TIME_FORMAT) : null,
      contactIcon: contact.contactIcon,
      endUser: contact.endUser,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contact = this.createFromForm();
    if (contact.id !== undefined) {
      this.subscribeToSaveResponse(this.contactService.update(contact));
    } else {
      this.subscribeToSaveResponse(this.contactService.create(contact));
    }
  }

  private createFromForm(): IContact {
    return {
      ...new Contact(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      email: this.editForm.get(['email'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      sipExtension: this.editForm.get(['sipExtension'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      contactIcon: this.editForm.get(['contactIcon'])!.value,
      endUser: this.editForm.get(['endUser'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContact>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IEndUser): any {
    return item.id;
  }
}
