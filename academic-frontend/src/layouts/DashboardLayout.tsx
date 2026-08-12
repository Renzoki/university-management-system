import { Outlet, useNavigate } from "react-router-dom"
import { useAuth } from "@/hooks/useAuth"

export default function DashboardLayout() {
  const { user, logout } = useAuth()
  const navigate = useNavigate()

  function handleLogout() {
    logout()
    navigate("/login", { replace: true })
  }

  return (
    <div className="min-h-screen bg-background">
      <header className="flex items-center justify-between border-b border-border px-6 py-4">
        <div>
          <h1 className="text-lg font-semibold">
            Academic Management System
          </h1>

          {user && (
            <p className="text-sm text-muted-foreground">
              {user.email}
            </p>
          )}
        </div>

        <button
          type="button"
          onClick={handleLogout}
          className="rounded-md border border-border px-4 py-2 text-sm font-medium hover:bg-muted"
        >
          Logout
        </button>
      </header>

      <main className="p-6">
        <Outlet />
      </main>
    </div>
  )
}