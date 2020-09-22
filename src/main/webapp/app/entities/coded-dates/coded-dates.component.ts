import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICodedDates } from 'app/shared/model/coded-dates.model';
import { CodedDatesService } from './coded-dates.service';
import { CodedDatesDeleteDialogComponent } from './coded-dates-delete-dialog.component';

@Component({
  selector: 'jhi-coded-dates',
  templateUrl: './coded-dates.component.html'
})
export class CodedDatesComponent implements OnInit, OnDestroy {
  codedDates?: ICodedDates[];
  eventSubscriber?: Subscription;

  constructor(protected codedDatesService: CodedDatesService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.codedDatesService.query().subscribe((res: HttpResponse<ICodedDates[]>) => (this.codedDates = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCodedDates();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICodedDates): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCodedDates(): void {
    this.eventSubscriber = this.eventManager.subscribe('codedDatesListModification', () => this.loadAll());
  }

  delete(codedDates: ICodedDates): void {
    const modalRef = this.modalService.open(CodedDatesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.codedDates = codedDates;
  }
}
