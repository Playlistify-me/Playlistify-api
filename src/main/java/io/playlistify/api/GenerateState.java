package io.playlistify.api;

/**
 * Generates a random string of characters.
 */
public class GenerateState {
    /**
     * Generates a random string of characters.
     *
     * @param length The length of the string to be generated.
     *               Will be checked by {@link #checkLength(int)}.
     * @return The generated string of random characters.
     */
    public static String generateString(int length) {
        String generatedString = "";
        String possibleCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        int randomCharPosition;

        for (int i = 0; i < checkLength(length); i++) {
            randomCharPosition = (int) Math.floor(Math.random() * possibleCharacters.length());
            generatedString += possibleCharacters.charAt(randomCharPosition);
        }

        return generatedString;
    }

    /**
     * Checks if the length of the generated string is between 25 and 65.
     *
     * @param length The length to check.
     * @return The length of the string to be generated if it is between 25 and 65. Otherwise, returns 35.
     */
    public static int checkLength(int length) {
        int minimumLength = 25;
        int maximumLength = 65;

        int defaultLength = 35;

        if ((length >= minimumLength) && (length <= maximumLength)) {
            return length;
        } else {
            System.out.println("The length of the generated string must be between " + minimumLength + " and " + maximumLength + ".");
            System.out.println("The default length of " + defaultLength + " will be used.");
            return defaultLength;
        }
    }
}
