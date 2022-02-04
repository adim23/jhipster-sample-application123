export interface ITeams {
  id?: number;
  name?: string;
}

export class Teams implements ITeams {
  constructor(public id?: number, public name?: string) {}
}
