package ca.mcmaster.se2aa4.mazerunner;

public class PathRecorder implements CommandObserver{

    private String movementLog;

    public PathRecorder() {
        this.movementLog = "";
    }

    @Override
    public void recordAction(char action) {
        if (action == 'F' || action == 'L' || action == 'R') {
            movementLog += action;
        } else if (action == 'U'){
            movementLog = movementLog.substring(0, movementLog.length() - 1);
        }
    }

    public String getCanonicalPath() {
        return movementLog;
    }

    // factorize movement log
    public String getFactorizedPath() {

        // Initialize variables
        String factorized = "";
        int occurence = 1;
        char lastMove = movementLog.charAt(0);

        // Search through canonical path
        for (int i = 1; i < movementLog.length(); i++) {
            // if current is equivalent to last char, increment occurences
            if (movementLog.charAt(i) == lastMove) {
                occurence += 1;
            } // if not, append to factorized path
            else {

                // ignore occurence if only 1
                if (occurence == 1) {
                    factorized += lastMove + " ";
                }
                else {
                    factorized += String.valueOf(occurence) + lastMove + " ";
                }

                // reset occurence and last move
                occurence = 1;
                lastMove = movementLog.charAt(i);
            }
        }

        // appending last moves, ignore occurence if only 1
        if (occurence == 1) {
            factorized += lastMove + " ";
        }
        else {
            factorized += String.valueOf(occurence) + lastMove + " ";
        }

        // return factorized path
        return factorized;
    }


    
}
