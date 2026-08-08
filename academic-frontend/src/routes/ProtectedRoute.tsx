import { Outlet } from "react-router-dom"

/**
 * Route guard placeholder.
 *
 * Authentication has not been implemented yet (per Prompt 1 scope).
 * Right now this simply renders its children. In the auth prompt,
 * this will check for a valid JWT / user session and redirect
 * unauthenticated users to /login.
 */
export default function ProtectedRoute() {
  return <Outlet />
}
