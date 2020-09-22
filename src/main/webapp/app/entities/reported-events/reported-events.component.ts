import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IReportedEvents } from 'app/shared/model/reported-events.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ReportedEventsService } from './reported-events.service';
import { ReportedEventsDeleteDialogComponent } from './reported-events-delete-dialog.component';

@Component({
  selector: 'jhi-reported-events',
  templateUrl: './reported-events.component.html'
})
export class ReportedEventsComponent implements OnInit, OnDestroy {
  reportedEvents?: IReportedEvents[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected reportedEventsService: ReportedEventsService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.reportedEventsService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IReportedEvents[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInReportedEvents();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IReportedEvents): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInReportedEvents(): void {
    this.eventSubscriber = this.eventManager.subscribe('reportedEventsListModification', () => this.loadPage());
  }

  delete(reportedEvents: IReportedEvents): void {
    const modalRef = this.modalService.open(ReportedEventsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.reportedEvents = reportedEvents;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IReportedEvents[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/reported-events'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.reportedEvents = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
