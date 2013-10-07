import java.util.*;
import java.io.*;
import java.util.Scanner;
public class OS {
	
	
	
	//this class reads the input file and creates an instance of each simulator(SingleJob, SJBuff, SJobBuffSpool, MultiBuffSpool) 
	//and runs each simulator with the data from the input file
	public void readFile(Job j) throws FileNotFoundException{
		/**
		 * LineNumberReader reads the Jobs.dat and counts the number of lines in the file.
		 * Since the given input format has 5 lines of data specifying device space, transfer sizes, and transfer times,
		 * the remaining lines should equal the number of jobs in the file. Therefore: the number of jobs (j.jobCount) = lnr.getLineNumber()-5;
		 */
		LineNumberReader  lnr = new LineNumberReader(new FileReader(new File("Jobs.dat")));
		try {
			lnr.skip(Long.MAX_VALUE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		j.jobCount = lnr.getLineNumber()-5;//
		//System.out.println(lnr.getLineNumber());debugging
		
		Scanner s = null;
		//assign Job instance variables to corresponding values in Jobs.dat file
        try {
            s = new Scanner(new BufferedReader(new FileReader("Jobs.dat")));
            j.tapeBlock = Integer.parseInt(s.next());//100
            j.tapeTime = Integer.parseInt(s.next());//50
            j.diskSize = Integer.parseInt(s.next());//5000
            j.diskBlock = Integer.parseInt(s.next());//50
            j.diskTime = Integer.parseInt(s.next());//5
            j.memSize = Integer.parseInt(s.next());//1000
            j.jobSpace = Integer.parseInt(s.next());//50
            j.bufferSpace = Integer.parseInt(s.next());//50
            while (s.hasNext()) 
            {
            	
                j.jobName = s.next();//the name of this job (one, two, three, etc)
                //j.jobCount +=1;
                Scanner s2 = new Scanner(s.nextLine());//reads a line of job description
                //creates an array of the job description {burst, data, burst, data,...,burst}
                j.jobLength=0;
                while(s2.hasNext()){
                	
                	j.jobDescription[j.jobLength] = Integer.parseInt(s2.next());
                	
                	//System.out.println("JobLength: "+j.jobLength);//debug
                	j.jobLength+=1;	
                	
                }
                
                {
                	if(s2 != null){
                		s2.close();
                	}
                }
                //execute the current job description
                //j.burst = j.jobDescription[0];//set the size of the cpu burst
                j.runCount+=1;
                j.tape2Disk();  //run the job
                
            }

        } finally {
            if (s != null) {
                s.close();
            }
        }
        
        System.out.println("All jobs done                                 "+j.total_time + "\n");
        System.out.println("Time to complete all jobs                     "+j.total_time);
        System.out.println("Time spent executing user jobs                "+j.execute_time);
        System.out.println("Time spent by the system                      "+j.system_time + "\n\n");
    }

	
	
	
	
	public static void main(String[] args) throws FileNotFoundException {
		
		//create an instance of the driver class OS
		OS os = new OS();
		
		//create and run an instance of a Single Job with no buffering or spooling
		SingleJob sj = new SingleJob();
		System.out.println("**Single Job - No Buffering or Spooling**\n");
		os.readFile(sj);
		
		//create and run an instance of a Single Job with Buffering, NO Spooling
		SJBuffer sjb = new SJBuffer();
		System.out.println("-------------------------------------------------------------------");
		System.out.println("\n\n**Single Job with Buffering - No Spooling**\n");
		os.readFile(sjb);
		
		//create and run an instance of a Single Job with Buffering AND Spooling
		System.out.println("-------------------------------------------------------------------");
		System.out.println("\n\n**Single Job with Buffering and Spooling**\n");
		SJBuffSpool sjbs = new SJBuffSpool();
		os.readFile(sjbs);
		
		//create and run an instance of MultipleJobs with Buffering and Spooling
		System.out.println("-------------------------------------------------------------------");
		System.out.println("\n\n**Multiple Job - Buffering and Spooling**\n");
		MultiProBS mp = new MultiProBS();
		os.readFile(mp);
		
		
		

	}
}

