import { Moment } from 'moment';
import { IOrganization } from 'app/shared/model/organization.model';

export interface ICustomer {
  id?: string;
  customerName?: string;
  customerIcon?: string;
  created?: Moment;
  organizations?: IOrganization[];
}

export class Customer implements ICustomer {
  constructor(
    public id?: string,
    public customerName?: string,
    public customerIcon?: string,
    public created?: Moment,
    public organizations?: IOrganization[]
  ) {}
}
