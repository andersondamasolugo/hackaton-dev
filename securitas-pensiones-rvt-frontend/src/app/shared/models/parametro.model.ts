/** DTO de request para actualización de un parámetro del ramo. */
export interface ActualizarParametroRequest {
  valor: string;
  descripcion?: string;
}

/** DTO de response con datos de un parámetro del ramo. */
export interface ParametroResponse {
  parametroId: number;
  codigo: string;
  descripcion: string;
  valor: string;
  estado: string;
  rangoMinimo: string;
  rangoMaximo: string;
}
