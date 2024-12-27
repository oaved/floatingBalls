public class Coord {
    double x, y;

    Coord(double xCoord, double yCoord) {
        this.x = xCoord;
        this.y = yCoord;
    }

    static final Coord ZERO = new Coord(0,0);
    static final Coord I_HAT = new Coord(1, 0);
    static final Coord J_HAT = new Coord(0, 1);

    void increase(Coord vel) {
        x += vel.x;
        y += vel.y;
    }

    void decrease(Coord vel) {
        x -= vel.x;
        y -= vel.y;
    }

    static double scal(Coord a, Coord b) {      // scalar product
        return a.x * b.x + a.y * b.y;
    } 
    
    static Coord sub(Coord a, Coord b) {        
        return new Coord(a.x - b.x, a.y - b.y);
    }

    static Coord add(Coord a, Coord b) {
        return new Coord(a.x + b.x, a.y + b.y);
    }

    static double distance(Coord a, Coord b) {
        return Coord.sub(a, b).magnitude();
    }

    double magnitude() {                        
        return Math.sqrt(x * x + y * y);
    }

    static Coord mul(double k, Coord c) {
        return new Coord(k * c.x, k * c.y);
    }
}
