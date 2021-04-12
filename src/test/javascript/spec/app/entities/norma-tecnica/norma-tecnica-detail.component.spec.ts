/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PocTestModule } from '../../../test.module';
import { NormaTecnicaDetailComponent } from 'app/entities/norma-tecnica/norma-tecnica-detail.component';
import { NormaTecnica } from 'app/shared/model/norma-tecnica.model';

describe('Component Tests', () => {
  describe('NormaTecnica Management Detail Component', () => {
    let comp: NormaTecnicaDetailComponent;
    let fixture: ComponentFixture<NormaTecnicaDetailComponent>;
    const route = ({ data: of({ normaTecnica: new NormaTecnica(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PocTestModule],
        declarations: [NormaTecnicaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NormaTecnicaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NormaTecnicaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.normaTecnica).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
