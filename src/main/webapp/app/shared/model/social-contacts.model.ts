import { ISocialKinds } from '@/shared/model/social-kinds.model';
import { IContactTypes } from '@/shared/model/contact-types.model';
import { ICitizens } from '@/shared/model/citizens.model';

export interface ISocialContacts {
  id?: number;
  name?: string;
  favored?: boolean | null;
  social?: ISocialKinds | null;
  kind?: IContactTypes | null;
  citizen?: ICitizens | null;
}

export class SocialContacts implements ISocialContacts {
  constructor(
    public id?: number,
    public name?: string,
    public favored?: boolean | null,
    public social?: ISocialKinds | null,
    public kind?: IContactTypes | null,
    public citizen?: ICitizens | null
  ) {
    this.favored = this.favored ?? false;
  }
}
