import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INeighborhoods } from 'app/shared/model/neighborhoods.model';

@Component({
  selector: 'jhi-neighborhoods-detail',
  templateUrl: './neighborhoods-detail.component.html'
})
export class NeighborhoodsDetailComponent implements OnInit {
  neighborhoods: INeighborhoods | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ neighborhoods }) => (this.neighborhoods = neighborhoods));
  }

  previousState(): void {
    window.history.back();
  }
}
