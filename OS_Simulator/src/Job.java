
public abstract class Job {

	public int tapeBlock;//block size for trnsfr from tape to disk
	public int tapeTime;//time for trnsfr from tape to disk
	public int diskSize;//Available space on disk
	public int diskBlock;//block size for trnsfr from disk to memory
	public int diskTime;//time for trnsfr from disk to memory
	public int memSize;//size of the memory
	public int jobSpace; //size of the input space for a job
	public int bufferSpace;//size of buffer into which DMA transfer could occur
	public int system_time = 0;//time spent by the system
	public int execute_time = 0;
	public int total_time = 0;
	public int savedBufferTime = 0;
	public int tapeData;//the amount of data on the tape
	public int diskData;//the amount of data on the disk
	public int jobCode = 200;//the size of the jobCode for a job
	public int jobData = 100;//size of the data for a Job
	public int data_on_Disk = 0;//amt of data on the disk
	public int data_in_Buffer = 0;//amt of data in the buffer
	public Integer [] jobDescription = new Integer[20];
	public int jobLength;//the number of elements in jobDescription (i.e. 200 50 200 is 3)
	public int jdCount = 0;//iterates the jobDescription array
	public String jobName;//for string output formatting
	
	public abstract void tape2Disk();
	public abstract void disk2memory();
	
	public abstract void dataTape2Disk();
	public abstract void dataDisk2Memory();
	
	

	public abstract void execute(int burst);
}
