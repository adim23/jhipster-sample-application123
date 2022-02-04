import { IPhoneTypes } from '@/shared/model/phone-types.model';
import { ICompanies } from '@/shared/model/companies.model';
import { ICitizens } from '@/shared/model/citizens.model';

export interface IPhones {
  id?: number;
  phone?: string;
  description?: string | null;
  favourite?: boolean | null;
  kind?: IPhoneTypes | null;
  company?: ICompanies | null;
  citizen?: ICitizens | null;
}

export class Phones implements IPhones {
  constructor(
    public id?: number,
    public phone?: string,
    public description?: string | null,
    public favourite?: boolean | null,
    public kind?: IPhoneTypes | null,
    public company?: ICompanies | null,
    public citizen?: ICitizens | null
  ) {
    this.favourite = this.favourite ?? false;
  }
}
