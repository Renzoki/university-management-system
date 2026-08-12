import axios from "axios"
import { tokenStorage } from "@/utils/jwt"

/**
 * Axios instance for the Academic service (courses, students, faculty,
 * enrollments, grades). This is a SEPARATE backend service from auth,
 * running on its own base URL (VITE_ACADEMIC_API_URL).
 *
 * Unlike authClient, every request here needs a valid JWT attached,
 * since all academic endpoints require authentication.
 */
export const academicClient = axios.create({
  baseURL: import.meta.env.VITE_ACADEMIC_API_URL,
  headers: {
    "Content-Type": "application/json",
  },
})

// Attach the JWT to every outgoing request.
academicClient.interceptors.request.use((config) => {
  const token = tokenStorage.get()

  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }

  return config
})

// If the academic service rejects the token (expired / invalid),
// clear it and send the user back to login. We do a hard redirect
// here (not React Router) because this runs outside React's render
// cycle and we want it to work no matter where in the app it fires.
academicClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      tokenStorage.clear()
      window.location.href = "/login"
    }

    return Promise.reject(error)
  }
)

export default academicClient