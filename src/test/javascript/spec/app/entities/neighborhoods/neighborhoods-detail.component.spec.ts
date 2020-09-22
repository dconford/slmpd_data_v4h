import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SlmpdDataV4HTestModule } from '../../../test.module';
import { NeighborhoodsDetailComponent } from 'app/entities/neighborhoods/neighborhoods-detail.component';
import { Neighborhoods } from 'app/shared/model/neighborhoods.model';

describe('Component Tests', () => {
  describe('Neighborhoods Management Detail Component', () => {
    let comp: NeighborhoodsDetailComponent;
    let fixture: ComponentFixture<NeighborhoodsDetailComponent>;
    const route = ({ data: of({ neighborhoods: new Neighborhoods(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SlmpdDataV4HTestModule],
        declarations: [NeighborhoodsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NeighborhoodsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NeighborhoodsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load neighborhoods on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.neighborhoods).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
