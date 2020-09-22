import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INeighborhoods } from 'app/shared/model/neighborhoods.model';
import { NeighborhoodsService } from './neighborhoods.service';
import { NeighborhoodsDeleteDialogComponent } from './neighborhoods-delete-dialog.component';

@Component({
  selector: 'jhi-neighborhoods',
  templateUrl: './neighborhoods.component.html'
})
export class NeighborhoodsComponent implements OnInit, OnDestroy {
  neighborhoods?: INeighborhoods[];
  eventSubscriber?: Subscription;

  constructor(
    protected neighborhoodsService: NeighborhoodsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.neighborhoodsService.query().subscribe((res: HttpResponse<INeighborhoods[]>) => (this.neighborhoods = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInNeighborhoods();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: INeighborhoods): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInNeighborhoods(): void {
    this.eventSubscriber = this.eventManager.subscribe('neighborhoodsListModification', () => this.loadAll());
  }

  delete(neighborhoods: INeighborhoods): void {
    const modalRef = this.modalService.open(NeighborhoodsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.neighborhoods = neighborhoods;
  }
}
