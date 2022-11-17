package com.github.redawl.GradeCalc.Assignment;

import org.springframework.data.repository.CrudRepository;

public interface AssignmentRepository extends CrudRepository<Assignment, String> {
    Iterable<Assignment> findGAssignmentsByClassName(String className);
}
