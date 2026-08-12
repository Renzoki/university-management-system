import { useQuery } from "@tanstack/react-query"
import {
  getCourseById,
  getCourses,
} from "@/api/courseApi"

export const courseKeys = {
  all: ["courses"] as const,
  list: () => [...courseKeys.all, "list"] as const,
  detail: (courseId: string) =>
    [...courseKeys.all, "detail", courseId] as const,
}

export function useCourses() {
  return useQuery({
    queryKey: courseKeys.list(),
    queryFn: getCourses,
  })
}

export function useCourse(courseId: string) {
  return useQuery({
    queryKey: courseKeys.detail(courseId),
    queryFn: () => getCourseById(courseId),
    enabled: !!courseId,
  })
}