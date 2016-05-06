//Jeremy Sanders
//Insy 4305-001

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class CourseGUI extends JFrame
{
    private JLabel nameLabel;
    private JLabel streetLabel;
    private JLabel cityLabel;
    private JLabel stateLabel;
    private JLabel zipLabel;
    private JLabel accountNumberLabel;
    private JLabel customerTypeLabel;
    private JLabel studentLabel;
    private JLabel facultyLabel;
    private JLabel governmentLabel;
    private JLabel courseLabel;
    private JLabel submitLabel;
    private JLabel finishLabel;

    private JTextField nameField;
    private JTextField streetField;
    private JTextField cityField;
    private JTextField stateField;
    private JTextField zipField;
    private JTextField accountNumberField;

    private JPanel customerTypePanel;
    private ButtonGroup customerTypeGroup;
    private JRadioButton studentButton;
    private JRadioButton facultyButton;
    private JRadioButton governmentButton;

    private ArrayList<Course> courseList=new ArrayList<Course>();
    private String[] courses;
    private JComboBox<String> courseBox;
    private int index;

    private JButton submitButton;
    private JButton finishButton;
    private ArrayList<Customer> customerList=new ArrayList<Customer>();
    private boolean exists;
    private Customer tempCustomer;
    private int customerIndex;

    private JMenuBar bar;
    private JMenu addMenu;
    private JMenuItem addCustomer;
    private JMenuItem addCourse;
    private JMenu invoiceMenu;
    private JMenuItem generateInvoice;
    private JMenu exitMenu;
    private JMenuItem writeFiles;
    private JMenuItem exitProgram;
    private JMenuItem writeDatabase;

    private DatabaseMethods dbm=new DatabaseMethods();

    public CourseGUI()
    {
        super("Course GUI");
        setLayout(new GridLayout(10,2));

        nameLabel=new JLabel("Enter Name");
        streetLabel=new JLabel("Enter Street");
        cityLabel=new JLabel("Enter City");
        stateLabel=new JLabel("Enter State");
        zipLabel=new JLabel("Enter Zip Code");
        accountNumberLabel=new JLabel("Enter Account Number");
        customerTypeLabel=new JLabel("Select Type of Customer");
        studentLabel=new JLabel("Student");
        facultyLabel=new JLabel("Faculty");
        governmentLabel=new JLabel("Government");
        courseLabel=new JLabel("Select a Course");
        submitLabel=new JLabel("Click Submit When Done");
        finishLabel=new JLabel("Click When Finished");

        streetField=new JTextField(20);
        cityField=new JTextField(20);
        stateField=new JTextField(20);
        zipField=new JTextField(5);
        nameField=new JTextField(20);
        accountNumberField=new JTextField(20);

        customerTypePanel=new JPanel();
        customerTypeGroup=new ButtonGroup();
        studentButton=new JRadioButton();
        facultyButton=new JRadioButton();
        governmentButton=new JRadioButton();
        customerTypeGroup.add(studentButton);
        customerTypeGroup.add(facultyButton);
        customerTypeGroup.add(governmentButton);
        customerTypePanel.add(studentLabel);
        customerTypePanel.add(studentButton);
        customerTypePanel.add(facultyLabel);
        customerTypePanel.add(facultyButton);
        customerTypePanel.add(governmentLabel);
        customerTypePanel.add(governmentButton);

        courseList=readCourses("courses.txt");
        courses=new String[courseList.size()];
        index=0;
        for(Course c:courseList)
        {
            courses[index]=c.getTitle();
            index++;
        }
        courseBox=new JComboBox<String>(courses);
        courseBox.setMaximumRowCount(3);

        submitButton=new JButton("Submit");
        finishButton=new JButton("Finish");

        bar=new JMenuBar();
        addMenu=new JMenu("Add");
        addCustomer=new JMenuItem("Add Customer");
        addCourse=new JMenuItem("Add Course");
        invoiceMenu=new JMenu("Invoice");
        generateInvoice=new JMenuItem("Generate Invoice");
        exitMenu=new JMenu("Exit");
        writeFiles=new JMenuItem("Write Files");
        exitProgram=new JMenuItem("Exit");
        writeDatabase=new JMenuItem("Write Database");

        addMenu.add(addCustomer);
        addMenu.add(addCourse);
        invoiceMenu.add(generateInvoice);
        exitMenu.add(writeFiles);
        exitMenu.add(writeDatabase);
        exitMenu.add(exitProgram);

        bar.add(addMenu);
        bar.add(invoiceMenu);
        bar.add(exitMenu);

        setJMenuBar(bar);

        addCustomer.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                if(checkInput(false))
                {
                    try
                    {
                        if (addCustomer(nameField.getText(), new Address(streetField.getText(), cityField.getText(), stateField.getText(), Integer.parseInt(zipField.getText())), Integer.parseInt(accountNumberField.getText())))
                            throw new CustomerAlreadyExistsException(new Customer(nameField.getText(), new Address(streetField.getText(), cityField.getText(), stateField.getText(), Integer.parseInt(zipField.getText())), Integer.parseInt(accountNumberField.getText())));
                    }
                    catch (CustomerAlreadyExistsException caee)
                    {
                        JOptionPane.showMessageDialog(null, "Customer already exists.");
                    }

                    clearFields();

                }
            }
        });

        addCourse.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                addCourse(new Customer(nameField.getText(), new Address(streetField.getText(), cityField.getText(), stateField.getText(), Integer.parseInt(zipField.getText())), Integer.parseInt(accountNumberField.getText())), courseList.get(courseBox.getSelectedIndex()));

                clearFields();
            }
        });

        generateInvoice.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                String invoices="";

                for(Customer c:customerList)
                    invoices+=c.createInvoice();

                JOptionPane.showMessageDialog(null,invoices);
            }
        });

        writeFiles.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                ObjectOutputStream customerOutput;
                ObjectOutputStream courseOutput;

                try
                {
                    customerOutput = new ObjectOutputStream(new FileOutputStream("customers.ser"));
                    courseOutput = new ObjectOutputStream(new FileOutputStream("courses.ser"));

                    for (Customer c : customerList)
                        customerOutput.writeObject(c);

                    for (Course c : courseList)
                        courseOutput.writeObject(c);
                }
                catch(IOException ioe)
                {
                    ioe.printStackTrace();
                }
            }
        });

        writeDatabase.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dbm.writeCustomerTable();
                System.out.println();
                dbm.writeCourseTable();
                System.out.println();
                dbm.writeCustomerCoursesTable();
                System.out.println();
            }
        });

        exitProgram.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                ObjectInputStream customerInput;
                ObjectInputStream courseInput;

                Customer cu;
                Course co;

                try
                {
                    customerInput=new ObjectInputStream(new FileInputStream("customers.ser"));

                    while(true)
                    {
                        cu=(Customer)customerInput.readObject();

                        System.out.println(cu.toString());
                    }
                }
                catch(EOFException eofe)
                {

                }
                catch(ClassNotFoundException cnfe)
                {
                    System.out.println("Class doesn't exist");
                }
                catch(IOException ioe)
                {
                    ioe.printStackTrace();
                }

                try
                {
                    courseInput=new ObjectInputStream(new FileInputStream("courses.ser"));

                    while(true)
                    {
                        co=(Course)courseInput.readObject();

                        System.out.println(co.toString());
                    }
                }
                catch(EOFException eofe)
                {

                }
                catch(ClassNotFoundException cnfe)
                {
                    System.out.println("Class doesn't exist");
                }
                catch(IOException ioe)
                {
                    ioe.printStackTrace();
                }

                System.exit(0);
            }
        });

        submitButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                if(checkInput(true))
                {
                    addCustomer(nameField.getText(), new Address(streetField.getText(), cityField.getText(), stateField.getText(), Integer.parseInt(zipField.getText())), Integer.parseInt(accountNumberField.getText()));
                    addCourse(new Customer(nameField.getText(), new Address(streetField.getText(), cityField.getText(), stateField.getText(), Integer.parseInt(zipField.getText())), Integer.parseInt(accountNumberField.getText())), courseList.get(courseBox.getSelectedIndex()));
                    clearFields();
                }
            }
        }
        );

        finishButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                String invoices="";

                for(Customer c:customerList)
                {
                    System.out.println(c);
                    System.out.println();
                    invoices+=c.createInvoice();
                }

                JOptionPane.showMessageDialog(null,invoices);

                System.exit(0);
            }
        }
        );

        add(nameLabel);
        add(nameField);
        add(streetLabel);
        add(streetField);
        add(cityLabel);
        add(cityField);
        add(stateLabel);
        add(stateField);
        add(zipLabel);
        add(zipField);
        add(accountNumberLabel);
        add(accountNumberField);
        add(customerTypeLabel);
        add(customerTypePanel);
        add(courseLabel);
        add(courseBox);
        add(submitLabel);
        add(submitButton);
        add(finishLabel);
        add(finishButton);
    }

    public static ArrayList<Course> readCourses(String fileName)
    {
        ArrayList<Course> tempCourseList=new ArrayList<Course>();
        Scanner input;
        Course tempCourse;
        String line;
        String values[];
        boolean repeated;

        tempCourseList.add(new Course());

        try
        {
            input=new Scanner(new File(fileName));

            while(input.hasNext())
            {
                repeated=false;
                line=input.nextLine();
                values=line.split(";");

                if (values[0].equals("Online"))
                {
                    tempCourse=new OnlineCourse(values[1],values[2],Double.parseDouble(values[3]),new Date(Integer.parseInt(values[4]),Integer.parseInt(values[5]),Integer.parseInt(values[6])),new Date(Integer.parseInt(values[7]),Integer.parseInt(values[8]),Integer.parseInt(values[9])),values[11],Boolean.parseBoolean(values[12]),Integer.parseInt(values[13]));
                }
                else if (values[0].equals("Inclass"))
                {
                    tempCourse=new InClassCourse(values[1],values[2],Double.parseDouble(values[3]),new Date(Integer.parseInt(values[4]),Integer.parseInt(values[5]),Integer.parseInt(values[6])),new Date(Integer.parseInt(values[7]),Integer.parseInt(values[8]),Integer.parseInt(values[9])),values[11],new Time(Integer.parseInt(values[12]),Integer.parseInt(values[13])),new Time(Integer.parseInt(values[14]),Integer.parseInt(values[15])));
                }
                else
                {
                    System.out.println("ERROR CourseGUI.readCourses: Must be Online or In Class");
                    tempCourse=new Course();
                }

                switch (values[10])
                {
                    case "programming":
                        tempCourse.setCType(Course.CourseType.PROGRAMMING);
                        break;

                    case "mathematics":
                        tempCourse.setCType(Course.CourseType.MATHEMATICS);
                        break;

                    case "photography":
                        tempCourse.setCType(Course.CourseType.PHOTOGRAPHY);
                        break;

                    case "music":
                        tempCourse.setCType(Course.CourseType.MUSIC);
                        break;

                    case "painting":
                        tempCourse.setCType(Course.CourseType.PAINTING);
                        break;

                    case "misc":
                        tempCourse.setCType(Course.CourseType.MISC);
                        break;

                    default:
                        System.out.println("ERROR CourseTest.readCourses: Course type invalid");
                }

                for(Course c:tempCourseList)
                    if (c.equals(tempCourse))
                        repeated=true;

                if (!repeated)
                    tempCourseList.add(tempCourse);
            }
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }

        return tempCourseList;
    }

    public boolean confirm(String name, Course co)
    {
        JFrame confirmFrame=new JFrame();
        int result;
        result=JOptionPane.showConfirmDialog(confirmFrame,String.format("%s\n\nAdd to customer %s?",co,name));

        if(result==JOptionPane.YES_OPTION)
            return true;
        return false;
    }

    public boolean addCustomer(String name, Address a, int accountNumber)
    {
        tempCustomer=new Customer(name,a,accountNumber);

        if (studentButton.isSelected())
            tempCustomer.setCType(Customer.CustomerType.STUDENT);
        else if (facultyButton.isSelected())
            tempCustomer.setCType(Customer.CustomerType.FACULTY);
        else if (governmentButton.isSelected())
            tempCustomer.setCType(Customer.CustomerType.GOVERNMENT);
        else
            System.out.println("ERROR: CourseGUI.addCustomer() cType");

        exists=dbm.customerSearch(tempCustomer);

        if (!exists)
        {
            customerIndex=customerList.size();
            customerList.add(tempCustomer);
            dbm.addCustomer(tempCustomer);
        }

        return exists;
    }


    public void addCourse(Customer cu, Course co)
    {
        if (confirm(cu.getName(),co)&&dbm.customerSearch(cu))
            //customerList.get(customerIndex).addCourse(co);
            dbm.addCourseToCustomer(cu,courseBox.getSelectedIndex());
    }

    public void clearFields()
    {
        nameField.setText("");
        streetField.setText("");
        cityField.setText("");
        stateField.setText("");
        zipField.setText("");
        accountNumberField.setText("");
        customerTypeGroup.clearSelection();
        courseBox.setSelectedIndex(0);
        nameField.requestFocus();
    }

    public boolean checkInput(boolean checkCourse)
    {
        if(nameField.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null,"Enter a name");
            nameField.requestFocus();
            return false;
        }

        else if(streetField.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Enter a street address");
            streetField.requestFocus();
            return false;
        }

        else if(cityField.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Enter a city");
            cityField.requestFocus();
            return false;
        }

        else if(stateField.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Enter a state");
            stateField.requestFocus();
            return false;
        }

        else if(zipField.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Enter a zip code");
            zipField.requestFocus();
            return false;
        }

        else if(accountNumberField.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Enter an account number");
            accountNumberField.requestFocus();
            return false;
        }

        else if(!studentButton.isSelected() && !facultyButton.isSelected() && !governmentButton.isSelected())
        {
            JOptionPane.showMessageDialog(null,"Select a customer type");
            return false;
        }

        else if(courseBox.getSelectedIndex()==0 && checkCourse)
        {
            JOptionPane.showMessageDialog(null,"Select a course");
            return false;
        }

        try
        {
            Integer.parseInt(zipField.getText());
        }
        catch(NumberFormatException nfe)
        {
            JOptionPane.showMessageDialog(null,"Enter an integer in the Zip field");
            zipField.setText("");
            zipField.requestFocus();
            return false;
        }

        try
        {
            Integer.parseInt(accountNumberField.getText());
        }
        catch(NumberFormatException nfe)
        {
            JOptionPane.showMessageDialog(null,"Enter an integer in the Account Number field");
            accountNumberField.setText("");
            accountNumberField.requestFocus();
            return false;
        }

        return true;
    }
}
