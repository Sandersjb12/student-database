//Jeremy Sanders
//Insy 4305-001

public class OnlineCourse extends Course
{
	private String examProctor;
	private boolean video;
	private int numberOfChapters;

	public OnlineCourse()
	{
		this("None","None",-1,new Date(),new Date(),"None",false,-1);
	}

	public OnlineCourse(String title, String instructor, double price, Date startDate, Date endDate, String examProctor, boolean video, int numberOfChapters)
	{
		super(title,instructor,price,startDate,endDate);
		setExamProctor(examProctor);
		setVideo(video);
		setNumberOfChapters(numberOfChapters);
	}

	public void setExamProctor(String examProctor)
	{
		this.examProctor=examProctor;
	}

	public void setVideo(boolean video)
	{
		this.video=video;
	}

	public void setNumberOfChapters(int numberOfChapters)
	{
		this.numberOfChapters=numberOfChapters;
	}

	public String getExamProctor()
	{
		return examProctor;
	}

	public boolean getVideo()
	{
		return video;
	}

	public int getNumberOfChapters()
	{
		return numberOfChapters;
	}

	public double calculateCharge(Customer.CustomerType c)
	{
		switch (c)
		{
			case STUDENT:
				return super.calculateCharge(c)+20;

			case FACULTY:
				return super.calculateCharge(c)+25;

			case GOVERNMENT:
				return super.calculateCharge(c);

			default:
				System.out.println("ERROR: OnlineCourse.calculateCharge");
		}

		return super.calculateCharge(c);
	}

	public String toString()
	{
		return String.format("\n\nOnline Course:\n%s\nExam Proctor: %s\nVideo: %s\nNumber of Chapters: %d",super.toString(),getExamProctor(),getVideo(),getNumberOfChapters());
	}
}