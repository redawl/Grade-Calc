package com.example.GradeCalc;

import jdk.jshell.spi.ExecutionControl.NotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manager of the list of classes for a particular user
 */
public class ClassManager {
    private static final ClassManager manager = new ClassManager();
    private final HashMap<String, Class> classes;

    ClassManager(){
        classes = new HashMap<>();
    }

    /**
     * Get singleton manager instance
     * @return manager
     */
    public ClassManager getManager(){
        return manager;
    }

    /**
     * Look up a class by its name
     * @param name name of class to return
     * @return Class that matches name
     */
    public Class getClassByName(String name) {
       if(name == null || "".equals(name)){
           throw new IllegalArgumentException("name cannot be null or empty");
       }
       return classes.get(name);
    }

    /**
     * Add a class to the manager
     * @param newClass Class to add
     */
    public void addClass(Class newClass) {
        if(newClass == null){
            throw new IllegalArgumentException("newClass cannot be null");
        }
        String className = newClass.getClassName();
        classes.put(className, newClass);
    }

    /**
     * Remove a class by the passed in name
     * @param className Name of the class to remove
     */
    public void removeClassByName(String className) throws NotImplementedException {
        if(className == null || "".equals(className)){
            throw new IllegalArgumentException("className cannot be null or empty");
        }

        classes.remove(className);
    }
}
