import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { INormaTecnica, NormaTecnica } from 'app/shared/model/norma-tecnica.model';
import { NormaTecnicaService } from './norma-tecnica.service';

@Component({
  selector: 'jhi-norma-tecnica-update',
  templateUrl: './norma-tecnica-update.component.html'
})
export class NormaTecnicaUpdateComponent implements OnInit {
  isSaving: boolean;
  dataCriacaoDp: any;

  editForm = this.fb.group({
    id: [],
    nome: [],
    descricao: [],
    industrial: [],
    setor: [],
    dataCriacao: []
  });

  constructor(protected normaTecnicaService: NormaTecnicaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ normaTecnica }) => {
      this.updateForm(normaTecnica);
    });
  }

  updateForm(normaTecnica: INormaTecnica) {
    this.editForm.patchValue({
      id: normaTecnica.id,
      nome: normaTecnica.nome,
      descricao: normaTecnica.descricao,
      industrial: normaTecnica.industrial,
      setor: normaTecnica.setor,
      dataCriacao: normaTecnica.dataCriacao
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const normaTecnica = this.createFromForm();
    if (normaTecnica.id !== undefined) {
      this.subscribeToSaveResponse(this.normaTecnicaService.update(normaTecnica));
    } else {
      this.subscribeToSaveResponse(this.normaTecnicaService.create(normaTecnica));
    }
  }

  private createFromForm(): INormaTecnica {
    return {
      ...new NormaTecnica(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      descricao: this.editForm.get(['descricao']).value,
      industrial: this.editForm.get(['industrial']).value,
      setor: this.editForm.get(['setor']).value,
      dataCriacao: this.editForm.get(['dataCriacao']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INormaTecnica>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
