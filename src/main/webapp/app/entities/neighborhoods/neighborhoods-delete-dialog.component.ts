import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INeighborhoods } from 'app/shared/model/neighborhoods.model';
import { NeighborhoodsService } from './neighborhoods.service';

@Component({
  templateUrl: './neighborhoods-delete-dialog.component.html'
})
export class NeighborhoodsDeleteDialogComponent {
  neighborhoods?: INeighborhoods;

  constructor(
    protected neighborhoodsService: NeighborhoodsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.neighborhoodsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('neighborhoodsListModification');
      this.activeModal.close();
    });
  }
}
