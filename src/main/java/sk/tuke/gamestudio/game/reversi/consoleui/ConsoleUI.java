package sk.tuke.gamestudio.game.reversi.consoleui;
import org.springframework.stereotype.Component;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.reversi.core.*;
import sk.tuke.gamestudio.service.comment.CommentService;
import sk.tuke.gamestudio.service.comment.CommentServiceJPA;
import sk.tuke.gamestudio.service.rating.RatingException;
import sk.tuke.gamestudio.service.rating.RatingService;
import sk.tuke.gamestudio.service.rating.RatingServiceJDBC;
import sk.tuke.gamestudio.service.score.ScoreService;
import sk.tuke.gamestudio.service.score.ScoreServiceJPA;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

@Component
public class ConsoleUI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_GREEN = "\u001B[32m";

    private final Field field;
    private final Scanner scanner = new Scanner(System.in);

    private final ScoreService scoreService;
    private final CommentService commentService;
    private final RatingService ratingService;


    public ConsoleUI(Field field, ScoreService scoreService, CommentService commentService, RatingService ratingService) {
        this.field = field;
        this.scoreService = scoreService;
        this.commentService = commentService;
        this.ratingService = ratingService;
    }

    public void play() {
        scoreService.addScore(new Score("banan", "reversi", 5, new Date()));
        scoreService.addScore(new Score("bandasdan", "reversi", 2, new Date()));
        scoreService.addScore(new Score("banadassaan", "reversi", 6, new Date()));
        scoreService.addScore(new Score("banssssan", "reversi", 7, new Date()));
        menu(); // menu for user

        // main game loop
        if(field.getGameMode() == GameMode.PLAYER_VS_AI)
            do {
                printGame();
                processInput();
                printGame();
                processComputerMove();
            } while(field.getState() == GameState.PLAYING);
        else {
            do {
                printGame();
                processInput();
            } while(field.getState() == GameState.PLAYING);
        }

        // finished situations
        printField();
        if (field.getPlayer1().getScore() > field.getPlayer2().getScore()) {
            Player winningPlayer = field.getPlayer1();
            System.out.println("Player " + winningPlayer.getName() + " won!");
            // adding winning player to score database if player is not a computer
            if(!(winningPlayer instanceof Computer))
                scoreService.addScore(new Score(winningPlayer.getName(), "reversi", winningPlayer.getScore(), new Date()));
        }

        else if (field.getPlayer1().getScore() < field.getPlayer2().getScore()) {
            Player winningPlayer = field.getPlayer2();
            System.out.println("Player " + winningPlayer.getName() + " won!");
            // adding winning player to score database if player is not a computer
            if(!(winningPlayer instanceof Computer))
                scoreService.addScore(new Score(winningPlayer.getName(), "reversi", winningPlayer.getScore(), new Date()));
        }

        else System.out.println("Tie! Nobody won!");
    }

    private void menu() {


        int input = 999;
        while(input != 1) {
            printMenu();
            input = getInput();
            switch (input) {
                case 1 -> getGameSettingsFromUser();
                case 2 -> printTopScores();

                case 3 -> printAllComments();

                case 4 -> addComment();

                case 5 -> rateGame();

                case 6 -> showRating();

                case 7 -> resetScores();

                case 8 -> resetComments();

                case 9 -> resetRatings();
            }
        }
    }

    private void printTopScores() {
        System.out.println("* -------- TOP SCORES --------- *");
        var scores = scoreService.getTopScores("reversi");
        for(int i = 0; i < scores.size(); i++) {
            var score = scores.get(i);
            System.out.printf("%d. %s %d\n", i+1, score.getPlayer(), score.getPoints());
        }
        System.out.println("* ----------------------------- *\n");
        System.out.println("Type anything and press enter to go back to the menu!");
        scanner.next();
    }

    private void printAllComments() {
        System.out.println("* --------- COMMENTS ---------- *");
        var comments = commentService.getComments("reversi");
        if(comments.isEmpty()) System.out.println("There are no comments yet. Be the first one to comment!");
        for(Comment comment : comments) {
            System.out.println(comment.getPlayer() + ": " + comment.getComment());
        }
        System.out.println("* ----------------------------- *");
        System.out.println("Type anything and press enter to go back to the menu!");
        scanner.next();
    }

    private void addComment() {
        Scanner commentScanner = new Scanner(System.in);
        System.out.print("Please input your name: ");
        String name = commentScanner.next();
        System.out.print("Please input your comment: ");
        commentScanner.nextLine();
        String message = commentScanner.nextLine();
                Comment comment = new Comment(name, "reversi", message, new Date());
        commentService.addComment(comment);
    }

    private void rateGame() {
        Scanner ratingScanner = new Scanner(System.in);
        System.out.print("Please input your name: ");
        String name = ratingScanner.next();
        System.out.print("Please input your rating (1 - 5): ");
        int ratingScore;
        try{
            ratingScore = ratingScanner.nextInt();
        } catch  (InputMismatchException exception) {
            System.out.println("Wrong input! Rating was not added!");
            return;
        }

        try {
            ratingService.setRating(new Rating(name,  "reversi", ratingScore, new Date()));
        } catch (RatingException exception) {
            System.out.println("The rating was not added!");
            exception.printStackTrace();
        }

    }

    private void resetScores() {
        System.out.println("Are you sure you want to reset the scores? yes(y)/no(n)");
        Scanner resetScanner = new Scanner(System.in);
        String answer = resetScanner.nextLine();
        if(Objects.equals(answer, "yes") || Objects.equals(answer, "y")) {
            try {
                scoreService.reset();
            } catch (Exception e) {
                System.out.println("Error while resetting: " + e.getMessage());
                return;
            }

            System.out.println("The scores were reseted!");
        }
    }

    private void resetComments() {
        System.out.println("Are you sure you want to reset the comments? yes(y)/no(n)");
        Scanner resetScanner = new Scanner(System.in);
        String answer = resetScanner.nextLine();
        if(Objects.equals(answer, "yes") || Objects.equals(answer, "y")) {
        try {
            commentService.reset();
        } catch (Exception e) {
            System.out.println("Error while resetting: " + e.getMessage());
            return;
        }
            System.out.println("The comments were reseted!");
        }
    }

    private void resetRatings() {
        System.out.println("Are you sure you want to reset the ratings? yes(y)/no(n)");
        Scanner resetScanner = new Scanner(System.in);
        String answer = resetScanner.nextLine();
        if(Objects.equals(answer, "yes") || Objects.equals(answer, "y")) {
        try {
            ratingService.reset();
        } catch (Exception e) {
            System.out.println("Error while resetting: " + e.getMessage());
            return;
        }
            System.out.println("The ratings were reseted!");
        }
    }

    private void showRating() {

        System.out.println(ANSI_RED + "* ---------- REVERSI ---------- *" + ANSI_RESET);
        System.out.println(ANSI_RED + "*" + ANSI_BLUE + "\tPlease pick an option!" + ANSI_RED + "\t\t*" + ANSI_RESET);
        System.out.println(ANSI_RED + "*" + ANSI_GREEN + "\t\t 1: RATING" + ANSI_RED + "\t\t\t\t*" + ANSI_RESET);
        System.out.println(ANSI_RED + "*" + ANSI_GREEN + "\t\t 2: AVERAGE RATING" + ANSI_RED + "\t\t*" + ANSI_RESET);
        System.out.println(ANSI_RED + "*" + ANSI_GREEN + "\t\t 3: BACK TO MENU" + ANSI_RED + "\t\t*" + ANSI_RESET);

        int input = 999;
        while(input != 3) {
            input = getInput();
            switch (input) {
                case 1 -> {
                    Scanner ratingScanner = new Scanner(System.in);
                    System.out.println("Type your name to show your rating: ");
                    String name = ratingScanner.nextLine();
                    try{
                        System.out.println("Player rating " + name + ": " + ratingService.getRating("reversi", name));
                    } catch (RatingException exception) {
                        System.out.println("Error: " + exception.getMessage());
                    }

                    input = 3;
                    System.out.println("Type anything and press enter to go back to the menu!");
                    scanner.next();
                }
                case 2 -> {
                    System.out.println("Average rating is: " + ratingService.getAverageRating("reversi") + "/5");
                    input = 3;
                    System.out.println("Type anything and press enter to go back to the menu!");
                    scanner.next();
                }
            }
        }
    }

    private int getInput() {
        int input;
        do {
            try {
                System.out.println("> ");
                input = scanner.nextInt();
                if(input <= 0 || input >= 10) {
                    System.out.println("Wrong option. Try again!");
                    input = -1;
                }
            } catch (InputMismatchException exception) {
                System.out.println("Wrong input. Try again!");
                scanner.next();
                input = -1;
            }
        } while(input == -1);
        return input;
    }

    private void printMenu() {
        System.out.println(ANSI_RED + "* ---------- REVERSI ---------- *" + ANSI_RESET);
        System.out.println(ANSI_RED + "*" + ANSI_BLUE + "\tWelcome to the game!" + ANSI_RED + "\t\t*" + ANSI_RESET);
        System.out.println(ANSI_RED + "*" + ANSI_BLUE + "\tPlease pick an option!" + ANSI_RED + "\t\t*" + ANSI_RESET);
        System.out.println(ANSI_RED + "*" + ANSI_GREEN + "\t\t 1: PLAY GAME" + ANSI_RED + "\t\t\t*" + ANSI_RESET);
        System.out.println(ANSI_RED + "*" + ANSI_GREEN + "\t\t 2: TOP SCORES" + ANSI_RED + "\t\t\t*" + ANSI_RESET);
        System.out.println(ANSI_RED + "*" + ANSI_GREEN + "\t\t 3: COMMENTS" + ANSI_RED + "\t\t\t*" + ANSI_RESET);
        System.out.println(ANSI_RED + "*" + ANSI_GREEN + "\t\t 4: ADD COMMENT" + ANSI_RED + "\t\t\t*" + ANSI_RESET);
        System.out.println(ANSI_RED + "*" + ANSI_GREEN + "\t\t 5: RATE THE GAME" + ANSI_RED + "\t\t*" + ANSI_RESET);
        System.out.println(ANSI_RED + "*" + ANSI_GREEN + "\t\t 6: GAME RATINGS" + ANSI_RED + "\t\t*" + ANSI_RESET);
        System.out.println(ANSI_RED + "*" + ANSI_GREEN + "\t\t 7: RESET SCORES" + ANSI_RED + "\t\t*" + ANSI_RESET);
        System.out.println(ANSI_RED + "*" + ANSI_GREEN + "\t\t 8: RESET COMMENTS" + ANSI_RED + "\t\t*" + ANSI_RESET);
        System.out.println(ANSI_RED + "*" + ANSI_GREEN + "\t\t 9: RESET RATINGS" + ANSI_RED + "\t\t*" + ANSI_RESET);
    }

    private void processComputerMove() {
        if(field.getPlayerOnTurn() instanceof Computer) {
            try {
                ((Computer) field.getPlayerOnTurn()).makeTurn();
            } catch (Exception ignored) {}
        }
    }

    private void getGameSettingsFromUser() {
        switch (field.getGameMode()) {
            case PLAYER_VS_PLAYER -> {
                System.out.println("Player 1, please type your name: ");
                field.getPlayer1().setName(scanner.next());
                System.out.println("Player 2, please type your name: ");
                field.getPlayer1().setName(scanner.next());
            }
            case PLAYER_VS_AI -> {
                System.out.println("Please type your name: ");
                field.getPlayer1().setName(scanner.next());
                determineDifficulty();
            }
        }
    }

    private void determineDifficulty() {
        Difficulty difficulty = null;

        do {
            System.out.println("_______________________________________________");
            System.out.println("Pick difficulty " + ANSI_GREEN + "([e]asy" + ANSI_RESET + "/" + ANSI_RED + "[h]ard):" + ANSI_RESET);
            String userInput = scanner.next();

            if(Objects.equals(userInput, "easy") || Objects.equals(userInput, "e")) difficulty = Difficulty.EASY;
            else if(Objects.equals(userInput, "hard") || Objects.equals(userInput, "h")) difficulty = Difficulty.HARD;
            else {
                System.out.println("Wrong input!!!");
            }
        }
        while(difficulty == null);

        field.setDifficulty(difficulty);
        System.out.println("Difficulty set to: ");
        if(difficulty == Difficulty.EASY)
            System.out.println(ANSI_GREEN + "EASY" + ANSI_RESET);
        else System.out.println(ANSI_RED + "HARD" + ANSI_RESET);
    }

    public void printGame() {
        //for (int i = 0; i < 50; ++i) System.out.println();
        System.out.println("_______________________________________________");
        System.out.println("\nType two numbers [row] [col] and press Enter");
        printGameStats();
        printField();
    }

    private void printField() {
        System.out.print(" ");
        for(int i = 1; i <= field.getSize(); i++)
            System.out.print(ANSI_RESET + " " + i);
        System.out.println();

        int index = 0;
        for(Tile[] tileRow : field.getTiles()) {
            System.out.print(ANSI_RESET + ++index + " ");
            for(Tile tile : tileRow) {
                if(tile.getTileState() == TileState.OCCUPIED) {
                    if(tile.getStone().getPlayer().getColor() == Color.WHITE) System.out.print(ANSI_RED + "R " + ANSI_RESET);
                    else System.out.print(ANSI_BLUE + "B " + ANSI_RESET);
                } else System.out.print(ANSI_RESET + "- ");
            }
            System.out.println();
        }
    }

    private void printGameStats() {
        Player playerOnTurn = field.getPlayerOnTurn();
        if(playerOnTurn.getColor() == Color.WHITE)
            System.out.println("Player " + playerOnTurn.getName() + " with color " + ANSI_BLUE + "B" + ANSI_RESET + " is on turn.");
        else System.out.println("Player " + playerOnTurn.getName() + " with color " + ANSI_RED + "R" + ANSI_RESET + " is on turn.");
        System.out.println(field.getPlayer1().getName() + "'s score: " + field.getPlayer1().getScore());
        System.out.println(field.getPlayer2().getName() + "'s score: " + field.getPlayer2().getScore());
    }

    public void processInput() {

        int row, col;
        try {
            row = scanner.nextInt() - 1;
            col = scanner.nextInt() - 1;
        } catch(InputMismatchException exception) {

            scanner.next();
            scanner.next();
            System.out.println("Wrong input. Try again!");
            return;
        }
        try {
            field.addStoneToField(field.getPlayerOnTurn(), row, col);
        } catch (Exception e) {
            if(e instanceof Field.NoPossibleMovesException) {
                System.out.println("No more moves are possible. Both players skipped a move.");
                return;
            }
            System.out.println(e.getMessage());
            System.out.println("Try again!");
        }
    }
}
