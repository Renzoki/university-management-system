import {
  useMutation,
  useQuery,
  useQueryClient,
} from "@tanstack/react-query"
import {
  dropEnrollment,
  enrollInCourse,
  getCourseEnrollments,
  getMyEnrollments,
} from "@/api/enrollmentApi"

export const enrollmentKeys = {
  all: ["enrollments"] as const,
  mine: () => [...enrollmentKeys.all, "mine"] as const,
  course: (courseId: string) =>
    [...enrollmentKeys.all, "course", courseId] as const,
}

export function useMyEnrollments() {
  return useQuery({
    queryKey: enrollmentKeys.mine(),
    queryFn: getMyEnrollments,
  })
}

export function useCourseEnrollments(courseId: string) {
  return useQuery({
    queryKey: enrollmentKeys.course(courseId),
    queryFn: () => getCourseEnrollments(courseId),
    enabled: !!courseId,
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