import { QueryClient } from "@tanstack/react-query"

/**
 * Single shared QueryClient instance for the whole app.
 * Sensible defaults for a typical CRUD dashboard app:
 * - don't refetch on every window focus (annoying for tables/forms)
 * - retry once on failure
 */
export const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      refetchOnWindowFocus: false,
      retry: 1,
      staleTime: 30_000,
    },
  },
})
