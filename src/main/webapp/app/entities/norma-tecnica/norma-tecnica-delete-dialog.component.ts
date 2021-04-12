import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INormaTecnica } from 'app/shared/model/norma-tecnica.model';
import { NormaTecnicaService } from './norma-tecnica.service';

@Component({
  selector: 'jhi-norma-tecnica-delete-dialog',
  templateUrl: './norma-tecnica-delete-dialog.component.html'
})
export class NormaTecnicaDeleteDialogComponent {
  normaTecnica: INormaTecnica;

  constructor(
    protected normaTecnicaService: NormaTecnicaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.normaTecnicaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'normaTecnicaListModification',
        content: 'Deleted an normaTecnica'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-norma-tecnica-delete-popup',
  template: ''
})
export class NormaTecnicaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ normaTecnica }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(NormaTecnicaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.normaTecnica = normaTecnica;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/norma-tecnica', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/norma-tecnica', { outlets: { popup: null } }]);
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
