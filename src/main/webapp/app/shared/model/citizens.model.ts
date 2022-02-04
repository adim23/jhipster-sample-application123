import { ICitizenFolders } from '@/shared/model/citizen-folders.model';
import { ICompanies } from '@/shared/model/companies.model';
import { IMaritalStatus } from '@/shared/model/marital-status.model';
import { ITeams } from '@/shared/model/teams.model';
import { ICodes } from '@/shared/model/codes.model';
import { IOrigins } from '@/shared/model/origins.model';
import { IJobs } from '@/shared/model/jobs.model';
import { IPhones } from '@/shared/model/phones.model';
import { IAddresses } from '@/shared/model/addresses.model';
import { ISocialContacts } from '@/shared/model/social-contacts.model';
import { IEmails } from '@/shared/model/emails.model';
import { ICitizensRelations } from '@/shared/model/citizens-relations.model';

export interface ICitizens {
  id?: number;
  title?: string | null;
  lastname?: string | null;
  firstname?: string | null;
  fathersName?: string | null;
  comments?: string | null;
  birthDate?: Date | null;
  giortazi?: string | null;
  male?: boolean | null;
  meLetter?: number | null;
  meLabel?: number | null;
  imageContentType?: string | null;
  image?: string | null;
  folder?: ICitizenFolders | null;
  company?: ICompanies | null;
  maritalStatus?: IMaritalStatus | null;
  team?: ITeams | null;
  code?: ICodes | null;
  origin?: IOrigins | null;
  job?: IJobs | null;
  phones?: IPhones[] | null;
  addresses?: IAddresses[] | null;
  socials?: ISocialContacts[] | null;
  emails?: IEmails[] | null;
  relations?: ICitizensRelations[] | null;
}

export class Citizens implements ICitizens {
  constructor(
    public id?: number,
    public title?: string | null,
    public lastname?: string | null,
    public firstname?: string | null,
    public fathersName?: string | null,
    public comments?: string | null,
    public birthDate?: Date | null,
    public giortazi?: string | null,
    public male?: boolean | null,
    public meLetter?: number | null,
    public meLabel?: number | null,
    public imageContentType?: string | null,
    public image?: string | null,
    public folder?: ICitizenFolders | null,
    public company?: ICompanies | null,
    public maritalStatus?: IMaritalStatus | null,
    public team?: ITeams | null,
    public code?: ICodes | null,
    public origin?: IOrigins | null,
    public job?: IJobs | null,
    public phones?: IPhones[] | null,
    public addresses?: IAddresses[] | null,
    public socials?: ISocialContacts[] | null,
    public emails?: IEmails[] | null,
    public relations?: ICitizensRelations[] | null
  ) {
    this.male = this.male ?? false;
  }
}
