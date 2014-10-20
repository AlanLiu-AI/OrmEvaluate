package gaia.evaluate.datanucleus.model;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import gaia.evaluate.model.IResult;

@Entity(name = "result_datanucleus")
public class Result implements IResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "metadata", columnDefinition = "hstore")
    @Convert(converter = gaia.evaluate.datanucleus.conventer.MapToStringConveter.class)
    private Map<String, String> metadata = new HashMap<>();

    private String description = null;

    private String comments = null;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Map<String, String> getMetadata() {
        return metadata;
    }

    @Override
    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
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
