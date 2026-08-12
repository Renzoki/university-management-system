import { useQuery } from "@tanstack/react-query"
import { getCourses } from "@/api/courseApi"

export function useCourses() {
  return useQuery({
    queryKey: ["courses"],
    queryFn: getCourses,
  })
}