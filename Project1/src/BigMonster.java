import java.util.Scanner;

public class BigMonster extends Monster {
    private String image = "\uD83D\uDC79";

    public BigMonster(int sizeBoard) {
        super(sizeBoard);
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean taskMonster(int difficultGame) {
        System.out.println("Решите задачу (СЛОЖНЫЙ МОНСТР):");

        if (difficultGame == 1) {
            return super.taskMonster(difficultGame);
        } else {
            int x = r.nextInt(10 * (difficultGame - 1), 10 * difficultGame);
            int y = r.nextInt(10 * (difficultGame - 1), 10 * difficultGame);
            int z = r.nextInt(100 * (difficultGame - 1), 100 * difficultGame);
            int trueAnswer = x * y - z;

            System.out.println("Реши пример: " + x + " * " + y + " - " + z + " = ?");
            return checkAnswer(trueAnswer);
        }
    }
}