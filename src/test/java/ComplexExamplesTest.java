import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class ComplexExamplesTest {

    @Test
    void getNameGroupTest1() {
        ComplexExamples.Person[] people = new ComplexExamples.Person[]{
                new ComplexExamples.Person(1, "John"),
                new ComplexExamples.Person(2, "Isabel"),
                new ComplexExamples.Person(1, "John"),
                new ComplexExamples.Person(3, "John"),
                new ComplexExamples.Person(5, "Isabel"),
                new ComplexExamples.Person(6, "Isabel")
        };
        Map<String, Long> expected = new TreeMap<>() {
            {
                put("John", 2L);
                put("Isabel", 3L);
            }
        };
        Map<String, Long> result = ComplexExamples.getNameGroup(people);
        assertTrue(expected.equals(result));
    }

    @Test
    void getNameGroupTest2() {
        ComplexExamples.Person[] people = new ComplexExamples.Person[]{
                new ComplexExamples.Person(1, "john"),//тезка(Jonh 1)
                new ComplexExamples.Person(2, "Isabel"),//тезка (Isabel 1)
                new ComplexExamples.Person(9, "John"),//тезка (Jonh 2)
                new ComplexExamples.Person(9, "JOHN"),//дубликат
                new ComplexExamples.Person(3, "John"),//тезка (Jonh 3)
                new ComplexExamples.Person(1, "john"),//дубликат
                new ComplexExamples.Person(1, "JohN"),//дубликат
                new ComplexExamples.Person(5, "Isabel"),//тезка (Isabel 2)
                new ComplexExamples.Person(6, "isabel"),//тезка (Isabel 3)
                new ComplexExamples.Person(2, "IsaBel"),//дубликат
                new ComplexExamples.Person(2, "IsaBel"),//дубликат
                new ComplexExamples.Person(7, "IsaBel"),//тезка (Isabel 4)
                new ComplexExamples.Person(8, "joHN"),//тезка (Jonh 4)
                new ComplexExamples.Person(10, "JOHN"),//тезка (Jonh 5)
                new ComplexExamples.Person(12, "ISABEL"),//тезка (Isabel 51)
                new ComplexExamples.Person(12, "ISaBeL")//дубликат
        };
        Map<String, Long> expected = new TreeMap<>() {
            {
                put("John", 5L);
                put("Isabel", 5L);
            }
        };
        Map<String, Long> result = ComplexExamples.getNameGroup(people);
        assertTrue(expected.equals(result));
    }

    @Test
    void getNameGroupTest3() {
        ComplexExamples.Person[] people = new ComplexExamples.Person[]{
                new ComplexExamples.Person(1, "John"),
                new ComplexExamples.Person(2, "Isabel"),
                null,
                new ComplexExamples.Person(1, "John"),
                new ComplexExamples.Person(3, "John"),
                null,
                new ComplexExamples.Person(5, "Isabel"),
                null,
                new ComplexExamples.Person(6, "Isabel")
        };
        Map<String, Long> expected = new TreeMap<>() {
            {
                put("John", 2L);
                put("Isabel", 3L);
            }
        };
        Map<String, Long> result = ComplexExamples.getNameGroup(people);
        assertTrue(expected.equals(result));
    }

    @Test
    void getNameGroupTest4() {
        ComplexExamples.Person[] people = null;
        Map<String, Long> result = ComplexExamples.getNameGroup(people);
        assertTrue(result.isEmpty());
    }

    @Test
    void getNameGroupTest5() {
        ComplexExamples.Person[] people = new ComplexExamples.Person[]{
                null,
                null,
                null,
                null,
                null,
                null,

        };

        Map<String, Long> result = ComplexExamples.getNameGroup(people);
        assertTrue(result.isEmpty());
    }

    @Test
    void getNameGroupTest6() {
        ComplexExamples.Person[] people = new ComplexExamples.Person[]{
                new ComplexExamples.Person(1, "John"),
                new ComplexExamples.Person(2, "Isabel"),
                new ComplexExamples.Person(1, "John"),
                new ComplexExamples.Person(3, "John"),
                new ComplexExamples.Person(5, "Isabel"),
                new ComplexExamples.Person(6, "Isabel"),
                new ComplexExamples.Person(7, null),
                new ComplexExamples.Person(8, null),
                new ComplexExamples.Person(9, null)

        };
        Map<String, Long> expected = new TreeMap<>() {
            {
                put("John", 2L);
                put("Isabel", 3L);
            }
        };
        Map<String, Long> result = ComplexExamples.getNameGroup(people);
        assertTrue(expected.equals(result));
    }
}