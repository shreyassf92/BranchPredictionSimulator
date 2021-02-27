import java.text.DecimalFormat;
import java.util.Arrays;

public class Gshare extends BranchPredictor {

	private StringBuilder GBHRegister;
	
	public Gshare(int M, int n){
		
		super(M, n);
		
		GBHRegister = new StringBuilder("");
		for (int i = 0; i < n; i++) {
			GBHRegister.append("0");
		}
		
	}
	
	@Override
	String processBranch(String branchAddress, String trueValue) {
		
		String predictedValue = "t";
		
		this.noPredictions++;
		
		//step 1: get index
		this.CurrIndex = getIndex(branchAddress);
		
		//step 2: make prediction
		predictedValue = predict(CurrIndex);
		
		//step 3: update prediction table
				updatePredictionTable(CurrIndex, trueValue);	
				
		//step 4: update GBH register
		updateGlobalBranchRegister(trueValue);
		
		//Step 5: compare true and predicted
		if (!predictedValue.equals(trueValue)) {
			this.noMispredictions++;
		}	
		
		return predictedValue;
	}
	
	@Override
	protected
	int getIndex(String branchAddress) {
		
		return BitsProcessor.getIndex(branchAddress, GBHRegister.toString(), M, n);
	}
	
	public void updateGlobalBranchRegister(String trueValue) {
		
		GBHRegister.deleteCharAt(this.n-1);
		String temp = "";
		
		if (trueValue.equals("t")) {
			temp = GBHRegister.toString();
			GBHRegister = new StringBuilder("");
			GBHRegister.append("1");
			GBHRegister.append(temp);
		}
		else {
			temp = GBHRegister.toString();
			GBHRegister = new StringBuilder("");
			GBHRegister.append("0");
			GBHRegister.append(temp);
			
		}
		
	}
	
	@Override
	public void printOutput() {
		
		System.out.println("OUTPUT");
		System.out.println("number of predictions: "+ this.noPredictions);
		System.out.println("number of mispredictions: "+ this.noMispredictions);
		
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		double missPred = ((double)this.noMispredictions/(double)this.noPredictions)*100;
		
		System.out.println("misprediction rate: "+ decimalFormat.format(missPred) + "%");
		
		System.out.println("FINAL GSHARE CONTENTS");
		
		for (int i = 0; i < this.PREDICTION_TBL.length; i++) {
			
			System.out.println(i+ "\t" + this.PREDICTION_TBL[i]);
		}
	}
	
}
