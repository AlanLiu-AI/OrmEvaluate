package gaia.evaluate.datanucleus.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Column;

import gaia.evaluate.model.IResult;

@PersistenceCapable
public class Result implements IResult {

    @PrimaryKey
    @Persistent(valueStrategy=IdGeneratorStrategy.NATIVE)
    private Long id;

    @Column
    private String description = null;

    @Column
    private String comments = null;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getComments() {
        return comments;
    }

    @Override
    public void setComments(String comments) {
        this.comments = comments;
    }
}
