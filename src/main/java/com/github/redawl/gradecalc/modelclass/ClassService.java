package com.github.redawl.gradecalc.modelclass;

import com.github.redawl.gradecalc.assignment.AssignmentDTO;
import com.github.redawl.gradecalc.assignment.AssignmentTransformer;
import com.github.redawl.gradecalc.assignment.AssignmentRepository;
import com.github.redawl.gradecalc.exceptions.AssignmentNotFoundException;
import com.github.redawl.gradecalc.exceptions.ClassNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
public class ClassService {

    private final AssignmentRepository assignmentRepository;

    public ClassService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    /**
     * Add an assignment for a class
     * @param assignmentDto Assignment to add
     */
    public void addAssignment(AssignmentDTO assignmentDto, String username){
        if(assignmentDto == null){
            throw new IllegalArgumentException("Assignment cannot be null");
        }

        if(assignmentRepository.findAssignmentByAssignmentNameAndClassNameAndUsername(assignmentDto.getAssignmentName(),
                assignmentDto.getClassName(), username) != null){
            throw new IllegalArgumentException("Assignment already exists");
        }



        assignmentRepository.save(AssignmentTransformer.dtoToAssignment(assignmentDto, username));
    }

    /**
     * Remove an assignment by its name
     * @param assignmentName Name of assignment to remove
     */
    public void removeAssignment(String assignmentName, String className, String username) throws AssignmentNotFoundException {
        assertCorrectParameter(assignmentName, "Cannot remove a null or empty assignment");
        assertCorrectParameter(className, "Cannot remove a null or empty class");

        if(assignmentRepository.removeAssignmentByAssignmentNameAndClassNameAndUsername(assignmentName,
                className, username) == 0){
            throw new AssignmentNotFoundException(assignmentName, className);
        }
    }

    /**
     * Retrieve a list of all assignments for a class
     * @param className Name of the class
     * @return List of assignments
     */
    public Optional<Iterable<AssignmentDTO>> getAssignmentsByClass(String className, String username){
        assertCorrectParameter(className, "Cannot retrieve assignments for a null or empty class");

        List<AssignmentDTO> assignmentDTOS = new ArrayList<>();
        assignmentRepository.findAssignmentsByClassNameAndUsername(className, username)
                .forEach(assignment -> assignmentDTOS.add(AssignmentTransformer.assignmentToDTO(assignment)));

        return Optional.ofNullable(assignmentDTOS.isEmpty() ? null : assignmentDTOS);
    }

    /**
     * Compute the total grade for the class (So far)
     * @param className Name of class
     * @return Total grade
     */
    public double computeGradeForClass(String className, String username){
        assertCorrectParameter(className, "Cannot compute grade for null or empty class");

        AtomicReference<Double> computedGrade = new AtomicReference<>(0.0);

        assignmentRepository.findAssignmentsByClassNameAndUsername(className, username)
                .forEach(assignment -> computedGrade.updateAndGet(v -> (v + assignment.calculateValue())));

        return computedGrade.get();
    }

    /**
     * Compute the maximum possible grade for a class, given the current assignments
     * @param className Name of class
     * @return Maximum possible grade
     */
    public double computeMaxGradeForClass(String className, String username){
        assertCorrectParameter(className, "Cannot compute grade for null or empty class");

        AtomicReference<Double> totalGrade = new AtomicReference<>( 0.0);
        AtomicReference<Double> totalWeight = new AtomicReference<>(0.0);

        assignmentRepository.findAssignmentsByClassNameAndUsername(className, username).forEach((assignment -> {
            totalGrade.updateAndGet(v -> v + assignment.calculateValue());
            totalWeight.updateAndGet(v -> v + assignment.getAssignmentWeight());
        }));

        return totalGrade.get() + 100 - (100 * totalWeight.get());
    }

    /**
     * Compute total grade needed for the rest of the assignments for a class in order to reach the target grade
     * @param className Name of class
     * @param targetGrade Target grade
     * @return Required grade for target total grade
     */
    public double computeGradeNeededToGetGrade(String className, double targetGrade, String username){
        assertCorrectParameter(className, "Cannot compute needed grade for null or empty class");
        if(targetGrade < 0 || targetGrade > 100){
            throw new IllegalArgumentException(String.format("Target grade of %f is not a valid grade.", targetGrade));
        }

        AtomicReference<Double> totalGrade = new AtomicReference<>(0.0);
        AtomicReference<Double> totalWeight = new AtomicReference<>(0.0);

        assignmentRepository.findAssignmentsByClassNameAndUsername(className, username).forEach(assignment -> {
            totalGrade.updateAndGet(v -> v + assignment.calculateValue());
            totalWeight.updateAndGet(v -> v + assignment.getAssignmentWeight());
        });

        double requiredGrade = (targetGrade - totalGrade.get()) / (1 - totalWeight.get());

        return requiredGrade < 0 ? 0 : requiredGrade;
    }

    /**
     * Get a list of every class
     * @return list of classes
     */
    public Optional<List<Class>> getAllClasses(String username){
        HashMap<String, Class> classes = new HashMap<>();

        assignmentRepository.findAllByUsername(username).forEach(assignment -> classes.putIfAbsent(assignment.getClassName(),
                        new Class(assignment.getClassName())));

        return Optional.ofNullable(classes.isEmpty() ? null : new ArrayList<>(classes.values()));
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
