
public class EmployeeBuilder {
	
    private int employeeId;
    private String pps,firstName, surname, department;
    private char gender;
    private double salary;
    private boolean fullTime;

    public EmployeeBuilder() {
    	
    }

    //the employee id 
    public EmployeeBuilder id(int employeeId) {
        
    	this.employeeId = employeeId;
        return this;
    	}
    
    //employees pps
    public EmployeeBuilder pps(String pps) {
        this.pps = pps;
        return this;
    	}

    //the surname
    public EmployeeBuilder surname(String surname) {
        this.surname = surname;
        return this;
    	
    	}
    
    //first name
    public EmployeeBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    	}
   
    //gender 
    public EmployeeBuilder gender(char gender) {
        this.gender = gender;
        return this;
    	}

    //department
    public EmployeeBuilder department(String department) {
        this.department = department;
        return this;
    	}
    
    //salary 
    public EmployeeBuilder salary(double salary) {
        this.salary = salary;
        return this;
    	}

    //fulltime 
    public EmployeeBuilder fullTime(boolean fullTime) {
        this.fullTime = fullTime;
        return this;
    	}
   //new employee object returned
    public Employee build() {
    	return new Employee(employeeId, pps, surname, firstName, gender, department, salary, fullTime);
    	
    }
 }