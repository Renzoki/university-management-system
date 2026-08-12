import { academicClient } from "./academicClient"
import type { GradeDto } from "@/types/dto"

export interface SetGradeRequest {
  rawGrade: number
}

export async function getMyGrade(
  courseId: string
): Promise<GradeDto> {
  const response = await academicClient.get<GradeDto>(
    `/grades/self/${courseId}`
  )

  return response.data
}

export async function getStudentGrade(
  studentId: string,
  courseId: string
): Promise<GradeDto> {
  const response = await academicClient.get<GradeDto>(
    `/grades/${studentId}/${courseId}`
  )

  return response.data
}

export async function setGrade(
  enrollmentId: string,
  request: SetGradeRequest
): Promise<GradeDto> {
  const response = await academicClient.put<GradeDto>(
    `/grades/${enrollmentId}`,
    request
  )

  return response.data
}