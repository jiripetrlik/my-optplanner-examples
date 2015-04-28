package myoptaplannerexamples.knapsack;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

public class KnapsackScoreCalculator implements EasyScoreCalculator<Knapsack> {
	public HardSoftScore calculateScore(Knapsack knapsack) {
		int hardScore;
		int softScore;
		
		int sumMass=0;
		int sumValue=0;
		for(Item item: knapsack.getItems()) {
			if(item.isAccomodated()) {
				sumMass+=item.getMass();
				sumValue+=item.getValue();
			}
		}
		
		if(knapsack.getCapacity()>=sumMass) {
			hardScore=0;
		}
		else {
			hardScore=sumMass-knapsack.getCapacity();
		}
		
		softScore=-sumValue;
		
		return HardSoftScore.valueOf(-hardScore, -softScore);
	}
}
