package myoptaplannerexamples.knapsack;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.util.ArrayList;
import java.util.List;

@PlanningEntity
public class Item {
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMass() {
		return mass;
	}

	public void setMass(int mass) {
		this.mass = mass;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@PlanningVariable(valueRangeProviderRefs = {"accomodatedRange"})
	public boolean isAccomodated() {
		return accomodated;
	}

	public void setAccomodated(boolean accomodated) {
		this.accomodated = accomodated;
	}
	
	@ValueRangeProvider(id = "accomodatedRange")
    public List<Boolean> getAccomodatedRange() {
		List<Boolean> accomodatedRange=new ArrayList<Boolean>();
		accomodatedRange.add(true);
		accomodatedRange.add(false);
		
        return accomodatedRange;
    }

	private int id;

	@NotNull
	@Min(0)
	private int mass=5;
	
	@NotNull
	@Min(0)
	private int value=5;
	
	private boolean accomodated;
}
