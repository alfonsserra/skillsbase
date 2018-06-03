package com.systelab.skillsbase.repository;

public class AssessmentNotFoundException extends RuntimeException {
    private final Long id;

    public AssessmentNotFoundException(Long id) {
        super("assessment-not-found-" + id);
        this.id = id;
    }

    public Long getAssessmentId() {
        return id;
    }
}
