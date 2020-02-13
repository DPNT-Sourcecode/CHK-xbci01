package befaster.solutions.SUM;

import befaster.runner.SolutionNotImplementedException;

@SuppressWarnings("unused")
public class SumSolution {

    public int compute(int x, int y) {
        if(x>0 && x<100 && y> 0 && y<100)
        	return x+y;
        return 0;
    }

}

