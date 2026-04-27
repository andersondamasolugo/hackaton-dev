/** DTO de request para autenticación de usuario. */
export interface LoginRequest {
  username: string;
  password: string;
}

/** DTO de response para autenticación exitosa. */
export interface LoginResponse {
  token: string;
  expiresIn: number;
  username: string;
}
