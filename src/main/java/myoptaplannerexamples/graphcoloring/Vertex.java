package myoptaplannerexamples.graphcoloring;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class Vertex {
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@PlanningVariable(valueRangeProviderRefs={"colorRange"})
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	private int color;
	private int index;
}
