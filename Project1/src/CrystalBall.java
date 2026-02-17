import java.util.Random;
import java.util.Scanner;

public class CrystalBall {
    private String image = "\uD83D\uDD2E";
    private final int x, y;
    private boolean isActive = true;
    private Random r = new Random();

    public CrystalBall(int sizeBoard) {
        this.y = r.nextInt(sizeBoard);
        this.x = r.nextInt(sizeBoard);
    }

    public String getImage() {
        return isActive ? image : "  ";
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public boolean isActive() {
        return isActive;
    }

    public void deactivate() {
        isActive = false;
    }

    public boolean conflictPerson(int perX, int perY) {
        return isActive && perY - 1 == this.y && perX - 1 == this.x;
    }

    public boolean useBall(int difficultGame, Person person) {
        System.out.println("✨ Ты нашел хрустальный шар! Реши пример, чтобы получить дополнительную жизнь:");

        int x, y, z, trueAnswer;

        if (difficultGame == 1) {
            x = r.nextInt(100);
            y = r.nextInt(100);
            trueAnswer = x + y;
            System.out.println("Реши пример: " + x + " + " + y + " = ?");
        } else {
            x = r.nextInt(10 * (difficultGame - 1), 10 * difficultGame);
            y = r.nextInt(10 * (difficultGame - 1), 10 * difficultGame);
            z = r.nextInt(100 * (difficultGame - 1), 100 * difficultGame);
            trueAnswer = x * y - z;
            System.out.println("Реши пример: " + x + " * " + y + " - " + z + " = ?");
        }

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
            System.out.println("Верно! Хрустальный шар дарит тебе жизнь!");
            person.addLive();
            return true;
        } else {
            System.out.println("Неверно! Хрустальный шар исчезает...");
            return false;
        }
    }
}