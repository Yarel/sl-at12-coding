import java.util.ArrayList;
import java.util.Random;

public class Ghost extends Position {
    private boolean eatable;
    private ArrayList<Wall> walls;
    private Creator creator = new Creator();
    private String direction = "right";
    private String directionNeedToGo;
    private boolean stuckGhost = false;
    private static final int WALK_DISTANCE = 30;
    private static final int NUM_POSIBLE_RUTES = 4;

    public Ghost(final int x, final int y, final boolean exist) {
        super(x, y, exist);
        this.eatable = false;
        walls = creator.createWalls();
    }

    public Ghost(final int x, final int y, final boolean exist, final ArrayList<Wall> wallsCreated) {
        super(x, y, exist);
        this.eatable = false;
        walls = wallsCreated;
    }
    /**
     *
     * @return getStuckGhost
     */
    public boolean getStuckGhost() {
        return this.stuckGhost;
    }

    /**
     *
     * @return setStuckGhost
     */
    public void setStuckGhost(final boolean stuck) {
        this.stuckGhost = stuck;
    }

    /**
     *
     * @return getDirectionNeedToGo
     */
    public String getDirectionNeedToGo() {
        return this.directionNeedToGo;
    }

    /**
     *
     * @return getDirection
     */
    public String getDirection() {
        return this.direction;
    }

    /**
     *
     * Method setDirection
     */
    public void setDirection(final String dir) {
        this.direction = dir;
    }

    /**
     *
     * Method setDirectionNeedToGo
     */
    public void setDirectionNeedToGo(final String dir) {
        this.directionNeedToGo = dir;
    }

    /**
     *
     * @return isEatable
     */
    public boolean isEatable() {
        return this.eatable;
    }

    /**
     *
     * changeEatable
     */
    public void changeEatable() {
        this.eatable = true;
    }

    /**
     *
     * @return setEatable
     */
    public void setEatable(final boolean doesEatable) {
        this.eatable = doesEatable;
    }

    /**
     *
     *  moveUp
     */
    public void moveUp() {
        super.setY(super.getY() - WALK_DISTANCE);
    }

    /**
     *
     *  moveDown
     */
    public void moveDown() {
        super.setY(super.getY() + WALK_DISTANCE);
    }

    /**
     *
     *  moveLeft
     */
    public void moveLeft() {
        super.setX(super.getX() - WALK_DISTANCE);
    }

    /**
     *
     *  moveRight
     */
    public void moveRight() {
        super.setX(super.getX() + WALK_DISTANCE);
    }

