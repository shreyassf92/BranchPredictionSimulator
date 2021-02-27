import java.text.DecimalFormat;
import java.util.Arrays;

public class Hybrid extends BranchPredictor{

	private int K;
	
	private BiModal bimodalPredictor;
	private Gshare gsharePredictor;
	
	private String bimodalPrediction;
	private String gsharePrediction;
	
	private int[] CHOOSER_TBL;
	
	public Hybrid(int K, int M1, int n, int M2) {
		
		super(0, 0);
		
		bimodalPredictor = new BiModal(M2);
		gsharePredictor = new Gshare(M1, n);
		
		this.K = K;
		this.bimodalPrediction = "";
		this.gsharePrediction = "";
		this.noPredictions = 0;
		this.noMispredictions = 0;
		
		int tableSize = (int) Math.pow(2, K);
		CHOOSER_TBL = new int[tableSize];
		Arrays.fill(CHOOSER_TBL, 1);
	}

	@Override
	String processBranch(String branchAddress, String trueValue) {

		this.noPredictions++;
		
		String predictedValue = "";
		int bimodalIndex = 0;
		int gshareIndex = 0;
		
		bimodalIndex = bimodalPredictor.getIndex(branchAddress);
		bimodalPrediction = bimodalPredictor.predict(bimodalIndex);
		
		gshareIndex = gsharePredictor.getIndex(branchAddress);
		gsharePrediction = gsharePredictor.predict(gshareIndex);
		
		//Step 2: get Chooser index
		this.CurrIndex = getIndex(branchAddress);
		
		//Step 3 and 4: Make an overall prediction / update prediction table
		if (CHOOSER_TBL[this.CurrIndex] >= 2) {
			
			predictedValue = gsharePrediction;
			
			gsharePredictor.updatePredictionTable(gshareIndex, trueValue);
		}
		else {
			
			predictedValue = bimodalPrediction;
			
			bimodalPredictor.updatePredictionTable(bimodalIndex, trueValue);
		}
		
		if (!trueValue.equals(predictedValue)) {
			this.noMispredictions++;
		}
		
		//Step 5: update GBH
		gsharePredictor.updateGlobalBranchRegister(trueValue);
		
		//Step 6 : update chooser table
		updateChooserTable(bimodalPrediction, gsharePrediction, trueValue);
		
		return predictedValue;
	}

	@Override
	protected int getIndex(String branchAddress) {
		
		//determine index of chooser table
		return BitsProcessor.getIndex(branchAddress, K);

	}
	
	private void updateChooserTable(String biModalPred, String gsharePred, String trueValue){
		
		if (trueValue.equals(biModalPred) && trueValue.equals(gsharePred)) {
			
			//no change
		}
		else if (trueValue.equals(gsharePred) && !trueValue.equals(biModalPred)) {
			
			//increment
			if (this.CHOOSER_TBL[this.CurrIndex] != 3) {
				this.CHOOSER_TBL[this.CurrIndex]++;
			}
		}
		else if (trueValue.equals(biModalPred) && !trueValue.equals(gsharePred)) {
			
			//decrement
			if (this.CHOOSER_TBL[this.CurrIndex] != 0) {
				this.CHOOSER_TBL[this.CurrIndex]--;
			}
		}
		
	}
	
	@Override
	public void printOutput() {
		System.out.println("OUTPUT");
		System.out.println("number of predictions: "+ this.noPredictions);
		System.out.println("number of mispredictions: "+ this.noMispredictions);
		
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		double missPred = ((float)this.noMispredictions/(float)this.noPredictions)*100;
		
		System.out.println("misprediction rate: "+ decimalFormat.format(missPred) + "%");
		
		System.out.println("FINAL CHOOSER CONTENTS");
		
		for (int i = 0; i < this.CHOOSER_TBL.length; i++) {
			
			System.out.println(i+ "\t" + this.CHOOSER_TBL[i]);
		}
		
		System.out.println("FINAL GSHARE CONTENTS");
		
		for (int i = 0; i < this.gsharePredictor.PREDICTION_TBL.length; i++) {
			
			System.out.println(i+ "\t" + this.gsharePredictor.PREDICTION_TBL[i]);
		}
		
		System.out.println("FINAL BIMODAL CONTENTS");
		
		for (int i = 0; i < this.bimodalPredictor.PREDICTION_TBL.length; i++) {
			
			System.out.println(i+ "\t" + this.bimodalPredictor.PREDICTION_TBL[i]);
		}
	}
	

}
