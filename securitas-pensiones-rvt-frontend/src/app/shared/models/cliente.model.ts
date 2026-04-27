/** DTO de response con datos resumidos de un cliente. */
export interface ClienteResponse {
  clienteId: number;
  tipoIdentificacion: string;
  numeroIdentificacion: string;
  nombre: string;
  estado: string;
  numeroPolizas: number;
}

/** DTO de response con detalle consolidado de un cliente. */
export interface ClienteDetalleResponse {
  clienteId: number;
  tipoIdentificacion: string;
  numeroIdentificacion: string;
  nombre: string;
  estado: string;
  direccion: string;
  telefono: string;
  email: string;
  polizas: PolizaResumen[];
}

/** DTO resumido de póliza para incluir en detalle de cliente. */
export interface PolizaResumen {
  numeroPoliza: string;
  estado: string;
  montoRenta: number;
}
