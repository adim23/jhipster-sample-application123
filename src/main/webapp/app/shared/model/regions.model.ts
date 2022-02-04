import { ICountries } from '@/shared/model/countries.model';

export interface IRegions {
  id?: number;
  name?: string;
  country?: ICountries;
}

export class Regions implements IRegions {
  constructor(public id?: number, public name?: string, public country?: ICountries) {}
}
