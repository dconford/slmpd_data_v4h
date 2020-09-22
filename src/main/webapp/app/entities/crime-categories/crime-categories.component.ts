import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICrimeCategories } from 'app/shared/model/crime-categories.model';
import { CrimeCategoriesService } from './crime-categories.service';
import { CrimeCategoriesDeleteDialogComponent } from './crime-categories-delete-dialog.component';

@Component({
  selector: 'jhi-crime-categories',
  templateUrl: './crime-categories.component.html'
})
export class CrimeCategoriesComponent implements OnInit, OnDestroy {
  crimeCategories?: ICrimeCategories[];
  eventSubscriber?: Subscription;

  constructor(
    protected crimeCategoriesService: CrimeCategoriesService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.crimeCategoriesService.query().subscribe((res: HttpResponse<ICrimeCategories[]>) => (this.crimeCategories = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCrimeCategories();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICrimeCategories): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCrimeCategories(): void {
    this.eventSubscriber = this.eventManager.subscribe('crimeCategoriesListModification', () => this.loadAll());
  }

  delete(crimeCategories: ICrimeCategories): void {
    const modalRef = this.modalService.open(CrimeCategoriesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.crimeCategories = crimeCategories;
  }
}
