import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { INeighborhoods, Neighborhoods } from 'app/shared/model/neighborhoods.model';
import { NeighborhoodsService } from './neighborhoods.service';

@Component({
  selector: 'jhi-neighborhoods-update',
  templateUrl: './neighborhoods-update.component.html'
})
export class NeighborhoodsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    neighborhoodName: []
  });

  constructor(protected neighborhoodsService: NeighborhoodsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ neighborhoods }) => {
      this.updateForm(neighborhoods);
    });
  }

  updateForm(neighborhoods: INeighborhoods): void {
    this.editForm.patchValue({
      id: neighborhoods.id,
      neighborhoodName: neighborhoods.neighborhoodName
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const neighborhoods = this.createFromForm();
    if (neighborhoods.id !== undefined) {
      this.subscribeToSaveResponse(this.neighborhoodsService.update(neighborhoods));
    } else {
      this.subscribeToSaveResponse(this.neighborhoodsService.create(neighborhoods));
    }
  }

  private createFromForm(): INeighborhoods {
    return {
      ...new Neighborhoods(),
      id: this.editForm.get(['id'])!.value,
      neighborhoodName: this.editForm.get(['neighborhoodName'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INeighborhoods>>): void {
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
