/*TODO: The Goalie class should be a child class of the Player class. */
public class Goalie {

    public boolean movingDown = true;
    public boolean kicksRight = true;

    // Goal bounds (match SoccerGame)
    public int goalTop = 180;
    public int goalBottom = 320;

    /*
     * TODO: Create a constructor for Goalie objects. Should take in a jersey
     * number, starting x position (double), starting y position (double), and
     * whether or not this goalie kicks to the right (boolean). Pass the appropriate
     * values to the parent constructor.
     */

    /*
     * TODO: Override the update() method.
     * The goalie's y value should change depending on the value of the movingDown
     * variable. Once the goalie gets to the goalTop or goalBottom, it should change
     * directions. If the player is touching the ball, call the ball's kick()
     * method.
     */

}