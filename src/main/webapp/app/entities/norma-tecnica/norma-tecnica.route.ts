import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { NormaTecnica } from 'app/shared/model/norma-tecnica.model';
import { NormaTecnicaService } from './norma-tecnica.service';
import { NormaTecnicaComponent } from './norma-tecnica.component';
import { NormaTecnicaDetailComponent } from './norma-tecnica-detail.component';
import { NormaTecnicaUpdateComponent } from './norma-tecnica-update.component';
import { NormaTecnicaDeletePopupComponent } from './norma-tecnica-delete-dialog.component';
import { INormaTecnica } from 'app/shared/model/norma-tecnica.model';

@Injectable({ providedIn: 'root' })
export class NormaTecnicaResolve implements Resolve<INormaTecnica> {
  constructor(private service: NormaTecnicaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<INormaTecnica> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<NormaTecnica>) => response.ok),
        map((normaTecnica: HttpResponse<NormaTecnica>) => normaTecnica.body)
      );
    }
    return of(new NormaTecnica());
  }
}

export const normaTecnicaRoute: Routes = [
  {
    path: '',
    component: NormaTecnicaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'NormaTecnicas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NormaTecnicaDetailComponent,
    resolve: {
      normaTecnica: NormaTecnicaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'NormaTecnicas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NormaTecnicaUpdateComponent,
    resolve: {
      normaTecnica: NormaTecnicaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'NormaTecnicas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NormaTecnicaUpdateComponent,
    resolve: {
      normaTecnica: NormaTecnicaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'NormaTecnicas'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const normaTecnicaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: NormaTecnicaDeletePopupComponent,
    resolve: {
      normaTecnica: NormaTecnicaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'NormaTecnicas'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
