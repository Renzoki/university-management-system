import { useState } from "react"
import { useParams } from "react-router-dom"
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { useCourse } from "@/hooks/useCourses"
import { useCourseEnrollments } from "@/hooks/useEnrollments"
import { useSetGrade } from "@/hooks/useGrades"
import { toast } from "sonner"

export default function FacultyCourseDetailPage() {
  const { courseId } = useParams<{ courseId: string }>()

  const [gradeInputs, setGradeInputs] = useState<
    Record<string, string>
  >({})

  const {
    data: course,
    isLoading: courseLoading,
    isError: courseError,
    error: courseErrorData,
  } = useCourse(courseId ?? "")

  const {
    data: enrollments,
    isLoading: enrollmentsLoading,
    isError: enrollmentsError,
    error: enrollmentsErrorData,
  } = useCourseEnrollments(courseId ?? "")

  const setGradeMutation = useSetGrade()

  if (courseLoading || enrollmentsLoading) {
    return (
      <p className="text-sm text-muted-foreground">
        Loading course...
      </p>
    )
  }

  if (courseError) {
    return (
      <p className="text-sm text-destructive">
        Failed to load course:{" "}
        {(courseErrorData as Error).message}
      </p>
    )
  }

  if (enrollmentsError) {
    return (
      <p className="text-sm text-destructive">
        Failed to load students:{" "}
        {(enrollmentsErrorData as Error).message}
      </p>
    )
  }

  if (!course) {
    return (
      <p className="text-sm text-muted-foreground">
        Course not found.
      </p>
    )
  }

  function handleGradeChange(
    enrollmentId: string,
    value: string
  ) {
    setGradeInputs((current) => ({
      ...current,
      [enrollmentId]: value,
    }))
  }

  function handleSaveGrade(
    enrollmentId: string,
    studentName: string
  ) {
    const value = gradeInputs[enrollmentId]

    if (value === undefined || value.trim() === "") {
      toast.error("Please enter a grade.")
      return
    }

    const rawGrade = Number(value)

    if (!Number.isFinite(rawGrade)) {
      toast.error("Please enter a valid grade.")
      return
    }

    if (rawGrade < 0 || rawGrade > 100) {
      toast.error("Grade must be between 0 and 100.")
      return
    }

    setGradeMutation.mutate(
      {
        enrollmentId,
        request: {
          rawGrade,
        },
      },
      {
        onSuccess: (grade) => {
          toast.success(
            `Grade saved for ${studentName}.`
          )

          setGradeInputs((current) => {
            const updated = { ...current }
            delete updated[enrollmentId]
            return updated
          })

          console.log("Saved grade:", grade)
        },
        onError: (error) => {
          toast.error(
            `Failed to save grade: ${
              (error as Error).message
            }`
          )
        },
      }
    )
  }

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-semibold">
          {course.name}
        </h1>

        <p className="text-sm text-muted-foreground">
          {course.courseCode}
        </p>

        {course.faculty && (
          <p className="mt-1 text-sm text-muted-foreground">
            Faculty: {course.faculty.firstName}{" "}
            {course.faculty.lastName}
          </p>
        )}
      </div>

      <Card>
        <CardHeader>
          <CardTitle>Enrolled Students</CardTitle>
        </CardHeader>

        <CardContent>
          {!enrollments || enrollments.length === 0 ? (
            <p className="text-sm text-muted-foreground">
              No students are currently enrolled in this
              course.
            </p>
          ) : (
            <div className="space-y-4">
              {enrollments.map((enrollment) => {
                const studentName = `${enrollment.student.firstName} ${enrollment.student.lastName}`

                const existingGrade =
                  enrollment.grade?.rawGrade

                const inputValue =
                  gradeInputs[enrollment.enrollmentId] ??
                  (existingGrade !== undefined
                    ? String(existingGrade)
                    : "")

                return (
                  <div
                    key={enrollment.enrollmentId}
                    className="rounded-lg border p-4"
                  >
                    <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
                      <div>
                        <p className="font-medium">
                          {studentName}
                        </p>

                        <p className="text-sm text-muted-foreground">
                          {enrollment.student.email}
                        </p>

                        {enrollment.grade && (
                          <div className="mt-2 space-y-1">
                            <p className="text-sm">
                              Raw Grade:{" "}
                              {enrollment.grade.rawGrade}
                            </p>

                            <p className="text-sm text-muted-foreground">
                              Grade Equivalent:{" "}
                              {
                                enrollment.grade
                                  .gradeEquivalent
                              }
                            </p>
                          </div>
                        )}

                        {!enrollment.grade && (
                          <p className="mt-2 text-sm text-muted-foreground">
                            No grade assigned
                          </p>
                        )}
                      </div>

                      <div className="flex w-full flex-col gap-2 sm:w-auto sm:min-w-56">
                        <Input
                          type="number"
                          min="0"
                          max="100"
                          step="0.01"
                          placeholder="Raw grade"
                          value={inputValue}
                          onChange={(event) =>
                            handleGradeChange(
                              enrollment.enrollmentId,
                              event.target.value
                            )
                          }
                        />

                        <Button
                          onClick={() =>
                            handleSaveGrade(
                              enrollment.enrollmentId,
                              studentName
                            )
                          }
                          disabled={setGradeMutation.isPending}
                        >
                          {setGradeMutation.isPending
                            ? "Saving..."
                            : enrollment.grade
                              ? "Update Grade"
                              : "Save Grade"}
                        </Button>
                      </div>
                    </div>
                  </div>
                )
              })}
            </div>
          )}
        </CardContent>
      </Card>
    </div>
  )
}