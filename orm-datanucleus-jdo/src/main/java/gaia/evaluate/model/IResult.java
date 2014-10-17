package gaia.evaluate.model;

public interface IResult {
    Long getId();

    String getDescription();

    void setDescription(String description);

    String getComments();

    void setComments(String comments);
}
