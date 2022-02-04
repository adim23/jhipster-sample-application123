import { ICountries } from '@/shared/model/countries.model';
import { IContactTypes } from '@/shared/model/contact-types.model';
import { IRegions } from '@/shared/model/regions.model';
import { ICompanies } from '@/shared/model/companies.model';
import { ICitizens } from '@/shared/model/citizens.model';

export interface IAddresses {
  id?: number;
  address?: string | null;
  addressNo?: string | null;
  zipCode?: string | null;
  prosfLetter?: string | null;
  nameLetter?: string | null;
  letterClose?: string | null;
  firstLabel?: string | null;
  secondLabel?: string | null;
  thirdLabel?: string | null;
  fourthLabel?: string | null;
  fifthLabel?: string | null;
  favourite?: boolean | null;
  country?: ICountries | null;
  kind?: IContactTypes | null;
  region?: IRegions | null;
  company?: ICompanies | null;
  citizen?: ICitizens | null;
}

export class Addresses implements IAddresses {
  constructor(
    public id?: number,
    public address?: string | null,
    public addressNo?: string | null,
    public zipCode?: string | null,
    public prosfLetter?: string | null,
    public nameLetter?: string | null,
    public letterClose?: string | null,
    public firstLabel?: string | null,
    public secondLabel?: string | null,
    public thirdLabel?: string | null,
    public fourthLabel?: string | null,
    public fifthLabel?: string | null,
    public favourite?: boolean | null,
    public country?: ICountries | null,
    public kind?: IContactTypes | null,
    public region?: IRegions | null,
    public company?: ICompanies | null,
    public citizen?: ICitizens | null
  ) {
    this.favourite = this.favourite ?? false;
  }
}
