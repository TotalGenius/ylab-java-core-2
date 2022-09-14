import javax.crypto.spec.PSource;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


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
        System.out.println("Task 1:");
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

        Map<String, List<Person>> map = getNameGroup(RAW_DATA);

        if (map.isEmpty()) {
            System.out.println("Map is empty");
        } else {
            for (Map.Entry<String, List<Person>> entry : map.entrySet()) {
                System.out.println(entry.getKey() + ":");
                List<Person> people = entry.getValue();
                int i = 1;
                for (Person person : people) {
                    System.out.println(i + " - " + person.getName() + " (" + person.getId() + ")");
                    i++;
                }
            }
            System.out.println("**************************************************");
            map.entrySet().forEach(element -> System.out.println("key:" + element.getKey() + "\n" + "value:" + element.getValue().size()));
        }

        System.out.println();
        /*
        Task2

            [3, 4, 2, 7], 10 -> [3, 7] - вывести пару именно в скобках, которые дают сумму - 10
         */
        System.out.println("Task 2:");
        //Вывод первой пары
        int[] array1 = {3, 4, 2, 7};
        int target = 10;
        System.out.println("Вывод первой пары чисел из массива, дающих в сумме " + target + ":");
        int[] firstPair = getFirstSumGroup(array1, target);
        System.out.print(Arrays.toString(array1) + ", " + target + " -> ");
        if (firstPair.length == 0) System.out.println("Решений нет");
        else System.out.println(Arrays.toString(firstPair));
        System.out.println();

        //Вывод всех пар
        int[] array2 = {3, 4, 6, 1, 9, 2, 7};
        int target2 = 10;
        System.out.println("Вывод всех пар чисел, дающих в сумме " + target2 + ":");
        List<int[]> list = getAllSumGroup(array2, target2);
        System.out.print(Arrays.toString(array2) + ", " + target2 + " -> ");
        if (list.isEmpty()) System.out.println("Решений нет");
        else list.forEach(x -> System.out.print(Arrays.toString(x) + " "));
        System.out.println();
        System.out.println();
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
        System.out.println("Task 3:");

        System.out.println("строка поиска: car, строка данных: ca6$$#_rtwheel");
        System.out.println(fuzzySearch("car", "ca6$$#_rtwheel"));
        System.out.println();

        System.out.println("строка поиска: cwhl, строка данных: cartwheel");
        System.out.println(fuzzySearch("cwhl", "cartwheel"));
        System.out.println();

        System.out.println("строка поиска: cwhee, строка данных: cartwheel");
        System.out.println(fuzzySearch("cwhee", "cartwheel"));
        System.out.println();

        System.out.println("строка поиска: cartwheel, строка данных: cartwheel");
        System.out.println(fuzzySearch("cartwheel", "cartwheel"));
        System.out.println();

        System.out.println("строка поиска: cwheeel, строка данных: cartwheel");
        System.out.println(fuzzySearch("cwheeel", "cartwheel"));
        System.out.println();

        System.out.println("строка поиска: lw, строка данных: cartwheel");
        System.out.println(fuzzySearch("lw", "cartwheel"));
        System.out.println();


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
    Проверяем, чтобы значение в массиве не равнялось null и чтобы "name" у объекта не было равно null
     */
    private static Predicate<Person> checkPerson = x -> x != null && x.getName() != null;

    //Получаем Map, где key = Person name, value = лист тезок
    public static Map<String, List<Person>> getNameGroup(Person[] people) {
        //Если пришел null, возвращаем пустой Map
        if (people == null) return Collections.EMPTY_MAP;
        Map<String, List<Person>> nameGroup = Arrays.stream(people)
                .filter(checkPerson) //избавляемся от null значений внутри массива
                .map(getNormalCaseNames)//Приводим имя к нормальному состоянию (первая буква с большой, остальные с маленькой)
                .distinct() // избавляемся от дубликатов
                .collect(Collectors.groupingBy(Person::getName, TreeMap::new, Collectors.toList()));// Получаем TreeMap, где key = имя, value = лист тезок
        //Сортируем каждый лист в Map по id
        nameGroup.entrySet().forEach(x -> x.getValue().sort((person1, person2) -> person1.getId() - person2.getId()));
        return nameGroup;
    }

    //Task2
    //Находим первую пару чисел, чья сумма дает в результате входное число
    public static int[] getFirstSumGroup(int[] array, int target) {
        //Если передан null или массив, размером меньше 2, возвращаем пустой лист
        if (array == null || array.length < 2) return new int[]{};
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            int value = array[i];
            int key = target - value;
            if (map.containsKey(value)) return new int[]{map.get(value), value};
            else map.put(key, value);
        }
        //Если пара чисел не найдена, возвращаем пустой массив
        return new int[]{};
    }

    //Находим все пары чисел, чьи суммы в результате дают входное число
    public static List<int[]> getAllSumGroup(int[] array, int target) {
        //Если передан null или массив, размером меньше 2, возвращаем пустой лист
        if (array == null || array.length < 2) return Collections.EMPTY_LIST;
        List<int[]> sumPairs = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            int value = array[i];
            int key = target - value;
            if (map.containsKey(value)) sumPairs.add(new int[]{map.get(value), value});
            else map.put(key, value);
        }
        return sumPairs;
    }

    //Task3
    public static boolean fuzzySearch(String search, String data) {
        if (search == null || data == null) return false;
        int hitCount = 0;//Количество совпадений (если есть сопадение по символам строк поиска и данных, счетчик увеличивается на 1)
        int symbolAmount = search.length();//количество символов искомой строки
        //Получим массив символов из строки поиска и строки данных
        char[] searchStrChars = search.toCharArray();
        char[] dataStrChars = data.toCharArray();
        /*
        Пробегаемся по массиву символов поиска
        j объявим в начале внешнего цикла, чтобы новый символ строки поиска начинал сравнение
        с символом строки данных на котором мы остановились.
         */
        for (int i = 0, j = 0; i < searchStrChars.length; i++) {
            while (j < dataStrChars.length) {
                /*
                если количество непроверенных символов в строке данных меньше чем
                количество символов которые нужно сравнить, чтобы hitCount == symbolAmount,
                завершаем работу метода, возвращаем false
                 */
                if (data.substring(j).length() < (symbolAmount - hitCount)) return false;
                /*
                Если не найдено ни одно соответствие и при этом количество
                непроверенных символов меньше длины строки поиска,
                завершаем работу метода, возвращаем false
                 */
                if (hitCount == 0 && data.substring(j).length() < search.length()) return false;
                //Если найдено совпадение
                if (searchStrChars[i] == dataStrChars[j]) {
                    hitCount++;//Увеличиваем количество совпадений
                    j++;
                    break;
                }
                j++;
            }
            /*
            Если количество совпадений равно длине строки поиска
            Завершаем работу метода, возвращаем true
             */
            if (symbolAmount == hitCount) return true;
        }
        //Строка поиска не найдена в строке данных
        return false;
    }
}
