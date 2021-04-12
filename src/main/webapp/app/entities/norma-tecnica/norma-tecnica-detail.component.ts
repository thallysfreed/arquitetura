import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INormaTecnica } from 'app/shared/model/norma-tecnica.model';

@Component({
  selector: 'jhi-norma-tecnica-detail',
  templateUrl: './norma-tecnica-detail.component.html'
})
export class NormaTecnicaDetailComponent implements OnInit {
  normaTecnica: INormaTecnica;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ normaTecnica }) => {
      this.normaTecnica = normaTecnica;
    });
  }

  previousState() {
    window.history.back();
  }
}
