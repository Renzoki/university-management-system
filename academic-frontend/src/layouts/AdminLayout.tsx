import { Outlet } from "react-router-dom"

/**
 * Placeholder wrapper for Admin-only routes.
 * Will host the Admin-specific sidebar in a later prompt.
 */
export default function AdminLayout() {
  return <Outlet />
}
