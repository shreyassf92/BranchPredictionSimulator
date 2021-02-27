import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class sim {
	
	public static void main(String[] args) {
		
		String traceFile = "";
		int M = 0;
		int n = 0;
		BranchPredictor branchPredictor = null;
		
		
		if (args[0].equals("bimodal")) {
			 M = Integer.parseInt(args[1]);
			 traceFile= args[2];
			 branchPredictor = new BiModal(M);
		}
		else if (args[0].equals("gshare")) {
			M = Integer.parseInt(args[1]);
			n = Integer.parseInt(args[2]);
			traceFile = args[3];
			branchPredictor = new Gshare(M, n);
		}
		else if (args[0].equals("hybrid")) {
			int K = Integer.parseInt(args[1]);
			int M1 = Integer.parseInt(args[2]);
			n = Integer.parseInt(args[3]);
			int M2 = Integer.parseInt(args[4]);
			traceFile = args[5];
			branchPredictor = new Hybrid(K,M1, n, M2);
		}
		
		List<String> traceList = readTraceFile(traceFile);
		
		for (int i = 0; i < traceList.size(); i++) {
			
			String[] entry = traceList.get(i).split(" ");
			branchPredictor.processBranch(entry[0], entry[1]);
		}
		
		//Print 
		System.out.println("COMMAND");
		
		if (args[0].equals("bimodal")) {
		
			System.out.println("./sim "+ args[0] + " "+ args[1] +" " + Paths.get(traceFile).getFileName());
		}
		else if (args[0].equals("gshare")) {
			System.out.println("./sim "+ args[0] + " "+ args[1] +" " + args[2] +" "+ Paths.get(traceFile).getFileName());
		}
		else if (args[0].equals("hybrid")) {
			System.out.println("./sim "+ args[0] + " "+ args[1] +" " + args[2] +" "+ args[3] +" "+ args[4] +" "+ Paths.get(traceFile).getFileName());
		}
		
		branchPredictor.printOutput();	
	}
	
	public static List<String> readTraceFile(String filename) {
		
		List<String> traceList = new ArrayList<String>(); 
		
		try {
		      File traceFile = new File(filename);
		      Scanner scReader = new Scanner(traceFile);
		      while (scReader.hasNextLine()) {
		    	  traceList.add(scReader.nextLine());
		      }
		      scReader.close();
		    } 
		catch (FileNotFoundException e) {
		      System.out.println("An error occurred while reading trace file");
		      System.exit(0);
		    }
		
		return traceList;
	}
}
