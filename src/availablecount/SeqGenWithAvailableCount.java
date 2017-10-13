package availablecount;
/**
 * 
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import commons.model.latinsquares.ArrayListLatinSquare;
import commons.model.latinsquares.ILatinSquare;
import commons.utils.RandomUtils;
import seqgen.model.generators.SeqGenWithReplGraph;


/**
 * @author ignacio
 *
 */
public class SeqGenWithAvailableCount extends SeqGenWithReplGraph {

		ILatinSquare ls2;
		
		public SeqGenWithAvailableCount(int n) {
			super(n);
			ls2 = new ArrayListLatinSquare(n);
		}

		
		@SuppressWarnings("unchecked")
		@Override
		public ArrayList<Integer> generateRow(int i_row) {
			Set<Integer> availableInRow = new HashSet<Integer>();
		    
			availableInRow.addAll(symbols);
		    
		    Set<Integer>[] initialAvailableInCol = new HashSet[n];
		    
		    for (int j=0; j<n; j++) {
		    	initialAvailableInCol[j] = new HashSet<Integer>();
		    	initialAvailableInCol[j].addAll(availableInCol[j]);
		    }
		    
		    //result of this method
		    ArrayList<Integer> row = new ArrayList<Integer>();
		    int i_col = 0;
		    
		    while (i_col < n) {//when i_col is n, there are n chosen numbers
		        //available is:
		        HashSet<Integer> available = new HashSet<Integer>();
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
		        	HashMap<Integer, HashSet<Integer>> map = this.constructReplGraph(row, i_col, initialAvailableInCol);
		        	int elem = RandomUtils.randomChoice(availableInCol[i_col]);
		        	this.makeElemAvailable(elem, map, row, i_col, availableInRow);
		        }
		    }
		    
		    
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

		public ILatinSquare getLs2() {
			return ls2;
		}

		public void setLs2(ILatinSquare ls2) {
			this.ls2 = ls2;
		}
		
	}
