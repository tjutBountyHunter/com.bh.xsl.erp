package Utils;

/**
 * 说明：
 *
 * @Auther: 11432_000
 * @Date: 2018/10/3 09:47
 * @Description:
 */
public class RandomUtils {

    public static int getNumberBetweenZeroAndTen(){
        return (int) (Math.random()*10);
    }

    public static int getNumberBetweenTwentyAndHundred(){
        int i = (int) (Math.random() * 100);
        return i < 80 ? i + 20 : i;
    }

    public static int getNumberBetweenZeroAndHundred(){
        return (int) (Math.random() * 100);
    }

    public static int getNumberBetweenZeroAndFive(){
        short i = (short) (Math.random() * 5);
        return i < 1 ? 1: i;
    }

    public static int getExperienceValue(int level){
        switch (level){
            case 1:
                return (int) (Math.random() * 500);
            case 2:
                return (int) (Math.random() * 1000);
            case 3:
                return (int) (Math.random() * 1500);
            case 4:
                return (int) (Math.random() * 2000);
            case 5:
                return (int) (Math.random() * 2500);
        }
        return 0;
    }
}
