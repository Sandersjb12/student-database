//Jeremy Sanders
//Insy 4305-001

import java.io.*;
import java.util.*;

public class Course implements Serializable
{
	private String title;
	private String instructor;
	private double price;
	public enum CourseType{PROGRAMMING,MATHEMATICS,PHOTOGRAPHY,MUSIC,PAINTING,MISC};
	private CourseType cType;
	private Date startDate;
	private Date endDate;

	public Course()
	{
		this("Select a Course","None",-1,new Date(),new Date());
	}

	public Course(String title, String instructor, double price, Date startDate, Date endDate)
	{
		setTitle(title);
		setInstructor(instructor);
		setPrice(price);
		setStartDate(startDate);
		setEndDate(endDate);
	}

	public void setTitle(String title)
	{
		this.title=title;
	}

	public void setInstructor(String instructor)
	{
		this.instructor=instructor;
	}

	public void setPrice(double price)
	{
		this.price=price;
	}

	public void setCType(CourseType cType)
	{
		this.cType=cType;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate=startDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate=endDate;
	}

	public String getTitle()
	{
		return title;
	}

	public String getInstructor()
	{
		return instructor;
	}

	public double getPrice()
	{
		return price;
	}

	public CourseType getCType()
	{
		return cType;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public double calculateCharge(Customer.CustomerType c)
	{
		switch(getCType())
		{
			case PROGRAMMING: case MATHEMATICS:
				return getPrice()+99;

			case PHOTOGRAPHY: case MUSIC: case PAINTING:
				return getPrice()+59;

			case MISC:
				return getPrice()+39;

			default:
				System.out.println("ERROR: Course.calculateCharge");
		}

		return getPrice();
	}

	public String toString()
	{
		return String.format("Title: %s\nInstructor: %s\nPrice: $%.2f\nStart Date: %s\nEnd Date: %s\nCourse Type: %s",getTitle(),getInstructor(),getPrice(),getStartDate(),getEndDate(),getCType());
	}
}