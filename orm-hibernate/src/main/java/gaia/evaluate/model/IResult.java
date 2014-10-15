package gaia.evaluate.model;

import java.util.Map;

public interface IResult {
	public abstract Long getId();

	public abstract Map<String, String> getMetadata();

	public abstract void setMetadata(Map<String, String> metadata);
}
