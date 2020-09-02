import { Moment } from 'moment';
import { IContact } from 'app/shared/model/contact.model';
import { IUserSettings } from 'app/shared/model/user-settings.model';
import { IOrganization } from 'app/shared/model/organization.model';

export interface IEndUser {
  id?: string;
  firstName?: string;
  lastName?: string;
  email?: string;
  phoneNumber?: string;
  created?: Moment;
  contacts?: IContact[];
  settings?: IUserSettings[];
  organization?: IOrganization;
}

export class EndUser implements IEndUser {
  constructor(
    public id?: string,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public phoneNumber?: string,
    public created?: Moment,
    public contacts?: IContact[],
    public settings?: IUserSettings[],
    public organization?: IOrganization
  ) {}
}
