package test;

/**
 * @Auther: MGL
 * @Date: 2019/3/22 20:40
 * @Description:
 */
@SuppressWarnings("All")
public class ThreadTest1 extends Thread{
    private final String name;

    public ThreadTest1(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        super.run();
        for (int i = 0; i < 100; i++)
            System.err.println(name + "\t" + i);
    }

    public static void main(String[] args) throws Exception {
        ThreadTest1 t = new ThreadTest1("t1");
        ThreadTest1 t2 = new ThreadTest1("t2");
        t.start();
        t.join();
        t2.start();
    }

}
