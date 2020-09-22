import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICodedDates, CodedDates } from 'app/shared/model/coded-dates.model';
import { CodedDatesService } from './coded-dates.service';

@Component({
  selector: 'jhi-coded-dates-update',
  templateUrl: './coded-dates-update.component.html'
})
export class CodedDatesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    codedDateString: []
  });

  constructor(protected codedDatesService: CodedDatesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ codedDates }) => {
      this.updateForm(codedDates);
    });
  }

  updateForm(codedDates: ICodedDates): void {
    this.editForm.patchValue({
      id: codedDates.id,
      codedDateString: codedDates.codedDateString
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const codedDates = this.createFromForm();
    if (codedDates.id !== undefined) {
      this.subscribeToSaveResponse(this.codedDatesService.update(codedDates));
    } else {
      this.subscribeToSaveResponse(this.codedDatesService.create(codedDates));
    }
  }

  private createFromForm(): ICodedDates {
    return {
      ...new CodedDates(),
      id: this.editForm.get(['id'])!.value,
      codedDateString: this.editForm.get(['codedDateString'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICodedDates>>): void {
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
