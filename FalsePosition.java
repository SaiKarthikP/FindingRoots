package roots;

import java.util.ArrayList;
import java.util.List;

public class FalsePosition {
    
    private WriteToFile fw = new WriteToFile();
    
	public void falsePositionCosh(){
		System.out.println("\nUsing the False Position Method: ");
		double root = 0.0;
		List<Double> approxRelErrData = new ArrayList<>();
		List<Double> approxTrueErrData = new ArrayList<>();
		double fa = 0.0, fb = 0.0, a = 120, b = 130;
		boolean searchForRoot = false;
		fa = a+10-(a*Math.cosh(50/a));
		fb = b+10-(b*Math.cosh(50/b));
		double l = 0.0, u = 0.0, fl = 0.0, fu = 0.0;
		if(fa<0){
			if(fb>0){
				l = a;
				fl = fa;
				u = b;
				fu = fb;
				searchForRoot = true;
			}
		}
		else{
			if(fb<0){
				l = b;
				fl = fb;
				u = a;
				fu = fa;
				searchForRoot = true;
			}
		}
		int iterations = 0;
		double c = 0, fc = 0.0, c_prev = 0.0, approxRelErr = 0, approxTrueErr = 0;
		while(searchForRoot){
			iterations++;
			//calculate c
			c = u-((fu*(l-u))/(fl-fu));
			//calculate approximate error
			approxRelErr = Math.abs((c - c_prev)/c);
			approxTrueErr = Math.abs((126.63243 - c_prev)/126.63243);
			approxRelErrData.add(approxRelErr);
			approxTrueErrData.add(approxTrueErr);
			//exit loop if error is less than 1%
			if(approxTrueErr<0.01 || iterations==100){
				System.out.println(c+" is a root.");
				root = c;
				searchForRoot = false;
			}
			else{
				//calculate functional value of c, f(c)
				fc = c+10-(c*Math.cosh(50/c));
				if(fc<0) l = c;
				else u = c;
				//save c value for later approximate error calculation
				c_prev = c;
			}
		}
		String fileName = "FalsePositionCoshRel.txt";
		String titleText = "Approximate Relative Error for Root "+ root;
		fw.writeToFile(fileName,titleText, approxRelErrData);

		
		fileName = "FalsePositionCoshTrue.txt";
		titleText = "Approximate True Error for Root "+ root;
		fw.writeToFile(fileName,titleText, approxTrueErrData);

	}

	public void falsePositionPoly(List<Double> coeff, int val1, int val2){
		System.out.println("\nUsing the False Position Method: ");
		List<Double> roots = new ArrayList<>();
		List<Double> approxRelErrData = new ArrayList<>();
		int rootCount = 0, fileCount = 0;
		for(int rootBounds=val1;rootBounds<val2 && rootCount<3;rootBounds++){
			double fa = 0.0, fb = 0.0, a = rootBounds, b = rootBounds+1;
			boolean searchForRoot = false;
			for(int i=0;i<coeff.size();i++){
				fa += coeff.get(i) * Math.pow(a,i);
			}
			for(int i=0;i<coeff.size();i++){
				fb += coeff.get(i) * Math.pow(b,i);
			}
			double l = 0.0, u = 0.0, fl = 0.0, fu = 0.0;
			if(fa<0){	
				if(fb>0){
					l = a;
					fl = fa;
					u = b;
					fu = fb;
					searchForRoot = true;
				}
			}
			else{
				if(fb<0){
					l = b;
					fl = fb;
					u = a;
					fu = fa;
					searchForRoot = true;
				}
			}
			
			int iterations = 0;
			double c = 0, fc = 0.0, c_prev = 0.0, approxRelErr = 0;
			while(searchForRoot){
				iterations++;
				//calculate c
				c = u-((fu*(l-u))/(fl-fu));
				//calculate approximate error
				approxRelErr = Math.abs((c - c_prev)/c);
				if(iterations>1)
					approxRelErrData.add(approxRelErr);
				//exit loop if error is less than 1%
				if(approxRelErr<0.01 || iterations==100){
						System.out.println(c+" is a root.");
						roots.add(c);
						rootCount++;
						searchForRoot = false;
				}
				else{
					//calculate functional value of c, f(c)
					fc = 0.0;
					for(int i=0;i<coeff.size();i++){
						fc += coeff.get(i) * Math.pow(c,i);
					}
					if(fc<0) l = c;
					else u = c;
					//save c value for later approximate error calculation
					c_prev = c;
				}
			}
			if(rootCount>fileCount){
				String fileName = "FalsePosition"+(++fileCount)+".txt";
				String titleText = "Approximate Relative Error for Root "+roots.get(rootCount-1);
				fw.writeToFile(fileName,titleText, approxRelErrData);
			}
			approxRelErrData.clear();
		}
	}

}
