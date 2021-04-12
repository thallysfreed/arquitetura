/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PocTestModule } from '../../../test.module';
import { CompilianceComponent } from 'app/entities/compiliance/compiliance.component';
import { CompilianceService } from 'app/entities/compiliance/compiliance.service';
import { Compiliance } from 'app/shared/model/compiliance.model';

describe('Component Tests', () => {
  describe('Compiliance Management Component', () => {
    let comp: CompilianceComponent;
    let fixture: ComponentFixture<CompilianceComponent>;
    let service: CompilianceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PocTestModule],
        declarations: [CompilianceComponent],
        providers: []
      })
        .overrideTemplate(CompilianceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompilianceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompilianceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Compiliance(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.compiliances[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
