import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Runs a number of algorithms that try to fit files onto disks.
 */
public class Bins {
    public static final String DATA_FILE = "princeton.txt";
    
    /**
     * Reads list of integer data from the given input.
     *
     * @param input tied to an input source that contains space separated numbers
     * @return list of the numbers in the order they were read
     */
    public List<Integer> readData (Scanner input) {
        List<Integer> results = new ArrayList<Integer>();
        while (input.hasNext()) {
            results.add(input.nextInt());
        }
        return results;
    }
    
    public static int worstFit(PriorityQueue<Disk> pq, List<Integer> data){
    	pq.add(new Disk(0));
    	int diskId = 1;
        int total = 0;
        for (Integer size : data) {
            Disk d = pq.peek();
            if (d.freeSpace() > size) {
                pq.poll();
                d.add(size);
                pq.add(d);
            } else {
                Disk d2 = new Disk(diskId);
                diskId++;
                d2.add(size);
                pq.add(d2);
            }
            total += size;
        }
        return total;
    }
    	
    public static int worstFitDecreasing(PriorityQueue<Disk> pq, List<Integer> data){
    	pq.add(new Disk(0));
    	Collections.sort(data, Collections.reverseOrder());
    	return worstFit(pq,data);
    }
    
    public static void printStats(PriorityQueue<Disk> pq, int total){        
    	System.out.println("total size = " + total / 1000000.0 + "GB");
    	System.out.println("number of pq used: " + pq.size());
        while (!pq.isEmpty()) {
            System.out.println(pq.poll());
        }
        
    }
    
    /**
     * The main program.
     */
    public static void main (String args[]) {
        Bins b = new Bins();
        Scanner input = new Scanner(Bins.class.getClassLoader().getResourceAsStream(DATA_FILE));
        List<Integer> data = b.readData(input);

        PriorityQueue<Disk> pq = new PriorityQueue<Disk>();
        
        System.out.println("worst-fit in-order method");
        int total = worstFit(pq, data);
        printStats(pq, total);
        
        System.out.println();
        
        System.out.println("worst-fit decreasing method");
        total = worstFitDecreasing(pq, data);
        printStats(pq, total);
    }
}
