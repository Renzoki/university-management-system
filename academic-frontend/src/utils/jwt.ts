import { jwtDecode } from "jwt-decode";
import type { AuthUser, JwtPayload } from "@/types/auth";

export function decodeToken(token: string): AuthUser | null {
  try {
    const payload = jwtDecode<JwtPayload>(token);

    return {
      id: payload.sub,
      email: payload.email,
      role: payload.role,
      status: payload.status,
    };
  } catch {
    return null;
  }
}

export function isTokenExpired(token: string): boolean {
  try {
    const payload = jwtDecode<JwtPayload>(token);

    return payload.exp * 1000 < Date.now();
  } catch {
    return true;
  }
}

const TOKEN_KEY = "accessToken";

export const tokenStorage = {
  get: (): string | null => localStorage.getItem(TOKEN_KEY),

  set: (token: string): void => {
    localStorage.setItem(TOKEN_KEY, token);
  },

  clear: (): void => {
    localStorage.removeItem(TOKEN_KEY);
  },
};