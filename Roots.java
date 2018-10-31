package roots;

import java.util.ArrayList;
import java.util.List;


public class Roots {
    
	public static void main(String[] args){
		System.out.println("1. The function is 2x^3 - 11.7x^2 + 17.7x - 5, a third degree polynomial.");
		//create an array for the polynomial coefficients
		List<Double> coeff = new ArrayList<>();
		coeff.add(-5.0);
		coeff.add(17.7);
		coeff.add(-11.7);
		coeff.add(2.0);
		
		Bisection b = new Bisection();
		b.bisectionPoly(coeff,0,4);
		
		NewtonRhapson nr = new NewtonRhapson();
		nr.newtonRhapsonPoly(coeff,0,4);
		
		Secant s = new Secant();
		s.secantPoly(coeff,0,4);
		
		ModifiedSecant ms = new ModifiedSecant();
		ms.modifiedSecantPoly(coeff,0,4);
		
		FalsePosition fp = new FalsePosition();
		fp.falsePositionPoly(coeff,0,4);
		
		System.out.println("\n2. The function is  x + 10 – xcosh(50/x).");
		b.bisectionCosh();
		nr.newtonRhapsonCosh();
		s.secantCosh();
		ms.modifiedSecantCosh();
		fp.falsePositionCosh();
	}
	
	
}
