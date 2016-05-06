//Jeremy Sanders
//Insy 4305-001

public class InClassCourse extends Course
{
	private String room;
	private Time startTime;
	private Time endTime;

	public InClassCourse()
	{
		this("None","None",-1,new Date(),new Date(),"None",new Time(),new Time());
	}

	public InClassCourse(String title, String instructor, double price, Date startDate, Date endDate, String room, Time startTime, Time endTime)
	{
		super(title,instructor,price,startDate,endDate);
		setRoom(room);
		setStartTime(startTime);
		setEndTime(endTime);
	}

	public void setRoom(String room)
	{
		this.room=room;
	}

	public void setStartTime(Time startTime)
	{
		this.startTime=startTime;
	}

	public void setEndTime(Time endTime)
	{
		this.endTime=endTime;
	}

	public String getRoom()
	{
		return room;
	}

	public Time getStartTime()
	{
		return startTime;
	}

	public Time getEndTime()
	{
		return endTime;
	}

	public double calculateCharge(Customer.CustomerType c)
	{
		switch (c)
		{
			case STUDENT:
				return super.calculateCharge(c);

			case FACULTY:
				return super.calculateCharge(c)+5;

			case GOVERNMENT:
				return super.calculateCharge(c);

			default:
				System.out.println("ERROR: InClassCourse.calculateCharge");
				break;
		}

		return super.calculateCharge(c);
	}

	public String toString()
	{
		return String.format("\n\nIn Class Course:\n%s\nRoom: %s\nStart Time: %s\nEnd Time: %s",super.toString(),getRoom(),getStartTime(),getEndTime());
	}
}