package roots;

import java.util.ArrayList;
import java.util.List;

public class Secant {
    
    private WriteToFile fw = new WriteToFile();

    
	public void secantCosh(){
		System.out.println("\nUsing the Secant Method: ");
		double root = 0.0;
		List<Double> approxRelErrData = new ArrayList<>();
		List<Double> approxTrueErrData = new ArrayList<>();
		double fa = 0.0, fb = 0.0, a = 120, b = 130;  //c
		boolean searchForRoot = true;
		fa = a+10-(a*Math.cosh(50/a));
		fb = b+10-(b*Math.cosh(50/b));
		int iterations = 0;
		double c = 0, fc = 0.0, approxRelErr = 0, approxTrueErr = 0;
		while(searchForRoot){
			iterations++;
			//calculate c
			c = b-(fb*(b-a))/(fb-fa);
			//calculate approximate error
			approxRelErr = Math.abs((c - b)/c);
			approxTrueErr = Math.abs((126.63243-c)/126.63243);
			approxRelErrData.add(approxRelErr);
			approxTrueErrData.add(approxTrueErr);
			//exit loop if error is less than 1%
			if(approxTrueErr<0.01 || iterations==100){
				System.out.println(c+" is the root.");
				root = c;
				searchForRoot = false;
			}
			else{
				//calculate functional value of c, f(c)
				//fc = 0;
				fc = c+10-(c*Math.cosh(50/c));
				a=b;
				fa=fb;
				b=c;
				fb=fc;
			}
		}
		String fileName = "SecantCoshRel.txt";
		String titleText = "Approximate Relative Error for Root "+ root;
		fw.writeToFile(fileName,titleText, approxRelErrData);

		
		fileName = "SecantCoshTrue.txt";
		titleText = "Approximate True Error for Root "+ root;
		fw.writeToFile(fileName,titleText, approxTrueErrData);

	}

	public void secantPoly(List<Double> coeff, int val1, int val2){
		System.out.println("\nUsing the Secant Method: ");
		List<Double> roots = new ArrayList<>();
		List<Double> approxRelErrData = new ArrayList<>();
		int rootCount = 0, fileCount = 0;
		for(int rootBounds=val1;rootBounds<val2 && rootCount<3;rootBounds++){
			double fa = 0.0, fb = 0.0, a = rootBounds, b = rootBounds+1;
			boolean searchForRoot = true;
			for(int i=0;i<coeff.size();i++){
				fa += coeff.get(i) * Math.pow(a,i);
			}
			for(int i=0;i<coeff.size();i++){
				fb += coeff.get(i) * Math.pow(b,i);
			}
			int iterations = 0;
			double c = 0, fc = 0.0, approxRelErr = 0;
			while(searchForRoot){
				iterations++;
				//calculate c
				c = b-(fb*(b-a))/(fb-fa);
				//calculate approximate error
				approxRelErr = Math.abs((c - b)/c);
				if(iterations>1)
					approxRelErrData.add(approxRelErr);
				//exit loop if error is less than 1%
				if(approxRelErr<0.01 || iterations==100){
					boolean oldRoot = false;
					for(int j=0;j<roots.size();j++){
						if(c-roots.get(j) < 0.001)
							oldRoot = true;
					}
					if(!oldRoot){
						System.out.println(c+" is a root.");
						roots.add(c);
						rootCount++;
						searchForRoot = false;
					}
					searchForRoot = false;
				}
				else{
					//calculate functional value of c, f(c)
					fc = 0;
					for(int i=0;i<coeff.size();i++){
						fc += coeff.get(i) * Math.pow(c,i);
					}
					a=b;
					fa=fb;
					b=c;
					fb=fc;
				}
			}
			if(rootCount>fileCount){
				String fileName = "Secant"+(++fileCount)+".txt";
				String titleText = "Approximate Relative Error for Root "+roots.get(rootCount-1);
				fw.writeToFile(fileName,titleText, approxRelErrData);
			}
			approxRelErrData.clear();
		}
	}


}
