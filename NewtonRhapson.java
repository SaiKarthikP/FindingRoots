package roots;

import java.util.ArrayList;
import java.util.List;

public class NewtonRhapson {

    
    private WriteToFile fw = new WriteToFile();

    
    //helper method for finding derivative of a polynomial
  	private static List<Double> derivative(List<Double> coeff){
  		List<Double> derivCoeff = new ArrayList<>();
  		for(int i=1;i<coeff.size();i++){
  			derivCoeff.add(coeff.get(i)*i);
  		}
  		return derivCoeff;
  	}
    
	public void newtonRhapsonCosh(){
		System.out.println("\nUsing the Newton Rhapson Method: ");
		double root = 0;
		List<Double> approxRelErrData = new ArrayList<>();
		List<Double> approxTrueErrData = new ArrayList<>();
		double fx, dfx, x = 120; //c
		boolean searchForRoot = true;		
		int iterations = 0;
		double newX = 0, approxRelErr = 0, approxTrueErr = 0;
		while(searchForRoot){
			iterations++;
			fx = x+10-(x*Math.cosh(50/x)); //c
			dfx = (50/x)*Math.sinh(50/x)-Math.cosh(50/x)+1;
			newX = x-(fx/dfx);
			approxRelErr = Math.abs((newX-x)/newX);
			approxTrueErr = Math.abs((126.63243-newX)/126.63243);
			approxRelErrData.add(approxRelErr);
			approxTrueErrData.add(approxTrueErr);
			//exit loop if error is less than 1%
			if(approxRelErr<0.01 || iterations==100){
				System.out.println(newX+" is the root.");
				root = newX;
				searchForRoot = false;
			}
			else{
				//save newX value for later approximate error calculation
				x = newX;
			}
		}
		String fileName = "NewtonRhapsonCoshRel.txt";
		String titleText = "Approximate Relative Error for Root "+ root;
		fw.writeToFile(fileName,titleText, approxRelErrData);

		
		fileName = "NewtonRhapsonCoshTrue.txt";
		titleText = "Approximate True Error for Root "+ root;
		fw.writeToFile(fileName,titleText, approxTrueErrData);
	}	

	public void newtonRhapsonPoly(List<Double> coeff, int val1, int val2){
		System.out.println("\nUsing the Newton Rhapson Method: ");
		List<Double> roots = new ArrayList<>();
		int rootCount = 0;
		List<Double> derivCoeff = derivative(coeff);
		List<Double> approxRelErrData = new ArrayList<>();
		for(int rootBounds=val1;rootBounds<val2 && rootCount<3;rootBounds++){
			double fx, dfx, x = rootBounds;
			boolean searchForRoot = true;		
			int iterations = 0;
			double newX = 0, approxRelErr = 0;
			while(searchForRoot){
				iterations++;
				fx = 0.0;
				for(int i=0;i<coeff.size();i++){
					fx += coeff.get(i) * Math.pow(x,i);
				}
				dfx = 0.0;
				for(int i=0;i<derivCoeff.size();i++){
					dfx += derivCoeff.get(i) * Math.pow(x,i);
				}
				newX = x-(fx/dfx);
				approxRelErr = Math.abs((newX-x)/newX);
				if(iterations>1)
					approxRelErrData.add(approxRelErr);
				//exit loop if error is less than 1%
				if(approxRelErr<0.01 || iterations==100){
					boolean oldRoot = false;
					for(int j=0;j<roots.size();j++){
						if(newX-roots.get(j) < 0.001)
							oldRoot = true;
					}
					if(!oldRoot){
						System.out.println(newX+" is a root.");
						roots.add(newX);
						rootCount++;
					}
					searchForRoot = false;
				}
				else{
					//save newX value for later approximate error calculation
					x = newX;
				}
			}
			String fileName = "NewtonRhapson"+(rootCount)+".txt";
			String titleText = "Approximate Relative Error for Root "+roots.get(rootCount-1);
			fw.writeToFile(fileName,titleText, approxRelErrData);
			
			approxRelErrData.clear();
		}
	}

}
