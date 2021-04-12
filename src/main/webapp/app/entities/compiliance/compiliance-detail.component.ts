import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompiliance } from 'app/shared/model/compiliance.model';

@Component({
  selector: 'jhi-compiliance-detail',
  templateUrl: './compiliance-detail.component.html'
})
export class CompilianceDetailComponent implements OnInit {
  compiliance: ICompiliance;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ compiliance }) => {
      this.compiliance = compiliance;
    });
  }

  previousState() {
    window.history.back();
  }
}
