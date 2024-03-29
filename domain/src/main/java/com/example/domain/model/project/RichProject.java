package com.example.domain.model.project;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

/**
 * @author Azret Magometov
 */
public class RichProject {

    @Embedded
    public Project mProject;

    @Relation(entity = Owner.class, entityColumn = "project_id", parentColumn = "id")
    public List<Owner> mOwners;

}
