public class SeaGrid {

    private String grid [][];

    public SeaGrid(String[][] grid) {
        this.grid = grid;
        grid = new String[30][30];
        // let's loop through array to populate grid
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                grid[row][col] = "~~~";
            }
            setGrid(grid);
        }
    }
    public String[][] getGrid() {
        return grid;
    }

    public void setGrid(String[][] grid) {
        this.grid = grid;
    }


    public void printSeaGrid(String grid [][])    {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                System.out.print(grid[row][col]);
            }
            System.out.println();
        }
    }
    public void placePortOnGrid(String grid [][], int coordinates[]) {
        grid[coordinates[0]][coordinates[1]] = "!!!";
        grid[coordinates[0]+1][coordinates[1]] = "!!!";
        grid[coordinates[0]+2][coordinates[1]] = "!!!";
    }

    public void placeShipOnGrid(String grid [][], int coordinates[]) {
        if (grid[coordinates[0]][coordinates[1]] != "!!!")
        grid[coordinates[0]][coordinates[1]] = "SSS";
    }
    public void removeShipFromGrid(String grid [][], int coordinates[]) {
        grid[coordinates[0]][coordinates[1]] = "~~~";
    }
    public final  void clearConsole() {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }
}
