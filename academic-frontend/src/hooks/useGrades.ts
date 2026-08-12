import {
  useMutation,
  useQuery,
  useQueryClient,
} from "@tanstack/react-query"
import {
  getMyGrade,
  getStudentGrade,
  setGrade,
  type SetGradeRequest,
} from "@/api/gradeApi"

export const gradeKeys = {
  all: ["grades"] as const,

  mine: (courseId: string) =>
    [...gradeKeys.all, "mine", courseId] as const,

  student: (
    studentId: string,
    courseId: string
  ) =>
    [
      ...gradeKeys.all,
      "student",
      studentId,
      courseId,
    ] as const,
}

export function useMyGrade(courseId: string) {
  return useQuery({
    queryKey: gradeKeys.mine(courseId),
    queryFn: () => getMyGrade(courseId),
    enabled: !!courseId,
  })
}

export function useStudentGrade(
  studentId: string,
  courseId: string
) {
  return useQuery({
    queryKey: gradeKeys.student(
      studentId,
      courseId
    ),
    queryFn: () =>
      getStudentGrade(studentId, courseId),
    enabled: !!studentId && !!courseId,
  })
}

export function useSetGrade() {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: ({
      enrollmentId,
      request,
    }: {
      enrollmentId: string
      request: SetGradeRequest
    }) =>
      setGrade(enrollmentId, request),

    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: gradeKeys.all,
      })

      queryClient.invalidateQueries({
        queryKey: ["enrollments"],
      })
    },
  })
}