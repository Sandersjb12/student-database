//Jeremy Sanders
//Insy 4305-001

import java.io.*;

public class Date implements Serializable
{
	private int month;
	private int day;
	private int year;

	public Date()
	{
		this(-1,-1,-1);
	}

	public Date(int month, int day, int year)
	{
		setMonth(month);
		setDay(day);
		setYear(year);
	}

	public void setMonth(int month)
	{
		this.month=month;
	}

	public void setDay(int day)
	{
		this.day=day;
	}

	public void setYear(int year)
	{
		this.year=year;
	}

	public int getMonth()
	{
		return month;
	}

	public int getDay()
	{
		return day;
	}

	public int getYear()
	{
		return year;
	}

	public String toString()
	{
		return String.format("%d/%d/%d",getMonth(),getDay(),getYear());
	}
}