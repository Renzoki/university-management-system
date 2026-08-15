import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useLoginMutation } from "@/hooks/useLoginMutation";
import { useAuth } from "@/hooks/useAuth";
import { decodeToken } from "@/utils/jwt";
import { seedAcademicData } from "@/api/auth/seedApi";

const SEED_STORAGE_KEY = "academic-data-seeded";

export default function LoginPage() {
  const navigate = useNavigate();
  const { loginWithToken } = useAuth();
  const loginMutation = useLoginMutation();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const [isSeeding, setIsSeeding] = useState(false);
  const [isSeeded, setIsSeeded] = useState(
    localStorage.getItem(SEED_STORAGE_KEY) === "true"
  );
  const [seedMessage, setSeedMessage] = useState(
    localStorage.getItem(SEED_STORAGE_KEY) === "true"
      ? "Academic data has already been seeded."
      : ""
  );
  const [seedError, setSeedError] = useState("");

  function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();

    loginMutation.mutate(
      { email, password },
      {
        onSuccess: (data) => {
          loginWithToken(data.accessToken);

          const user = decodeToken(data.accessToken);

          if (user?.role === "FACULTY") {
            navigate("/faculty");
          } else if (user?.role === "STUDENT") {
            navigate("/student");
          }
        },
        onError: (error) => {
          console.error("LOGIN ERROR:", error);
        },
      }
    );
  }

  async function handleSeed() {
    if (isSeeded || isSeeding) {
      return;
    }

    setIsSeeding(true);
    setSeedMessage("");
    setSeedError("");

    try {
      const message = await seedAcademicData();

      localStorage.setItem(SEED_STORAGE_KEY, "true");
      setIsSeeded(true);
      setSeedMessage(message);
    } catch (error) {
      console.error("SEED ERROR:", error);

      setSeedError(
        error instanceof Error
          ? error.message
          : "Failed to seed academic data."
      );
    } finally {
      setIsSeeding(false);
    }
  }

  return (
    <div className="flex min-h-screen w-full items-center justify-center px-6">
      <div className="flex w-full max-w-4xl items-center justify-center gap-6">
        <div className="w-full max-w-sm rounded-xl border border-border bg-card p-8 shadow-sm">
          <h1 className="text-xl font-semibold">Login</h1>

          <p className="mt-1 text-sm text-muted-foreground">
            Sign in to your account
          </p>

          <form onSubmit={handleSubmit} className="mt-6 space-y-4">
            <div>
              <label htmlFor="email" className="text-sm font-medium">
                Email
              </label>

              <input
                id="email"
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="you@example.com"
                required
                className="mt-1 w-full rounded-md border border-input bg-background px-3 py-2 text-sm"
              />
            </div>

            <div>
              <label htmlFor="password" className="text-sm font-medium">
                Password
              </label>

              <input
                id="password"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="Password"
                required
                className="mt-1 w-full rounded-md border border-input bg-background px-3 py-2 text-sm"
              />
            </div>

            {loginMutation.isError && (
              <p className="text-sm text-red-500">
                Invalid email or password.
              </p>
            )}

            <button
              type="submit"
              disabled={loginMutation.isPending}
              className="w-full rounded-md bg-primary px-4 py-2 text-sm font-medium text-primary-foreground disabled:opacity-50"
            >
              {loginMutation.isPending ? "Logging in..." : "Login"}
            </button>
          </form>
        </div>

        <div className="w-full max-w-sm rounded-xl border border-border bg-card p-8 shadow-sm">
          <h2 className="text-lg font-semibold">Development Seed</h2>

          <p className="mt-1 text-sm text-muted-foreground">
            Click the button below{" "}
            <span className="font-medium text-foreground">first</span> before
            logging in if the demo data has not been created yet.
          </p>

          <button
            type="button"
            onClick={handleSeed}
            disabled={isSeeding || isSeeded}
            className="mt-6 w-full rounded-md bg-primary px-4 py-2 text-sm font-medium text-primary-foreground disabled:cursor-not-allowed disabled:opacity-50"
          >
            {isSeeding
              ? "Seeding..."
              : isSeeded
                ? "Academic Data Already Seeded"
                : "Seed Academic Data"}
          </button>

          {seedMessage && (
            <p className="mt-3 text-sm text-green-600">
              {seedMessage}
            </p>
          )}

          {seedError && (
            <p className="mt-3 text-sm text-red-500">
              {seedError}
            </p>
          )}

          <div className="mt-6 space-y-4 border-t pt-5 text-sm">
            <div>
              <p className="font-medium">Admin</p>
              <p className="text-muted-foreground">
                admin@example.com
              </p>
              <p className="text-muted-foreground">
                Password: admin123
              </p>
            </div>

            <div>
              <p className="font-medium">Students</p>
              <div className="mt-1 space-y-1 text-muted-foreground">
                <p>juan.delacruz@dlsu.edu.ph / password123</p>
                <p>ana.reyes@dlsu.edu.ph / password123</p>
                <p>miguel.garcia@dlsu.edu.ph / password123</p>
              </div>
            </div>

            <div>
              <p className="font-medium">Faculty</p>
              <div className="mt-1 space-y-1 text-muted-foreground">
                <p>john.smith@dlsu.edu.ph / password123</p>
                <p>maria.santos@dlsu.edu.ph / password123</p>
              </div>
            </div>

            <div>
              <p className="font-medium">Academic Data</p>
              <p className="text-muted-foreground">
                Courses, faculty assignments, and enrollments.
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}