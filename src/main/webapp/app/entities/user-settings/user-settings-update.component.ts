import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IUserSettings, UserSettings } from 'app/shared/model/user-settings.model';
import { UserSettingsService } from './user-settings.service';
import { IEndUser } from 'app/shared/model/end-user.model';
import { EndUserService } from 'app/entities/end-user/end-user.service';

@Component({
  selector: 'jhi-user-settings-update',
  templateUrl: './user-settings-update.component.html',
})
export class UserSettingsUpdateComponent implements OnInit {
  isSaving = false;
  endusers: IEndUser[] = [];

  editForm = this.fb.group({
    id: [],
    wallpaper: [],
    ringTone: [],
    volume: [],
    created: [],
    endUser: [],
  });

  constructor(
    protected userSettingsService: UserSettingsService,
    protected endUserService: EndUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userSettings }) => {
      if (!userSettings.id) {
        const today = moment().startOf('day');
        userSettings.created = today;
      }

      this.updateForm(userSettings);

      this.endUserService.query().subscribe((res: HttpResponse<IEndUser[]>) => (this.endusers = res.body || []));
    });
  }

  updateForm(userSettings: IUserSettings): void {
    this.editForm.patchValue({
      id: userSettings.id,
      wallpaper: userSettings.wallpaper,
      ringTone: userSettings.ringTone,
      volume: userSettings.volume,
      created: userSettings.created ? userSettings.created.format(DATE_TIME_FORMAT) : null,
      endUser: userSettings.endUser,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userSettings = this.createFromForm();
    if (userSettings.id !== undefined) {
      this.subscribeToSaveResponse(this.userSettingsService.update(userSettings));
    } else {
      this.subscribeToSaveResponse(this.userSettingsService.create(userSettings));
    }
  }

  private createFromForm(): IUserSettings {
    return {
      ...new UserSettings(),
      id: this.editForm.get(['id'])!.value,
      wallpaper: this.editForm.get(['wallpaper'])!.value,
      ringTone: this.editForm.get(['ringTone'])!.value,
      volume: this.editForm.get(['volume'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      endUser: this.editForm.get(['endUser'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserSettings>>): void {
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
