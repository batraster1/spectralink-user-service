import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IOrganization, Organization } from 'app/shared/model/organization.model';
import { OrganizationService } from './organization.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';

@Component({
  selector: 'jhi-organization-update',
  templateUrl: './organization-update.component.html',
})
export class OrganizationUpdateComponent implements OnInit {
  isSaving = false;
  customers: ICustomer[] = [];

  editForm = this.fb.group({
    id: [],
    organizationName: [null, [Validators.required]],
    organizationDescription: [],
    organizationIcon: [],
    created: [],
    customer: [],
  });

  constructor(
    protected organizationService: OrganizationService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organization }) => {
      if (!organization.id) {
        const today = moment().startOf('day');
        organization.created = today;
      }

      this.updateForm(organization);

      this.customerService.query().subscribe((res: HttpResponse<ICustomer[]>) => (this.customers = res.body || []));
    });
  }

  updateForm(organization: IOrganization): void {
    this.editForm.patchValue({
      id: organization.id,
      organizationName: organization.organizationName,
      organizationDescription: organization.organizationDescription,
      organizationIcon: organization.organizationIcon,
      created: organization.created ? organization.created.format(DATE_TIME_FORMAT) : null,
      customer: organization.customer,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const organization = this.createFromForm();
    if (organization.id !== undefined) {
      this.subscribeToSaveResponse(this.organizationService.update(organization));
    } else {
      this.subscribeToSaveResponse(this.organizationService.create(organization));
    }
  }

  private createFromForm(): IOrganization {
    return {
      ...new Organization(),
      id: this.editForm.get(['id'])!.value,
      organizationName: this.editForm.get(['organizationName'])!.value,
      organizationDescription: this.editForm.get(['organizationDescription'])!.value,
      organizationIcon: this.editForm.get(['organizationIcon'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      customer: this.editForm.get(['customer'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrganization>>): void {
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

  trackById(index: number, item: ICustomer): any {
    return item.id;
  }
}
