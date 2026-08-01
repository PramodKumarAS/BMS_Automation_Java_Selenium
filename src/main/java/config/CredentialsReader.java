package config;

import io.github.cdimascio.dotenv.Dotenv;

public class CredentialsReader {

    // Loaded once, lazily. Safe if .env doesn't exist (Jenkins/GitHub won't have one).
    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    public static String username(String usernameVariable) {
        return get(usernameVariable);
    }

    public static String password(String passwordVariable) {
        return get(passwordVariable);
    }

    /**
     * Resolution order:
     * 1. Real OS/CI environment variable   (Jenkins, GitHub Actions)
     * 2. JVM system property (-Dkey=value) (optional override, any environment)
     * 3. .env file                          (local dev only)
     */
    private static String get(String key) {
        String value = System.getenv(key);

        if (value == null || value.isBlank()) {
            value = dotenv.get(key);
        }

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Missing required credential: " + key +
                            " (checked env var, system property, and .env file)"
            );
        }

        return value;
    }
}