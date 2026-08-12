export type CourseStatus = string
export type StudentStatus = string
export type FacultyStatus = string

export type EnrollmentStatus =
  | "ACTIVE"
  | "COMPLETED"
  | "DROPPED"

export interface FacultyDto {
  id: string
  firstName: string
  lastName: string
  email: string
  status: FacultyStatus
}

export interface StudentDto {
  id: string
  firstName: string
  lastName: string
  email: string
  status: StudentStatus
}

export interface CourseDto {
  id: string
  name: string
  courseCode: string
  status: CourseStatus
  faculty: FacultyDto | null
}

export interface GradeDto {
  gradeId: string
  rawGrade: number
  gradeEquivalent: number
}

export interface EnrollmentDto {
  enrollmentId: string
  course: CourseDto
  student: StudentDto
  grade: GradeDto | null
  status: EnrollmentStatus
}