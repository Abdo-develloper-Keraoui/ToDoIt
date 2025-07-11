package com.webapp.todoit.entity;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public enum TaskStatus {
    TODO,
    IN_PROGRESS,
    DONE;

    // Transition rules defined INSIDE the enum

    // Stores which status can transition to which other statuses
    private static final Map<TaskStatus, Set<TaskStatus>> TRANSITION_RULES;

    static {
        // Use EnumMap for enum key efficiency

        // This runs ONCE when enum is first loaded
        // EnumMap is faster than HashMap for enum keys
        Map<TaskStatus, Set<TaskStatus>> rules = new EnumMap<>(TaskStatus.class);

        // Define business rules: current status → allowed next statuses
        rules.put(TODO, Set.of(IN_PROGRESS, DONE));// TO_DO can go anywhere
        rules.put(IN_PROGRESS, Set.of(TODO, DONE));// IN_PROGRESS can revert or complete
        rules.put(DONE, Collections.emptySet()); // DONE is final - no transitions

        // Make immutable to prevent accidental modification
        // Make immutable so no one can accidentally change business rules
        TRANSITION_RULES = Collections.unmodifiableMap(rules);
    }



    /**
     * Checks if we can transition from current status to new status
     * Business rules: TODO→(IN_PROGRESS,DONE), IN_PROGRESS→(TODO,DONE), DONE→(nothing)
     *
     * @param newStatus - Status we want to change to
     * @return true if transition allowed, false if forbidden
     */

    public boolean isValidTransition(TaskStatus newStatus) {
        // Same status always invalid
        //--
        // Can't transition to same status
        if (this == newStatus) return false;

        // Safe lookup: returns empty set for unknown statuses
        //--
        // Look up allowed transitions for current status
        // getOrDefault prevents null errors if status not in map
        return TRANSITION_RULES.getOrDefault(this, Collections.emptySet())
                .contains(newStatus);
    }


    // Allowed statuses for editing fields (title, description, etc.)
    public static final Set<TaskStatus> ALLOW_FIELD_EDIT = Set.of(TODO, IN_PROGRESS);

    /**
     * Returns true if fields (title, description) can be edited in this status.
     */
    public boolean isFieldEditable() {
        return ALLOW_FIELD_EDIT.contains(this);
    }
}

/* public boolean isValidTransition(TasksStatus from, TasksStatus to)
    {
        if(from == to) //Status cannot be the same
        {
            //throw new InvalidStatusTransitionException(from, to);
            //I prefer a boolean over an exception
            return false;
        }
        if(from == TasksStatus.TODO && (to == TasksStatus.IN_PROGRESS  || to == TasksStatus.DONE))
        {
            return true;
        }
        if(from == TasksStatus.IN_PROGRESS && (to == TasksStatus.TODO  || to == TasksStatus.DONE))
        {
            return true;
        }
        if(from == TasksStatus.DONE && (to == TasksStatus.TODO  || to == TasksStatus.IN_PROGRESS))
        {
            return false;
        }
    }
*/
