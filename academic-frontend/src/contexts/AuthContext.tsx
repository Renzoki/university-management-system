import { createContext, useEffect, useState, type ReactNode } from "react";
import type { AuthUser } from "@/types/auth";
import { decodeToken, isTokenExpired, tokenStorage } from "@/utils/jwt";

interface AuthContextType {
  user: AuthUser | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  loginWithToken: (token: string) => void;
  logout: () => void;
}

export const AuthContext = createContext<AuthContextType | undefined>(
  undefined
);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<AuthUser | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const token = tokenStorage.get();

    if (token && !isTokenExpired(token)) {
      setUser(decodeToken(token));
    } else {
      tokenStorage.clear();
    }

    setIsLoading(false);
  }, []);

  function loginWithToken(token: string) {
    tokenStorage.set(token);
    setUser(decodeToken(token));
  }

  function logout() {
    tokenStorage.clear();
    setUser(null);
  }

  return (
    <AuthContext.Provider
      value={{
        user,
        isAuthenticated: !!user,
        isLoading,
        loginWithToken,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}