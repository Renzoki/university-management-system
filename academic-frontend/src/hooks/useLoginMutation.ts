import { useMutation } from "@tanstack/react-query";
import { login } from "@/api/auth/authApi";

export function useLoginMutation() {
  return useMutation({
    mutationFn: login,
  });
}