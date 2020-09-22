import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICrimeCategories } from 'app/shared/model/crime-categories.model';
import { CrimeCategoriesService } from './crime-categories.service';

@Component({
  templateUrl: './crime-categories-delete-dialog.component.html'
})
export class CrimeCategoriesDeleteDialogComponent {
  crimeCategories?: ICrimeCategories;

  constructor(
    protected crimeCategoriesService: CrimeCategoriesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.crimeCategoriesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('crimeCategoriesListModification');
      this.activeModal.close();
    });
  }
}
