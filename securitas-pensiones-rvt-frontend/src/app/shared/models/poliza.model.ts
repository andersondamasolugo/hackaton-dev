/** DTO de request para expedición de póliza de Rentas Voluntarias. */
export interface ExpedirPolizaRequest {
  tipoIdentificacion: string;
  numeroIdentificacion: string;
  nombreTomador: string;
  fechaInicioVigencia: string;
  montoRenta: number;
}

/** DTO de response con datos de una póliza. */
export interface PolizaResponse {
  numeroPoliza: string;
  estado: string;
  tipoIdentificacion: string;
  numeroIdentificacion: string;
  nombreTomador: string;
  fechaInicioVigencia: string;
  montoRenta: number;
  fechaCreacion: string;
}
