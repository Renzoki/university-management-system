/* js/auth.js
 * Token storage, JWT decoding, and page-guard helpers.
 * Load this before any other script on protected pages.
 */

const TOKEN_KEY = "accessToken";

/** Base64url decode (JWT payload segment) -> parsed JSON object. */
function decodeJwtPayload(token) {
  const parts = token.split(".");
  if (parts.length !== 3) throw new Error("Malformed JWT");
  let b64 = parts[1].replace(/-/g, "+").replace(/_/g, "/");
  while (b64.length % 4) b64 += "=";
  const json = decodeURIComponent(
    atob(b64)
      .split("")
      .map((c) => "%" + c.charCodeAt(0).toString(16).padStart(2, "0"))
      .join("")
  );
  return JSON.parse(json);
}

const auth = {
  getToken() {
    return localStorage.getItem(TOKEN_KEY);
  },

  setToken(token) {
    localStorage.setItem(TOKEN_KEY, token);
  },

  clearToken() {
    localStorage.removeItem(TOKEN_KEY);
  },

  /** Decoded payload of the stored token, or null if none/invalid. */
  getPayload() {
    const token = this.getToken();
    if (!token) return null;
    try {
      return decodeJwtPayload(token);
    } catch {
      return null;
    }
  },

  isTokenExpired() {
    const payload = this.getPayload();
    if (!payload || !payload.exp) return true;
    return Date.now() / 1000 >= payload.exp;
  },

  isAuthenticated() {
    return !!this.getToken() && !this.isTokenExpired();
  },

  getRole() {
    return this.getPayload()?.role ?? null;
  },

  getUserId() {
    return this.getPayload()?.sub ?? null;
  },

  logout() {
    this.clearToken();
    window.location.href = "login.html";
  },

  /**
   * Call at the top of every protected page.
   * Redirects to login.html if not authenticated, or 403.html if the
   * user's role isn't in allowedRoles.
   * @param {string[]} allowedRoles e.g. ["FACULTY"]
   */
  requireRole(allowedRoles) {
    if (!this.isAuthenticated()) {
      window.location.href = "login.html";
      return;
    }
    const role = this.getRole();
    if (!allowedRoles.includes(role)) {
      window.location.href = "403.html";
    }
  },
};
