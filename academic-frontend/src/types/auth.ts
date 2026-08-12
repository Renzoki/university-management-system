export type Role = "ADMIN" | "FACULTY" | "STUDENT";
export type UserRole = Role;
export type UserStatus = string;

export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  accessToken: string;
}

export interface JwtPayload {
  sub: string;
  role: UserRole;
  email: string;
  status: UserStatus;
  iat: number;
  exp: number;
}

export interface AuthUser {
  id: string;
  email: string;
  role: UserRole;
  status: UserStatus;
}