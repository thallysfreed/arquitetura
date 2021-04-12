/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PocTestModule } from '../../../test.module';
import { NormaTecnicaComponent } from 'app/entities/norma-tecnica/norma-tecnica.component';
import { NormaTecnicaService } from 'app/entities/norma-tecnica/norma-tecnica.service';
import { NormaTecnica } from 'app/shared/model/norma-tecnica.model';

describe('Component Tests', () => {
  describe('NormaTecnica Management Component', () => {
    let comp: NormaTecnicaComponent;
    let fixture: ComponentFixture<NormaTecnicaComponent>;
    let service: NormaTecnicaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PocTestModule],
        declarations: [NormaTecnicaComponent],
        providers: []
      })
        .overrideTemplate(NormaTecnicaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NormaTecnicaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NormaTecnicaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new NormaTecnica(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.normaTecnicas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
