/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PocTestModule } from '../../../test.module';
import { NormaTecnicaUpdateComponent } from 'app/entities/norma-tecnica/norma-tecnica-update.component';
import { NormaTecnicaService } from 'app/entities/norma-tecnica/norma-tecnica.service';
import { NormaTecnica } from 'app/shared/model/norma-tecnica.model';

describe('Component Tests', () => {
  describe('NormaTecnica Management Update Component', () => {
    let comp: NormaTecnicaUpdateComponent;
    let fixture: ComponentFixture<NormaTecnicaUpdateComponent>;
    let service: NormaTecnicaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PocTestModule],
        declarations: [NormaTecnicaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(NormaTecnicaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NormaTecnicaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NormaTecnicaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new NormaTecnica(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new NormaTecnica();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
