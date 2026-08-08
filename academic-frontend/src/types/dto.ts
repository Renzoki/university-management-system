/**
 * Placeholder DTOs mirroring the backend entities.
 * Will be fleshed out once each feature (courses, students,
 * faculty, enrollments, grades) is implemented.
 */

export interface CourseDto {
  id: number
  courseCode: string
  title: string
}

export interface StudentDto {
  id: number
  name: string
  email: string
}

export interface FacultyDto {
  id: number
  name: string
  email: string
}

export interface EnrollmentDto {
  id: number
  studentId: number
  courseId: number
  status: string
}

export interface GradeDto {
  enrollmentId: number
  grade: string | null
}
