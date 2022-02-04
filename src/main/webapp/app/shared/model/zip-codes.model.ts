import { ICountries } from '@/shared/model/countries.model';
import { IRegions } from '@/shared/model/regions.model';
import { ICities } from '@/shared/model/cities.model';

export interface IZipCodes {
  id?: number;
  street?: string | null;
  area?: string | null;
  fromNumber?: string | null;
  toNumber?: string | null;
  country?: ICountries | null;
  region?: IRegions | null;
  city?: ICities | null;
}

export class ZipCodes implements IZipCodes {
  constructor(
    public id?: number,
    public street?: string | null,
    public area?: string | null,
    public fromNumber?: string | null,
    public toNumber?: string | null,
    public country?: ICountries | null,
    public region?: IRegions | null,
    public city?: ICities | null
  ) {}
}
