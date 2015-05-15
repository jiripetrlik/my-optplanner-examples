package myoptaplannerexamples.nonblocking;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.optaplanner.core.api.solver.Solver;
import org.springframework.stereotype.Service;

@Service
public class SolverExecutor extends ThreadPoolExecutor {
	private static final int POOL_SIZE=2;
	private static final int KEEP_ALIVE=0;
	private static final int QUEUE_LENGTH=100;
	
	public SolverExecutor() {
		super(POOL_SIZE,POOL_SIZE,KEEP_ALIVE,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(QUEUE_LENGTH));
	}
	
	public Future<Solver> submitTask(AbstractTask task) {
		Future<Solver> future=this.submit(task);
		return future;
	}
}
