package myoptaplannerexamples.graphcoloring;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

public class GraphScoreCalculator implements EasyScoreCalculator<Graph> {

	@Override
	public HardSoftScore calculateScore(Graph graph) {
		
		int colorMax=0;
		for(Vertex vertex : graph.getVertexList()) {
			if(vertex.getColor() > colorMax) {
				colorMax=vertex.getColor();
			}
		}
		
		int conflicts=0;
		for(Edge edge : graph.getEdgeList()) {
			Vertex vertexA=graph.getVertexList().get(edge.getVertexA());
			Vertex vertexB=graph.getVertexList().get(edge.getVertexB());
			
			if(vertexA.getColor() == vertexB.getColor()) {
				conflicts++;
			}
		}
		
		return HardSoftScore.valueOf(-conflicts,-colorMax);
	}
	
}
