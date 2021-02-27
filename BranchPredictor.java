import java.text.DecimalFormat;
import java.util.Arrays;

public abstract class BranchPredictor {
	
	//common fields
	protected int M;
	protected int n;
	protected int[] PREDICTION_TBL;
	protected int CurrIndex;
	
	public int noPredictions;
	public int noMispredictions;

	//constructor
	public BranchPredictor(int M, int n)
	{
		this.M = M;
		this.n = n;
		
		this.noPredictions = 0;
		this.noMispredictions = 0;
		
		int tableSize = (int) Math.pow(2, M);
		PREDICTION_TBL = new int[tableSize];
		Arrays.fill(PREDICTION_TBL, 4);
		
	}
	
	//abstract methods that will be overridden in BiModal and Gshare classes
	abstract String processBranch(String branchAddress, String trueValue);
	
	protected abstract int getIndex(String branchAddress);

	//Common methods
	protected String predict(int index) {
		
		String predictedValue = "t";
		int counterValue = 0;
		
		// If the counter value is greater than or equal to 4, then the branch is predicted taken, 
		//else it is predicted not-taken.
		
		counterValue = this.PREDICTION_TBL[index];
		
		predictedValue = counterValue >= 4 ? "t" : "n";
		
		return predictedValue;
	}
	
	protected void updatePredictionTable(int index, String trueValue) {
		
		//counter is incremented if the branch was taken,
		//decremented if the branch was not taken
		//The counter saturates at the extremes (0 and 7)
		
		int counterValue = this.PREDICTION_TBL[index];
		
		if (trueValue.equals("t")) {

			if (counterValue != 7) {
				counterValue++;
			}
		}
		else if (trueValue.equals("n")) {
			
			if (counterValue != 0) {
				counterValue--;
			}
		}
			
		this.PREDICTION_TBL[index] = counterValue;
	}

	public void printOutput() {
		
	}
}
