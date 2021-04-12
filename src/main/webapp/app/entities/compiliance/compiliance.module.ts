import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PocSharedModule } from 'app/shared';
import {
  CompilianceComponent,
  CompilianceDetailComponent,
  CompilianceUpdateComponent,
  CompilianceDeletePopupComponent,
  CompilianceDeleteDialogComponent,
  compilianceRoute,
  compiliancePopupRoute
} from './';

const ENTITY_STATES = [...compilianceRoute, ...compiliancePopupRoute];

@NgModule({
  imports: [PocSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CompilianceComponent,
    CompilianceDetailComponent,
    CompilianceUpdateComponent,
    CompilianceDeleteDialogComponent,
    CompilianceDeletePopupComponent
  ],
  entryComponents: [CompilianceComponent, CompilianceUpdateComponent, CompilianceDeleteDialogComponent, CompilianceDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PocCompilianceModule {}
