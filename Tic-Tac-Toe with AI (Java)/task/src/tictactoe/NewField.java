package tictactoe;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class NewField {

    char[][] field = new char[3][3];

    char userOne = 'X';
    char userTwo = 'O';

    String x;
    String y;

    int numberY = 0;
    int numberX = 0;

    char move;

    boolean done = false;


    Scanner scanner = new Scanner(System.in);

    Random random = new Random();

    HashMap<Character, Integer> scoresForX;
    HashMap<Character, Integer> scoresForO;


    public NewField() {
        fillField();
        scoresForX = new HashMap<>();
        scoresForX.put('X', 10);
        scoresForX.put('O', -10);
        scoresForX.put('t', 0);

        scoresForO = new HashMap<>();
        scoresForO.put('O', 10);
        scoresForO.put('X', -10);
        scoresForO.put('t', 0);
    }

    public void fillField(){
        for(int i = 0; i <field.length; i++){
            for(int j = 0; j < field[i].length; j++) {
                field[i][j] = ' ';
            }
        }
    }

    public void drawField(){
        System.out.println("---------");
        for(int i = 0; i <field.length; i++){
            System.out.print("| ");
            for(int j = 0; j < field[i].length; j++){
                System.out.print(field[i][j] + " ");
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("---------");
    }

    public boolean allThreeSame(char a, char b, char c){
        return a == b && b == c && a != ' ';
    }
    public char checkWinner(){
        char winner = 'n';

        //horizontal
        for(int i = 0; i < 3; i++){
            if(allThreeSame(field[i][0], field[i][1], field[i][2])){
                winner = field[i][0];
            }
        }

        //vertical
        for(int i = 0; i < 3; i++){
            if(allThreeSame(field[0][i], field[1][i], field[2][i])){
                winner = field[0][i];
            }
        }

        //diagonal
        if(allThreeSame(field[0][0], field[1][1], field[2][2])){
            winner = field[0][0];
        }

        if(allThreeSame(field[2][0], field[1][1], field[0][2])){
            winner = field[2][0];
        }

        int empty = 0;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(field[i][j] == ' '){
                    empty++;
                }
            }
        }

        if(winner == 'n' && empty == 0){
            return 't';
        }else{
            return winner;
        }

    }

    public void startOrExit(){
        boolean goodAnswer = true;
        String firstUser = "";
        String secondUser = "";
        String startExit = "";
        String firstAnswer = "";

        do{
            goodAnswer = true;

            System.out.println("Input command: ");

            startExit = scanner.nextLine();

            if (startExit.equals("exit")) {
                System.exit(0);
            }

            try{
                String[] answer = startExit.trim().split(" ");
                firstAnswer = answer[0];
                firstUser = answer[1];
                secondUser = answer[2];
            }catch (Exception e) {
                System.out.println("Bad parameters!");
                goodAnswer = false;
                continue;
            }

            if(!firstAnswer.equals("start") && !firstAnswer.equals("exit") && !firstAnswer.equals("medium") && !firstAnswer.equals("hard")){
                System.out.println("Bad parameters!");
                goodAnswer = false;
                continue;
            }

            if(!firstUser.equals("user") && !firstUser.equals("easy") && !firstUser.equals("medium") && !firstUser.equals("hard")){
                System.out.println("Bad parameters!");
                goodAnswer = false;
                continue;
            }

            if(!secondUser.equals("user") && !secondUser.equals("easy") && !secondUser.equals("medium") && !secondUser.equals("hard")){
                System.out.println("Bad parameters!");
                goodAnswer = false;
            }

        }while(!goodAnswer);

        gameLoop(firstUser,secondUser);
    }

    public void bestMove(char user) {

        int bestScore = Integer.MIN_VALUE;
        int[] move = {0, 0};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(field[i][j] == ' '){
                    field[i][j] = user;
                    int score = miniMax(field, 0 , false, user);
                    field[i][j] = ' ';
                    if(score > bestScore) {
                        bestScore = score;
                        move[0] = i;
                        move[1] = j;
                    }
                }
            }
        }
        field[move[0]][move[1]] = user;
    }

    public boolean oneLevelMiniMax(char user){
        char opposing;
        if(user == 'X'){
            opposing = 'O';
        }else{
            opposing = 'X';
        }

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3;j++){
                if(field[i][j] == ' '){
                    field[i][j] = user;
                    char result = checkWinner();
                    if(result == user){
                        return true;
                    }

                    field[i][j] = opposing;
                    result = checkWinner();
                    if(result == opposing){
                        field[i][j] = user;
                        return true;
                    }
                    field[i][j] = ' ';
                }
            }
        }
        return false;
    }

    public int miniMax(char[][] field, int depth, boolean isMaximizing, char user){
        char userOne;
        char userTwo;
        if(user == 'X'){
            userOne = 'X';
            userTwo = 'O';
        }else{
            userOne = 'O';
            userTwo = 'X';
        }
        char result = checkWinner();
        if(result != 'n'){
            if(user == 'X'){
                return scoresForX.get(result);
            }else{
                return scoresForO.get(result);
            }
        }

        if(isMaximizing){
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if(field[i][j] == ' '){
                        field[i][j] = userOne;
                        int score = miniMax(field, depth + 1, false, user);
                        field [i][j] = ' ';
                        if(score > bestScore){
                            bestScore = score;
                        }
                    }
                }
            }
            return bestScore;
        }else{
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if(field[i][j] == ' '){
                        field[i][j] = userTwo;
                        int score = miniMax(field, depth + 1, true, user);
                        field[i][j] = ' ';
                        if(score < bestScore){
                            bestScore = score;
                        }
                    }
                }
            }
            return bestScore;
        }
    }

    public void gameLoop(String firstUser, String secondUser){


        drawField();
        char result;

        while(!done){

            boolean done = true;

            if(firstUser.equals("user")){
                playerMove('X');
            }else if(firstUser.equals("medium")){
               if(!oneLevelMiniMax('X')){
                   computerMove('X');
               }
            }else if(firstUser.equals("hard")){
                bestMove('X');
            }else{
                computerMove('X');
            }

            if(!firstUser.equals("user")){
                System.out.println("Making move level " + "\"" + firstUser + "\"");
            }

            drawField();


            result = checkWinner();
            if(result != 'n'){
                if(result == 't'){
                    System.out.println("Draw");
                    break;
                }else{
                    System.out.println(result + " wins");
                    break;
                }
            }else{
                done =false;
            }

            if(secondUser.equals("user")){
                playerMove('O');
            }else if(secondUser.equals("medium")){
                if(!oneLevelMiniMax('O')){
                    computerMove('O');
                }
            }else if(secondUser.equals("hard")){
                bestMove('O');
            }else{
                computerMove('O');
            }

            if(!secondUser.equals("user")){
                System.out.println("Making move level " + "\"" + secondUser + "\"");
            }

            drawField();

            result = checkWinner();
            if(result != 'n'){
                if(result == 't'){
                    System.out.println("Draw");
                    break;
                }else{
                    System.out.println(result + " wins");
                    break;
                }
            }else{
                done =false;
            }

        }

    }

    public void playerMove(char move){

        boolean correctEntry = false;

        while(!correctEntry){

            System.out.println("Enter the coordinates: ");

            x = scanner.next();

            try{
                numberX = Integer.parseInt(x) - 1;
            }catch (Exception e){
                System.out.println("You should enter numbers!");
                continue;
            }
            y = scanner.next();

            try{
                numberY = Integer.parseInt(y) - 1;
            }catch(Exception e){
                System.out.println("You should enter numbers!");
                continue;
            }

            if(numberY < 0 || numberY > 2 || numberX < 0 || numberX > 2){
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }

            if(field[numberX ][numberY] != ' '){
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }

            field[numberX][numberY] = move;

            correctEntry = true;

        }
    }

    public void computerMove(char move) {

        boolean correct = false;

        while(!correct){

            int x = random.nextInt(3);
            int y = random.nextInt(3);

            if(field[x][y] != 'X' && field[x][y] != 'O'){

                field[x][y] = move;

                correct = true;
            }
        }

    }

    public static void main(String[] args) {
        NewField newField = new NewField();

        newField.startOrExit();
    }
}
