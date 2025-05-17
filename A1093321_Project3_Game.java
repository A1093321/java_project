import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class A1093321_Project3_Game
{
    // The randomizer which is used for shuffling the deck.
    private static final Random RANDOM = new Random(System.currentTimeMillis());
    // The game consists of 5 rounds of exploration.
    private static final int ROUND = 5;

    // All explorers participate in the game.
    private final ArrayList<A1093321_Project3_Agent> explorers = new ArrayList<>();
    // The deck of cards to be used for the game.
    private final ArrayList<A1093321_Project3_Card> deck = new ArrayList<>();
    // A tomb-like path for exploration.
    private final ArrayList<A1093321_Project3_Card> path = new ArrayList<>();
    // There are 5 sections (5 rounds) of exploration in the tomb, and one particular artifact is deposited in each section.
    private final ArrayList<A1093321_Project3_Artifact> artifacts = new ArrayList<>();
    // The Hazards that occurred during the game play.
    private final ArrayList<A1093321_Project3_Hazard> occurredHazards = new ArrayList<>();

    public A1093321_Project3_Game(String[] participants)
    {
        if (participants.length < 3 || participants.length > 8)
            throw new IllegalArgumentException("the number of participants is not between 3 and 8");

        try
        {
            for (int i = 0; i < participants.length; i++)
            {
                String participant = participants[i];
                Class<?> clazz = Class.forName(participant);
                Constructor<?> constructor = clazz.getConstructor(int.class);
                this.explorers.add((A1093321_Project3_Agent) constructor.newInstance(i));
            }
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("invalid participant");
        }

        this.setUpCards();
    }
    
    public void runGame()
    {
        for (int round = 0; round < ROUND; round++)
        {
            /********************************* TODO (Checkpoint 3) *********************************
             * TODO 6-1 (Past): First, the game data should be initialized at the beginning of each round.
             * Hint 1: All explorers' status should be switched to true.
             * Hint 2: Clear all cards on the path and shuffle them back in the deck.
             * Hint 3: Reset the value of all gemstone cards.
             * Hint 4: You need to remove the artifact for the previous round from the deck even if it was unrevealed or unclaimed,
             *         then put the one for the current round into the deck.
             * Hint 5: For the hazard that occurred in the previous round (if any), should be removed from the deck.
             * Hint 6: Make sure you use shuffleDeck() method to shuffle the deck.
             * Hint 7: You need to print "ROUND X START!" which X represents for the round number (1~5).
             * Notice 1: In this section, you can use doNothing() method as you like to set timeout between any message you would print,
             *           but the format of your output must identically be the same as what the document shows.
             ************************************* End of TODO *************************************/

            /************ START OF YOUR CODE ************/
            ArrayList<A1093321_Project3_Agent> explorers_leave = new ArrayList<>();
            ArrayList<A1093321_Project3_Agent> save_explorers_leave = new ArrayList<>();
            ArrayList<A1093321_Project3_Gemstone> GemShare = new ArrayList<>();
            ArrayList<A1093321_Project3_Hazard> HazardThisTurn = new ArrayList<>();
            ArrayList<A1093321_Project3_Hazard> OnceHazard = new ArrayList<>();
            ArrayList<A1093321_Project3_Artifact> TTArtifact = new ArrayList<>();
            String H = "hazard occurs, all explorers attempt to flee!";

            for ( int i = 0; i < explorers.size() ; i++){
                explorers.get(i).setInExploring(true);
            }
            
            path.clear();
            deck.clear();
            setUpCards();
            deck.add(artifacts.get(round));

            for (int i = 0; i < occurredHazards.size();i++){
                for (int j = 0; j < deck.size(); j++){
                    if (deck.get(j).name() == occurredHazards.get(i).name()){
                        deck.remove(j);
                        break;
                    }
                }
            }
            shuffleDeck();
            
            System.out.printf("ROUND %d START!%n%n", round+1);

            /************* END OF YOUR CODE *************/

            while (this.isAnyoneStay())
            {
                /********************************* TODO (Checkpoint 3) *********************************
                 * TODO 6-2 (Past): During a round, all explorers explore the path in the ancient tomb and hunt for abundant treasures.
                 * Hint 1: To move forward in our exploration, you need to draw a card from the top of the deck and put it on the end of the path.
                 * Hint 2: When the drawn card is gemstone, use share() method of Gemstone object to share the value of it with all explorers who stay.
                 * Hint 3: When the drawn card is hazard, check whether it is the secondly same type of hazard that has been drawn.
                 *         If this is the case, all explorers attempt to flee and you should add this card into the occurred hazard.
                 * Hint 4: Print out the information of the path.
                 * Hint 5: Print out the information of all explorers in sequence. If the explorer stays in the tomb, print "Explorer X has Y gem(s).",
                 *         otherwise, print "Explorer X left." which X and Y represent for their number and the quantity of collected gems.
                 * Hint 6: After "----- STAY or LEAVE -----" is printed, all explorers who stay have to make their decision about staying or leaving.
                 *         For this purpose, you can use act() method of Agent object.
                 * Hint 7: Print "Everyone keeps exploring." if there is no explorer choose to leave,
                 *         else print "Explorer X wants to leave." for each explorer who chose to leave, which X represents for their number.
                 * Hint 8: On condition that this round had been broken off by a hazard, you should print "X hazard occurs, all explorers attempt to flee!"
                 *         rather than what Hint 7 does, which X represents the name of that occurred hazard.
                 * Hint 9: For those who chose to leave the tomb, should share the value of all the gemstone cards on the path while each one works independently.
                 *         If the path is [<G: 3/11>, <G: 1/9>, <A: Ankh 7>, <G: 5/13>], for instance, there are 3 explorers who chose to leave,
                 *         then the path will become [<G: 0/11>, <G: 1/9>, <A: Ankh 7>, <G: 2/13>] after they leave.
                 * Notice 1: Beware of the mechanism of sharing an artifact.
                 * Notice 2: The act() method of Agent requires an Environment object as its parameter, which indicates that agent can act upon the environment.
                 *           Note that the variable $environment was already declared for you, all you need to do is pass it into the act() method.
                 *           All explorers should act upon the same environment, so do not declare another Environment object or it may cause some error.
                 * Notice 3: In this section, you can use doNothing() method as you like to set timeout between any message you would print,
                 *           but the format of your output must identically be the same as what the document shows.
                 ************************************* End of TODO *************************************/

                /************ START OF YOUR CODE ************/
                path.add(deck.get(0));

                if (deck.get(0).name() == "Gemstone"){
                    GemShare.add((A1093321_Project3_Gemstone) deck.get(0));
                    GemShare.get(0).share(explorers);
                    GemShare.remove(0);
                }
                if (deck.get(0) instanceof A1093321_Project3_Artifact){
                    TTArtifact.add((A1093321_Project3_Artifact)deck.get(0));
                }
                if (deck.get(0) instanceof A1093321_Project3_Hazard){
                    String HazardName = deck.get(0).name();
                    if (OnceHazard.size() > 0){
                        for (int i = 0; i < OnceHazard.size(); i++)
                            if (OnceHazard.get(i).name().equals(HazardName)){
                                occurredHazards.add((A1093321_Project3_Hazard)deck.get(0));
                                HazardThisTurn.add((A1093321_Project3_Hazard)deck.get(0));
                                for (int j = 0; j < explorers.size(); j++)
                                    if (explorers.get(j).isInExploring() == true)    
                                        explorers.get(j).flee();
                        }
                    }
                    OnceHazard.add((A1093321_Project3_Hazard)deck.get(0));
                }

                deck.remove(0);
                explorers_leave.clear();

                //path imformation
                System.out.printf("[");
                for (int i = 0; i < path.size() ; i++){
                    if (i == path.size() - 1)
                        System.out.printf("%s", path.get(i).toString());
                    else
                        System.out.printf("%s, ", path.get(i).toString());
                }
                System.out.printf("]%n");

                //explorers imformation
                for ( int i = 0; i < explorers.size() ; i++){
                    if ( explorers.get(i).isInExploring() == true ){
                        System.out.printf("Explorer %d has %d gem(s).%n", i, explorers.get(i).getCollectedGems());
                    }else
                        System.out.printf("Explorer %d left.%n", i);
                }

                /************* END OF YOUR CODE *************/

                System.out.println("----- STAY or LEAVE -----");

                A1093321_Project3_Environment environment = this.createEnvironment();

                /************ START OF YOUR CODE ************/
                if (HazardThisTurn.isEmpty() == false){
                    System.out.printf("%s %s%n%n", HazardThisTurn.get(0).name(), H);
                    break;
                }
                //explorers leave this turn
                int LTTcount = 0; //Leave This Turn
                for (int i = 0; i < explorers.size() ; i++)
                    explorers.get(i).act(environment);
                for ( int i = 0; i < explorers.size() ; i++){
                    if ( explorers.get(i).isInExploring() == false )
                        explorers_leave.add(explorers.get(i));
                }
                if ( save_explorers_leave.size() > 0 )
                    for ( int i = 0; i < save_explorers_leave.size(); i++){
                        explorers_leave.remove(save_explorers_leave.get(i));
                    }
                for ( int i = 0; i < explorers_leave.size(); i++){
                    save_explorers_leave.add(explorers_leave.get(i));
                    LTTcount++;
                }
                //print after ----- STAY or LEAVE -----
                for ( int i = 0; i < explorers_leave.size(); i++){
                    System.out.printf("%s wants to leave.%n", explorers_leave.get(i).toString());
                }
                if ( LTTcount == 0 )
                    System.out.printf("Everyone keeps exploring.%n");
                System.out.println();             

                //remain distribute to leave
                if ( explorers_leave.size() != 0 ){
                    for (int i = 0; i <path.size(); i++){
                        if (path.get(i).name() == "Gemstone"){
                            GemShare.add((A1093321_Project3_Gemstone) path.get(i));
                            GemShare.get(0).share(explorers_leave);
                            GemShare.remove(0);
                        }
                    }
                    if (TTArtifact.size() > 0) {
                        if (TTArtifact.get(0).isInTomb() == true)
                            TTArtifact.get(0).share(explorers_leave);
                    }
                }

                /************* END OF YOUR CODE *************/
            }

            /********************************* TODO (Checkpoint 3) *********************************
             * TODO 6-3 (Past): At the end of a round, all explorers finish their exploration and return to the camp with treasure.
             * Hint 1: First, print "ROUND X END!" which X represents for the round number (1~5).
             * Hint 2: To make all explorers store gems they've collected during this round into their tent,
             *         you can use storeGemsIntoTent() method of Agent object.
             * Notice 1: In this section, you can use doNothing() method as you like to set timeout between any message you would print,
             *           but the format of your output must identically be the same as what the document shows.
             ************************************* End of TODO *************************************/

            /************ START OF YOUR CODE ************/
            System.out.printf("ROUND %d END!%n%n", round+1);
            for (int i = 0; i < explorers.size() ; i++)
                explorers.get(i).storeGemsIntoTent();

            /************* END OF YOUR CODE *************/
        }

        System.out.println("GAME OVER!");
        System.out.println();
        System.out.println("----- Final result -----");

        for (A1093321_Project3_Agent explorer : this.explorers)
            System.out.println(explorer + ": " + explorer.totalValue());

        System.out.println();
        System.out.println("Winner: " + this.getWinners());

    }

    private void setUpCards()
    {
        this.deck.add(new A1093321_Project3_Hazard(0));
        this.deck.add(new A1093321_Project3_Hazard(0));
        this.deck.add(new A1093321_Project3_Hazard(0));
        this.deck.add(new A1093321_Project3_Hazard(1));
        this.deck.add(new A1093321_Project3_Hazard(1));
        this.deck.add(new A1093321_Project3_Hazard(1));
        this.deck.add(new A1093321_Project3_Hazard(2));
        this.deck.add(new A1093321_Project3_Hazard(2));
        this.deck.add(new A1093321_Project3_Hazard(2));
        this.deck.add(new A1093321_Project3_Hazard(3));
        this.deck.add(new A1093321_Project3_Hazard(3));
        this.deck.add(new A1093321_Project3_Hazard(3));
        this.deck.add(new A1093321_Project3_Hazard(4));
        this.deck.add(new A1093321_Project3_Hazard(4));
        this.deck.add(new A1093321_Project3_Hazard(4));
        
        this.deck.add(new A1093321_Project3_Gemstone(0, 1));
        this.deck.add(new A1093321_Project3_Gemstone(1, 2));
        this.deck.add(new A1093321_Project3_Gemstone(2, 3));
        this.deck.add(new A1093321_Project3_Gemstone(3, 4));
        this.deck.add(new A1093321_Project3_Gemstone(4, 5));
        this.deck.add(new A1093321_Project3_Gemstone(4, 5));
        this.deck.add(new A1093321_Project3_Gemstone(5, 7));
        this.deck.add(new A1093321_Project3_Gemstone(5, 7));
        this.deck.add(new A1093321_Project3_Gemstone(6, 9));
        this.deck.add(new A1093321_Project3_Gemstone(7, 11));
        this.deck.add(new A1093321_Project3_Gemstone(7, 11));
        this.deck.add(new A1093321_Project3_Gemstone(8, 13));
        this.deck.add(new A1093321_Project3_Gemstone(9, 14));
        this.deck.add(new A1093321_Project3_Gemstone(10, 15));
        this.deck.add(new A1093321_Project3_Gemstone(11, 17));
        
        this.artifacts.add(new A1093321_Project3_Artifact(0, 5));
        this.artifacts.add(new A1093321_Project3_Artifact(1, 7));
        this.artifacts.add(new A1093321_Project3_Artifact(2, 8));
        this.artifacts.add(new A1093321_Project3_Artifact(3, 10));
        this.artifacts.add(new A1093321_Project3_Artifact(4, 12));
    }

    private void shuffleDeck()
    {
        Collections.shuffle(this.deck, RANDOM);
    }

    private ArrayList<A1093321_Project3_Agent> getStayExplorers()
    {
        /********************************* TODO (Checkpoint 3) *********************************
         * TODO 5-1 (Past): Get all explorers who stay in the tomb.
         * Hint 1: You can check each explorer's status.
         ************************************* End of TODO *************************************/

        /************ START OF YOUR CODE ************/
        ArrayList<A1093321_Project3_Agent> StayExplorers = new ArrayList<>();
        for (int i = 0; i < explorers.size() ;i++){
            if ( explorers.get(i).isInExploring() == true )
                StayExplorers.add(explorers.get(i));
        }
        return StayExplorers;

        /************* END OF YOUR CODE *************/
    }

    private boolean isAnyoneStay()
    {
        /********************************* TODO (Checkpoint 3) *********************************
         * TODO 5-2 (Past): Check if there is anyone who stays in the tomb.
         * Hint 1: Return true if at least one explorer was in exploring.
         ************************************* End of TODO *************************************/

        /************ START OF YOUR CODE ************/
        for (int i = 0; i < explorers.size();i++ ){
            if ( explorers.get(i).isInExploring() == true )
                return true;
        }
        return false;

        /************* END OF YOUR CODE *************/
    }

    private ArrayList<A1093321_Project3_Agent> getWinners()
    {
        /********************************* TODO (Checkpoint 3) *********************************
         * TODO 5-3 (Past): The winners will be the explorers who hold the highest value of treasure.
         * Hint 1: You can use totalValue() method of Agent object to check the total value that they hold.
         * Notice 1: There might be multiple winners if more than one explorers equivalently hold the highest value.
         ************************************* End of TODO *************************************/

        /************ START OF YOUR CODE ************/
        ArrayList<A1093321_Project3_Agent> Winners = new ArrayList<>();
        int most = 0;
        int mostExplorer = 0;

        for (int i = 0; i < explorers.size();i++ ){
            if ( explorers.get(i).totalValue() > most ){
                most = explorers.get(i).totalValue() ;
                mostExplorer = i;
            }
        }
        
        Winners.add(explorers.get(mostExplorer));

        for (int i = 0; i < explorers.size();i++ ){
            if ( i != mostExplorer && explorers.get(i).totalValue() == most ){
                Winners.add(explorers.get(i));
            }
        }
        return Winners;

        /************* END OF YOUR CODE *************/
    }

    private A1093321_Project3_Environment createEnvironment()
    {
        A1093321_Project3_Environment environment = new A1093321_Project3_Environment();

        environment.setCountOfExplorers(this.explorers.size());
        environment.setCountOfStayExplorers(this.getStayExplorers().size());
        environment.setPath(this.path);
        environment.setOccurredHazards(this.occurredHazards);

        return environment;
    }

    private static void doNothing(long millisecond)
    {
        if (millisecond > 2000)
            throw new IllegalArgumentException("timeout value is over 2000");

        try
        {
            Thread.sleep(millisecond);
        }
        catch (InterruptedException e)
        {
            throw new IllegalStateException("unexpected interruption");
        }
    }
}
