1,Create Classes:a,DisruptLecture.java

b,TestArithmetic.java

c,GlyphTest.java

d,Transmogrify.java

1,a,
public class DisruptLecture {
    public static void main(String[] args) {
        CellPhone noiseMaker = new CellPhone();
        ObnoxiousTune ot = new ObnoxiousTune();
        noiseMaker.ring(ot); // ot works though Tune called for
    }
}b,

public class TestArithmetic {
    // evaluate 1.1 + 2.2 + 3.3
    public static void main(String[] args) {
        Node n = new Plus(
                new Plus(
                        new Const(1.1), new Const(2.2)),
                new Const(3.3));
        System.out.println("" + n.eval());
    }
}c,

public class GlyphTest {
    public static void main(String[] args) {
        new RoundGlyph(5);
    }
}d,

public class Transmogrify {
 public static void 
 main(String[] args){
 Stage s = new Stage();
 s.go(); //happy actor
 s.change();
 s.go() // sad actor
 }
}
