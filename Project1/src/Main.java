import java.util.Random;
import java.util.Scanner;

// === Класс Person ===
class Person {
    protected int x, y;
    private String image = "\uD83E\uDDD9\u200D";
    private int live = 3;
    Random r = new Random();

    Person(int sizeBoard) {
        y = sizeBoard;
        int n = r.nextInt(sizeBoard);
        x = n == 0 ? 1 : n;
    }

    Person(int x, int y){
        this.x = x;
        this.y = y;
    }
    Person(){
        this(1, 1);
    }

    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLive() {
        return live;
    }

    public String getImage(){
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean moveCorrect(int x, int y){
        return this.x == x && Math.abs(this.y - y) == 1 || this.y == y && Math.abs(this.x - x) == 1;
    }

    void move(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void downLive(){
        live--;
    }

    public void addLive(){
        live++;
        System.out.println("❤️ Ты получил дополнительную жизнь! Теперь жизней: " + live);
    }
}

// === Класс Monster ===
class Monster {
    private String image = "\uD83E\uDDDF\u200D";
    private final int x, y;
    Random r = new Random();

    Monster(int sizeBoard){
        this.y = r.nextInt(sizeBoard - 1);
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

    public boolean conflictPerson(int perX, int perY){
        return perY - 1 == this.y && perX - 1 == this.x;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean taskMonster(int difficultGame){
        System.out.println("Решите задачу:");
        int x = r.nextInt(100);
        int y = r.nextInt(100);
        int trueAnswer = x + y;
        System.out.println("Реши пример: " + x + " + " + y + " = ?");
        Scanner sc = new Scanner(System.in);
        int ans = sc.nextInt();
        if (trueAnswer == ans) {
            System.out.println("Верно! Ты победил монстра");
            return true;
        }
        System.out.println("Ты проиграл эту битву!");
        return false;
    }
}

// === Класс BigMonster (наследник Monster) ===
class BigMonster extends Monster{

    private String image = "\uD83D\uDC79";

    BigMonster(int sizeBoard) {
        super(sizeBoard);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // переопределим метод:
    @Override
    public boolean taskMonster(int difficultGame){
        System.out.println("Решите задачу:");
        if (difficultGame == 1){
            return taskMonster();
        }else {
            int x = r.nextInt(10 * (difficultGame - 1), 10 * difficultGame);
            int y = r.nextInt(10 * (difficultGame - 1), 10 * difficultGame);
            int z = r.nextInt(100 * (difficultGame - 1), 100 * difficultGame);
            int trueAnswer = x * y - z;
            System.out.println("Реши пример: " + x + " * " + y + " - " + z + " = ?");
            Scanner sc = new Scanner(System.in);
            int ans = sc.nextInt();
            if (trueAnswer == ans) {
                System.out.println("Верно! Ты победил монстра");
                return true;
            }
            System.out.println("Ты проиграл эту битву!");
            return false;
        }

    }

    public boolean taskMonster() {
        return super.taskMonster(0);
    }
}

// === Класс CrystalBall (хрустальный шар) ===
class CrystalBall {
    private String image = "\uD83D\uDD2E";
    private final int x, y;
    private boolean isActive = true;
    Random r = new Random();

    CrystalBall(int sizeBoard) {
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
        int ans = sc.nextInt();

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

// === Главный класс Main с методом main ===
public class Main {
    public static void main(String[] args) {

        String castle = "\uD83C\uDFF0";
        int sizeBoard = 5;

        Person person = new Person(sizeBoard);

        int step = 0;

        String[][] board = new String[sizeBoard][sizeBoard];
        for (int y = 0; y < sizeBoard; y++) {
            for (int x = 0; x < sizeBoard; x++) {
                board[y][x] = "  ";
            }
        }

        int countMonster = sizeBoard * sizeBoard - sizeBoard - 5;
        Random r = new Random();

        Monster[] arrMonster = new Monster[countMonster + 1];
        int count = 0;
        Monster test;
        while (count <= countMonster){
            if (r.nextBoolean()) {
                test = new Monster(sizeBoard);
            }else {
                test = new BigMonster(sizeBoard);
            }
            if (board[test.getY()][test.getX()].equals("  ")){
                board[test.getY()][test.getX()] = test.getImage();
                arrMonster[count] = test;
                count++;
            }
        }

        // Создаем хрустальный шар
        CrystalBall crystalBall = new CrystalBall(sizeBoard);

        while (!board[crystalBall.getY()][crystalBall.getX()].equals("  ") ||
                (crystalBall.getY() == person.getY() - 1 && crystalBall.getX() == person.getX() - 1)) {
            crystalBall = new CrystalBall(sizeBoard);
        }
        board[crystalBall.getY()][crystalBall.getX()] = crystalBall.getImage();

        int castleX = r.nextInt(sizeBoard);
        int castleY = 0;

        board[castleY][castleX] = castle;

        System.out.println("Привет! Ты готов начать играть в игру? (Напиши: ДА или НЕТ)");

        Scanner sc = new Scanner(System.in);
        String answer = sc.nextLine();
        System.out.println("Ваш ответ:\t" + answer);

        switch (answer) {
            case "ДА" -> {
                System.out.println("Выбери сложность игры(от 1 до 5):");
                int difficultGame = sc.nextInt();
                System.out.println("Выбранная сложность:\t" + difficultGame);
                while (true) {
                    board[person.getY() - 1][person.getX() - 1] = person.getImage();
                    outputBoard(board, person.getLive());
                    System.out.println("Введите куда будет ходить персонаж(ход возможен только по вертикали и горизонтали на одну клетку;" +
                            "\nКоординаты персонажа - (x: " + person.getX() + ", y: " + person.getY() + "))");
                    int x = sc.nextInt();
                    int y = sc.nextInt();

                    // проверка
                    if (person.moveCorrect(x, y)) {
                        String next = board[y - 1][x - 1];
                        if (next.equals("  ")) {
                            board[person.getY() - 1][person.getX() - 1] = "  ";
                            person.move(x, y);
                            step++;
                            System.out.println("Ход корректный; Новые координаты: " + person.getX() + ", " + person.getY() +
                                    "\nХод номер: " + step);
                        } else if (next.equals(castle)) {
                            System.out.println("Вы прошли игру!");
                            break;
                        } else if (crystalBall.isActive() && crystalBall.conflictPerson(x, y)) {
                            // Игрок наступил на хрустальный шар
                            board[person.getY() - 1][person.getX() - 1] = "  ";
                            person.move(x, y);

                            if (crystalBall.useBall(difficultGame, person)) {
                                // Шар использован и исчезает
                                crystalBall.deactivate();
                                board[y - 1][x - 1] = "  ";
                            } else {
                                // Шар исчезает даже при неверном ответе
                                crystalBall.deactivate();
                                board[y - 1][x - 1] = "  ";
                            }
                            step++;
                        } else {
                            boolean monsterFound = false;
                            for (Monster monster : arrMonster) {
                                if (monster.conflictPerson(x, y)) {
                                    monsterFound = true;
                                    if (monster.taskMonster(difficultGame)) {
                                        board[person.getY() - 1][person.getX() - 1] = "  ";
                                        person.move(x, y);
                                    } else {
                                        person.downLive();
                                    }
                                    break;
                                }
                            }
                            if (!monsterFound) {
                                System.out.println("Что-то пошло не так...");
                            }
                        }
                    } else {
                        System.out.println("Неккоректный ход");
                    }

                    // Проверка на смерть персонажа
                    if (person.getLive() <= 0) {
                        System.out.println("💀 Игра окончена! У тебя закончились жизни.");
                        break;
                    }
                }
            }
            case "НЕТ" -> System.out.println("Жаль, приходи еще!");
            default -> System.out.println("Данные введены неккоректно");
        }
    }

    static void outputBoard(String[][] board, int live) {
        String leftBlock = "| ";
        String rightBlock = "|";
        String wall = "+ —— + —— + —— + —— + —— +";

        for (String[] raw : board) {
            System.out.println(wall);
            for (String col : raw) {
                System.out.print(leftBlock + col + " ");
            }
            System.out.println(rightBlock);
        }
        System.out.println(wall);

        System.out.println("Количество жизней:\t" + live + "\n");
    }
}