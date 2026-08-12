import authClient from "./authClient";
import type { LoginRequest, LoginResponse } from "@/types/auth";

export async function login(
  data: LoginRequest
): Promise<LoginResponse> {
  const response = await authClient.post<LoginResponse>(
    "/login",
    data
  );

  return response.data;
}