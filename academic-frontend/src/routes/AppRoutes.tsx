import { Routes, Route } from "react-router-dom"

import PublicLayout from "@/layouts/PublicLayout"
import DashboardLayout from "@/layouts/DashboardLayout"
import AdminLayout from "@/layouts/AdminLayout"
import FacultyLayout from "@/layouts/FacultyLayout"
import StudentLayout from "@/layouts/StudentLayout"

import ProtectedRoute from "./ProtectedRoute"
import RoleRoute from "./RoleRoute"

import HomePage from "@/pages/HomePage"
import LoginPage from "@/pages/auth/LoginPage"
import AdminDashboardPage from "@/pages/admin/AdminDashboardPage"
import FacultyDashboardPage from "@/pages/faculty/FacultyDashboardPage"
import FacultyCoursesPage from "@/pages/faculty/FacultyCoursesPage"
import StudentDashboardPage from "@/pages/student/StudentDashboardPage"
import StudentCoursesPage from "@/pages/student/StudentCoursesPage"
import StudentEnrollmentsPage from "@/pages/student/StudentEnrollmentsPage"
import NotFoundPage from "@/pages/errors/NotFoundPage"
import ForbiddenPage from "@/pages/errors/ForbiddenPage"

/**
 * Central route tree.
 *
 * Structure:
 *  - Public routes  ("/", "/login")
 *  - Protected shell (DashboardLayout) wraps every authenticated area
 *    - Admin routes,   gated by RoleRoute(["ADMIN"])   [not implemented yet]
 *    - Faculty routes, gated by RoleRoute(["FACULTY"])
 *    - Student routes, gated by RoleRoute(["STUDENT"])
 *  - /403  → shown when an authenticated user hits a route their
 *            role doesn't allow (see RoleRoute)
 *  - *     → 404 fallback
 */
export default function AppRoutes() {
  return (
    <Routes>
      {/* Public routes */}
      <Route element={<PublicLayout />}>
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<LoginPage />} />
      </Route>

      {/* Protected routes */}
      <Route element={<ProtectedRoute />}>
        <Route element={<DashboardLayout />}>
          {/* Admin — not implemented yet, kept as placeholder */}
          <Route element={<RoleRoute allowedRoles={["ADMIN"]} />}>
            <Route element={<AdminLayout />}>
              <Route path="/admin" element={<AdminDashboardPage />} />
            </Route>
          </Route>

          {/* Faculty */}
          <Route element={<RoleRoute allowedRoles={["FACULTY"]} />}>
            <Route element={<FacultyLayout />}>
              <Route path="/faculty" element={<FacultyDashboardPage />} />
              <Route path="/faculty/courses" element={<FacultyCoursesPage />} />
            </Route>
          </Route>

          {/* Student */}
          <Route element={<RoleRoute allowedRoles={["STUDENT"]} />}>
            <Route element={<StudentLayout />}>
              <Route
                path="/student"
                element={<StudentDashboardPage />}
              />

              <Route
                path="/student/courses"
                element={<StudentCoursesPage />}
              />

              <Route
                path="/student/enrollments"
                element={<StudentEnrollmentsPage />}
              />
            </Route>
          </Route>
        </Route>
      </Route>

      {/* Errors */}
      <Route path="/403" element={<ForbiddenPage />} />
      <Route path="*" element={<NotFoundPage />} />
    </Routes>
  )
}