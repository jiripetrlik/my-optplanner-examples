package myoptaplannerexamples.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import myoptaplannerexamples.knapsack.Item;
import myoptaplannerexamples.knapsack.Knapsack;
import myoptaplannerexamples.nonblocking.SolverExecutor;
import myoptaplannerexamples.nonblocking.Task;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Scope("session")
@RequestMapping("/knapsack")
public class KnapsackController {
	
	private static final int DEFAULT_KNAPSACK_CAPACITY=100;
	
	@RequestMapping("")
	public String indexAction(Model model) {
		model.addAttribute("items",this.items);
		model.addAttribute("capacity",this.capacity);
		model.addAttribute("newItem",new Item());
		model.addAttribute("future",this.future);
		
		return "knapsack/index";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String addItemAction(@Valid @ModelAttribute("newItem") Item newItem,BindingResult bindingResult,Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("items",this.items);
			model.addAttribute("capacity",this.capacity);
			model.addAttribute("future",this.future);
			
			return "knapsack/index";
		}
		else {
			newItem.setId(this.nextItemId);
			this.nextItemId++;
			
			items.add(newItem);
			
			return "redirect:/knapsack";
		}
	}
	
	@RequestMapping("/delete/{index}")
	public String deleteItemAction(@PathVariable("index") int index) {
		items.remove(index);
		
		return "redirect:/knapsack";
	}
	
	@RequestMapping("/capacity")
	public String setCapacityAction(@ModelAttribute("capacity") int capacity,BindingResult bindingResult,Model model) {
		if(capacity<0) {
			model.addAttribute("items",this.items);
			model.addAttribute("newItem",new Item());
			model.addAttribute("future",this.future);
			
			model.addAttribute("capacityError","Knapsack capacity must be at least 0.");
			
			return "knapsack/index";
		}
		else {
			this.capacity=capacity;
			
			return "redirect:/knapsack";
		}
	}
	
	@RequestMapping("/clear")
	public String clearAction() {
		this.nextItemId=1;
		this.items.clear();
		this.capacity=DEFAULT_KNAPSACK_CAPACITY;
		this.future=null;
		
		return "redirect:/knapsack";
	}
	
	@RequestMapping("/clearSolution")
	public String clearSolutionAction() {
		this.future=null;
		
		return "redirect:/knapsack";
	}
	
	@RequestMapping("/calculate")
	public String calculateAction() throws Exception {
		Knapsack knapsack=new Knapsack();
		knapsack.setCapacity(this.capacity);
		knapsack.setItems(this.items);
		
		File solverFile=new File("src/main/resources/solvers/knapsack.xml");
		SolverFactory solverFactory;
		if(solverFile.exists()) {
			solverFactory=SolverFactory.createFromXmlFile(solverFile);
		}
		else {
			solverFactory=SolverFactory.createFromXmlInputStream(
					context.getResourceAsStream("/WEB-INF/classes/solvers/knapsack.xml"));
		}
		
		Solver solver=solverFactory.buildSolver();
		Task<HardSoftScore> task=new Task<HardSoftScore>(solver, knapsack);
		this.future=this.solverExecutor.submitTask(task);
		
		return "redirect:/knapsack";
	}
	
	private int nextItemId=1;
	private List<Item> items=new ArrayList<Item>();
	private int capacity=DEFAULT_KNAPSACK_CAPACITY;
	
	private Future<Solver> future=null;
	
	@Autowired
	private ServletContext context;
	
	@Autowired
	private SolverExecutor solverExecutor;
}
