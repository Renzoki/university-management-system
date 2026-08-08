/** Authentication-related types. Filled in when auth is implemented. */

export type Role = "ADMIN" | "FACULTY" | "STUDENT"

export interface AuthUser {
  id: number
  email: string
  role: Role
}

export interface LoginRequest {
  email: string
  password: string
}

export interface LoginResponse {
  token: string
  user: AuthUser
}
