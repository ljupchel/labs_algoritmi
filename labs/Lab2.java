import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Lab2 {
    public static class Product {

        public String name;
        public double price;


        Product(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public double finalPrice() {
            return price;
        }

        @Override
        public String toString() {
            return String.format("%s price=%.2f final=%.2f", name, price, finalPrice());
        }
    }

    static class Food extends Product {

        public int daysToExpiry;

        Food(String name, double price, int daysToExpiry) {
            super(name, price);
            this.daysToExpiry = daysToExpiry;
        }

        @Override
        public double finalPrice() {
            if (daysToExpiry <= 3) {
                return price * 0.5;
            } else {
                return price;
            }
        }

        @Override
        public String toString() {
            return String.format("%s price=%.2f final=%.2f days=%d", name, price, finalPrice(), daysToExpiry);
        }
    }

    static class Electronics extends Product {
        private int warantyMonths;

        Electronics(String name, double price, int warantyMonths) {
            super(name, price);
            this.warantyMonths = warantyMonths;
        }

        @Override
        public double finalPrice() {
            double warantyPrice = price;
            if (warantyMonths > 24) {
                return warantyPrice *= 1.15;
            } else {
                return finalPrice();
            }
        }

        @Override
        public String toString() {
            return String.format("%s price=%.2f final=%.2f warranty=%d", name, price, finalPrice(), warantyMonths);
        }
    }

    static class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

    static class Container<T extends Product> {
        public String name;
        public int size;
        public List<T> items = new ArrayList<>();


        public Container(String name, int size, List<T> items) {
            this.name = name;
            this.size = size;
            this.items = items;
        }

        //TODO
        //boolean add(T item) – додава производ; враќа false ако контејнерот е полн
        public boolean add(T item) {
            if (items.size() > this.size) {
                items.add(item);
                return true;
            } else {
                return false;
            }
        }

        public int size() {
            return items.size();
        }

        public double totalValue() {
            double sum = 0;
            for (T item : items) {
                sum += item.finalPrice();
            }
            return sum;
        }

        //TODO
        //T mostExpensive() – производот со највисока крајна цена; при еднаквост се враќа првиот, а ако контејнерот е празен се враќа null
        T mostExpensive() {
            double max = 0;
            int index = 0;
            for (int i = 0; i < size(); i++) {
                if (max < items.get(i).finalPrice()) {
                    max = items.get(i).finalPrice();
                    index = i;
                }
            }
            return items.get(index);
        }

        Pair<String, Double> summary() {
            return new Pair<>(name, totalValue());
        }

        //TODO
        //печати === NAME (SIZE/CAPACITY) ===, па сите производи, па Total: X, па Most expensive: NAME (PRICE)или Most expensive: none
        void print() {
            System.out.printf("=== %s (%d/%d) ===\n", name, size, size());
        }
    }

    public static void main(String[]args) {

        Scanner scanner = new Scanner(System.in);
        int fridgeCount = scanner.nextInt();
        int techCount = scanner.nextInt();
        int overflowCount = scanner.nextInt();
        Container<Food> fridge = new Container<>("Fridge", fridgeCount, new ArrayList<>());
        Container<Electronics> tech = new Container<>("Tech", techCount, new ArrayList<>());
        Container<Product> overflow = new Container<>("Overflow", overflowCount, new ArrayList<>());
        int n = scanner.nextInt();

        for (int i = 0; i < n; i++) {
            String type = scanner.next();
            String name = scanner.next();
            double price = scanner.nextDouble();
            if (type.equals("F")) {
                int daysToExpiry = scanner.nextInt();
                Food food = new Food(name, price, daysToExpiry);
                fridge.add(food);
            } else if (type.equals("E")) {
                int waranty = scanner.nextInt();
                Electronics electronics = new Electronics(name, price, waranty);
                tech.add(electronics);
            } else {
                System.out.println("Overflow");
            }
        }
        fridge.print();
        tech.print();
    }



}
