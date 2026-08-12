import { useCourses } from "@/hooks/useCourses"
import {
  useEnrollInCourse,
  useMyEnrollments,
} from "@/hooks/useEnrollments"
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"
import { Badge } from "@/components/ui/badge"
import { Button } from "@/components/ui/button"
import { toast } from "sonner"

export default function StudentCoursesPage() {
  const {
    data: courses,
    isLoading: coursesLoading,
    isError: coursesError,
    error: coursesErrorData,
  } = useCourses()

  const {
    data: enrollments,
    isLoading: enrollmentsLoading,
    isError: enrollmentsError,
    error: enrollmentsErrorData,
  } = useMyEnrollments()

  const enrollMutation = useEnrollInCourse()

  if (coursesLoading || enrollmentsLoading) {
    return (
      <p className="text-sm text-muted-foreground">
        Loading courses...
      </p>
    )
  }

  if (coursesError) {
    return (
      <p className="text-sm text-destructive">
        Failed to load courses:{" "}
        {(coursesErrorData as Error).message}
      </p>
    )
  }

  if (enrollmentsError) {
    return (
      <p className="text-sm text-destructive">
        Failed to load your enrollments:{" "}
        {(enrollmentsErrorData as Error).message}
      </p>
    )
  }

  if (!courses || courses.length === 0) {
    return (
      <div className="space-y-2">
        <h1 className="text-2xl font-semibold">
          Courses
        </h1>

        <p className="text-sm text-muted-foreground">
          No courses are currently available.
        </p>
      </div>
    )
  }

  function getEnrollment(courseId: string) {
    return (enrollments ?? []).find(
      (enrollment) =>
        enrollment.course.id === courseId &&
        enrollment.status === "ACTIVE"
    )
  }

  function handleEnroll(
    courseId: string,
    courseName: string
  ) {
    enrollMutation.mutate(courseId, {
      onSuccess: () => {
        toast.success(
          `Successfully enrolled in ${courseName}.`
        )
      },
      onError: (error) => {
        toast.error(
          `Failed to enroll in ${courseName}: ${
            (error as Error).message
          }`
        )
      },
    })
  }

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-semibold">
          Courses
        </h1>

        <p className="text-sm text-muted-foreground">
          Browse the available courses.
        </p>
      </div>

      <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
        {courses.map((course) => {
          const enrollment = getEnrollment(course.id)

          return (
            <Card key={course.id}>
              <CardHeader>
                <div className="flex items-start justify-between gap-4">
                  <CardTitle>{course.name}</CardTitle>

                  <Badge>{course.status}</Badge>
                </div>
              </CardHeader>

              <CardContent className="space-y-4">
                <div className="space-y-2">
                  <p className="text-sm text-muted-foreground">
                    {course.courseCode}
                  </p>

                  {course.faculty ? (
                    <p className="text-sm">
                      {course.faculty.firstName}{" "}
                      {course.faculty.lastName}
                    </p>
                  ) : (
                    <p className="text-sm text-muted-foreground">
                      No faculty assigned
                    </p>
                  )}
                </div>

                {enrollment?.grade ? (
                  <div className="rounded-md border p-3">
                    <p className="text-sm font-medium">
                      Grade
                    </p>

                    <p className="text-sm text-muted-foreground">
                      Raw Grade:{" "}
                      {enrollment.grade.rawGrade}
                    </p>

                    <p className="text-sm text-muted-foreground">
                      Grade Equivalent:{" "}
                      {enrollment.grade.gradeEquivalent}
                    </p>
                  </div>
                ) : enrollment ? (
                  <div className="rounded-md border p-3">
                    <p className="text-sm font-medium">
                      Grade
                    </p>

                    <p className="text-sm text-muted-foreground">
                      No grade assigned yet.
                    </p>
                  </div>
                ) : null}

                {enrollment ? (
                  <Button
                    variant="secondary"
                    disabled
                    className="w-full"
                  >
                    Enrolled
                  </Button>
                ) : (
                  <Button
                    className="w-full"
                    disabled={enrollMutation.isPending}
                    onClick={() =>
                      handleEnroll(
                        course.id,
                        course.name
                      )
                    }
                  >
                    {enrollMutation.isPending
                      ? "Enrolling..."
                      : "Enroll"}
                  </Button>
                )}
              </CardContent>
            </Card>
          )
        })}
      </div>
    </div>
  )
}