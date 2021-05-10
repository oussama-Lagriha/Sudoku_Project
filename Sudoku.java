import java.lang.*;
import java.util.*;
class Sudoku 
{
    public static int N = 9;
    int L;
    int[][] arr;
    Sudoku(int L)
    {
        this.L = L;
        arr = new int[N][N];
    }

    int rand(int number)
    {
        return (int) Math.floor(Math.random()*number+1);
    }
    
    boolean isInBox(int row, int col, int number)
    {
        int r = row - row%3;
        int c = col - col%3;
        for(int i = r; i < r + 3; i++)
        {
            for(int j = c; j < c + 3; j++)
            {
                if(arr[i][j] == number) return true;
            }
        }
        return false;
    }

    boolean isInRow(int row, int number)
    {
        for(int i = 0; i < N; i++)
        {
            if(arr[row][i] == number) return true;
        }
        return false;
    }

    boolean isInCol(int col, int number)
    {
        for(int i = 0; i < N; i++)
        {
            if(arr[i][col] == number) return true;
        }
        return false;
    }

    boolean isSafe(int row, int col, int number)
    {
        return !isInBox(row, col, number) && !isInCol(col, number) && !isInRow(row, number);
    }

    boolean sudokuSolver(int[][] arr)
    {
        for(int i = 0; i < N; i++)
        {
            for(int j = 0; j < N; j++)
            {
                if(arr[i][j] == 0)
                {
                    for(int number = 1; number <= N; number++)
                    {
                        if(isSafe(i, j, number))
                        {
                            arr[i][j] = number;
                            if(sudokuSolver(arr)) return true;
                            else arr[i][j] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    void fillBox(int row, int col)
    {
        int number;
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                do
                {
                    number = rand(N);
                }
                while(isInBox(row, col, number));
                arr[row + i][col + j] = number;
            }
        }
    }

    void fillDiago()
    {
        for(int i = 0; i < N; i += 3)
        {
            fillBox(i, i);
        }
    }

    void level()
    {
        int count = L;
        while (count != 0)
        {
            int cell = rand(N*N);
            int i = (cell/N);
            int j = cell%9;
            if (j != 0)
                j = j - 1;
  
            if (arr[i][j] != 0)
            {
                count--;
                arr[i][j] = 0;
            }
        }
    }

    void generate()
    {
        fillDiago();
        sudokuSolver(arr);
        level();
    }

    void affiche()
    {
        System.out.println("");
        for(int i = 0; i < N; i++)
            {
                for(int j = 0; j < N; j++)
                {
                    if(j % 3 == 0 && j != 0)
                    {
                        System.out.print("|");
                    }
                    System.out.print(" "+arr[i][j]+" ");
                }
                System.out.println("");
                if(i % 3 == 2 && i != 0 && i != 8)
                {
                    System.out.println(" - - - - - - - - - - - - - - ");
                }
            }
    }
    void isCorrect()
    {
        int i = 0, j = 0;
        for (i = 0; i < N; i++)
        {
            for (j = 0; j < N; j++)
            {
                if(arr[i][j] == 0)
                {
                    System.out.println("you didn't complet the game : "+"("+ i+","+ j+")");
                    System.exit(0);
                }
            }
        }
        for (i = 0; i < N; i++)
        {
            for (j = 0; j < N; j++)
            {
                int temp = arr[i][j];
                arr[i][j] = 0;
                if(!isSafe(i, j, temp))
                {
                    arr[i][j] = temp;
                    System.out.println("wrong answer in : "+"("+ i+","+ j+")");
                    System.exit(0);
                }
            }
        }
        System.out.println("god job !");
    }

    public static void main(String[] args)
    {
        Sudoku sudoku;
        Scanner sc = new Scanner(System.in);
        System.out.println("enter you Username : ");
        String userName = sc.nextLine();
        System.out.println("");
        System.out.println("Hi " + userName);
        char c;
        int level = 0;
        do {
            System.out.println("to play press the key 'p', to solve a sudoku game press the key s : ");
            c = sc.next().charAt(0);
        } while (c != 's' && c != 'p');
        if(c =='p') 
        {
            System.out.println("please chose level 1 -> 9 :");
            level = sc.nextInt();
            System.out.println("");
            System.out.println("enjoy ");
            sudoku = new Sudoku(level + 10);
            sudoku.generate();
            sudoku.affiche();
            System.out.println("");
            System.out.println("to submet a solution press 'a' to show solution press 'u' : ");
            char s = sc.next().charAt(0);
            if(s == 'a')
            {
                Sudoku sub = new Sudoku(10);
                for (int i = 0; i < 9; i++)
                {
                    for (int j = 0; j < 9; j++)
                    {
                        sub.arr[i][j] = sc.nextInt();
                    }
                }
                System.out.println("");
                sub.affiche();
                System.out.println("");
                sub.isCorrect();
            }
            else if(s == 'u')
            {
                sudoku.sudokuSolver(sudoku.arr);
                sudoku.affiche();

            }

        }
        else if (c == 's')
        {
            sudoku = new Sudoku(10);
            System.out.println("please enter the numbers and replace the empty place with 0 : ");
            for (int i = 0; i < 9; i++)
                {
                    for (int j = 0; j < 9; j++)
                    {
                        sudoku.arr[i][j] = sc.nextInt();
                    }
                }
            System.out.println("");
            sudoku.sudokuSolver(sudoku.arr);
            sudoku.affiche();
        }
    }    
}