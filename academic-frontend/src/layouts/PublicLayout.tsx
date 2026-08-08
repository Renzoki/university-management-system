import { Outlet } from "react-router-dom"

/**
 * Layout for public, unauthenticated pages (e.g. Login).
 * Centers content on a clean, branded background.
 */
export default function PublicLayout() {
  return (
    <div className="flex min-h-screen items-center justify-center bg-muted/40 px-4">
      <Outlet />
    </div>
  )
}
