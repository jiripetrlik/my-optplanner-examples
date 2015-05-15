package myoptaplannerexamples.nonblocking;

import java.util.concurrent.Callable;

import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.solver.Solver;

public class Task<S extends Score<S>> extends AbstractTask implements Callable<Solver> {
	
	public Task(Solver solver,Solution<S> initialSolution) {
		this.solver=solver;
		this.initialSolution=initialSolution;
	}
	
	@Override
	public Solver call() throws Exception {
		solver.solve(initialSolution);
		
		return solver;
	}
	
	private Solver solver;
	private Solution<S> initialSolution;
}
