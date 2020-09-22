import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SlmpdDataV4HTestModule } from '../../../test.module';
import { CrimeCategoriesComponent } from 'app/entities/crime-categories/crime-categories.component';
import { CrimeCategoriesService } from 'app/entities/crime-categories/crime-categories.service';
import { CrimeCategories } from 'app/shared/model/crime-categories.model';

describe('Component Tests', () => {
  describe('CrimeCategories Management Component', () => {
    let comp: CrimeCategoriesComponent;
    let fixture: ComponentFixture<CrimeCategoriesComponent>;
    let service: CrimeCategoriesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SlmpdDataV4HTestModule],
        declarations: [CrimeCategoriesComponent]
      })
        .overrideTemplate(CrimeCategoriesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CrimeCategoriesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CrimeCategoriesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CrimeCategories(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.crimeCategories && comp.crimeCategories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
