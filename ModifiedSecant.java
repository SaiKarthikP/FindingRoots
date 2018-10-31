package roots;

import java.util.ArrayList;
import java.util.List;

public class ModifiedSecant {

    
    private WriteToFile fw = new WriteToFile();

    
	public void modifiedSecantCosh(){
		System.out.println("\nUsing the Modified Secant Method: ");
		double root = 0.0;
		List<Double> approxRelErrData = new ArrayList<>();
		List<Double> approxTrueErrData = new ArrayList<>();
		double fa , a = 120, fb = 0, newA = 0, approxRelErr = 0, approxTrueErr = 0.0;
		boolean searchForRoot = true;
		int iterations = 0;
		while(searchForRoot){
			iterations++;
			fa = a+10-(a*Math.cosh(50/a)); 
			fb = (a+(a*.01))+10-((a+(a*.01))*Math.cosh(50/(a+(a*.01))));
			//calculate newA
			newA = a-((fa*(a*0.01))/(fb-fa));
			//calculate approximate error
			approxRelErr = Math.abs((newA-a)/newA);
			approxTrueErr = Math.abs((126.63243-newA)/126.63243);
			approxRelErrData.add(approxRelErr);
			approxTrueErrData.add(approxTrueErr);
			//exit loop if error is less than 1%
			if(approxTrueErr<0.01 || iterations==100){
				System.out.println(newA+" is the root.");
				root = newA;
				searchForRoot = false;
			}
			else{
				//calculate functional value of a
				a = newA;
			}
		}
		String fileName = "ModifiedSecantCoshRel.txt";
		String titleText = "Approximate Relative Error for Root "+ root;
		fw.writeToFile(fileName,titleText, approxRelErrData);

		
		fileName = "ModifiedSecantCoshTrue.txt";
		titleText = "Approximate True Error for Root "+ root;
		fw.writeToFile(fileName,titleText, approxTrueErrData);
		
	}

	
	public void modifiedSecantPoly(List<Double> coeff, int val1, int val2){
		System.out.println("\nUsing the Modified Secant Method: ");
		List<Double> roots = new ArrayList<>();
		List<Double> approxRelErrData = new ArrayList<>();
		int rootCount = 0, fileCount = 0;
		double rootBounds=val1;
		while(rootBounds<val2 && rootCount<3){
			double fa , a = rootBounds, fb = 0, newA = 0, approxRelErr = 0;
			boolean searchForRoot = true;
			if(a==0){
				searchForRoot = false;
			}
			int iterations = 0;
			while(searchForRoot){
				iterations++;
				fa = 0;
				for(int i=0;i<coeff.size();i++){
					fa += coeff.get(i) * Math.pow(a,i);
				}
				fb = 0;
				for(int i=0;i<coeff.size();i++){
					fb += coeff.get(i) * Math.pow(a+(a*.01),i);
				}
				
				//calculate newA
				newA = a-((fa*(a*0.01))/(fb-fa));
				//calculate approximate error
				approxRelErr = Math.abs((newA-a)/newA);
				if(iterations>1)
					approxRelErrData.add(approxRelErr);
				//exit loop if error is less than 1%
				if(approxRelErr<0.01 || iterations==100){
					boolean oldRoot = false;
					for(int j=0;j<roots.size();j++){
						if(newA-roots.get(j) < 0.001)
							oldRoot = true;
					}
					if(!oldRoot){
						System.out.println(newA+" is a root.");
						roots.add(newA);
						rootCount++;
						searchForRoot = false;
					}
					searchForRoot = false;
				}
				else{
					//calculate functional value of a
					a = newA;
				}
			}
			if(rootCount>fileCount){
				String fileName = "ModifiedSecant"+(++fileCount)+".txt";
				String titleText = "Approximate Relative Error for Root "+roots.get(rootCount-1);
				fw.writeToFile(fileName,titleText, approxRelErrData);
				
			}
			approxRelErrData.clear();
			rootBounds++;
		}
	}

}
