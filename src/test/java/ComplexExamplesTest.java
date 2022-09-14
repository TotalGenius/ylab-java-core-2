import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Stream;

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

        List<ComplexExamples.Person> listJohn = new ArrayList<>();
        listJohn.add(new ComplexExamples.Person(1, "John"));
        listJohn.add(new ComplexExamples.Person(3, "John"));

        List<ComplexExamples.Person> listIsabel = new ArrayList<>();
        listIsabel.add(new ComplexExamples.Person(2, "Isabel"));
        listIsabel.add(new ComplexExamples.Person(5, "Isabel"));
        listIsabel.add(new ComplexExamples.Person(6, "Isabel"));

        listJohn.sort((x, y) -> x.getId() - y.getId());
        listIsabel.sort((x, y) -> x.getId() - y.getId());

        Map<String, List<ComplexExamples.Person>> expected = new TreeMap<>() {
            {
                put("John", listJohn);
                put("Isabel", listIsabel);
            }
        };

        Map<String, List<ComplexExamples.Person>> actual = ComplexExamples.getNameGroup(people);

        boolean result = true;

        if (expected.size() != actual.size()) {
            result = false;
        } else {
            Iterator<Map.Entry<String, List<ComplexExamples.Person>>> expectedIterator = expected.entrySet().iterator();
            Iterator<Map.Entry<String, List<ComplexExamples.Person>>> actualIterator = actual.entrySet().iterator();

            while (expectedIterator.hasNext() && actualIterator.hasNext()) {

                Map.Entry<String, List<ComplexExamples.Person>> expectedEntry = expectedIterator.next();
                Map.Entry<String, List<ComplexExamples.Person>> actualEntry = actualIterator.next();

                if (!expectedEntry.getKey().equals(actualEntry.getKey())) {
                    result = false;
                    break;
                }
                List<ComplexExamples.Person> expectedList = expectedEntry.getValue();
                List<ComplexExamples.Person> actualList = actualEntry.getValue();
                if (!expectedList.equals(actualList)) {
                    result = false;
                    break;
                }

            }
            assertTrue(result);
        }


        assertTrue(expected.equals(actual));
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
                new ComplexExamples.Person(12, "ISABEL"),//тезка (Isabel 5)
                new ComplexExamples.Person(12, "ISaBeL")//дубликат
        };

        List<ComplexExamples.Person> listJohn = new ArrayList<>();
        listJohn.add(new ComplexExamples.Person(1, "John"));
        listJohn.add(new ComplexExamples.Person(9, "John"));
        listJohn.add(new ComplexExamples.Person(8, "John"));
        listJohn.add(new ComplexExamples.Person(10, "John"));
        listJohn.add(new ComplexExamples.Person(3, "John"));

        List<ComplexExamples.Person> listIsabel = new ArrayList<>();
        listIsabel.add(new ComplexExamples.Person(2, "Isabel"));
        listIsabel.add(new ComplexExamples.Person(6, "Isabel"));
        listIsabel.add(new ComplexExamples.Person(5, "Isabel"));
        listIsabel.add(new ComplexExamples.Person(12, "Isabel"));
        listIsabel.add(new ComplexExamples.Person(7, "Isabel"));

        listJohn.sort((x, y) -> x.getId() - y.getId());
        listIsabel.sort((x, y) -> x.getId() - y.getId());

        Map<String, List<ComplexExamples.Person>> expected = new TreeMap<>() {
            {
                put("John", listJohn);
                put("Isabel", listIsabel);
            }
        };

        Map<String, List<ComplexExamples.Person>> actual = ComplexExamples.getNameGroup(people);

        boolean result = true;

        if (expected.size() != actual.size()) {
            result = false;
        } else {
            Iterator<Map.Entry<String, List<ComplexExamples.Person>>> expectedIterator = expected.entrySet().iterator();
            Iterator<Map.Entry<String, List<ComplexExamples.Person>>> actualIterator = actual.entrySet().iterator();
            while (expectedIterator.hasNext() && actualIterator.hasNext()) {

                Map.Entry<String, List<ComplexExamples.Person>> expectedEntry = expectedIterator.next();
                Map.Entry<String, List<ComplexExamples.Person>> actualEntry = actualIterator.next();

                if (!expectedEntry.getKey().equals(actualEntry.getKey())) {
                    result = false;
                    break;
                }
                List<ComplexExamples.Person> expectedList = expectedEntry.getValue();
                List<ComplexExamples.Person> actualList = actualEntry.getValue();
                if (!expectedList.equals(actualList)) {
                    result = false;
                    break;
                }

            }
            assertTrue(result);
        }


        assertTrue(expected.equals(actual));


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


        List<ComplexExamples.Person> listJohn = new ArrayList<>();
        listJohn.add(new ComplexExamples.Person(1, "John"));
        listJohn.add(new ComplexExamples.Person(3, "John"));

        List<ComplexExamples.Person> listIsabel = new ArrayList<>();
        listIsabel.add(new ComplexExamples.Person(2, "Isabel"));
        listIsabel.add(new ComplexExamples.Person(5, "Isabel"));
        listIsabel.add(new ComplexExamples.Person(6, "Isabel"));

        listJohn.sort((x, y) -> x.getId() - y.getId());
        listIsabel.sort((x, y) -> x.getId() - y.getId());

        Map<String, List<ComplexExamples.Person>> expected = new TreeMap<>() {
            {
                put("John", listJohn);
                put("Isabel", listIsabel);
            }
        };

        Map<String, List<ComplexExamples.Person>> actual = ComplexExamples.getNameGroup(people);

        boolean result = true;

        if (expected.size() != actual.size()) {
            result = false;
        } else {
            Iterator<Map.Entry<String, List<ComplexExamples.Person>>> expectedIterator = expected.entrySet().iterator();
            Iterator<Map.Entry<String, List<ComplexExamples.Person>>> actualIterator = actual.entrySet().iterator();
            while (expectedIterator.hasNext() && actualIterator.hasNext()) {

                Map.Entry<String, List<ComplexExamples.Person>> expectedEntry = expectedIterator.next();
                Map.Entry<String, List<ComplexExamples.Person>> actualEntry = actualIterator.next();

                if (!expectedEntry.getKey().equals(actualEntry.getKey())) {
                    result = false;
                    break;
                }
                List<ComplexExamples.Person> expectedList = expectedEntry.getValue();
                List<ComplexExamples.Person> actualList = actualEntry.getValue();
                if (!expectedList.equals(actualList)) {
                    result = false;
                    break;
                }

            }
            assertTrue(result);
        }


        assertTrue(expected.equals(actual));

    }

    @Test
    void getNameGroupTest4() {
        ComplexExamples.Person[] people = null;
        Map<String, List<ComplexExamples.Person>> result = ComplexExamples.getNameGroup(people);
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

        Map<String, List<ComplexExamples.Person>> result = ComplexExamples.getNameGroup(people);
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


        List<ComplexExamples.Person> listJohn = new ArrayList<>();
        listJohn.add(new ComplexExamples.Person(1, "John"));
        listJohn.add(new ComplexExamples.Person(3, "John"));

        List<ComplexExamples.Person> listIsabel = new ArrayList<>();
        listIsabel.add(new ComplexExamples.Person(2, "Isabel"));
        listIsabel.add(new ComplexExamples.Person(5, "Isabel"));
        listIsabel.add(new ComplexExamples.Person(6, "Isabel"));

        listJohn.sort((x, y) -> x.getId() - y.getId());
        listIsabel.sort((x, y) -> x.getId() - y.getId());

        Map<String, List<ComplexExamples.Person>> expected = new TreeMap<>() {
            {
                put("John", listJohn);
                put("Isabel", listIsabel);
            }
        };

        Map<String, List<ComplexExamples.Person>> actual = ComplexExamples.getNameGroup(people);

        boolean result = true;

        if (expected.size() != actual.size()) {
            result = false;
        } else {
            Iterator<Map.Entry<String, List<ComplexExamples.Person>>> expectedIterator = expected.entrySet().iterator();
            Iterator<Map.Entry<String, List<ComplexExamples.Person>>> actualIterator = actual.entrySet().iterator();
            while (expectedIterator.hasNext() && actualIterator.hasNext()) {

                Map.Entry<String, List<ComplexExamples.Person>> expectedEntry = expectedIterator.next();
                Map.Entry<String, List<ComplexExamples.Person>> actualEntry = actualIterator.next();

                if (!expectedEntry.getKey().equals(actualEntry.getKey())) {
                    result = false;
                    break;
                }
                List<ComplexExamples.Person> expectedList = expectedEntry.getValue();
                List<ComplexExamples.Person> actualList = actualEntry.getValue();
                if (!expectedList.equals(actualList)) {
                    result = false;
                    break;
                }

            }
            assertTrue(result);
        }


        assertTrue(expected.equals(actual));

    }

    @Test
    void getFirstSumGroupTest1() {
        int[] array = {1, 5, 9, 7, 12, 10};
        int target = 17;
        int[] actual = ComplexExamples.getFirstSumGroup(array, target);
        int[] expected = {5, 12};
        assertArrayEquals(expected, actual);
    }

    @Test
    void getFirstSumGroupTest2() {
        int[] array = {1};
        int target = 7;
        int[] actual = ComplexExamples.getFirstSumGroup(array, target);
        assertTrue(actual.length == 0);
    }

    @Test
    void getFirstSumGroupTest3() {
        int[] array = null;
        int target = 7;
        int[] actual = ComplexExamples.getFirstSumGroup(array, target);
        assertTrue(actual.length == 0);
    }

    @Test
    void getAllSumGroupTest1() {
        int[] array = {1, 5, 9, 7, 12, 10, 16};
        int target = 17;
        int[] expected = {5,12,7,10,1,16};
        List<int[]> list = ComplexExamples.getAllSumGroup(array, target);
        int[] actual = list.stream().flatMapToInt(x -> Arrays.stream(x)).toArray();
        assertArrayEquals(expected, actual);
    }

    @Test
    void getAllSumGroupTest2() {
        int[] array = {3};
        int target = 9;
        List<int[]> actual = ComplexExamples.getAllSumGroup(array, target);
        assertTrue(actual.isEmpty());
    }

    @Test
    void getAllSumGroupTest3() {
        int[] array = null;
        int target = 9;
        List<int[]> actual = ComplexExamples.getAllSumGroup(array, target);
        assertTrue(actual.isEmpty());
    }

    @Test
    void fuzzySearchTest1() {
        boolean actual = ComplexExamples.fuzzySearch("hello", "e#h!_&^sedaflhedhYlyo");
        assertTrue(actual);
    }

    @Test
    void fuzzySearchTest2() {
        boolean actual = ComplexExamples.fuzzySearch("world", "w, phf!2@doghnrhldjfdksh");
        assertTrue(actual);
    }

    @Test
    void fuzzySearchTest3() {
        boolean actual = ComplexExamples.fuzzySearch("car", "d#132afjlr");
        assertFalse(actual);
    }
}