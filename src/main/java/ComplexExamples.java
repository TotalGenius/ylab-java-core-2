import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class ComplexExamples {

    static class Person {
        final int id;

        final String name;

        Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person person)) return false;
            return getId() == person.getId() && getName().equals(person.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getName());
        }
    }

    private static Person[] RAW_DATA = new Person[]{
            new Person(0, "Harry"),
            new Person(0, "Harry"), // дубликат
            new Person(1, "Harry"), // тёзка
            new Person(2, "Harry"),
            new Person(3, "Emily"),
            new Person(4, "Jack"),
            new Person(4, "Jack"),
            new Person(5, "Amelia"),
            new Person(5, "Amelia"),
            new Person(6, "Amelia"),
            new Person(7, "Amelia"),
            new Person(8, "Amelia"),
    };

    /*  Raw data:

    0 - Harry
    0 - Harry
    1 - Harry
    2 - Harry
    3 - Emily
    4 - Jack
    4 - Jack
    5 - Amelia
    5 - Amelia
    6 - Amelia
    7 - Amelia
    8 - Amelia

    **************************************************

    Duplicate filtered, grouped by name, sorted by name and id:

    Amelia:
    1 - Amelia (5)
    2 - Amelia (6)
    3 - Amelia (7)
    4 - Amelia (8)
    Emily:
    1 - Emily (3)
    Harry:
    1 - Harry (0)
    2 - Harry (1)
    3 - Harry (2)
    Jack:
    1 - Jack (4)
 */

    public static void main(String[] args) {
        System.out.println("Raw data:");
        System.out.println();

        for (Person person : RAW_DATA) {
            System.out.println(person.id + " - " + person.name);
        }

        System.out.println();
        System.out.println("**************************************************");
        System.out.println();
        System.out.println("Duplicate filtered, grouped by name, sorted by name and id:");
        System.out.println();

        /*
        Task1
            Убрать дубликаты, отсортировать по идентификатору, сгруппировать по имени

            Что должно получиться
                Key: Amelia
                Value:4
                Key: Emily
                Value:1
                Key: Harry
                Value:3
                Key: Jack
                Value:1
         */

        Map<String, Long> map = getNameGroup(RAW_DATA);
        if (map.isEmpty()) {
            System.out.println("Map is empty");
        } else {
            map.entrySet().forEach(element -> System.out.println("key:" + element.getKey() + "\n" + "value:" + element.getValue()));
        }

        /*
        Task2

            [3, 4, 2, 7], 10 -> [3, 7] - вывести пару менно в скобках, которые дают сумму - 10
         */



        /*
        Task3
            Реализовать функцию нечеткого поиска

                    fuzzySearch("car", "ca6$$#_rtwheel"); // true
                    fuzzySearch("cwhl", "cartwheel"); // true
                    fuzzySearch("cwhee", "cartwheel"); // true
                    fuzzySearch("cartwheel", "cartwheel"); // true
                    fuzzySearch("cwheeel", "cartwheel"); // false
                    fuzzySearch("lw", "cartwheel"); // false
         */


    }

    //Task1

    /*
        getNormalCaseNames - нужен для приведения имен и в номарльную форму.
        т.е. если в массиве содержаться имена "Harry", "harry", "HaRRy" - в Map
        они не должны отображаться как разные ключи.

        Если в контексте нашей задачи такое не подразумевается, то убираем метод "map"
     */
    private static Function<Person, Person> getNormalCaseNames = x -> new Person(x.getId(),
            x.getName().substring(0, 1).toUpperCase() + x.getName().substring(1).toLowerCase());

    /*
    Проверяем, чтобы значение в массиве не равнялось null и, чтобы "name" у объекта не было равно null
     */
    private static Predicate<Person> checkPerson = x -> x != null && x.getName() != null; //

    public static Map<String, Long> getNameGroup(Person[] people) {
        if (people == null) return Collections.EMPTY_MAP;

        Map<String, Long> nameGroup = Arrays.stream(people)
                .filter(checkPerson) //избавляемся от null значений внутри массива
                .map(getNormalCaseNames)
                .distinct() // избавляемся от дубликатов
                .collect(Collectors.groupingBy(Person::getName, TreeMap::new, Collectors.counting()));// Получаем Map, где key = имя, value = количесвто тезок
        return nameGroup;
    }

}
