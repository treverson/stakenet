import java.util.*;

import stock.cluster.*;
import stock.directed.StockGraph;
import stock.util.*;
import stock.vertex.StockVertex;

public class Main_Cluster_Baseline {

	public static void main(String[] args) throws Exception
	{		
		// Parameters
		int targetYear = 98;
		int targetMonth = 10;
		
		int removeSingleNodes = 1;
		
		boolean removeOneDegreeVertex = true;
		boolean removeBankVertex = true;
		boolean countDirectOwnCompany = true;
		int alpha = 3;
		int beta = 20;
		
		// Graph name
		String vertexFileName = "graph/vertex_" + targetYear + "_" + targetMonth + ".txt";		
		String edgeFileName = "graph/edge_" + targetYear + "_" + targetMonth + ".txt";

		// Load stock data
		StockData sd = StockData.load("exception.csv", "company.csv", "english.csv");

		// Load graph (directed)
		StockGraph g = StockGraph.load(vertexFileName, edgeFileName, removeSingleNodes);
		g.setVertexEnglishs(sd);

		// Load clustering answer
		HashSet<String> bgc = StockUtil.loadBussinessGroupCompanies("bussiness_group.txt");
		System.out.println("Answer companies = " + bgc.size());
		
		// Baseline
		//g.removeBanks("bank.txt");
		Set<Set<StockVertex>> c = StockUtil.computeClusters(g);
		Set<Set<StockVertex>> clusters = StockUtil.filterClusteringResult(c, bgc);
		System.out.println("Computed companies = " + StockUtil.computeClusterCompanies(clusters));

		// Compute NMI
		StockCluster sc = new StockCluster(false ,g, removeOneDegreeVertex, removeBankVertex, countDirectOwnCompany, alpha, beta);
		System.out.println("NMI = " + sc.getNMI(clusters));
	}
}
