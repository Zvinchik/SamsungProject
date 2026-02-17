import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final String CASTLE = "\uD83C\uDFF0";
    private static final int SIZE_BOARD = 5;
    private static Random random = new Random();

    public static void main(String[] args) {
        Person person = new Person(SIZE_BOARD);
        int step = 0;

        String[][] board = initializeBoard();
        Monster[] monsters = placeMonsters(board);
        CrystalBall crystalBall = placeCrystalBall(board, person);
        placeCastle(board);

        if (!startGame()) {
            System.out.println("Жаль, приходи еще!");
            return;
        }

        int difficultGame = selectDifficulty();
        Scanner sc = new Scanner(System.in);

        while (true) {
            updateBoardWithPerson(board, person);
            outputBoard(board, person.getLive());

            int[] targetCoords = getPlayerMove(sc, person);
            if (targetCoords == null) continue;

            int x = targetCoords[0];
            int y = targetCoords[1];

            if (!person.moveCorrect(x, y)) {
                System.out.println("Некорректный ход (можно двигаться только на одну клетку)");
                continue;
            }

            String nextCell = board[y - 1][x - 1];

            if (nextCell.equals("  ")) {
                makeMove(board, person, x, y);
                step++;
                System.out.println("Ход номер: " + step);
            } else if (nextCell.equals(CASTLE)) {
                System.out.println("🏆 Поздравляем! Вы прошли игру! 🏆");
                break;
            } else if (crystalBall.isActive() && crystalBall.conflictPerson(x, y)) {
                handleCrystalBall(board, person, crystalBall, x, y, difficultGame);
                step++;
            } else {
                handleMonsterEncounter(board, person, monsters, x, y, difficultGame);
            }

            if (person.getLive() <= 0) {
                System.out.println("💀 Игра окончена! У тебя закончились жизни.");
                break;
            }
        }

        sc.close();
    }

    private static String[][] initializeBoard() {
        String[][] board = new String[SIZE_BOARD][SIZE_BOARD];
        for (int y = 0; y < SIZE_BOARD; y++) {
            for (int x = 0; x < SIZE_BOARD; x++) {
                board[y][x] = "  ";
            }
        }
        return board;
    }

    private static Monster[] placeMonsters(String[][] board) {
        int countMonster = SIZE_BOARD * SIZE_BOARD - SIZE_BOARD - 5;
        Monster[] monsters = new Monster[countMonster];
        int count = 0;

        while (count < countMonster) {
            Monster monster = random.nextBoolean() ?
                    new Monster(SIZE_BOARD) : new BigMonster(SIZE_BOARD);

            if (board[monster.getY()][monster.getX()].equals("  ")) {
                board[monster.getY()][monster.getX()] = monster.getImage();
                monsters[count] = monster;
                count++;
            }
        }

        return monsters;
    }

    private static CrystalBall placeCrystalBall(String[][] board, Person person) {
        CrystalBall crystalBall;
        do {
            crystalBall = new CrystalBall(SIZE_BOARD);
        } while (!board[crystalBall.getY()][crystalBall.getX()].equals("  ") ||
                (crystalBall.getY() == person.getY() - 1 && crystalBall.getX() == person.getX() - 1));

        board[crystalBall.getY()][crystalBall.getX()] = crystalBall.getImage();
        return crystalBall;
    }

    private static void placeCastle(String[][] board) {
        int castleX = random.nextInt(SIZE_BOARD);
        int castleY = 0;
        board[castleY][castleX] = CASTLE;
    }

    private static boolean startGame() {
        System.out.println("Привет! Ты готов начать играть в игру? (Напиши: ДА или НЕТ)");
        Scanner sc = new Scanner(System.in);
        String answer = sc.nextLine().trim().toUpperCase();

        return answer.equals("ДА") || answer.equals("Y") || answer.equals("YES") ||
                answer.equals("LF") || answer.equals("ДА") || answer.startsWith("Д");
    }

    private static int selectDifficulty() {
        System.out.println("Выбери сложность игры (от 1 до 5):");
        Scanner sc = new Scanner(System.in);

        while (true) {
            if (sc.hasNextInt()) {
                int difficulty = sc.nextInt();
                if (difficulty >= 1 && difficulty <= 5) {
                    System.out.println("Выбранная сложность: " + difficulty);
                    sc.nextLine();
                    return difficulty;
                }
            }
            System.out.println("Некорректный ввод! Введите число от 1 до 5:");
            sc.next();
        }
    }

    private static int[] getPlayerMove(Scanner sc, Person person) {
        System.out.println("Введите координаты x и y (от 1 до " + SIZE_BOARD + "):");
        System.out.println("Текущая позиция: (" + person.getX() + ", " + person.getY() + ")");

        while (true) {
            if (sc.hasNextInt()) {
                int x = sc.nextInt();
                if (sc.hasNextInt()) {
                    int y = sc.nextInt();
                    if (x >= 1 && x <= SIZE_BOARD && y >= 1 && y <= SIZE_BOARD) {
                        return new int[]{x, y};
                    }
                }
            }
            System.out.println("Некорректный ввод! Введите два числа от 1 до " + SIZE_BOARD + ":");
            sc.nextLine();
        }
    }

    private static void updateBoardWithPerson(String[][] board, Person person) {
        board[person.getY() - 1][person.getX() - 1] = person.getImage();
    }

    private static void makeMove(String[][] board, Person person, int x, int y) {
        board[person.getY() - 1][person.getX() - 1] = "  ";
        person.move(x, y);
    }

    private static void handleCrystalBall(String[][] board, Person person,
                                          CrystalBall crystalBall, int x, int y, int difficulty) {
        board[person.getY() - 1][person.getX() - 1] = "  ";
        person.move(x, y);

        crystalBall.useBall(difficulty, person);
        crystalBall.deactivate();
        board[y - 1][x - 1] = "  ";
    }

    private static void handleMonsterEncounter(String[][] board, Person person,
                                               Monster[] monsters, int x, int y, int difficulty) {
        for (Monster monster : monsters) {
            if (monster.conflictPerson(x, y)) {
                if (monster.taskMonster(difficulty)) {
                    board[person.getY() - 1][person.getX() - 1] = "  ";
                    person.move(x, y);
                } else {
                    person.downLive();
                }
                return;
            }
        }
    }

    static void outputBoard(String[][] board, int live) {
        String leftBlock = "| ";
        String rightBlock = "|";
        String wall = "+ —— + —— + —— + —— + —— +";

        for (String[] row : board) {
            System.out.println(wall);
            for (String cell : row) {
                System.out.print(leftBlock + cell + " ");
            }
            System.out.println(rightBlock);
        }
        System.out.println(wall);
        System.out.println("Количество жизней: " + live + "\n");
    }
}