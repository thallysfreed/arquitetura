import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INormaTecnica } from 'app/shared/model/norma-tecnica.model';

type EntityResponseType = HttpResponse<INormaTecnica>;
type EntityArrayResponseType = HttpResponse<INormaTecnica[]>;

@Injectable({ providedIn: 'root' })
export class NormaTecnicaService {
  public resourceUrl = SERVER_API_URL + 'api/norma-tecnicas';

  constructor(protected http: HttpClient) {}

  create(normaTecnica: INormaTecnica): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(normaTecnica);
    return this.http
      .post<INormaTecnica>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(normaTecnica: INormaTecnica): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(normaTecnica);
    return this.http
      .put<INormaTecnica>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INormaTecnica>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INormaTecnica[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(normaTecnica: INormaTecnica): INormaTecnica {
    const copy: INormaTecnica = Object.assign({}, normaTecnica, {
      dataCriacao:
        normaTecnica.dataCriacao != null && normaTecnica.dataCriacao.isValid() ? normaTecnica.dataCriacao.format(DATE_FORMAT) : null
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
      res.body.forEach((normaTecnica: INormaTecnica) => {
        normaTecnica.dataCriacao = normaTecnica.dataCriacao != null ? moment(normaTecnica.dataCriacao) : null;
      });
    }
    return res;
  }
}
