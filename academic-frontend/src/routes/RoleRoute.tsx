import { Navigate, Outlet } from "react-router-dom"
import type { Role } from "@/types"
import { useAuth } from "@/hooks/useAuth"

interface RoleRouteProps {
  allowedRoles: Role[]
}

export default function RoleRoute({ allowedRoles }: RoleRouteProps) {
  const { user } = useAuth()

  if (!user) {
    return <Navigate to="/login" replace />
  }

  if (!allowedRoles.includes(user.role)) {
    return <Navigate to="/403" replace />
  }

  return <Outlet />
}