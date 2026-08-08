import { Outlet } from "react-router-dom"
import type { Role } from "@/types"

interface RoleRouteProps {
  allowedRoles: Role[]
}

/**
 * Role guard placeholder.
 *
 * Will check the authenticated user's role against `allowedRoles`
 * and redirect (e.g. to a 403 page) if it doesn't match. For now,
 * with no auth in place, it simply renders its children.
 */
export default function RoleRoute({ allowedRoles }: RoleRouteProps) {
  void allowedRoles
  return <Outlet />
}
