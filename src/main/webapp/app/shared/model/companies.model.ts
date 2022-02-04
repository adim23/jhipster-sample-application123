import { ICompanyKinds } from '@/shared/model/company-kinds.model';
import { IPhones } from '@/shared/model/phones.model';
import { IAddresses } from '@/shared/model/addresses.model';

export interface ICompanies {
  id?: number;
  name?: string;
  kind?: ICompanyKinds | null;
  phones?: IPhones[] | null;
  addresses?: IAddresses[] | null;
}

export class Companies implements ICompanies {
  constructor(
    public id?: number,
    public name?: string,
    public kind?: ICompanyKinds | null,
    public phones?: IPhones[] | null,
    public addresses?: IAddresses[] | null
  ) {}
}
