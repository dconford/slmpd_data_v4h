import { IReportedEvents } from 'app/shared/model/reported-events.model';

export interface INeighborhoods {
  id?: number;
  neighborhoodName?: string;
  reportedEvents?: IReportedEvents[];
}

export class Neighborhoods implements INeighborhoods {
  constructor(public id?: number, public neighborhoodName?: string, public reportedEvents?: IReportedEvents[]) {}
}
