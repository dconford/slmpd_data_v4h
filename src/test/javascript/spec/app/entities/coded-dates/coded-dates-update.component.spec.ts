import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SlmpdDataV4HTestModule } from '../../../test.module';
import { CodedDatesUpdateComponent } from 'app/entities/coded-dates/coded-dates-update.component';
import { CodedDatesService } from 'app/entities/coded-dates/coded-dates.service';
import { CodedDates } from 'app/shared/model/coded-dates.model';

describe('Component Tests', () => {
  describe('CodedDates Management Update Component', () => {
    let comp: CodedDatesUpdateComponent;
    let fixture: ComponentFixture<CodedDatesUpdateComponent>;
    let service: CodedDatesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SlmpdDataV4HTestModule],
        declarations: [CodedDatesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CodedDatesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CodedDatesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CodedDatesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CodedDates(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CodedDates();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
