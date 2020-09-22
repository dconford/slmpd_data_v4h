import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICodedDates } from 'app/shared/model/coded-dates.model';
import { CodedDatesService } from './coded-dates.service';

@Component({
  templateUrl: './coded-dates-delete-dialog.component.html'
})
export class CodedDatesDeleteDialogComponent {
  codedDates?: ICodedDates;

  constructor(
    protected codedDatesService: CodedDatesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.codedDatesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('codedDatesListModification');
      this.activeModal.close();
    });
  }
}
