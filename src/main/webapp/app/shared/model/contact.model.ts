import { Moment } from 'moment';
import { IEndUser } from 'app/shared/model/end-user.model';

export interface IContact {
  id?: string;
  firstName?: string;
  lastName?: string;
  email?: string;
  phoneNumber?: string;
  sipExtension?: string;
  created?: Moment;
  contactIcon?: string;
  endUser?: IEndUser;
}

export class Contact implements IContact {
  constructor(
    public id?: string,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public phoneNumber?: string,
    public sipExtension?: string,
    public created?: Moment,
    public contactIcon?: string,
    public endUser?: IEndUser
  ) {}
}
