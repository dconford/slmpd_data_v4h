import { IReportedEvents } from 'app/shared/model/reported-events.model';

export interface ICrimeCategories {
  id?: number;
  crimeCategory?: string;
  reportedEvents?: IReportedEvents[];
}

export class CrimeCategories implements ICrimeCategories {
  constructor(public id?: number, public crimeCategory?: string, public reportedEvents?: IReportedEvents[]) {}
}
