import { Link } from "react-router-dom"
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"

export default function FacultyDashboardPage() {
  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-semibold">
          Faculty Dashboard
        </h1>

        <p className="text-sm text-muted-foreground">
          Manage your assigned courses and student grades.
        </p>
      </div>

      <div className="grid gap-4 sm:grid-cols-2">
        <Link to="/faculty/courses">
          <Card className="h-full transition-colors hover:bg-accent/40">
            <CardHeader>
              <CardTitle>My Assigned Courses</CardTitle>
            </CardHeader>

            <CardContent>
              <p className="text-sm text-muted-foreground">
                View the courses assigned to you and manage your students.
              </p>
            </CardContent>
          </Card>
        </Link>
      </div>
    </div>
  )
}