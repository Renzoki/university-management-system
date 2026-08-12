import { academicClient } from "./academicClient"
import type { CourseDto, StudentDto } from "@/types/dto"

/**
 * Course-related API calls against the Academic service.
 * All IDs are UUID strings, matching the backend's CourseDTO.
 */
export async function getCourses(): Promise<CourseDto[]> {
  const response = await academicClient.get<CourseDto[]>("/courses")
  return response.data
}

export async function getCourseById(courseId: string): Promise<CourseDto> {
  const response = await academicClient.get<CourseDto>(`/courses/${courseId}`)
  return response.data
}

/**
 * Students enrolled in a given course.
 * Backend restricts this to ADMIN and FACULTY.
 */
export async function getStudentsByCourseId(
  courseId: string
): Promise<StudentDto[]> {
  const response = await academicClient.get<StudentDto[]>(
    `/courses/${courseId}/students`
  )
  return response.data
}