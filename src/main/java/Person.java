/**
 * @author Tomasz Lelek
 * @since 2014-04-18
 */
//example domain object to send to orchestrate
public class Person {
    public Person() {
    }

    private String name;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {

        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Integer age;


}
