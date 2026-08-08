import axios from "axios"

/**
 * Central Axios instance used by every API service in the app.
 *
 * - baseURL comes from the VITE_API_BASE_URL env variable so the
 *   frontend can point at different backend environments without
 *   code changes.
 * - The auth interceptor (registered separately, once auth exists)
 *   will attach the JWT to every outgoing request and handle
 *   401 responses by logging the user out.
 */
export const axiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
})

export default axiosInstance
