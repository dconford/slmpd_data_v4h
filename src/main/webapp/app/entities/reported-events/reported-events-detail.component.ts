import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReportedEvents } from 'app/shared/model/reported-events.model';

@Component({
  selector: 'jhi-reported-events-detail',
  templateUrl: './reported-events-detail.component.html'
})
export class ReportedEventsDetailComponent implements OnInit {
  reportedEvents: IReportedEvents | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reportedEvents }) => (this.reportedEvents = reportedEvents));
  }

  previousState(): void {
    window.history.back();
  }
}
