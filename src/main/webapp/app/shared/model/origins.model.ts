export interface IOrigins {
  id?: number;
  name?: string;
}

export class Origins implements IOrigins {
  constructor(public id?: number, public name?: string) {}
}
