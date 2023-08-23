package other.generics;

public class TypeTest2<T> extends TypeTest<T, Integer> {
    public <X extends Integer> TypeTest2(X x, T t) {
        super(x, t);
    }

    public static void main(String[] args) {
        TypeTest2<String> test = new TypeTest2<>(1, "");
        System.out.println(test.test());

        TypeTest2<? super Integer> test2 = new TypeTest2<Integer>(1, 2);
        test2.test2(new TypeTest2<>(1, 2));
    }

    @SuppressWarnings("unchecked")
    public <TT extends String> TT test() {
        return (TT) "hello world";
    }

    public <TT extends Number> void test2(TypeTest2<TT> typeTest2) {
        System.out.println("hello");
    }
}
