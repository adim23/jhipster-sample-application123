export interface IJobs {
  id?: number;
  name?: string;
  description?: string | null;
}

export class Jobs implements IJobs {
  constructor(public id?: number, public name?: string, public description?: string | null) {}
}
