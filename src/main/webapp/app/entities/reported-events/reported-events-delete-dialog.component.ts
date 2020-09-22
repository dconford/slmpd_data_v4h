import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReportedEvents } from 'app/shared/model/reported-events.model';
import { ReportedEventsService } from './reported-events.service';

@Component({
  templateUrl: './reported-events-delete-dialog.component.html'
})
export class ReportedEventsDeleteDialogComponent {
  reportedEvents?: IReportedEvents;

  constructor(
    protected reportedEventsService: ReportedEventsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.reportedEventsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('reportedEventsListModification');
      this.activeModal.close();
    });
  }
}
