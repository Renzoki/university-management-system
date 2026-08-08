import { Link } from "react-router-dom"
import { Button } from "@/components/ui/button"

export default function HomePage() {
  return (
    <div className="flex min-h-screen flex-col items-center justify-center gap-4 text-center">
      <h1 className="text-2xl font-semibold">Academic Management System</h1>
      <p className="text-muted-foreground">Frontend scaffold is running.</p>
      <Button asChild>
        <Link to="/login">Go to Login</Link>
      </Button>
    </div>
  )
}
