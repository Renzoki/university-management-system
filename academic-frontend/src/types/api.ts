/** Generic API response wrappers shared across services. */

export interface ApiError {
  message: string
  status?: number
}

export interface PaginatedResponse<T> {
  content: T[]
  totalElements: number
  totalPages: number
  page: number
  size: number
}
