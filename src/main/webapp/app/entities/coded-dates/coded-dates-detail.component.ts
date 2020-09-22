import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICodedDates } from 'app/shared/model/coded-dates.model';

@Component({
  selector: 'jhi-coded-dates-detail',
  templateUrl: './coded-dates-detail.component.html'
})
export class CodedDatesDetailComponent implements OnInit {
  codedDates: ICodedDates | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ codedDates }) => (this.codedDates = codedDates));
  }

  previousState(): void {
    window.history.back();
  }
}
