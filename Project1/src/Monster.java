import java.util.Random;
import java.util.Scanner;

public class Monster {
    private String image = "\uD83E\uDDDF\u200D";
    private final int x, y;
    protected Random r = new Random();

    public Monster(int sizeBoard) {
        this.y = r.nextInt(sizeBoard);
        this.x = r.nextInt(sizeBoard);
    }

    public String getImage() {
        return image;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public boolean conflictPerson(int perX, int perY) {
        return perY - 1 == this.y && perX - 1 == this.x;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean taskMonster(int difficultGame) {
        System.out.println("Решите задачу:");
        int x = r.nextInt(100);
        int y = r.nextInt(100);
        int trueAnswer = x + y;
        System.out.println("Реши пример: " + x + " + " + y + " = ?");

        return checkAnswer(trueAnswer);
    }

    protected boolean checkAnswer(int trueAnswer) {
        Scanner sc = new Scanner(System.in);
        int ans;

        while (true) {
            if (sc.hasNextInt()) {
                ans = sc.nextInt();
                break;
            } else {
                System.out.println("Некорректный ввод! Введите число:");
                sc.next();
            }
        }

        if (trueAnswer == ans) {
            System.out.println("Верно! Ты победил монстра");
            return true;
        }
        System.out.println("Ты проиграл эту битву!");
        return false;
    }
}