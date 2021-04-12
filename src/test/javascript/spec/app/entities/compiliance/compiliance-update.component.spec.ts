/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PocTestModule } from '../../../test.module';
import { CompilianceUpdateComponent } from 'app/entities/compiliance/compiliance-update.component';
import { CompilianceService } from 'app/entities/compiliance/compiliance.service';
import { Compiliance } from 'app/shared/model/compiliance.model';

describe('Component Tests', () => {
  describe('Compiliance Management Update Component', () => {
    let comp: CompilianceUpdateComponent;
    let fixture: ComponentFixture<CompilianceUpdateComponent>;
    let service: CompilianceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PocTestModule],
        declarations: [CompilianceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CompilianceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompilianceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompilianceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Compiliance(123);
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
        const entity = new Compiliance();
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
