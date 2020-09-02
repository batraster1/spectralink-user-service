import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEndUser } from 'app/shared/model/end-user.model';

type EntityResponseType = HttpResponse<IEndUser>;
type EntityArrayResponseType = HttpResponse<IEndUser[]>;

@Injectable({ providedIn: 'root' })
export class EndUserService {
  public resourceUrl = SERVER_API_URL + 'api/end-users';

  constructor(protected http: HttpClient) {}

  create(endUser: IEndUser): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(endUser);
    return this.http
      .post<IEndUser>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(endUser: IEndUser): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(endUser);
    return this.http
      .put<IEndUser>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IEndUser>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEndUser[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(endUser: IEndUser): IEndUser {
    const copy: IEndUser = Object.assign({}, endUser, {
      created: endUser.created && endUser.created.isValid() ? endUser.created.toJSON() : undefined,
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
      res.body.forEach((endUser: IEndUser) => {
        endUser.created = endUser.created ? moment(endUser.created) : undefined;
      });
    }
    return res;
  }
}
