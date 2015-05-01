package myoptaplannerexamples.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import myoptaplannerexamples.graphcoloring.Edge;
import myoptaplannerexamples.graphcoloring.Graph;
import myoptaplannerexamples.graphcoloring.Vertex;

@Controller
@Scope("session")
@RequestMapping("/graphcoloring")
public class GraphColoringController {
	
	@RequestMapping("")
	public String indexAction(Model model) {
		model.addAttribute("vertexNumber",this.vertexNumber);
		model.addAttribute("vertexOptions",this.vertexOptions());
		model.addAttribute("edgeList",this.edgeList);
		model.addAttribute("vertexList",this.vertexList);
		model.addAttribute("solution",this.solution);
		
		if(this.vertexNumber>0) {
			model.addAttribute("vertexA",0);
			model.addAttribute("vertexB",0);
		}
		
		return "graphcoloring/index";
	}
	
	@RequestMapping(value="/setvertexnumber",method=RequestMethod.POST)
	public String setVertexNumberAction(@ModelAttribute("vertexNumber") int vertexNumber,BindingResult bindingResult,
			Model model) {
		
		if(vertexNumber>=0) {
			this.clearAction();
			
			this.vertexNumber=vertexNumber;
			for(int i=0;i<vertexNumber;i++) {
				Vertex vertex=new Vertex();
				vertex.setIndex(i);
				
				vertexList.add(vertex);
			}
			
			return "redirect:/graphcoloring";
		}
		else {
			model.addAttribute("vertexNumber",this.vertexNumber);
			model.addAttribute("vertexOptions",this.vertexOptions());
			model.addAttribute("edgeList",this.edgeList);
			model.addAttribute("vertexList",this.vertexList);
			model.addAttribute("solution",this.solution);
			
			if(this.vertexNumber>0) {
				model.addAttribute("vertexA",0);
				model.addAttribute("vertexB",0);
			}
			
			model.addAttribute("vertexNumberError","The vertex number must be higher or equal to zero");
			
			return "graphcoloring/index";
		}	
	}
	
	@RequestMapping(value="/addedge",method=RequestMethod.POST)
	public String addEdgeAction(@ModelAttribute("vertexA") int vertexA,@ModelAttribute("vertexB") int vertexB,
			BindingResult bindingResult,Model model) {
		
		boolean valid=true;
		if((vertexA < 0) || (vertexA >= vertexNumber)) {
			valid=false;
			model.addAttribute("vertexAError","Incorect vertex");
		}
		if((vertexB < 0) || (vertexB >= vertexNumber)) {
			valid=false;
			model.addAttribute("vertexBError","Incorect vertex");
		}
		if(vertexA == vertexB) {
			valid=false;
			model.addAttribute("vertexBError","Vertex of the edge can't be the same.");
		}
		
		if(valid) {
			Edge edge=new Edge();
			edge.setVertexA(vertexA);
			edge.setVertexB(vertexB);
			
			this.edgeList.add(edge);
			
			return "redirect:/graphcoloring";
		}
		else {
			model.addAttribute("vertexNumber",this.vertexNumber);
			model.addAttribute("vertexOptions",this.vertexOptions());
			model.addAttribute("edgeList",this.edgeList);
			model.addAttribute("vertexList",this.vertexList);
			model.addAttribute("solution",this.solution);
			
			return "graphcoloring/index";
		}	
	}
	
	@RequestMapping("/removeedge/{index}")
	public String removeEdgeAction(@PathVariable("index") int index) {
		edgeList.remove(index);
		
		return "redirect:/graphcoloring";
	}
	
	@RequestMapping("/solve")
	public String solveAction() {
		Graph graph=new Graph();
		graph.setVertexList(this.vertexList);
		graph.setEdgeList(this.edgeList);
		
		File solverFile=new File("src/main/resources/solvers/graphcoloring.xml");
		SolverFactory solverFactory;
		if(solverFile.exists()) {
			solverFactory=SolverFactory.createFromXmlFile(solverFile);
		}
		else {
			solverFactory=SolverFactory.createFromXmlInputStream(
					context.getResourceAsStream("/WEB-INF/classes/solvers/graphcoloring.xml"));
		}
		
		Solver solver=solverFactory.buildSolver();
		solver.solve(graph);
		this.solution=(Graph) solver.getBestSolution();
		
		return "redirect:/graphcoloring";
	}
	
	@RequestMapping("/clear")
	public String clearAction() {
		this.vertexNumber=0;
		this.vertexList.clear();
		this.edgeList.clear();
		this.solution=null;
		
		return "redirect:/graphcoloring";
	}
	
	@RequestMapping("/clearsolution")
	public String clearSolutionAction() {
		this.solution=null;
		
		return "redirect:/graphcoloring";
	}
	
	private List<Integer> vertexOptions() {
		List<Integer> options=new ArrayList<Integer>();
		
		for(int i=0;i<vertexNumber;i++) {
			options.add(i);
		}
		
		return options;
	}
	
	private int vertexNumber=0;
	private List<Vertex> vertexList=new ArrayList<Vertex>();
	private List<Edge> edgeList=new ArrayList<Edge>();
	
	private Graph solution=null;
	
	@Autowired
	private ServletContext context;
}
