import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompiliance } from 'app/shared/model/compiliance.model';
import { CompilianceService } from './compiliance.service';

@Component({
  selector: 'jhi-compiliance-delete-dialog',
  templateUrl: './compiliance-delete-dialog.component.html'
})
export class CompilianceDeleteDialogComponent {
  compiliance: ICompiliance;

  constructor(
    protected compilianceService: CompilianceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.compilianceService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'compilianceListModification',
        content: 'Deleted an compiliance'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-compiliance-delete-popup',
  template: ''
})
export class CompilianceDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ compiliance }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CompilianceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.compiliance = compiliance;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/compiliance', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/compiliance', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
