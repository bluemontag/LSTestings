package availablecount;
/**
 * 
 */


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import basicImpl.model.generators.SimpleGenWithRestartRow;
import basicImpl.model.latinsquares.ArrayListLatinSquare;
import commons.model.ILatinSquare;
import commons.utils.RandomUtils;

/**
 * @author ignacio
 *
 */
public class SimpleGenWithAvailableCountRestart extends SimpleGenWithRestartRow {
	
	ILatinSquare ls2;
	
	public SimpleGenWithAvailableCountRestart(int n) {
		super(n);
		ls2 = new ArrayListLatinSquare(n);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Integer> generateRow(int i_row) {
		HashSet<Integer> availableInRow = new HashSet<Integer>();
		availableInRow.addAll(symbols);
	    
		Set<Integer>[] initialAvailableInCol = new HashSet[n];
	    
	    for (int j=0; j<n; j++) {
	    	initialAvailableInCol[j] = new HashSet<Integer>();
	    	initialAvailableInCol[j].addAll(availableInCol[j]);
	    }
		
	    int collisionCount = 0;
	    
	    //result of this method
	    ArrayList<Integer> row = new ArrayList<Integer>();
	    Set<ArrayList<Integer>> rows = new HashSet<ArrayList<Integer>>();
	    int size=0;
	    
	    do {
		    row = new ArrayList<Integer>();
		    
		    int i_col = 0;
		    
		    while (i_col < n) {//when i_col is n, there are n chosen numbers
		        //available is:
		        Set<Integer> available = new HashSet<Integer>();
		        available.addAll(availableInCol[i_col]);
		    	available.retainAll(availableInRow);
		    	
		        if (!available.isEmpty()) { //if there are available
		            //choose a symbol at random
		            Integer symbol = RandomUtils.randomChoice(available);
		            
		            //count the chosen symbol
		            availableInCol[i_col].remove(symbol);
		            availableInRow.remove(symbol);
		            row.add(symbol);
		            i_col++;
		        } else {//collision
		        	collisionCount++;
		        	
		        	//remove all symbols in row and return to available
		        	while (i_col>0) {
		        		i_col--;
		        		Integer symbol = row.get(i_col);
		        		availableInCol[i_col].add(symbol);
		        		availableInRow.add(symbol);
		        		row.remove(i_col);
		        	}
	
		            if (collisionCount%10000==0) {
		            	System.out.println(collisionCount +" restarts!");
		            }
		        }
		    }
		    
		    rows.add(row);
		    size++;
		    System.out.println("Row "+i_row+" finished "+size+" times");
		    
		    for (int j=0; j<n; j++) {
		    	availableInCol[j] = new HashSet<Integer>();
		    	availableInCol[j].addAll(initialAvailableInCol[j]);
		    }
		    
		    availableInRow = new HashSet<Integer>();
			availableInRow.addAll(symbols);
		    
	    } while (collisionCount<1000000 && size<100);
	    
	    System.out.println(collisionCount+" collisions");
	    System.out.println(rows.size()+" possible rows counted.");
	    
	    availableInRow.addAll(symbols);
	    
	    for(int j=0; j<n; j++) {
	    	HashSet<Integer> av = new HashSet<Integer>();
	    	av.addAll(initialAvailableInCol[j]);
	    	av.retainAll(availableInRow);
	    	
	    	ls2.setValueAt(i_row, j, av.size());
	    	
	    	availableInRow.remove(row.get(j));
	    }
	    
	    return row;
	}

	@Override
	public String getMethodName() {
		return "Generation row by row with restarting row with available count.";
	}
	
	public ILatinSquare getLs2() {
		return ls2;
	}

	public void setLs2(ILatinSquare ls2) {
		this.ls2 = ls2;
	}
}