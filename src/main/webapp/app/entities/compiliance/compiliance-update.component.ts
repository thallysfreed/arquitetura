import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { ICompiliance, Compiliance } from 'app/shared/model/compiliance.model';
import { CompilianceService } from './compiliance.service';

@Component({
  selector: 'jhi-compiliance-update',
  templateUrl: './compiliance-update.component.html'
})
export class CompilianceUpdateComponent implements OnInit {
  isSaving: boolean;
  dataCriacaoDp: any;

  editForm = this.fb.group({
    id: [],
    nome: [],
    descricao: [],
    setor: [],
    industrial: [],
    dataCriacao: []
  });

  constructor(protected compilianceService: CompilianceService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ compiliance }) => {
      this.updateForm(compiliance);
    });
  }

  updateForm(compiliance: ICompiliance) {
    this.editForm.patchValue({
      id: compiliance.id,
      nome: compiliance.nome,
      descricao: compiliance.descricao,
      setor: compiliance.setor,
      industrial: compiliance.industrial,
      dataCriacao: compiliance.dataCriacao
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const compiliance = this.createFromForm();
    if (compiliance.id !== undefined) {
      this.subscribeToSaveResponse(this.compilianceService.update(compiliance));
    } else {
      this.subscribeToSaveResponse(this.compilianceService.create(compiliance));
    }
  }

  private createFromForm(): ICompiliance {
    return {
      ...new Compiliance(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      descricao: this.editForm.get(['descricao']).value,
      setor: this.editForm.get(['setor']).value,
      industrial: this.editForm.get(['industrial']).value,
      dataCriacao: this.editForm.get(['dataCriacao']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompiliance>>) {
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
