
public class SingleJob extends Job {

	//move job code from tape to disk
	public void tape2Disk(){
		int tapeCode = jobCode;//the amount of code for the  job that is on the tape and needs to be moved(200)
		int block = tapeBlock;
		int t = tapeTime;
		//keep moving code for  job until it is all off the tape
		while(tapeCode > 0){
		//move units into disk if disk has enough space
		if (diskSize >= block)
		{
			tapeCode -= block;
			diskSize -= block;
			System.out.println("Tape to Disk transfer - code for job "+jobName+"      " + total_time );
			total_time += t;
			system_time += t;	
		}
		}
		//transfer from disk to memory after all units for this job are on the disk
		
		disk2memory();
	}
	
	
	//move job code from disk to memory
	public void disk2memory(){
		int diskCode = jobCode;//the amount of code ly on the disk that needs to be moved to memory
		int block = diskBlock;
		int t = diskTime;
		//keep moving code for this job until it is all off the disk
		while(diskCode > 0){
			//move units into memory if memory has enough space
			if( memSize >= block)
			{
				diskCode -= block;
				memSize = memSize-block;
				System.out.println("Disk to memory transfer - code for job "+jobName+ "    " + total_time);
				total_time += t;
				system_time += t;
				//System.out.println(diskCode);//debug
				//System.out.println("memSize: " + memSize + " block: " + block+"");//debug
				}
			}
		
		System.out.println("Code for job "+jobName+" one in memory                " + total_time +"\n");
		//Execute the job when all of it's code is in memory
		int burst = jobDescription[jdCount];
		if(jdCount == 0){
			System.out.println("Start executing job "+jobName+"                       "+ total_time +'\n');
		}
		jdCount+=1;
		execute(burst);
	}

	//move job data from tape to disk
		public void dataTape2Disk(){
			tapeData = jobData;//the amount of data for the  job that is on the tape and needs to be moved(100)
			int block = tapeBlock;
			int t = tapeTime;
			//keep moving code for  job until it is all off the tape
			while(tapeData > 0){
			//move units into disk if disk has enough space
				if ((diskSize >= block));
				{
					tapeData -= block;
					diskSize -= block; 
					data_on_Disk += block;//add a block of data to the disk
					//System.out.println("Data_on_Disk: "+data_on_Disk);//debugging
					System.out.println("Tape to Disk transfer - data for job "+jobName+"      " + total_time );
					total_time += t;
					system_time += t;
				
				}
			}
			System.out.println("Data for job " + jobName +" on disk                      " +total_time);
			//transfer from disk to memory after all units for this job are on the disk
			dataDisk2Memory();
		}
		
		//move job data from disk to memory
		public void dataDisk2Memory(){
				
			diskData = jobData;//the amount of data on the disk that needs to be moved to memory
			int block = diskBlock;
			int t = diskTime;
			//keep moving data for this job until it is all off the disk
			//System.out.println("bufferSpace:"+bufferSpace+ "  block: "+block);//debugging
			while(bufferSpace > 0){
				if( bufferSpace >= block)
				{
					diskData -= block;
					bufferSpace -= block;
					data_in_Buffer += block;//add a block of data to the buffer
					System.out.println("Disk to memory transfer - data for job "+jobName+ "    " + total_time);
					total_time += t;
					system_time += t;
					//System.out.println("bufferSpace:"+bufferSpace+ "  block: "+block+"diskData: "+ diskData);//debugging
					
					//System.out.println(diskData);//debug
					//System.out.println("memSize: " + memSize + " block: " + block+"");//debug
				}
				
			}
			
			System.out.println("Input buffer full for "+jobName+"                     "+ total_time+'\n');
			System.out.println("Continue job "+jobName+"                              "+ total_time+'\n');
			//Execute the job when all of it's code is in memory
			jdCount+=1;//CPU BURST
			int burst = jobDescription[jdCount];
			//System.out.println("JDCOUNT dataD2M: "+ jdCount +'\n');//debug
			jdCount+=1;//data
			bufferSpace += block;//data processed, made room in buffer
			data_in_Buffer -= block;//''''
			execute(burst);
			
		}
		
		public void execute(int burst){//TODO fix the if then else of this method
			total_time+=burst;
			execute_time += burst;
			//System.out.println("jdCount: "+jdCount);//debug
			//System.out.println("jobLength:"+jobLength);//debug
			if(jdCount>=jobLength){
				//System.out.println(jobDescription[jdCount]);//debug
				System.out.println("Job "+jobName+" done                                  " + total_time + '\n');
				jdCount = 0;//reset for next job
				data_in_Buffer = 0;//reset for next job
				data_on_Disk = 0;//reset for next job
			}
			else  if(jdCount%2 != 0)//read Data element of job description ***this condition might not be necessary
			{
				System.out.println("Need data for job "+jobName+"                         "+ total_time +'\n');
				//System.out.println("data_in_buffer: "+data_in_Buffer);//debugging
				
				if((data_in_Buffer == 0)&&(data_on_Disk==0))//data buffer is empty and no data on disk
				{
					dataTape2Disk();//get the data from the tape
				}
				else//move data from disk to memory
				{
					dataDisk2Memory();
				}
			}
			
		}


		

}
