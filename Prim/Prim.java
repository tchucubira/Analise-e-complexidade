package edu.stanford.algo.greedy;

import java.util.HashSet;
import java.util.Set;

import edu.stanford.algo.structures.graph.Edge;
import edu.stanford.algo.structures.graph.UndirectedGraph;


public class Prim {


    public static Set<Edge> getMinimumSpanningTree(UndirectedGraph g) {
	Set<Edge> mst = new HashSet<Edge>();
	Set<Integer> spannedVertices = new HashSet<Integer>();
	spannedVertices.add(g.getVertices().iterator().next());
	while (spannedVertices.size() < g.getNumVertices()) {
	    Edge cheapestEdge = null;
	    for (Integer vertex : spannedVertices) {
		for (Edge e : g.getOutgoingEdges(vertex)) {
		    if (!spannedVertices.contains(e.getOtherVertex(vertex)) && (cheapestEdge == null || e.getLength() < cheapestEdge.getLength())) {
			cheapestEdge = e;
		    }
		}
	    }
	    if (cheapestEdge == null) {
		return null;
	    }
	    mst.add(cheapestEdge);
	    spannedVertices.add(cheapestEdge.getVertexA());
	    spannedVertices.add(cheapestEdge.getVertexB());
	}
	return mst;
    }

}
