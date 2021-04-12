import { Moment } from 'moment';

export interface ICompiliance {
  id?: number;
  nome?: string;
  descricao?: string;
  setor?: string;
  industrial?: boolean;
  dataCriacao?: Moment;
}

export class Compiliance implements ICompiliance {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: string,
    public setor?: string,
    public industrial?: boolean,
    public dataCriacao?: Moment
  ) {
    this.industrial = this.industrial || false;
  }
}
