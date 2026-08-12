import { academicClient } from "./academicClient"
import type { EnrollmentDto } from "@/types/dto"

export async function getMyEnrollments(): Promise<EnrollmentDto[]> {
  const response = await academicClient.get<EnrollmentDto[]>(
    "/enrollments/student/self"
  )

  return response.data
}

export async function enrollInCourse(
  courseId: string
): Promise<EnrollmentDto> {
  const response = await academicClient.post<EnrollmentDto>(
    `/enrollments/self/${courseId}`
  )

  return response.data
}

export async function dropEnrollment(
  enrollmentId: string
): Promise<EnrollmentDto> {
  const response = await academicClient.patch<EnrollmentDto>(
    `/enrollments/self/${enrollmentId}`
  )

  return response.data
}