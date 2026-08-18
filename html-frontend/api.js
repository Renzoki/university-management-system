/* js/api.js
 * Thin fetch() wrappers around the two backend services.
 * Attaches Authorization header automatically from localStorage.
 * Requires auth.js to be loaded first.
 */

const AUTH_BASE = "http://localhost:8080/auth";
const ACADEMIC_BASE = "http://localhost:8081";
// SeedController has no /auth prefix, so it sits on the auth service root.
const AUTH_ROOT = "http://localhost:8080";

async function request(baseUrl, path, options = {}) {
  const headers = { "Content-Type": "application/json", ...(options.headers || {}) };
  const token = auth.getToken();
  if (token) headers["Authorization"] = `Bearer ${token}`;

  const res = await fetch(`${baseUrl}${path}`, { ...options, headers });

  if (!res.ok) {
    let message = `Request failed (${res.status})`;
    try {
      const body = await res.json();
      message = body.message || body.error || message;
    } catch {
      /* no JSON body */
    }
    throw new Error(message);
  }

  if (res.status === 204) return null;
  return res.json();
}

const api = {
  // ---- Auth ----
  login(email, password) {
    return request(AUTH_BASE, "/login", {
      method: "POST",
      body: JSON.stringify({ email, password }),
    });
  },

  /**
   * Demo-only: seeds the academic DB. Returns plain text (not JSON), and
   * throws if the backend reports it's already been seeded.
   */
  async seedAcademicData() {
    const res = await fetch(`${AUTH_ROOT}/seed/academic`, { method: "POST" });
    const text = await res.text();
    if (!res.ok) throw new Error(text || `Request failed (${res.status})`);
    return text;
  },

  // ---- Courses ----
  getCourses() {
    return request(ACADEMIC_BASE, "/courses");
  },

  getCourseById(courseId) {
    return request(ACADEMIC_BASE, `/courses/${courseId}`);
  },

  getCourseByCode(courseCode) {
    return request(ACADEMIC_BASE, `/courses/code/${courseCode}`);
  },

  getStudentsByCourseId(courseId) {
    return request(ACADEMIC_BASE, `/courses/${courseId}/students`);
  },

  getStudentsByCourseCode(courseCode) {
    return request(ACADEMIC_BASE, `/courses/code/${courseCode}/students`);
  },

  // ---- Self profiles ----
  getStudentSelf() {
    return request(ACADEMIC_BASE, "/students/self");
  },

  getFacultySelf() {
    return request(ACADEMIC_BASE, "/faculty/self");
  },

  // ---- Enrollments ----
  getMyEnrollments() {
    return request(ACADEMIC_BASE, "/enrollments/student/self");
  },

  getEnrollmentsByCourse(courseId) {
    return request(ACADEMIC_BASE, `/enrollments/courses/${courseId}`);
  },

  enrollSelf(courseId) {
    return request(ACADEMIC_BASE, `/enrollments/self/${courseId}`, { method: "POST" });
  },

  dropEnrollment(enrollmentId) {
    return request(ACADEMIC_BASE, `/enrollments/self/${enrollmentId}`, { method: "PATCH" });
  },

  // ---- Grades ----
  getMyGrade(courseId) {
    return request(ACADEMIC_BASE, `/grades/self/${courseId}`);
  },

  getGrade(studentId, courseId) {
    return request(ACADEMIC_BASE, `/grades/${studentId}/${courseId}`);
  },

  /** rawGrade must be a Double between 0.0 and 100.0 (SetGradeRequest). */
  setGrade(enrollmentId, rawGrade) {
    return request(ACADEMIC_BASE, `/grades/${enrollmentId}`, {
      method: "PUT",
      body: JSON.stringify({ rawGrade }),
    });
  },
};
