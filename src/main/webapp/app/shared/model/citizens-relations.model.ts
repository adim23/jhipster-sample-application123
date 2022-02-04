import { ICitizens } from '@/shared/model/citizens.model';

export interface ICitizensRelations {
  id?: number;
  name?: string;
  citizen?: ICitizens | null;
}

export class CitizensRelations implements ICitizensRelations {
  constructor(public id?: number, public name?: string, public citizen?: ICitizens | null) {}
}
