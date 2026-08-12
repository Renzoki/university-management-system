import { Link } from "react-router-dom"
import { useCourses } from "@/hooks/useCourses"
import { useAuth } from "@/hooks/useAuth"
import {
  Card,
  CardHeader,
  CardTitle,
  CardContent,
} from "@/components/ui/card"

/**
 * Shows only the courses assigned to the logged-in faculty member.
 *
 * ASSUMPTION: there is currently no backend endpoint that returns
 * "my assigned courses" directly. GET /courses returns every course
 * in the system, and each CourseDTO includes a nested FacultyDTO.
 * So we filter client-side by matching course.faculty.id against the
 * logged-in user's id (from the decoded JWT).
 *
 * This is a stopgap. If the course list grows large, this should be
 * replaced by a real backend endpoint (e.g. GET /faculty/self/courses)
 * instead of fetching and filtering the entire course list.
 */
export default function FacultyCoursesPage() {
  const { user } = useAuth()
  const { data: courses, isLoading, isError, error } = useCourses()

  if (isLoading) {
    return <p className="text-sm text-muted-foreground">Loading courses...</p>
  }

  if (isError) {
    return (
      <p className="text-sm text-destructive">
        Failed to load courses: {(error as Error).message}
      </p>
    )
  }

  const myCourses = (courses ?? []).filter(
    (course) => course.faculty?.id === user?.id
  )

  if (myCourses.length === 0) {
    return (
      <p className="text-sm text-muted-foreground">
        No courses assigned to you yet.
      </p>
    )
  }

  return (
    <div className="space-y-4">
      <h1 className="text-2xl font-semibold">My Courses</h1>

      <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
        {myCourses.map((course) => (
          <Link key={course.id} to={`/faculty/courses/${course.id}`}>
            <Card className="transition-colors hover:bg-accent/40">
              <CardHeader>
                <CardTitle>{course.name}</CardTitle>
              </CardHeader>
              <CardContent>
                <p className="text-sm text-muted-foreground">
                  {course.courseCode}
                </p>
              </CardContent>
            </Card>
          </Link>
        ))}
      </div>
    </div>
  )
}