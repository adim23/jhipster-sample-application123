import { IUser } from '@/shared/model/user.model';

import { FileType } from '@/shared/model/enumerations/file-type.model';
export interface IFiles {
  id?: number;
  uuid?: string | null;
  filename?: string | null;
  fileType?: FileType | null;
  fileSize?: number | null;
  createDate?: Date | null;
  filePath?: string | null;
  version?: string | null;
  mime?: string | null;
  parent?: IFiles | null;
  createdBy?: IUser | null;
  children?: IFiles[] | null;
}

export class Files implements IFiles {
  constructor(
    public id?: number,
    public uuid?: string | null,
    public filename?: string | null,
    public fileType?: FileType | null,
    public fileSize?: number | null,
    public createDate?: Date | null,
    public filePath?: string | null,
    public version?: string | null,
    public mime?: string | null,
    public parent?: IFiles | null,
    public createdBy?: IUser | null,
    public children?: IFiles[] | null
  ) {}
}
