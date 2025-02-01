package com.kortex.aau_social_media.constants;

import com.kortex.aau_social_media.model.StudentCredential;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AauStudents {

    public static final List<StudentCredential> DUMMY_STUDENT_CREDENTIALS = Collections.unmodifiableList(Arrays.asList(
            new StudentCredential("ugr-6052-14", "somePassword"),
            new StudentCredential("ugr-6052-15", "anotherPassword"),
            new StudentCredential("ugr-6052-16", "password123"),
            new StudentCredential("ugr-6052-17", "securePass!@#"),
            new StudentCredential("ugr-6052-18", "charliePass456"),
            new StudentCredential("ugr-7011-19", "alphaBeta123"),
            new StudentCredential("ugr-8022-20", "deltaGamma789"),
            new StudentCredential("ugr-9033-21", "secureXYZ!@#"),
            new StudentCredential("ugr-1121-22", "omegaLambda456"),
            new StudentCredential("ugr-2232-23", "thetaPi890"),
            new StudentCredential("ugr-3343-24", "passWord!987"),
            new StudentCredential("ugr-4454-25", "sigmaTau654"),
            new StudentCredential("ugr-5565-26", "epsilonPhi321"),
            new StudentCredential("ugr-6676-27", "zetaOmega234"),
            new StudentCredential("ugr-7787-28", "muNu890!@#"),
            new StudentCredential("ugr-8898-29", "betaAlpha567"),
            new StudentCredential("ugr-9909-30", "kappaIota432"),
            new StudentCredential("ugr-1010-31", "randomKey098"),
            new StudentCredential("ugr-2121-32", "universalPass321"),
            new StudentCredential("ugr-3232-33", "strongPass!765"),
            new StudentCredential("ugr-4343-34", "ultraSafe2024"),
            new StudentCredential("ugr-5454-35", "superSecure999"),
            new StudentCredential("ugr-6565-36", "studentKeyABC"),
            new StudentCredential("ugr-7676-37", "accessGranted777"),
            new StudentCredential("ugr-8787-38", "verifyNowPass111")

            ));
            


    // Private constructor to prevent instantiation
    private AauStudents() {
        throw new UnsupportedOperationException("Utility class");
    }
    
    /**
     * Checks if a student with the given universityId and universityPassword
     * exists.
     *
     * @param universityId       The university ID to check.
     * @param universityPassword The university password to check.
     * @return {@code true} if a matching student exists; {@code false} otherwise.
     */
    public static boolean userExists(String universityId, String universityPassword) {
        return DUMMY_STUDENT_CREDENTIALS.stream()
                .anyMatch(cred -> cred.getUniversityId().equals(universityId) &&
                        cred.getUniversityPassword().equals(universityPassword));
    }
} 
 