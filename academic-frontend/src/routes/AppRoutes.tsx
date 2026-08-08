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
import StudentDashboardPage from "@/pages/student/StudentDashboardPage"
import NotFoundPage from "@/pages/errors/NotFoundPage"

/**
 * Central route tree.
 *
 * Structure:
 *  - Public routes  ("/", "/login")
 *  - Protected shell (DashboardLayout) wraps every authenticated area
 *    - Admin routes,   gated by RoleRoute(["ADMIN"])
 *    - Faculty routes, gated by RoleRoute(["FACULTY"])
 *    - Student routes, gated by RoleRoute(["STUDENT"])
 *
 * No authentication logic exists yet — ProtectedRoute / RoleRoute
 * are pass-through placeholders for now.
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
          {/* Admin */}
          <Route element={<RoleRoute allowedRoles={["ADMIN"]} />}>
            <Route element={<AdminLayout />}>
              <Route path="/admin" element={<AdminDashboardPage />} />
            </Route>
          </Route>

          {/* Faculty */}
          <Route element={<RoleRoute allowedRoles={["FACULTY"]} />}>
            <Route element={<FacultyLayout />}>
              <Route path="/faculty" element={<FacultyDashboardPage />} />
            </Route>
          </Route>

          {/* Student */}
          <Route element={<RoleRoute allowedRoles={["STUDENT"]} />}>
            <Route element={<StudentLayout />}>
              <Route path="/student" element={<StudentDashboardPage />} />
            </Route>
          </Route>
        </Route>
      </Route>

      {/* 404 */}
      <Route path="*" element={<NotFoundPage />} />
    </Routes>
  )
}
