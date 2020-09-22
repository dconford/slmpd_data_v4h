import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SlmpdDataV4HTestModule } from '../../../test.module';
import { ReportedEventsDetailComponent } from 'app/entities/reported-events/reported-events-detail.component';
import { ReportedEvents } from 'app/shared/model/reported-events.model';

describe('Component Tests', () => {
  describe('ReportedEvents Management Detail Component', () => {
    let comp: ReportedEventsDetailComponent;
    let fixture: ComponentFixture<ReportedEventsDetailComponent>;
    const route = ({ data: of({ reportedEvents: new ReportedEvents(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SlmpdDataV4HTestModule],
        declarations: [ReportedEventsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ReportedEventsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReportedEventsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load reportedEvents on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.reportedEvents).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
