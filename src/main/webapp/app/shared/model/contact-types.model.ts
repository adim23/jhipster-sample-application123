export interface IContactTypes {
  id?: number;
  name?: string;
}

export class ContactTypes implements IContactTypes {
  constructor(public id?: number, public name?: string) {}
}
