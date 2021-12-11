package ua.com.foxminded.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class InitialData {

    private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String numeric = "0123456789";

    public List<String> createFullName() throws IOException {
        List<String> fullNames = new ArrayList<>();

        List<String> names;
        try {
            names = Arrays.stream(new String(Objects.requireNonNull(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("names.txt")).readAllBytes(),
                    StandardCharsets.UTF_8).split("\r\n")).toList();

        } catch (IOException e) {
            throw new IOException("Error: names file not found");
        }

        List<String> surnames;
        try {

            surnames = Arrays.stream(new String(Objects.requireNonNull(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("surnames.txt")).readAllBytes(),
                    StandardCharsets.UTF_8).split("\r\n")).toList();

        } catch (IOException e) {
            throw new IOException("Error: surnames file not found");
        }

        int count = 0;
        for (int i = 0; count < 200; i++) {
            Random random = new Random();
            String strTemp = surnames.get(i) + " " + names.get(random.nextInt(20));
            fullNames.add(strTemp);
            count++;

            if (i == 19) {
                i = 0;
            }
        }
        return fullNames;
    }

    public List<String> createCourses() throws IOException {
        List<String> courses;

        try {
            courses = Arrays.stream(new String(Objects.requireNonNull(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("courses.txt")).readAllBytes(),
                    StandardCharsets.UTF_8).split("\r\n")).toList();

        } catch (IOException e) {
            throw new IOException("Error: course file not found");
        }
        return courses;
    }

    public List<String> createGroups(int numberGroups, int lengthWords, int lengthNumeric) {
        List<String> groups = new ArrayList<>();

        for (int i = 0; i < numberGroups; i++) {
            groups.add(this.generateOneGroupsName(lengthWords, lengthNumeric));
        }
        return groups;
    }

    private String generateOneGroupsName(int lengthWords, int lengthNumeric) {

        Random r = new Random();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < lengthWords; i++) {
            result.append(alphabet.charAt(r.nextInt(alphabet.length())));
        }

        result.append("-");

        for (int i = 0; i < lengthNumeric; i++) {
            result.append(numeric.charAt(r.nextInt(numeric.length())));
        }
        return result.toString();
    }
}
