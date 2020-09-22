import { IReportedEvents } from 'app/shared/model/reported-events.model';

export interface ICodedDates {
  id?: number;
  codedDateString?: string;
  reportedEvents?: IReportedEvents[];
}

export class CodedDates implements ICodedDates {
  constructor(public id?: number, public codedDateString?: string, public reportedEvents?: IReportedEvents[]) {}
}
