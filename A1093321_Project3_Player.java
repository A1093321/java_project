import java.io.IOException;
import java.util.Scanner;
import java.nio.file.Paths;

public class A1093321_Project3_Player extends A1093321_Project3_Agent
{
    public A1093321_Project3_Player(int number)
    {
        super(number);
    }

    @Override
    public boolean decision(String fileName) throws IOException
    {
        /********************************* TODO (Checkpoint 3) *********************************
         * TODO 7-1: Here goes your algorithm for the gameplay. In order to get more treasure and reduce the risk,
         *            you have to make your own decision based on the current game state.
         * Hint 1: The parameter $fileName indicates the file path that you need to read.
         *         The file contains information about the current path, removed hazards, count of explorers who stay, etc.
         * Hint 2: This method requires you to return a boolean value. If the return value is true,
         *         it means that you'd like to keep your exploration and stay in the tomb, otherwise, you chose to leave.
         * Hint 3: To recognize the format of the file content, you can just temporarily print it out or see what the document shows.
         *         The file itself will not be preserved after this method finishes.
         * Notice 1: Your explorer's data such as collected gems can be accessed from the parent class,
         *           but due to the orientation of this checkpoint (file I/O), all information must be read from the given file.
         *           That is, do not call any method or variable from the parent class.
         ************************************* End of TODO *************************************/

        /************ START OF YOUR CODE ************/
        Scanner input = new Scanner(Paths.get(fileName));
        while (input.hasNext()){
            String str = input.nextLine();
            if (str.contains("<A: ")){
                return false;
            }
        }
        return true;
        
        /************* END OF YOUR CODE *************/
    }
}
