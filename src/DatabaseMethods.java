//Jeremy Sanders
//INSY 4305-001

import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;

import javax.swing.*;
import java.sql.*;

public class DatabaseMethods
{
    private String query;
    private PreparedStatement statement;
    private Connection connection;
    private ResultSet result;
    private String output;

    public DatabaseMethods()
    {
        try
        {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "cdavis", "fall2013");
            statement=connection.prepareStatement("USE customer");
            statement.execute();
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
    }

    public void addCustomer(Customer c)
    {
        Address a=c.getAddress();
        query = "INSERT INTO Customers VALUES(?,?,?,?);";

        try
        {
            statement = connection.prepareStatement(query);

            statement.setInt(1, c.getAccountNumber());
            statement.setString(2, c.getName());
            statement.setString(3, String.format("%s;%s;%s;%d", a.getStreet(), a.getCity(), a.getState(), a.getZip()));
            if(c.getCType()==Customer.CustomerType.STUDENT)
                statement.setString(4,"student");
            else if(c.getCType()==Customer.CustomerType.FACULTY)
                statement.setString(4,"faculty");
            else
                statement.setString(4,"government");

            statement.executeUpdate();

            query="INSERT INTO CustomerCourses VALUES(?,?,?,?,?,?,?);";
            statement=connection.prepareStatement(query);

            statement.setInt(1,c.getAccountNumber());
            statement.setString(2,c.getName());
            statement.setNull(3,Types.INTEGER);
            statement.setNull(4,Types.INTEGER);
            statement.setNull(5,Types.INTEGER);
            statement.setNull(6, Types.INTEGER);
            statement.setNull(7,Types.INTEGER);

            statement.executeUpdate();
            statement.close();
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
    }

    public void addCourseToCustomer(Customer cu, int courseIndex)
    {
        int customerIndex=0;

        query="SELECT * FROM CustomerCourses WHERE AccountNumber=?;";

        try
        {
            statement=connection.prepareStatement(query);

            statement.setInt(1,cu.getAccountNumber());

            result=statement.executeQuery();

            while(result.next())
            {
                if(result.getInt(3)==0)
                    customerIndex=1;
                else if(result.getInt(4)==0)
                    customerIndex=2;
                else if(result.getInt(5)==0)
                    customerIndex=3;
                else if(result.getInt(6)==0)
                    customerIndex=4;
                else if(result.getInt(7)==0)
                    customerIndex=5;
            }

            query="UPDATE CustomerCourses SET Course"+customerIndex+"= ? WHERE AccountNumber = ?;";

            statement=connection.prepareStatement(query);

            statement.setInt(1,courseIndex);
            statement.setInt(2,cu.getAccountNumber());

            statement.executeUpdate();

            statement.close();
            result.close();
        }
        catch(SQLException sqle)
        {
            JOptionPane.showMessageDialog(null,String.format("Customer %s is enrolled in the maximum amount of courses",cu.getName()));
        }
    }

    public boolean customerSearch(Customer c)
    {
        boolean found=false;

        query="SELECT * FROM Customers WHERE AccountNumber=?";

        try
        {
            statement = connection.prepareStatement(query);

            statement.setInt(1,c.getAccountNumber());

            result=statement.executeQuery();

            while(result.next())
            {
                found=true;
            }

            statement.close();
            result.close();
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }

        return found;
    }

    public void writeCustomerTable()
    {
        query="SELECT * FROM Customers;";

        try
        {
            statement=connection.prepareStatement(query);

            result=statement.executeQuery();

            while(result.next())
            {
                output=String.format("%d, %s, %s, %s",result.getInt("AccountNumber"),result.getString("Name"),result.getString("Address"),result.getString("CustomerType"));

                System.out.println(output);
            }

            statement.close();
            result.close();
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
    }

    public void writeCourseTable()
    {
        query="SELECT * FROM Courses;";

        try
        {
            statement=connection.prepareStatement(query);

            result=statement.executeQuery();

            while(result.next())
            {
                output=String.format("%d, %s, %s, %.2f, %s, %s, %s, %s, %s, %d, %s, %s, %s",result.getInt(1),result.getString(2),result.getString(3),result.getDouble(4),result.getString(5),result.getString(6),
                        result.getString(7),result.getString(8),result.getString(9),result.getInt(10),result.getString(11),result.getString(12),result.getString(13));

                System.out.println(output);
            }

            statement.close();
            result.close();
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
    }

    public void writeCustomerCoursesTable()
    {
        query="SELECT * FROM CustomerCourses;";

        try
        {
            statement=connection.prepareStatement(query);

            result=statement.executeQuery();

            while(result.next())
            {
                output=String.format("%d, %s, %d, %d, %d, %d, %d",result.getInt("AccountNumber"),result.getString("Name"),result.getInt(3),result.getInt(4),result.getInt(5),result.getInt(6),result.getInt(7));

                System.out.println(output);
            }

            statement.close();
            result.close();
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
    }
}
