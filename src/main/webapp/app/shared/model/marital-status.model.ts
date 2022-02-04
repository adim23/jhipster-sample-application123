export interface IMaritalStatus {
  id?: number;
  name?: string;
}

export class MaritalStatus implements IMaritalStatus {
  constructor(public id?: number, public name?: string) {}
}
