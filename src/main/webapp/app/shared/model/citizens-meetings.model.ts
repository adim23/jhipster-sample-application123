import { ICitizens } from '@/shared/model/citizens.model';

export interface ICitizensMeetings {
  id?: number;
  meetDate?: Date;
  agenda?: string;
  comments?: string | null;
  amount?: number | null;
  quantity?: number | null;
  status?: string;
  flag?: boolean;
  citizen?: ICitizens | null;
}

export class CitizensMeetings implements ICitizensMeetings {
  constructor(
    public id?: number,
    public meetDate?: Date,
    public agenda?: string,
    public comments?: string | null,
    public amount?: number | null,
    public quantity?: number | null,
    public status?: string,
    public flag?: boolean,
    public citizen?: ICitizens | null
  ) {
    this.flag = this.flag ?? false;
  }
}
