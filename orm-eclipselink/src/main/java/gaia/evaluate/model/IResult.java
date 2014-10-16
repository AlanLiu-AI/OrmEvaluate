package gaia.evaluate.model;

import java.util.Map;

public interface IResult {
	Long getId();
        
        String getDescription();
        
	void setDescription(String description);
        
        String getComments();
        
	void setComments(String comments);

	Map<String, String> getMetadata();

	void setMetadata(Map<String, String> metadata);
}
