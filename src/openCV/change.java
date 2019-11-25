package openCV;

/**
 * @Auther: MGL
 * @Date: 2019/3/17 15:59
 * @Description:
 */
public class change {
    public native void changeVedio(int x,int y);

    static{
        System.loadLibrary("OPCV");
    }
}
