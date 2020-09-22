import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SlmpdDataV4HTestModule } from '../../../test.module';
import { ReportedEventsUpdateComponent } from 'app/entities/reported-events/reported-events-update.component';
import { ReportedEventsService } from 'app/entities/reported-events/reported-events.service';
import { ReportedEvents } from 'app/shared/model/reported-events.model';

describe('Component Tests', () => {
  describe('ReportedEvents Management Update Component', () => {
    let comp: ReportedEventsUpdateComponent;
    let fixture: ComponentFixture<ReportedEventsUpdateComponent>;
    let service: ReportedEventsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SlmpdDataV4HTestModule],
        declarations: [ReportedEventsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ReportedEventsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReportedEventsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReportedEventsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ReportedEvents(123);
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
        const entity = new ReportedEvents();
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
