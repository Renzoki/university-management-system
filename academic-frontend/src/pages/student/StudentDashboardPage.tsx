import { Link } from "react-router-dom"
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"

export default function StudentDashboardPage() {
  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-semibold">
          Student Dashboard
        </h1>

        <p className="text-sm text-muted-foreground">
          Manage your courses and view your grades.
        </p>
      </div>

      <div className="grid gap-4 sm:grid-cols-2">
        <Link to="/student/courses">
          <Card className="h-full transition-colors hover:bg-accent/40">
            <CardHeader>
              <CardTitle>Browse Courses</CardTitle>
            </CardHeader>

            <CardContent>
              <p className="text-sm text-muted-foreground">
                Browse available courses and enroll in the ones you want to
                take.
              </p>
            </CardContent>
          </Card>
        </Link>

        <Link to="/student/enrollments">
          <Card className="h-full transition-colors hover:bg-accent/40">
            <CardHeader>
              <CardTitle>My Enrollments & Grades</CardTitle>
            </CardHeader>

            <CardContent>
              <p className="text-sm text-muted-foreground">
                View your enrolled courses and check your grades.
              </p>
            </CardContent>
          </Card>
        </Link>
      </div>
    </div>
  )
}