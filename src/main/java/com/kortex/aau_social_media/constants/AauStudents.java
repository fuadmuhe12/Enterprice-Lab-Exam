package com.kortex.aau_social_media.constants;

import java.util.HashMap;
import java.util.Map;

public class AauStudents {

    // Example data structure to hold student information
    private static final Map<String, String> students = new HashMap<>();

    static {
        // Populate with some example data
        students.put("studentId1", "password1");
        students.put("studentId2", "password2");
        // Add more students as needed
    }

    /**
     * Checks if a student exists with the given university ID and password.
     *
     * @param universityId       the university ID of the student
     * @param universityPassword the password of the student
     * @return true if the student exists, false otherwise
     */
    public static boolean userExists(String universityId, String universityPassword) {
        return students.containsKey(universityId) && students.get(universityId).equals(universityPassword);
    }
} 