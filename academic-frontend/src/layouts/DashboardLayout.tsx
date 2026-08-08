import { Outlet } from "react-router-dom"

/**
 * Shared application shell for all authenticated areas
 * (Admin / Faculty / Student). Top navigation bar + sidebar
 * + main content area, per the spec.
 *
 * The sidebar itself is role-specific and will be built in a
 * later prompt; for now this just provides the shell structure.
 */
export default function DashboardLayout() {
  return (
    <div className="flex min-h-screen flex-col">
      <header className="flex h-14 items-center border-b border-border px-6">
        <span className="font-semibold">Academic Management System</span>
      </header>
      <div className="flex flex-1">
        <aside className="hidden w-60 shrink-0 border-r border-border p-4 md:block">
          {/* Role-based sidebar placeholder */}
        </aside>
        <main className="container-page flex-1 py-6">
          <Outlet />
        </main>
      </div>
    </div>
  )
}
