import java.util.*;
import java.io.*;
import java.util.Scanner;
public class OS {
	
	
	
	//this class reads the input file and creates an instance of each simulator(SingleJob, SJBuff, SJobBuffSpool, MultiBuffSpool) 
	//and runs each simulator with the data from the input file
	public void readFile(Job j) throws FileNotFoundException{
		Scanner s = null;

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
                //System.out.println(j.run);debugging
                j.tape2Disk();  
            }
            j.run=false;
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
		System.out.println("\n\n**Single Job with Buffering - No Spooling**\n");
		os.readFile(sjb);
		
		//create and run an instance of a Single Job with Buffering AND Spooling
		System.out.println("\n\n**Single Job with Buffering and Spooling**\n");
		SJBuffSpool sjbs = new SJBuffSpool();
		os.readFile(sjbs);
		
		//create and run an instance of MultipleJobs with Buffering and Spooling
		System.out.println("\n\n**Multiple Job - Buffering and Spooling**\n");
		MultiProBS mp = new MultiProBS();
		os.readFile(mp);
		
		
		

	}
}

