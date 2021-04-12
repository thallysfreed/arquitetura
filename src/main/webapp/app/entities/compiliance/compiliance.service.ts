import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICompiliance } from 'app/shared/model/compiliance.model';

type EntityResponseType = HttpResponse<ICompiliance>;
type EntityArrayResponseType = HttpResponse<ICompiliance[]>;

@Injectable({ providedIn: 'root' })
export class CompilianceService {
  public resourceUrl = SERVER_API_URL + 'api/compiliances';

  constructor(protected http: HttpClient) {}

  create(compiliance: ICompiliance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(compiliance);
    return this.http
      .post<ICompiliance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(compiliance: ICompiliance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(compiliance);
    return this.http
      .put<ICompiliance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICompiliance>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICompiliance[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(compiliance: ICompiliance): ICompiliance {
    const copy: ICompiliance = Object.assign({}, compiliance, {
      dataCriacao: compiliance.dataCriacao != null && compiliance.dataCriacao.isValid() ? compiliance.dataCriacao.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataCriacao = res.body.dataCriacao != null ? moment(res.body.dataCriacao) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((compiliance: ICompiliance) => {
        compiliance.dataCriacao = compiliance.dataCriacao != null ? moment(compiliance.dataCriacao) : null;
      });
    }
    return res;
  }
}
