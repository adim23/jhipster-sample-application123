export interface ICountries {
  id?: number;
  name?: string;
  iso?: string | null;
  language?: string | null;
  lang?: string | null;
}

export class Countries implements ICountries {
  constructor(
    public id?: number,
    public name?: string,
    public iso?: string | null,
    public language?: string | null,
    public lang?: string | null
  ) {}
}
