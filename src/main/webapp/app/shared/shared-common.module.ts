import { NgModule } from '@angular/core';

import { PocSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [PocSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [PocSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class PocSharedCommonModule {}
