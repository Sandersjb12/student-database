//Jeremy Sanders
//Insy 4305-001

import java.util.*;

public class CustomerNotEnrolledException extends Exception
{
	Customer c;

	public CustomerNotEnrolledException(Customer c)
	{
		this.c=c;
	}

	public Customer getCustomer()
	{
		return c;
	}
}