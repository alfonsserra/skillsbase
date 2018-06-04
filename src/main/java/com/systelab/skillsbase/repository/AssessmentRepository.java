package com.systelab.skillsbase.repository;

import com.systelab.skillsbase.model.skill.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    Optional<Assessment> findById(@Param("id") Long id);

    Optional<Assessment> findFirstByUserIdOrderByRealizationDate(@Param("userid") Long userId);

}