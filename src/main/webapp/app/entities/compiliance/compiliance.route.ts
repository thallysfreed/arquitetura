import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Compiliance } from 'app/shared/model/compiliance.model';
import { CompilianceService } from './compiliance.service';
import { CompilianceComponent } from './compiliance.component';
import { CompilianceDetailComponent } from './compiliance-detail.component';
import { CompilianceUpdateComponent } from './compiliance-update.component';
import { CompilianceDeletePopupComponent } from './compiliance-delete-dialog.component';
import { ICompiliance } from 'app/shared/model/compiliance.model';

@Injectable({ providedIn: 'root' })
export class CompilianceResolve implements Resolve<ICompiliance> {
  constructor(private service: CompilianceService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICompiliance> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Compiliance>) => response.ok),
        map((compiliance: HttpResponse<Compiliance>) => compiliance.body)
      );
    }
    return of(new Compiliance());
  }
}

export const compilianceRoute: Routes = [
  {
    path: '',
    component: CompilianceComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Compiliances'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CompilianceDetailComponent,
    resolve: {
      compiliance: CompilianceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Compiliances'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CompilianceUpdateComponent,
    resolve: {
      compiliance: CompilianceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Compiliances'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CompilianceUpdateComponent,
    resolve: {
      compiliance: CompilianceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Compiliances'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const compiliancePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CompilianceDeletePopupComponent,
    resolve: {
      compiliance: CompilianceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Compiliances'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
