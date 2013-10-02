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
                //execute this job line description
                j.tape2Disk();
                
            }
        } finally {
            if (s != null) {
                s.close();
            }
        }
        
        System.out.println("All jobs done                                 "+j.total_time + "\n");
        System.out.println("Time to complete all jobs                     "+j.total_time);
        System.out.println("Time spent executing user jobs                "+j.execute_time);
        System.out.println("Time spent by the system                      "+j.system_time);
    }

	
	
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		OS os = new OS();
		//SingleJob sj = new SingleJob();
		//os.readFile(sj);
		SJBuffer sjb = new SJBuffer();
		os.readFile(sjb);
		
		

	}
}

