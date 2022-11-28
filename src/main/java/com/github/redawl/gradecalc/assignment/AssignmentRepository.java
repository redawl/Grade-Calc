package com.github.redawl.gradecalc.assignment;

import org.springframework.data.repository.CrudRepository;

public interface AssignmentRepository extends CrudRepository<Assignment, String> {
    Iterable<Assignment> findAssignmentsByClassNameAndUsername(String className, String username);

    Iterable<Assignment> findAllByUsername(String username);

    Assignment findAssignmentByAssignmentNameAndClassNameAndUsername(String assignmentName, String className, String username);

    Integer removeAssignmentByAssignmentNameAndClassNameAndUsername(String assignmentName, String className, String username);
}
