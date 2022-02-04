export interface IPhoneTypes {
  id?: number;
  name?: string;
}

export class PhoneTypes implements IPhoneTypes {
  constructor(public id?: number, public name?: string) {}
}
