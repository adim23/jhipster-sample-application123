import { IJob } from '@/shared/model/job.model';

export interface ITask {
  id?: number;
  title?: string;
  description?: string | null;
  startDate?: Date;
  endDate?: Date | null;
  percentCompleted?: number | null;
  dependsOn?: ITask | null;
  dependents?: ITask[] | null;
  jobs?: IJob[] | null;
}

export class Task implements ITask {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string | null,
    public startDate?: Date,
    public endDate?: Date | null,
    public percentCompleted?: number | null,
    public dependsOn?: ITask | null,
    public dependents?: ITask[] | null,
    public jobs?: IJob[] | null
  ) {}
}
