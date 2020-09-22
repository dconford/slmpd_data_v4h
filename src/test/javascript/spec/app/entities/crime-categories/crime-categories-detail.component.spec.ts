import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SlmpdDataV4HTestModule } from '../../../test.module';
import { CrimeCategoriesDetailComponent } from 'app/entities/crime-categories/crime-categories-detail.component';
import { CrimeCategories } from 'app/shared/model/crime-categories.model';

describe('Component Tests', () => {
  describe('CrimeCategories Management Detail Component', () => {
    let comp: CrimeCategoriesDetailComponent;
    let fixture: ComponentFixture<CrimeCategoriesDetailComponent>;
    const route = ({ data: of({ crimeCategories: new CrimeCategories(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SlmpdDataV4HTestModule],
        declarations: [CrimeCategoriesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CrimeCategoriesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CrimeCategoriesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load crimeCategories on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.crimeCategories).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
