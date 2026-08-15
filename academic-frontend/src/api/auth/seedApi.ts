import axios from "axios";

export async function seedAcademicData(): Promise<string> {
  const baseUrl = import.meta.env.VITE_API_BASE_URL.replace(/\/api$/, "");

  try {
    const response = await axios.post<string>(
      `${baseUrl}/seed/academic`
    );

    return response.data;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      console.error("SEED REQUEST ERROR:", {
        url: error.config?.url,
        method: error.config?.method,
        status: error.response?.status,
        data: error.response?.data,
        message: error.message,
      });

      if (error.response?.status === 409) {
        return "Academic data is already seeded.";
      }

      throw new Error(
        error.response?.data ||
          `Seed request failed (${error.response?.status ?? "network error"})`
      );
    }

    throw error;
  }
}