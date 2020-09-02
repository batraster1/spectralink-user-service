import { Moment } from 'moment';
import { IEndUser } from 'app/shared/model/end-user.model';

export interface IUserSettings {
  id?: string;
  wallpaper?: string;
  ringTone?: string;
  volume?: string;
  created?: Moment;
  endUser?: IEndUser;
}

export class UserSettings implements IUserSettings {
  constructor(
    public id?: string,
    public wallpaper?: string,
    public ringTone?: string,
    public volume?: string,
    public created?: Moment,
    public endUser?: IEndUser
  ) {}
}
