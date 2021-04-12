import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { INormaTecnica } from 'app/shared/model/norma-tecnica.model';
import { AccountService } from 'app/core';
import { NormaTecnicaService } from './norma-tecnica.service';

@Component({
  selector: 'jhi-norma-tecnica',
  templateUrl: './norma-tecnica.component.html'
})
export class NormaTecnicaComponent implements OnInit, OnDestroy {
  normaTecnicas: INormaTecnica[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected normaTecnicaService: NormaTecnicaService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.normaTecnicaService
      .query()
      .pipe(
        filter((res: HttpResponse<INormaTecnica[]>) => res.ok),
        map((res: HttpResponse<INormaTecnica[]>) => res.body)
      )
      .subscribe(
        (res: INormaTecnica[]) => {
          this.normaTecnicas = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInNormaTecnicas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: INormaTecnica) {
    return item.id;
  }

  registerChangeInNormaTecnicas() {
    this.eventSubscriber = this.eventManager.subscribe('normaTecnicaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
