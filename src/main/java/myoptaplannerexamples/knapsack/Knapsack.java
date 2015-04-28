package myoptaplannerexamples.knapsack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

@PlanningSolution
public class Knapsack implements Solution<HardSoftScore> {

	@Override
	public Collection<? extends Object> getProblemFacts() {
		List<Object> facts=new ArrayList<Object>();
		facts.add(new Integer(capacity));
		
		return null;
	}

	@Override
	public HardSoftScore getScore() {
		return this.score;
	}

	@Override
	public void setScore(HardSoftScore score) {
		this.score=score;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	@PlanningEntityCollectionProperty
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	private int capacity=100;
	private List<Item> items;
	private HardSoftScore score=null;
}
