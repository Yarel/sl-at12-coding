import java.util.ArrayList;

class Pacman extends Position {
    private boolean eatable;
    private ArrayList<Dot> dots;
    private ListWalls lWall = new ListWalls();
    private ArrayList<Position> walls = lWall.getWalls();
    private final int positionDimension = 30;
    private final int frameXSize = 30;
    private final int frameYSize = 30;
    private final int indexOfSpecialDot0 = 0;
    private final int indexOfSpecialDot1 = 27;
    private final int indexOfSpecialDot2 = 67;
    private final int indexOfSpecialDot3 = 77;
    private final int indexOfSpecialDot4 = 78;
    private final int indexOfSpecialDot5 = 113;
    private final int indexOfSpecialDot6 = 178;
    private final int indexOfSpecialDot7 = 223;
    private final int indexOfSpecialDot8 = 228;
    private final int indexOfSpecialDot9 = 238;
    private final int indexOfSpecialDot10 = 257;
    private final int indexOfSpecialDot11 = 294;
    private final int indexOfSpecialDot12 = 353;
    private final int indexOfSpecialDot13 = 300;
    private final int indexOfSpecialDot14 = 301;
    private final int indexOfSpecialDot15 = 302;
    private final int indexOfSpecialDot16 = 303;

    Pacman(final int x, final int y, final boolean exist) {
        super(x, y, exist);
        eatable = true;
        lWall = new ListWalls();
        walls = new ArrayList<Position>();
        walls = lWall.getWalls();
        dots = new ArrayList<Dot>();
        addDots();
    }

    public boolean isEatable() {
        return this.eatable;
    }

    public void setNotEatable() {
        this.eatable = false;
    }

    public void setEatable() {
        this.eatable = true;
    }

    public void die() {
        this.doesnotExist();
    }

    public void down() {
        if (!existWall(super.getX(), super.getY() + positionDimension)) {
            super.setY(super.getY() + positionDimension);
        }
    }

    public void right() {
        if (!existWall(super.getX() + positionDimension, super.getY())) {
            super.setX(super.getX() + positionDimension);
        }
    }

    public void left() {
        if (!existWall(super.getX() - positionDimension, super.getY())) {
            super.setX(super.getX() - positionDimension);
        }
    }

    public void up() {
        if (!existWall(super.getX(), super.getY() - positionDimension)) {
            super.setY(super.getY() - positionDimension);
        }
    }

    public String move(final String type) {
        String direction = "";
        switch (type) {
            case "left":
                left();
                direction = "pacmanLeft.gif";
                break;
            case "right":
                right();
                direction = "pacmanRight.gif";
                break;
            case "up":
                up();
                direction = "pacmanUp.gif";
                break;
            case "down":
                down();
                direction = "pacmanDown.gif";
                break;
            default:
                break;
        }
        return direction;
    }

    public boolean existWall(final int xx, final int yy) {
        boolean exist = false;
        for (Position wall: walls) {
            if (wall.getX() == xx && wall.getY() == yy) {
                exist = true;
            }
        }
        return exist;
    }

    //Check if there is a dot in x and y position
    public boolean existDot(final int positionx, final int positiony) {
        boolean exist = false;
        for (Position i : dots) {
            if (i.getX() == positionx && i.getY() == positiony) {
                exist = true;
            }
        }
        return exist;
    }

    public void addDots() {
        boolean draw = true;
        for (int iy = 1; iy < frameXSize - 1; iy++) {
            for (int ix = 1; ix < frameYSize - 1; ix++) {
                for (Position wall : walls) {
                    if ((wall.getX() == ix * positionDimension) && (wall.getY() == iy * positionDimension)) {
                        draw = false;
                        break;
                    }
                }
                if (draw) {
                    dots.add(new Dot(ix * positionDimension, iy * positionDimension, true));
                }
                draw = true;
            }
        }
        //The next lines change isSpecial attribute of Dot to true, at specific positions.
        dots.get(indexOfSpecialDot0).setSpecial();
        dots.get(indexOfSpecialDot1).setSpecial();
        dots.get(indexOfSpecialDot2).setSpecial();
        dots.get(indexOfSpecialDot3).setSpecial();
        dots.get(indexOfSpecialDot4).setSpecial();
        dots.get(indexOfSpecialDot5).setSpecial();
        dots.get(indexOfSpecialDot6).setSpecial();
        dots.get(indexOfSpecialDot7).setSpecial();
        dots.get(indexOfSpecialDot8).setSpecial();
        dots.get(indexOfSpecialDot9).setSpecial();
        dots.get(indexOfSpecialDot10).setSpecial();
        dots.get(indexOfSpecialDot11).setSpecial();
        dots.get(indexOfSpecialDot12).setSpecial();
        dots.get(indexOfSpecialDot13).setSpecial();
        dots.get(indexOfSpecialDot14).setSpecial();
        dots.get(indexOfSpecialDot15).setSpecial();
        dots.get(indexOfSpecialDot16).setSpecial();
    }

    public ArrayList<Dot> getDots() {
        return dots;
    }

    public boolean isEatable(final Dot dotsPosition) {
        if (
        (super.getX() == dotsPosition.getX())
        &&
        (super.getY() == dotsPosition.getY())
        &&
        dotsPosition.doesExist()
        ) {
            dotsPosition.doesnotExist();
            return true;
        }
        return false;
    }

    public boolean isEatable(final Ghost ghostPosition) {
        if (
        (super.getX() == ghostPosition.getX())
        &&
        (super.getY() == ghostPosition.getY())
        &&
        ghostPosition.isEatable()
        ) {
            return true;
        } else {
            super.doesnotExist();
            return false;
        }
    }

    public int pacmanEatDot(final Dot dot, final ArrayList<Ghost> ghosts, final int seconds) {
        if (existDot(dot.getX(), dot.getY()) && dot.doesExist()) {
            dot.doesnotExist();
            if (dot.isSpecialDot()) {
                setNotEatable();
                for (Ghost ghost : ghosts) {
                    ghost.changeEatable();
                }
                return 0;
            }
        }
        return seconds;
    }

    public void pacmanEatGhosts(final ArrayList<Ghost> ghosts) {
        for (Ghost ghost : ghosts) {
            if (getX() == ghost.getX() && getY() == ghost.getY()) {
                if (!ghost.isEatable()) {
                    die();
                } else {
                    ghost.die();
                }
            }
        }
    }
}
