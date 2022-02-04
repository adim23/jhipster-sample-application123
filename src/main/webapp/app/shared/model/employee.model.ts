import { IJob } from '@/shared/model/job.model';
import { IDepartment } from '@/shared/model/department.model';

export interface IEmployee {
  id?: number;
  firstName?: string;
  lastName?: string;
  fullName?: string;
  email?: string;
  phoneNumber?: string | null;
  hireDateTime?: Date | null;
  zonedHireDateTime?: Date | null;
  hireDate?: Date | null;
  salary?: number | null;
  commissionPct?: number | null;
  duration?: string | null;
  pictContentType?: string | null;
  pict?: string | null;
  comments?: string | null;
  cvContentType?: string | null;
  cv?: string | null;
  active?: boolean | null;
  jobs?: IJob[] | null;
  manager?: IEmployee | null;
  department?: IDepartment | null;
  employees?: IEmployee[] | null;
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public fullName?: string,
    public email?: string,
    public phoneNumber?: string | null,
    public hireDateTime?: Date | null,
    public zonedHireDateTime?: Date | null,
    public hireDate?: Date | null,
    public salary?: number | null,
    public commissionPct?: number | null,
    public duration?: string | null,
    public pictContentType?: string | null,
    public pict?: string | null,
    public comments?: string | null,
    public cvContentType?: string | null,
    public cv?: string | null,
    public active?: boolean | null,
    public jobs?: IJob[] | null,
    public manager?: IEmployee | null,
    public department?: IDepartment | null,
    public employees?: IEmployee[] | null
  ) {
    this.active = this.active ?? false;
  }
}
