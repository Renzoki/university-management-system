import { Outlet } from "react-router-dom"

/**
 * Placeholder wrapper for Student-only routes.
 * Will host the Student-specific sidebar in a later prompt.
 */
export default function StudentLayout() {
  return <Outlet />
}