    /**
     *
     * @return isPosibleMoveDown
     */
    public boolean isPosibleMoveDown() {
        for (Position wall : walls) {
            if (wall.getX() == super.getX() && wall.getY() == super.getY() + WALK_DISTANCE) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @return isPosibleMoveUp
     */
    public boolean isPosibleMoveUp() {
        for (Position wall : walls) {
            if (wall.getX() == super.getX() && wall.getY() == super.getY() - WALK_DISTANCE) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @return isPosibleMoveLeft
     */
    public boolean isPosibleMoveLeft() {
        for (Position wall : walls) {
            if (wall.getX() == super.getX() - WALK_DISTANCE && wall.getY() == super.getY()) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @return isPosibleMoveRight
     */
    public boolean isPosibleMoveRight() {
        for (Position wall : walls) {
            if (wall.getX() == super.getX() + WALK_DISTANCE && wall.getY() == super.getY()) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @return string direction X
     */
    public String getDirecctionX() {
        final int numRand = new Random().nextInt(4);
        final String dirRand1 = "left";
        final String dirRand2 = "right";
        String dirX = dirRand1;
        if (isPosibleMove(dirRand1) && isPosibleMove(dirRand2)) {
            if (numRand % 2 == 0) {
                dirX = dirRand2;
             }
        } else {
            if (isPosibleMove(dirRand2)) {
                dirX = dirRand2;
            }
        }
        return dirX;
    }

    /**
     *
     * @return string direction y
     */
    public String getDirecctionY() {
        final int numRand = new Random().nextInt(4);
        final String dirRand1 = "up";
        final String dirRand2 = "down";
        String dirY = dirRand1;
        if (isPosibleMove(dirRand1) && isPosibleMove(dirRand2)) {
            if (numRand % 2 == 0) {
                dirY = dirRand2;
             }
        } else {
            if (isPosibleMove(dirRand2)) {
                dirY = dirRand2;
            }
        }
        return dirY;
    }

    /**
     *
     * stuckGhost
     */
    public String solveStuckGhost(final String dirOptional) {
        String routeOptional = dirOptional;
        if (isPosibleMove(directionNeedToGo)) {
            routeOptional = directionNeedToGo;
            stuckGhost = false;
        } else {
            switch (dirOptional) {
            case "down":
                if (!isPosibleMoveDown()) {
                    routeOptional = getDirecctionX();
                }
                break;
            case "up":
                if (!isPosibleMoveUp()) {
                    routeOptional = getDirecctionX();
                }
                break;
            case "left":
                if (!isPosibleMoveLeft()) {
                    routeOptional = getDirecctionY();
                }
                break;
            case "right":
                if (!isPosibleMoveRight()) {
                    routeOptional = getDirecctionY();
                }
                break;
            default:
                break;
            }
        }
        return routeOptional;
    }

    /**
     *
     * @return string direction X
     */
    public String getNextDirectionGhostX(final Pacman pacman) {
        String nextDirX = "";
        if (pacman.getX() > this.getX()) {
            nextDirX = "right";
        } else {
            if (pacman.getX() < this.getX()) {
                nextDirX = "left";
            } else {
                nextDirX = "intersection";
            }
        }
        return nextDirX;
    }

    /**
     *
     * @return string direction Y
     */
    public String getNextDirectionGhostY(final Pacman pacman) {
        String nextDirY = "";
        if (pacman.getY() > super.getY()) {
            nextDirY = "down";
        } else {
            if (pacman.getY() < super.getY()) {
                nextDirY = "up";
            } else {
                nextDirY = "intersection";
            }
        }
        return nextDirY;
    }

    /**
     *
     * @return boolean isPosibleMove
     */
    public boolean isPosibleMove(final String nextDirection) {
        boolean result = false;
        switch (nextDirection) {
        case "down":
            result = isPosibleMoveDown();
            break;
        case "up":
            result = isPosibleMoveUp();
            break;
        case "left":
            result = isPosibleMoveLeft();
            break;
        case "right":
            result = isPosibleMoveRight();
            break;
        default:
            result = false;
            break;
        }
        return result;
    }

    /**
     *
     * @return string getRoute
     */
    public String getRoute(final Pacman pacman) {
        String dirToGo = "";
        if (stuckGhost) {
            dirToGo = solveStuckGhost(direction);
        } else {
            final String sigDX = getNextDirectionGhostX(pacman);
            final String sigDY = getNextDirectionGhostY(pacman);
            if (isEatable()) {
                dirToGo = eatableChangeRoute(sigDX, sigDY, pacman);
            } else {
                if (directionNeedToGo != null) {
                    if (isPosibleMove(directionNeedToGo) && directionNeedToGo == (getRoutePosible(sigDX, sigDY))) {
                        dirToGo = directionNeedToGo;
                    } else {
                        dirToGo = getRoutePosible(sigDX, sigDY);
                        directionNeedToGo = null;
                        if (stuckGhost) {
                            directionNeedToGo = dirToGo;
                        }
                    }
                } else {
                    dirToGo = getRoutePosible(sigDX, sigDY);
                    if (stuckGhost) {
                        directionNeedToGo = dirToGo;
                    }
                }
            }

        }
        return dirToGo;
    }

    /**
     *
     *  move
     */
    public void move(final String directionTo) {
        switch (directionTo) {
        case "down":
            moveDown();
            break;
        case "up":
            moveUp();
            break;
        case "left":
            moveLeft();
            break;
        case "right":
            moveRight();
            break;
        default:
            break;
        }
    }

    /**
     *
     *  searchRouteGhost
     */
    public void searchRouteGhost(final Pacman pacman) {
        if (this.doesExist()) {
            direction = getRoute(pacman);
            if (isPosibleMove(direction)) {
                move(direction);
                if (isEatable()) {
                    if (existGhostEatable(pacman)) {
                        super.die();
                    }
                } else {
                    if (existPacmanEatable(pacman)) {
                        pacman.die();
                    }
                }
            }
        }
    }

    /**
     *
     * @return existPacmanEatable
     */
    public boolean existPacmanEatable(final Pacman pacman) {
        if (pacman.doesExist() && pacman.getX() == super.getX() && pacman.getY() == super.getY()) {
            return true;
        }
        return false;
    }

    /**
     *
     * @return existGhostEatable
     */
    public boolean existGhostEatable(final Pacman pacman) {
        if (this.doesExist() && pacman.getX() == this.getX() && pacman.getY() == this.getY()) {
            return true;
        }
        return false;
    }

    /**
     *
     * @return eatableChangeRoute
     */
    public String eatableChangeRoute(final String routeX, final String routeY, final Pacman pacman) {
        String eatableRoute;
        final int distanciamin = 200;
        if (Math.abs(pacman.getX() - this.getX()) < distanciamin) {
            if (routeX == "intersection") {
                eatableRoute = getDirecctionY();
            }
            eatableRoute = routeEscape(routeX);
        } else if (Math.abs(pacman.getY() - this.getY()) < distanciamin) {
            eatableRoute = routeEscape(routeY);
        } else {
            eatableRoute = getRoutePosible(routeX, routeY);
        }
        return eatableRoute;
    }

    /**
     *
     * @return getRoutePosible
     */
    public String getRoutePosible(final String routeX, final String routeY) {
        String routePosible = "right";
        if (isPosibleMove(routeX)) {
            routePosible = routeX;
        } else if (isPosibleMove(routeY)) {
            routePosible = routeY;
        } else if (routeX != "intersection") {
            routePosible = routeX;
            stuckGhost = true;
        } else if (routeY != "intersection") {
            routePosible = routeY;
            stuckGhost = true;
        }
        return routePosible;
    }

    /**
     *
     * @return routeEscape
     */
    public String routeEscape(final String route) {
        String routeEscape = route;
        if (route == "down") {
            routeEscape = getDirecctionY();
            if (isPosibleMove("left")) {
                routeEscape = "left";
            }
        } else if (route == "left") {
            routeEscape = getDirecctionY();
            if (isPosibleMove("right")) {
                routeEscape = "right";
            }
        } else if (route == "up") {
            routeEscape = getDirecctionX();
            if (isPosibleMove("down")) {
                routeEscape = "down";
            }
        } else if (route == "down") {
            routeEscape = getDirecctionX();
            if (isPosibleMove("up")) {
                routeEscape = "up";
            }
        } else if (route == "intersection") {
            routeEscape = getDirecctionX();
            if (isPosibleMove(getDirecctionY())) {
                routeEscape = getDirecctionY();
            }
        }
        return routeEscape;
    }
}
