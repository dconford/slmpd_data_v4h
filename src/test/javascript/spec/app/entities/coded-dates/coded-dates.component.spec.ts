import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SlmpdDataV4HTestModule } from '../../../test.module';
import { CodedDatesComponent } from 'app/entities/coded-dates/coded-dates.component';
import { CodedDatesService } from 'app/entities/coded-dates/coded-dates.service';
import { CodedDates } from 'app/shared/model/coded-dates.model';

describe('Component Tests', () => {
  describe('CodedDates Management Component', () => {
    let comp: CodedDatesComponent;
    let fixture: ComponentFixture<CodedDatesComponent>;
    let service: CodedDatesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SlmpdDataV4HTestModule],
        declarations: [CodedDatesComponent]
      })
        .overrideTemplate(CodedDatesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CodedDatesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CodedDatesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CodedDates(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.codedDates && comp.codedDates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
