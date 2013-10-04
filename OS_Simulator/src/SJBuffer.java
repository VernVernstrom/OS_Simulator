
public class SJBuffer extends Job {


	@Override
	public void tape2Disk() {
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

	@Override
	public void disk2memory() {
		int diskCode = jobCode;//the amount of code left on the disk that needs to be moved to memory
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
				}
			}
		
		System.out.println("Code for job "+jobName+" one in memory                " + total_time +"\n");
		//Execute the job when all of it's code is in memory
		burst = jobDescription[jdCount];
		if(jdCount == 0){
			System.out.println("Start executing job "+jobName+"                       "+ total_time +'\n');
			//start buffering
			dataTape2Disk();
		}		
	}

	

	@Override
	public void dataTape2Disk() {

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
				savedBufferTime+=t;
			
			}
		}
		System.out.println("Data for job " + jobName +" on disk                      " +total_time);
		
		//transfer from disk to memory after all units for this job are on the disk
		dataDisk2Memory();
	}

	@Override
	public void dataDisk2Memory() {

	int diskData = jobData;//the amount of data on the disk that needs to be moved to memory
	int block = diskBlock;
	int t = diskTime;
	//keep moving data for this job until it is all off the disk
	while(data_in_Buffer == 0){
		if( data_in_Buffer <= block)
		{
			diskData -= block;
			data_in_Buffer += block;//add a block of data to the buffer
			System.out.println("Disk to memory transfer - data for job "+jobName+ "    " + total_time);
			total_time += t;
			system_time += t;
			savedBufferTime+=t;
		}
		
	}
	
	System.out.println("Input buffer full for job "+jobName+"                 "+ total_time+'\n');
	total_time -= savedBufferTime;//subtract time saved from total time
	system_time -= savedBufferTime;//subtract time saved from system time
	savedBufferTime = 0;//reset for next buffering session
	burst = jobDescription[jdCount];//get the cpu burst amt 
	execute(burst);

	}
	
	@Override
	public void execute(int burst) {//TODO fix the if then else of this method
		jdCount+=1;//increment for conditional control(get data from job description)
		total_time+=burst;
		execute_time += burst;
		data_in_Buffer = 0;//burst uses up the data in the buffer
		
		if(jdCount>=jobLength){//job is completed
			System.out.println("Job "+jobName+" done                                  " + total_time + '\n');
			jdCount = 0;//reset for next job
			data_in_Buffer = 0;//reset for next job
			data_on_Disk = 0;//reset for next job
		}
		else  if(jdCount%2 != 0)//data ready to be brought in
		{
			System.out.println("Need data for job "+jobName+"                         "+ total_time +'\n');
			System.out.println("Continue job "+jobName+"                              "+ total_time+'\n');
			jdCount+=1;//get cpu burst from job description
			if((data_in_Buffer == 0)&&(jdCount<jobLength-1))//buffer is empty and the last data was processed
			{
				dataDisk2Memory();//put data in the buffer
			}
			else
			{
				execute(burst);//no more data to buffer, do last burst.
			}
			
		}
		
	}

}
