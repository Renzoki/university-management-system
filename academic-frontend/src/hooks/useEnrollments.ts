import {
  useMutation,
  useQuery,
  useQueryClient,
} from "@tanstack/react-query"
import {
  dropEnrollment,
  enrollInCourse,
  getMyEnrollments,
} from "@/api/enrollmentApi"

export const enrollmentKeys = {
  all: ["enrollments"] as const,
  mine: () => [...enrollmentKeys.all, "mine"] as const,
}

export function useMyEnrollments() {
  return useQuery({
    queryKey: enrollmentKeys.mine(),
    queryFn: getMyEnrollments,
  })
}

export function useEnrollInCourse() {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: enrollInCourse,
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: enrollmentKeys.mine(),
      })

      queryClient.invalidateQueries({
        queryKey: ["courses"],
      })
    },
  })
}

export function useDropEnrollment() {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: dropEnrollment,
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: enrollmentKeys.mine(),
      })

      queryClient.invalidateQueries({
        queryKey: ["courses"],
      })
    },
  })
}