import { Moment } from 'moment';

export interface INormaTecnica {
  id?: number;
  nome?: string;
  descricao?: string;
  industrial?: boolean;
  setor?: string;
  dataCriacao?: Moment;
}

export class NormaTecnica implements INormaTecnica {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: string,
    public industrial?: boolean,
    public setor?: string,
    public dataCriacao?: Moment
  ) {
    this.industrial = this.industrial || false;
  }
}
