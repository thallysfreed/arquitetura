/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PocTestModule } from '../../../test.module';
import { CompilianceDetailComponent } from 'app/entities/compiliance/compiliance-detail.component';
import { Compiliance } from 'app/shared/model/compiliance.model';

describe('Component Tests', () => {
  describe('Compiliance Management Detail Component', () => {
    let comp: CompilianceDetailComponent;
    let fixture: ComponentFixture<CompilianceDetailComponent>;
    const route = ({ data: of({ compiliance: new Compiliance(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PocTestModule],
        declarations: [CompilianceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CompilianceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompilianceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.compiliance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
