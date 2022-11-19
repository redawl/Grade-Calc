package com.github.redawl.GradeCalc.Class;

import com.github.redawl.GradeCalc.Assignment.Assignment;
import com.github.redawl.GradeCalc.Assignment.AssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClassService {

    private final AssignmentRepository assignmentRepository;

    public ClassService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    /**
     * Add an assignment for a class
     * @param assignment Assignment to add
     */
    public void addAssignment(Assignment assignment){
        if(assignment == null){
            throw new IllegalArgumentException("Assignment cannot be null");
        }

        if(assignmentRepository.findAssignmentByAssignmentNameAndClassName(assignment.getAssignmentName(),
                assignment.getClassName()) != null){
            throw new IllegalArgumentException("Assignment already exists");
        }

        assignmentRepository.save(assignment);
    }

    /**
     * Remove an assignment by its name
     * @param assignmentName Name of assignment to remove
     */
    public boolean removeAssignment(String assignmentName, String className){
        assertCorrectParameter(assignmentName, "Cannot remove a null or empty assignment");
        assertCorrectParameter(className, "Cannot remove a null or empty class");

        return assignmentRepository.removeAssignmentByAssignmentNameAndClassName(assignmentName, className) != 0;
    }

    /**
     * Retrieve a list of all assignments for a class
     * @param className Name of the class
     * @return List of assignments
     */
    public Iterable<Assignment> getAssignmentsByClass(String className){
        assertCorrectParameter(className, "Cannot retrieve assignments for a null or empty class");

        return assignmentRepository.findAssignmentsByClassName(className);
    }

    /**
     * Compute the total grade for the class (So far)
     * @param className Name of class
     * @return Total grade
     */
    public double computeGradeForClass(String className){
        assertCorrectParameter(className, "Cannot compute grade for null or empty class");

        double computedGrade = 0.0;
        for (Assignment assignment : assignmentRepository.findAssignmentsByClassName(className)) {
            computedGrade += assignment.calculateValue();
        }

        return computedGrade;
    }

    /**
     * Compute the maximum possible grade for a class, given the current assignments
     * @param className Name of class
     * @return Maximum possible grade
     */
    public double computeMaxGradeForClass(String className){
        assertCorrectParameter(className, "Cannot compute grade for null or empty class");

        double totalGrade = 0;
        double totalWeight = 0;
        for(Assignment assignment : assignmentRepository.findAssignmentsByClassName(className)){
            totalGrade += assignment.calculateValue();
            totalWeight += assignment.getAssignmentWeight();
        }

        return totalGrade + (100 * (1 - totalWeight));
    }

    /**
     * Compute total grade needed for the rest of the assignments for a class in order to reach the target grade
     * @param className Name of class
     * @param targetGrade Target grade
     * @return Required grade for target total grade
     */
    public double computeGradeNeededToGetGrade(String className, double targetGrade){
        assertCorrectParameter(className, "Cannot compute needed grade for null or empty class");
        if(targetGrade < 0 || targetGrade > 100){
            throw new IllegalArgumentException(String.format("Target grade of %f is not a valid grade.", targetGrade));
        }

        double totalGrade = 0;
        double totalWeight = 0;
        for(Assignment assignment : assignmentRepository.findAssignmentsByClassName(className)){
            totalGrade += assignment.calculateValue();
            totalWeight += assignment.getAssignmentWeight();
        }

        // targetGrade = totalGrade + (x * (1 - totalWeight))
        // (targetGrade - totalGrade) / (1 - totalWeight)= x ;

        double requiredGrade = (targetGrade - totalGrade) / (1 - totalWeight);

        return requiredGrade < 0 ? 0 : requiredGrade;
    }

    /**
     * Get a list of every class
     * @return list of classes
     */
    public List<Class> getAllClasses(){
        HashMap<String, Class> classes = new HashMap<>();

        assignmentRepository.findAll().forEach(assignment -> classes.putIfAbsent(assignment.getClassName(),
                        new Class(assignment.getClassName())));

        return new ArrayList<>(classes.values());
    }

    /**
     * Verify that a parameter is not null or empty
     * @param parameter Class name to verify
     * @param errorMessage Message to throw if class name was not valid
     */
    private void assertCorrectParameter(String parameter, String errorMessage){
        if(parameter == null || "".equals(parameter)){
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
