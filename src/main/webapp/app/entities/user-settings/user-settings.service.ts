import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserSettings } from 'app/shared/model/user-settings.model';

type EntityResponseType = HttpResponse<IUserSettings>;
type EntityArrayResponseType = HttpResponse<IUserSettings[]>;

@Injectable({ providedIn: 'root' })
export class UserSettingsService {
  public resourceUrl = SERVER_API_URL + 'api/user-settings';

  constructor(protected http: HttpClient) {}

  create(userSettings: IUserSettings): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userSettings);
    return this.http
      .post<IUserSettings>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userSettings: IUserSettings): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userSettings);
    return this.http
      .put<IUserSettings>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IUserSettings>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserSettings[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(userSettings: IUserSettings): IUserSettings {
    const copy: IUserSettings = Object.assign({}, userSettings, {
      created: userSettings.created && userSettings.created.isValid() ? userSettings.created.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.created = res.body.created ? moment(res.body.created) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((userSettings: IUserSettings) => {
        userSettings.created = userSettings.created ? moment(userSettings.created) : undefined;
      });
    }
    return res;
  }
}
