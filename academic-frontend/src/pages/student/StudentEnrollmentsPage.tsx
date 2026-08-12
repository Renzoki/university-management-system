import { Link } from "react-router-dom"
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"
import { Badge } from "@/components/ui/badge"
import { useMyEnrollments } from "@/hooks/useEnrollments"

export default function StudentEnrollmentsPage() {
  const {
    data: enrollments,
    isLoading,
    isError,
    error,
  } = useMyEnrollments()

  if (isLoading) {
    return (
      <p className="text-sm text-muted-foreground">
        Loading your enrollments...
      </p>
    )
  }

  if (isError) {
    return (
      <p className="text-sm text-destructive">
        Failed to load your enrollments: {(error as Error).message}
      </p>
    )
  }

  const activeEnrollments = (enrollments ?? []).filter(
    (enrollment) => enrollment.status === "ACTIVE"
  )

  if (activeEnrollments.length === 0) {
    return (
      <div className="space-y-2">
        <h1 className="text-2xl font-semibold">My Enrollments</h1>
        <p className="text-sm text-muted-foreground">
          You are not currently enrolled in any courses.
        </p>
      </div>
    )
  }

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-semibold">My Enrollments</h1>
        <p className="text-sm text-muted-foreground">
          View the courses you are currently enrolled in.
        </p>
      </div>

      <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
        {activeEnrollments.map((enrollment) => {
          const course = enrollment.course

          return (
            <Link
              key={enrollment.enrollmentId}
              to={`/student/courses/${course.id}`}
            >
              <Card className="h-full transition-colors hover:bg-accent/40">
                <CardHeader>
                  <div className="flex items-start justify-between gap-4">
                    <CardTitle>{course.name}</CardTitle>

                    <Badge>{enrollment.status}</Badge>
                  </div>
                </CardHeader>

                <CardContent className="space-y-2">
                  <p className="text-sm text-muted-foreground">
                    {course.courseCode}
                  </p>

                  {course.faculty && (
                    <p className="text-sm">
                      {course.faculty.firstName}{" "}
                      {course.faculty.lastName}
                    </p>
                  )}

                  {enrollment.grade && (
                    <p className="text-sm text-muted-foreground">
                      Grade: {enrollment.grade.gradeEquivalent}
                    </p>
                  )}
                </CardContent>
              </Card>
            </Link>
          )
        })}
      </div>
    </div>
  )
}