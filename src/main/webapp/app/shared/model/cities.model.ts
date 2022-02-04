import { ICountries } from '@/shared/model/countries.model';
import { IRegions } from '@/shared/model/regions.model';

export interface ICities {
  id?: number;
  name?: string;
  president?: string | null;
  presidentsPhone?: string | null;
  secretary?: string | null;
  secretarysPhone?: string | null;
  police?: string | null;
  policesPhone?: string | null;
  doctor?: string | null;
  doctorsPhone?: string | null;
  teacher?: string | null;
  teachersPhone?: string | null;
  priest?: string | null;
  priestsPhone?: string | null;
  country?: ICountries;
  region?: IRegions;
}

export class Cities implements ICities {
  constructor(
    public id?: number,
    public name?: string,
    public president?: string | null,
    public presidentsPhone?: string | null,
    public secretary?: string | null,
    public secretarysPhone?: string | null,
    public police?: string | null,
    public policesPhone?: string | null,
    public doctor?: string | null,
    public doctorsPhone?: string | null,
    public teacher?: string | null,
    public teachersPhone?: string | null,
    public priest?: string | null,
    public priestsPhone?: string | null,
    public country?: ICountries,
    public region?: IRegions
  ) {}
}
