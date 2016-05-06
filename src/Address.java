//Jeremy Sanders
//Insy 4305-001

import java.io.*;

public class Address implements Serializable
{
	private String street;
	private String city;
	private String state;
	private int zip;

	public Address()
	{
		this("None","None","None",-1);
	}

	public Address(String street, String city, String state, int zip)
	{
		setStreet(street);
		setCity(city);
		setState(state);
		setZip(zip);
	}

	public void setStreet(String street)
	{
		this.street=street;
	}

	public void setCity(String city)
	{
		this.city=city;
	}

	public void setState(String state)
	{
		this.state=state;
	}

	public void setZip(int z)
	{
		zip=z;
	}

	public String getStreet()
	{
		return street;
	}

	public String getCity()
	{
		return city;
	}

	public String getState()
	{
		return state;
	}

	public int getZip()
	{
		return zip;
	}

	public String toString()
	{
		return String.format("%s \n%s, %s %d",getStreet(),getCity(),getState(),getZip());
	}
}