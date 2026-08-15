import { NavLink, Outlet } from "react-router-dom"
import { BookOpen, LayoutDashboard } from "lucide-react"

const navigation = [
  {
    name: "Dashboard",
    href: "/faculty",
    icon: LayoutDashboard,
  },
  {
    name: "My Courses",
    href: "/faculty/courses",
    icon: BookOpen,
  },
]

export default function FacultyLayout() {
  return (
    <div className="flex min-h-screen">
      <aside className="w-64 shrink-0 border-r bg-background">
        <div className="flex h-full flex-col">
          <div className="border-b px-6 py-5">
            <h1 className="text-lg font-semibold">Faculty Portal</h1>
            <p className="text-sm text-muted-foreground">
              Academic Management
            </p>
          </div>

          <nav className="flex-1 space-y-1 p-4">
            {navigation.map((item) => {
              const Icon = item.icon

              return (
                <NavLink
                  key={item.href}
                  to={item.href}
                  end={item.href === "/faculty"}
                  className={({ isActive }) =>
                    [
                      "flex items-center gap-3 rounded-md px-3 py-2.5 text-sm font-medium transition-colors",
                      isActive
                        ? "bg-primary text-primary-foreground"
                        : "text-muted-foreground hover:bg-muted hover:text-foreground",
                    ].join(" ")
                  }
                >
                  <Icon className="h-5 w-5" />
                  <span>{item.name}</span>
                </NavLink>
              )
            })}
          </nav>
        </div>
      </aside>

      <main className="min-w-0 flex-1 p-6">
        <Outlet />
      </main>
    </div>
  )
}