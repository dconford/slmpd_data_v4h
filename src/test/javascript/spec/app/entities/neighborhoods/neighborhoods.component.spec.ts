import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SlmpdDataV4HTestModule } from '../../../test.module';
import { NeighborhoodsComponent } from 'app/entities/neighborhoods/neighborhoods.component';
import { NeighborhoodsService } from 'app/entities/neighborhoods/neighborhoods.service';
import { Neighborhoods } from 'app/shared/model/neighborhoods.model';

describe('Component Tests', () => {
  describe('Neighborhoods Management Component', () => {
    let comp: NeighborhoodsComponent;
    let fixture: ComponentFixture<NeighborhoodsComponent>;
    let service: NeighborhoodsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SlmpdDataV4HTestModule],
        declarations: [NeighborhoodsComponent]
      })
        .overrideTemplate(NeighborhoodsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NeighborhoodsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NeighborhoodsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Neighborhoods(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.neighborhoods && comp.neighborhoods[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
