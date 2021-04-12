import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PocSharedModule } from 'app/shared';
import {
  NormaTecnicaComponent,
  NormaTecnicaDetailComponent,
  NormaTecnicaUpdateComponent,
  NormaTecnicaDeletePopupComponent,
  NormaTecnicaDeleteDialogComponent,
  normaTecnicaRoute,
  normaTecnicaPopupRoute
} from './';

const ENTITY_STATES = [...normaTecnicaRoute, ...normaTecnicaPopupRoute];

@NgModule({
  imports: [PocSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    NormaTecnicaComponent,
    NormaTecnicaDetailComponent,
    NormaTecnicaUpdateComponent,
    NormaTecnicaDeleteDialogComponent,
    NormaTecnicaDeletePopupComponent
  ],
  entryComponents: [
    NormaTecnicaComponent,
    NormaTecnicaUpdateComponent,
    NormaTecnicaDeleteDialogComponent,
    NormaTecnicaDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PocNormaTecnicaModule {}
