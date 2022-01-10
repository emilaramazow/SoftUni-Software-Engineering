package L02Encapsulation.Lab;

public class Person {
    private String firstName;
    private String lastName;
    private int age;
    private double salary;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public double getSalary() {
        return salary;
    }

    public Person(String firstName, String lastName, int age, double salary) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAge(age);
        this.setSalary(salary);

    }

    private void setSalary(double salary) {
        if (salary < 460) {
            throw new IllegalArgumentException("Salary cannot be less than 460 leva");
        }
        this.salary = salary;
    }

    public void setAge(int age) {
        if (age <= 0) {
            throw new IllegalArgumentException("Age cannot be zero or negative integer");
        }
        this.age = age;
    }

    public void setLastName(String lastName) {
        ensureNameLength(lastName, "Last");

        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        ensureNameLength(firstName, "First");

        this.firstName = firstName;
    }

    public void ensureNameLength (String nameParameter, String messagePrefix) {
        if (nameParameter.length() < 3) {
            throw new IllegalArgumentException(messagePrefix + " name cannot be less than 3 symbols");
        }

        this.firstName = firstName;
    }

    public void increaseSalary (double bonus) {
        if (this.getAge() < 30) {
            bonus /= 2;
        }
        this.setSalary(this.salary *= (1.0 + bonus / 100));
    }

    @Override
    public String toString() {
        return String.format("%s %s gets %s leva", this.getFirstName(), this.getLastName(), this.getSalary());
    }


}