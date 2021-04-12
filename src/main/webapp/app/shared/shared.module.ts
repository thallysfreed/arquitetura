import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { PocSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [PocSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [PocSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PocSharedModule {
  static forRoot() {
    return {
      ngModule: PocSharedModule
    };
  }
}
