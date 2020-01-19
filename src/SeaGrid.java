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
    }
    public void placeShipOnGrid(String grid [][], int coordinates[]) {
        grid[coordinates[0]][coordinates[1]] = "SSS";
    }
}
