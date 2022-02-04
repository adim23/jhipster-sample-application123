export interface ICompanyKinds {
  id?: number;
  name?: string;
}

export class CompanyKinds implements ICompanyKinds {
  constructor(public id?: number, public name?: string) {}
}
