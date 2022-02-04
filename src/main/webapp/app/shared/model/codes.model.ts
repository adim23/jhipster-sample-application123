export interface ICodes {
  id?: number;
  name?: string;
}

export class Codes implements ICodes {
  constructor(public id?: number, public name?: string) {}
}
