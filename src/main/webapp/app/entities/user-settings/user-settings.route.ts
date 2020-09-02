import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserSettings, UserSettings } from 'app/shared/model/user-settings.model';
import { UserSettingsService } from './user-settings.service';
import { UserSettingsComponent } from './user-settings.component';
import { UserSettingsDetailComponent } from './user-settings-detail.component';
import { UserSettingsUpdateComponent } from './user-settings-update.component';

@Injectable({ providedIn: 'root' })
export class UserSettingsResolve implements Resolve<IUserSettings> {
  constructor(private service: UserSettingsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserSettings> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userSettings: HttpResponse<UserSettings>) => {
          if (userSettings.body) {
            return of(userSettings.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserSettings());
  }
}

export const userSettingsRoute: Routes = [
  {
    path: '',
    component: UserSettingsComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'UserSettings',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserSettingsDetailComponent,
    resolve: {
      userSettings: UserSettingsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserSettings',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserSettingsUpdateComponent,
    resolve: {
      userSettings: UserSettingsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserSettings',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserSettingsUpdateComponent,
    resolve: {
      userSettings: UserSettingsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserSettings',
    },
    canActivate: [UserRouteAccessService],
  },
];
