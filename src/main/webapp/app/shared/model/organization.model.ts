import { Moment } from 'moment';
import { IEndUser } from 'app/shared/model/end-user.model';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IOrganization {
  id?: string;
  organizationName?: string;
  organizationDescription?: string;
  organizationIcon?: string;
  created?: Moment;
  endUsers?: IEndUser[];
  customer?: ICustomer;
}

export class Organization implements IOrganization {
  constructor(
    public id?: string,
    public organizationName?: string,
    public organizationDescription?: string,
    public organizationIcon?: string,
    public created?: Moment,
    public endUsers?: IEndUser[],
    public customer?: ICustomer
  ) {}
}
