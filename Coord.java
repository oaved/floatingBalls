public class Coord {
    double x, y;

    Coord(double xCoord, double yCoord) {
        this.x = xCoord;
        this.y = yCoord;
    }

    void increase(Coord vel) {
        x += vel.x;
        y += vel.y;
    }
    void decrease(Coord vel) {
        x -= vel.x;
        y -= vel.y;
    }
}
