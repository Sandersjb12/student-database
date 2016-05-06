//Jeremy Sanders
//Insy 4305-001

public class CustomerAlreadyExistsException extends Exception
{
    Customer c;

    public CustomerAlreadyExistsException(Customer c)
    {
        this.c=c;
    }

    public Customer getCustomer()
    {
        return c;
    }
}
