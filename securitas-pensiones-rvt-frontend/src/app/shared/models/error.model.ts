/** DTO estándar de respuesta de error. */
export interface ErrorResponse {
  timestamp: string;
  status: number;
  error: string;
  message: string;
  details: FieldError[];
  correlationId: string;
}

/** Detalle de error de validación por campo. */
export interface FieldError {
  field: string;
  message: string;
}
