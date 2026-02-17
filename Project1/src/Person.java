import java.util.Random;

public class Person {
    protected int x, y;
    private String image = "\uD83E\uDDD9\u200D";
    private int live = 3;
    private Random r = new Random();

    public Person(int sizeBoard) {
        this.y = sizeBoard;
        int n = r.nextInt(sizeBoard);
        this.x = n == 0 ? 1 : n;
    }

    public Person(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Person() {
        this(1, 1);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLive() {
        return live;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean moveCorrect(int x, int y) {
        return this.x == x && Math.abs(this.y - y) == 1 || this.y == y && Math.abs(this.x - x) == 1;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void downLive() {
        live--;
        System.out.println("💔 Ты потерял жизнь! Осталось жизней: " + live);
    }

    public void addLive() {
        live++;
        System.out.println("❤️ Ты получил дополнительную жизнь! Теперь жизней: " + live);
    }
}