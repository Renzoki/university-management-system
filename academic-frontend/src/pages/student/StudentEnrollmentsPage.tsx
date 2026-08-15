import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"
import { Badge } from "@/components/ui/badge"
import { Button } from "@/components/ui/button"
import {
  useDropEnrollment,
  useMyEnrollments,
} from "@/hooks/useEnrollments"
import { toast } from "sonner"

export default function StudentEnrollmentsPage() {
  const {
    data: enrollments,
    isLoading,
    isError,
    error,
  } = useMyEnrollments()

  const dropMutation = useDropEnrollment()

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
        Failed to load your enrollments:{" "}
        {(error as Error).message}
      </p>
    )
  }

  const currentEnrollments = (enrollments ?? []).filter(
    (enrollment) => enrollment.status === "ACTIVE"
  )

  const completedEnrollments = (enrollments ?? []).filter(
    (enrollment) => enrollment.status === "COMPLETED"
  )

  async function handleDrop(enrollmentId: string) {
    try {
      await dropMutation.mutateAsync(enrollmentId)

      toast.success("Course dropped successfully.")
    } catch (error) {
      toast.error(
        error instanceof Error
          ? error.message
          : "Failed to drop the course."
      )
    }
  }

  function renderGrade(
    enrollment: (typeof enrollments)[number]
  ) {
    if (!enrollment.grade) {
      return (
        <span className="text-muted-foreground">
          No grade assigned yet
        </span>
      )
    }

    return (
      <div className="space-y-1">
        <p>
          Raw: {enrollment.grade.rawGrade}
        </p>

        <p>
          Equivalent: {enrollment.grade.gradeEquivalent}
        </p>
      </div>
    )
  }

  return (
    <div className="space-y-8">
      <div>
        <h1 className="text-2xl font-semibold">
          My Enrollments
        </h1>

        <p className="text-sm text-muted-foreground">
          View your current and completed courses.
        </p>
      </div>

      <section className="space-y-4">
        <div>
          <h2 className="text-xl font-semibold">
            Current Courses
          </h2>

          <p className="text-sm text-muted-foreground">
            Courses you are currently enrolled in.
          </p>
        </div>

        {currentEnrollments.length === 0 ? (
          <Card>
            <CardContent className="pt-6">
              <p className="text-sm text-muted-foreground">
                You are not currently enrolled in any courses.
              </p>
            </CardContent>
          </Card>
        ) : (
          <Card>
            <CardHeader>
              <CardTitle>
                Current Enrollments
              </CardTitle>
            </CardHeader>

            <CardContent>
              <div className="overflow-x-auto">
                <table className="w-full text-sm">
                  <thead>
                    <tr className="border-b text-left">
                      <th className="px-4 py-3 font-medium">
                        Course
                      </th>

                      <th className="px-4 py-3 font-medium">
                        Code
                      </th>

                      <th className="px-4 py-3 font-medium">
                        Faculty
                      </th>

                      <th className="px-4 py-3 font-medium">
                        Grade
                      </th>

                      <th className="px-4 py-3 font-medium">
                        Status
                      </th>

                      <th className="px-4 py-3 font-medium">
                        Action
                      </th>
                    </tr>
                  </thead>

                  <tbody>
                    {currentEnrollments.map(
                      (enrollment) => (
                        <tr
                          key={enrollment.enrollmentId}
                          className="border-b last:border-0"
                        >
                          <td className="px-4 py-3 font-medium">
                            {enrollment.course.name}
                          </td>

                          <td className="px-4 py-3 text-muted-foreground">
                            {enrollment.course.courseCode}
                          </td>

                          <td className="px-4 py-3">
                            {enrollment.course.faculty ? (
                              <>
                                {
                                  enrollment.course.faculty
                                    .firstName
                                }{" "}
                                {
                                  enrollment.course.faculty
                                    .lastName
                                }
                              </>
                            ) : (
                              <span className="text-muted-foreground">
                                No faculty assigned
                              </span>
                            )}
                          </td>

                          <td className="px-4 py-3">
                            {renderGrade(enrollment)}
                          </td>

                          <td className="px-4 py-3">
                            <Badge>
                              {enrollment.status}
                            </Badge>
                          </td>

                          <td className="px-4 py-3">
                            <Button
                              type="button"
                              disabled={dropMutation.isPending}
                              onClick={() =>
                                handleDrop(
                                  enrollment.enrollmentId
                                )
                              }
                            >
                              {dropMutation.isPending
                                ? "Dropping..."
                                : "Drop"}
                            </Button>
                          </td>
                        </tr>
                      )
                    )}
                  </tbody>
                </table>
              </div>
            </CardContent>
          </Card>
        )}
      </section>

      <section className="space-y-4">
        <div>
          <h2 className="text-xl font-semibold">
            Completed Courses
          </h2>

          <p className="text-sm text-muted-foreground">
            Courses you have already completed.
          </p>
        </div>

        {completedEnrollments.length === 0 ? (
          <Card>
            <CardContent className="pt-6">
              <p className="text-sm text-muted-foreground">
                You have not completed any courses yet.
              </p>
            </CardContent>
          </Card>
        ) : (
          <Card>
            <CardHeader>
              <CardTitle>
                Completed Enrollments
              </CardTitle>
            </CardHeader>

            <CardContent>
              <div className="overflow-x-auto">
                <table className="w-full text-sm">
                  <thead>
                    <tr className="border-b text-left">
                      <th className="px-4 py-3 font-medium">
                        Course
                      </th>

                      <th className="px-4 py-3 font-medium">
                        Code
                      </th>

                      <th className="px-4 py-3 font-medium">
                        Faculty
                      </th>

                      <th className="px-4 py-3 font-medium">
                        Grade
                      </th>

                      <th className="px-4 py-3 font-medium">
                        Status
                      </th>
                    </tr>
                  </thead>

                  <tbody>
                    {completedEnrollments.map(
                      (enrollment) => (
                        <tr
                          key={enrollment.enrollmentId}
                          className="border-b last:border-0"
                        >
                          <td className="px-4 py-3 font-medium">
                            {enrollment.course.name}
                          </td>

                          <td className="px-4 py-3 text-muted-foreground">
                            {enrollment.course.courseCode}
                          </td>

                          <td className="px-4 py-3">
                            {enrollment.course.faculty ? (
                              <>
                                {
                                  enrollment.course.faculty
                                    .firstName
                                }{" "}
                                {
                                  enrollment.course.faculty
                                    .lastName
                                }
                              </>
                            ) : (
                              <span className="text-muted-foreground">
                                No faculty assigned
                              </span>
                            )}
                          </td>

                          <td className="px-4 py-3">
                            {renderGrade(enrollment)}
                          </td>

                          <td className="px-4 py-3">
                            <Badge>
                              {enrollment.status}
                            </Badge>
                          </td>
                        </tr>
                      )
                    )}
                  </tbody>
                </table>
              </div>
            </CardContent>
          </Card>
        )}
      </section>
    </div>
  )
}