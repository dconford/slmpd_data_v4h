import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SlmpdDataV4HTestModule } from '../../../test.module';
import { CodedDatesDetailComponent } from 'app/entities/coded-dates/coded-dates-detail.component';
import { CodedDates } from 'app/shared/model/coded-dates.model';

describe('Component Tests', () => {
  describe('CodedDates Management Detail Component', () => {
    let comp: CodedDatesDetailComponent;
    let fixture: ComponentFixture<CodedDatesDetailComponent>;
    const route = ({ data: of({ codedDates: new CodedDates(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SlmpdDataV4HTestModule],
        declarations: [CodedDatesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CodedDatesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CodedDatesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load codedDates on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.codedDates).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
