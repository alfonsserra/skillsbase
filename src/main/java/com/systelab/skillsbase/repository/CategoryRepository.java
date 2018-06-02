package com.systelab.skillsbase.repository;

import com.systelab.skillsbase.model.skill.Category;
import com.systelab.skillsbase.model.skill.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findById(@Param("id") Long id);

}