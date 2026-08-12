import { Link } from "react-router-dom"
import { useCourses } from "@/hooks/useCourses"
import { useAuth } from "@/hooks/useAuth"
import {
  Card,
  CardHeader,
  CardTitle,
  CardContent,
} from "@/components/ui/card"

export default function FacultyCoursesPage() {
  const { user } = useAuth()
  const {
    data: courses,
    isLoading,
    isError,
    error,
  } = useCourses()

  if (isLoading) {
    return (
      <p className="text-sm text-muted-foreground">
        Loading courses...
      </p>
    )
  }

  if (isError) {
    return (
      <p className="text-sm text-destructive">
        Failed to load courses:{" "}
        {(error as Error).message}
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
      <div>
        <h1 className="text-2xl font-semibold">
          My Courses
        </h1>

        <p className="text-sm text-muted-foreground">
          Manage students and grades for your assigned
          courses.
        </p>
      </div>

      <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
        {myCourses.map((course) => (
          <Link
            key={course.id}
            to={`/faculty/courses/${course.id}`}
          >
            <Card className="h-full transition-colors hover:bg-accent/40">
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