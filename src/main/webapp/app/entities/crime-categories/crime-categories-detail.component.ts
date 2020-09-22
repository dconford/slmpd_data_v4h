import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICrimeCategories } from 'app/shared/model/crime-categories.model';

@Component({
  selector: 'jhi-crime-categories-detail',
  templateUrl: './crime-categories-detail.component.html'
})
export class CrimeCategoriesDetailComponent implements OnInit {
  crimeCategories: ICrimeCategories | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ crimeCategories }) => (this.crimeCategories = crimeCategories));
  }

  previousState(): void {
    window.history.back();
  }
}
