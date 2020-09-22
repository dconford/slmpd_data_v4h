import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SlmpdDataV4HTestModule } from '../../../test.module';
import { CrimeCategoriesUpdateComponent } from 'app/entities/crime-categories/crime-categories-update.component';
import { CrimeCategoriesService } from 'app/entities/crime-categories/crime-categories.service';
import { CrimeCategories } from 'app/shared/model/crime-categories.model';

describe('Component Tests', () => {
  describe('CrimeCategories Management Update Component', () => {
    let comp: CrimeCategoriesUpdateComponent;
    let fixture: ComponentFixture<CrimeCategoriesUpdateComponent>;
    let service: CrimeCategoriesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SlmpdDataV4HTestModule],
        declarations: [CrimeCategoriesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CrimeCategoriesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CrimeCategoriesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CrimeCategoriesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CrimeCategories(123);
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
        const entity = new CrimeCategories();
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
