import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IEndUser, EndUser } from 'app/shared/model/end-user.model';
import { EndUserService } from './end-user.service';
import { IOrganization } from 'app/shared/model/organization.model';
import { OrganizationService } from 'app/entities/organization/organization.service';

@Component({
  selector: 'jhi-end-user-update',
  templateUrl: './end-user-update.component.html',
})
export class EndUserUpdateComponent implements OnInit {
  isSaving = false;
  organizations: IOrganization[] = [];

  editForm = this.fb.group({
    id: [],
    firstName: [null, [Validators.required]],
    lastName: [],
    email: [],
    phoneNumber: [],
    created: [],
    organization: [],
  });

  constructor(
    protected endUserService: EndUserService,
    protected organizationService: OrganizationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ endUser }) => {
      if (!endUser.id) {
        const today = moment().startOf('day');
        endUser.created = today;
      }

      this.updateForm(endUser);

      this.organizationService.query().subscribe((res: HttpResponse<IOrganization[]>) => (this.organizations = res.body || []));
    });
  }

  updateForm(endUser: IEndUser): void {
    this.editForm.patchValue({
      id: endUser.id,
      firstName: endUser.firstName,
      lastName: endUser.lastName,
      email: endUser.email,
      phoneNumber: endUser.phoneNumber,
      created: endUser.created ? endUser.created.format(DATE_TIME_FORMAT) : null,
      organization: endUser.organization,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const endUser = this.createFromForm();
    if (endUser.id !== undefined) {
      this.subscribeToSaveResponse(this.endUserService.update(endUser));
    } else {
      this.subscribeToSaveResponse(this.endUserService.create(endUser));
    }
  }

  private createFromForm(): IEndUser {
    return {
      ...new EndUser(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      email: this.editForm.get(['email'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      organization: this.editForm.get(['organization'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEndUser>>): void {
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

  trackById(index: number, item: IOrganization): any {
    return item.id;
  }
}
