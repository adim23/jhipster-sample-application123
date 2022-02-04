export interface ICitizenFolders {
  id?: number;
  name?: string;
  description?: string | null;
  special?: boolean;
}

export class CitizenFolders implements ICitizenFolders {
  constructor(public id?: number, public name?: string, public description?: string | null, public special?: boolean) {
    this.special = this.special ?? false;
  }
}
