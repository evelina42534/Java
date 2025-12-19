public class Main {
    public static void main(String[] args) {

        System.out.println("MyArrayList:");
        MyArrayList<Integer> arrayList = new MyArrayList<>();
        for (int i = 0; i < 20; i++) {
            arrayList.add(i);
        }

        MyIterator<Integer> it1 = arrayList.iterator();
        while (it1.hasNext()) {
            System.out.print(it1.next() + " ");
        }
        System.out.println();
        System.out.println("Размер массива: " + arrayList.size());

        System.out.println();
        System.out.println("MyLinkedList:");
        MyLinkedList<String> linkedList = new MyLinkedList<>();
        linkedList.add("A");
        linkedList.add("B");
        linkedList.add("C");

        MyIterator<String> it2 = linkedList.iterator();
        while (it2.hasNext()) {
            System.out.print(it2.next() + " ");
        }
        System.out.println();
        System.out.println("Размер списка: " + linkedList.size());
    }
}
