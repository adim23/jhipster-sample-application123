import { IContactTypes } from '@/shared/model/contact-types.model';
import { ICitizens } from '@/shared/model/citizens.model';

export interface IEmails {
  id?: number;
  email?: string;
  description?: string | null;
  favourite?: boolean | null;
  kind?: IContactTypes | null;
  citizen?: ICitizens | null;
}

export class Emails implements IEmails {
  constructor(
    public id?: number,
    public email?: string,
    public description?: string | null,
    public favourite?: boolean | null,
    public kind?: IContactTypes | null,
    public citizen?: ICitizens | null
  ) {
    this.favourite = this.favourite ?? false;
  }
}
