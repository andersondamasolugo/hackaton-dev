/**
 * Barrel export de modelos compartidos (DTOs).
 * Interfaces TypeScript alineadas con el contrato OpenAPI del backend.
 */
export type { LoginRequest, LoginResponse } from './auth.model';
export type { MenuTreeResponse, MenuCategory, MenuItem } from './menu.model';
export type { ExpedirPolizaRequest, PolizaResponse } from './poliza.model';
export type { ActualizarBeneficiarioRequest, BeneficiarioResponse } from './beneficiario.model';
export type { ClienteResponse, ClienteDetalleResponse, PolizaResumen } from './cliente.model';
export type { ActualizarParametroRequest, ParametroResponse } from './parametro.model';
export type { ErrorResponse, FieldError } from './error.model';
