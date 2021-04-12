import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICompiliance } from 'app/shared/model/compiliance.model';
import { AccountService } from 'app/core';
import { CompilianceService } from './compiliance.service';

@Component({
  selector: 'jhi-compiliance',
  templateUrl: './compiliance.component.html'
})
export class CompilianceComponent implements OnInit, OnDestroy {
  compiliances: ICompiliance[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected compilianceService: CompilianceService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.compilianceService
      .query()
      .pipe(
        filter((res: HttpResponse<ICompiliance[]>) => res.ok),
        map((res: HttpResponse<ICompiliance[]>) => res.body)
      )
      .subscribe(
        (res: ICompiliance[]) => {
          this.compiliances = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCompiliances();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICompiliance) {
    return item.id;
  }

  registerChangeInCompiliances() {
    this.eventSubscriber = this.eventManager.subscribe('compilianceListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
