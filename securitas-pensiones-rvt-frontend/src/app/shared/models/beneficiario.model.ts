/** DTO de request para actualización de datos de un beneficiario. */
export interface ActualizarBeneficiarioRequest {
  nombreCompleto: string;
  tipoIdentificacion: string;
  numeroIdentificacion: string;
  parentesco: string;
  porcentajeParticipacion: number;
}

/** DTO de response con datos de un beneficiario. */
export interface BeneficiarioResponse {
  beneficiarioId: number;
  numeroPoliza: string;
  nombreCompleto: string;
  tipoIdentificacion: string;
  numeroIdentificacion: string;
  parentesco: string;
  porcentajeParticipacion: number;
}
