import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICrimeCategories, CrimeCategories } from 'app/shared/model/crime-categories.model';
import { CrimeCategoriesService } from './crime-categories.service';

@Component({
  selector: 'jhi-crime-categories-update',
  templateUrl: './crime-categories-update.component.html'
})
export class CrimeCategoriesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    crimeCategory: []
  });

  constructor(
    protected crimeCategoriesService: CrimeCategoriesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ crimeCategories }) => {
      this.updateForm(crimeCategories);
    });
  }

  updateForm(crimeCategories: ICrimeCategories): void {
    this.editForm.patchValue({
      id: crimeCategories.id,
      crimeCategory: crimeCategories.crimeCategory
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const crimeCategories = this.createFromForm();
    if (crimeCategories.id !== undefined) {
      this.subscribeToSaveResponse(this.crimeCategoriesService.update(crimeCategories));
    } else {
      this.subscribeToSaveResponse(this.crimeCategoriesService.create(crimeCategories));
    }
  }

  private createFromForm(): ICrimeCategories {
    return {
      ...new CrimeCategories(),
      id: this.editForm.get(['id'])!.value,
      crimeCategory: this.editForm.get(['crimeCategory'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICrimeCategories>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
