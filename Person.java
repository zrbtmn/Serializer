@XMLObject
public class Person {

    @XMLTag(name = "Name")
    private final String name;

    @XMLAttribute(tag = "Name", name = "Man")
    private final String sex;

    @XMLTag
    private final Work work = new Work();

    final int age;


    @XMLTag
    private final String test = "test";


    public Person(String name, String sex, int age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public String getName() {
        return name;
    }
    @XMLTag
    public int getAge() {
        return age;
    }
}
