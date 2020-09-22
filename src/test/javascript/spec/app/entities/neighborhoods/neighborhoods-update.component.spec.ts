import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SlmpdDataV4HTestModule } from '../../../test.module';
import { NeighborhoodsUpdateComponent } from 'app/entities/neighborhoods/neighborhoods-update.component';
import { NeighborhoodsService } from 'app/entities/neighborhoods/neighborhoods.service';
import { Neighborhoods } from 'app/shared/model/neighborhoods.model';

describe('Component Tests', () => {
  describe('Neighborhoods Management Update Component', () => {
    let comp: NeighborhoodsUpdateComponent;
    let fixture: ComponentFixture<NeighborhoodsUpdateComponent>;
    let service: NeighborhoodsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SlmpdDataV4HTestModule],
        declarations: [NeighborhoodsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(NeighborhoodsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NeighborhoodsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NeighborhoodsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Neighborhoods(123);
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
        const entity = new Neighborhoods();
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
