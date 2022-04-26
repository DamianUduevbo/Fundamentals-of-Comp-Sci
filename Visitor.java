import tester.*;

import java.util.function.*;

//Represents an IArith 
interface IArith {
  <R> R accept(IArithVisitor<R> visitor);
}

//visits an IArith and produces a result of type R.
interface IArithVisitor<R> extends Function<IArith, R> {
  // method for applying the method
  R apply(IArith obj);

  // method for const
  R visitConst(Const c);

  // method for unary
  R visitUnary(UnaryFormula u);

  // method for binary
  R visitBinary(BinaryFormula b);

}

/////////////////////////////
//checks whether the tree is double 
class EvalVisitor implements IArithVisitor<Double> {
  EvalVisitor() {
  }

  @Override
  public Double visitConst(Const c) {
    return c.num;
  }

  @Override
  public Double visitUnary(UnaryFormula u) {
    return u.func.apply(u.child.accept(this));
  }

  @Override
  public Double visitBinary(BinaryFormula b) {
    return b.f.apply(b.left.accept(this), b.right.accept(this));
  }

  @Override
  public Double apply(IArith obj) {
    return obj.accept(this);
  }
}

//produces a string representing the arithmetic taking place
class PrintVisitor implements IArithVisitor<String> {
  PrintVisitor() {
  }

  @Override
  public String visitConst(Const c) {
    return Double.toString(c.num);
  }

  @Override
  public String visitUnary(UnaryFormula u) {
    return "(" + u.name + " " + u.child.accept(this) + ")";
  }

  @Override
  public String visitBinary(BinaryFormula u) {
    return "(" + u.name + " " + u.left.accept(this) + " " + u.right.accept(this) + ")";
  }

  @Override
  public String apply(IArith obj) {
    return obj.accept(this);
  }
}

//takes every IArith and produce another IArith where all of the consts are doubled 
class DoublerVisitor implements IArithVisitor<IArith> {
  DoublerVisitor() {
  }

  @Override
  public IArith visitConst(Const c) {
    return new Const(c.num * 2);
  }

  @Override
  public IArith visitUnary(UnaryFormula u) {
    return new UnaryFormula(u.func, u.name, u.child.accept(this));
  }

  @Override
  public IArith visitBinary(BinaryFormula b) {
    return new BinaryFormula(b.f, b.name, b.left.accept(this), b.right.accept(this));
  }

  @Override
  public IArith apply(IArith obj) {
    return obj.accept(this);
  }
}

//if a Negative number is in the tree 
class NoNegativeResults implements IArithVisitor<Boolean> {

  @Override
  public Boolean visitConst(Const c) {
    return c.num >= 0;
  }

  @Override
  public Boolean visitUnary(UnaryFormula u) {
    if (u.name.equals("Neg")) {
      return false;
    }
    return u.child.accept(this);
  }

  @Override
  public Boolean visitBinary(BinaryFormula b) {
    return b.left.accept(this) && b.right.accept(this) && b.accept(new EvalVisitor()) >= 0;
  }

  @Override
  public Boolean apply(IArith obj) {
    return obj.accept(this);
  }

}

//////////////////////

//Represents a constant 
class Const implements IArith {
  double num;

  Const(double num) {
    this.num = num;
  }

  public <R> R accept(IArithVisitor<R> visitor) {
    return visitor.visitConst(this);
  }
}

// represents a unary Formula 
class UnaryFormula implements IArith {
  String name;
  IArith child;
  Function<Double, Double> func;

  UnaryFormula(Function<Double, Double> func, String name, IArith child) {
    this.name = name;
    this.child = child;
    this.func = func;
  }

  public <R> R accept(IArithVisitor<R> visitor) {
    return visitor.visitUnary(this);
  }
}

// represents a binary Formula 
class BinaryFormula implements IArith {
  String name;
  IArith left;
  IArith right;
  BiFunction<Double, Double, Double> f;

  BinaryFormula(BiFunction<Double, Double, Double> f, String name, IArith left, IArith right) {
    this.name = name;
    this.left = left;
    this.right = right;
    this.f = f;
  }

  public <R> R accept(IArithVisitor<R> visitor) {
    return visitor.visitBinary(this);
  }

}

//represents Plus arithmetic 
class Plus implements BiFunction<Double, Double, Double> {
  public Double apply(Double num1, Double num2) {
    return num1 + num2;
  }
}

//represents Minus arithmetic 
class Minus implements BiFunction<Double, Double, Double> {
  public Double apply(Double num1, Double num2) {
    return num1 - num2;
  }
}

//represents Multiplication arithmetic 
class Mul implements BiFunction<Double, Double, Double> {
  public Double apply(Double num1, Double num2) {
    return num1 * num2;
  }
}

// represents Division arithmetic 
class Div implements BiFunction<Double, Double, Double> {
  public Double apply(Double num1, Double num2) {
    return num1 / num2;
  }
}

// represents square arithmetic 
class Sqr implements Function<Double, Double> {
  public Double apply(Double num) {
    return num * num;
  }
}

//represents Negative arithmetic 
class Neg implements Function<Double, Double> {
  public Double apply(Double num) {
    return num * -1;
  }
}

// represents the examples of visitor 
class ExamplesVisitor {
  ExamplesVisitor() {
  }

  IArith const1 = new Const(2.0);
  IArith const2 = new Const(10.0);
  IArith const3 = new Const(-9.0);

  IArith Neg1 = new UnaryFormula(new Neg(), "Neg", const1);
  IArith Neg2 = new UnaryFormula(new Neg(), "Neg", const2);

  IArith Sqr1 = new UnaryFormula(new Sqr(), "Sqr", const1);
  IArith Sqr2 = new UnaryFormula(new Sqr(), "Sqr", const3);

  IArith Plus = new BinaryFormula(new Plus(), "Plus", const1, const2);
  IArith Plus1 = new BinaryFormula(new Plus(), "Plus", const2, const3);
  IArith Minus = new BinaryFormula(new Minus(), "Minus", const1, const2);
  IArith Mul = new BinaryFormula(new Mul(), "Mul", const1, const3);
  IArith Div = new BinaryFormula(new Div(), "Div", const2, const1);

  IArith Multiple = new BinaryFormula(new Plus(), "Plus", Plus, Neg1);

  // test Negative arithmetic
  boolean testNeg(Tester t) {
    return t.checkExpect(new Neg().apply(5.0), -5.0) && t.checkExpect(new Neg().apply(15.0), -15.0)
        && t.checkExpect(new Neg().apply(-10.0), 10.0);
  }

  // test square arithmetic
  boolean testSqr(Tester t) {
    return t.checkExpect(new Sqr().apply(5.0), 25.0)
        && t.checkExpect(new Sqr().apply(-10.0), 100.0);
  }

  // test addition arithmetic
  boolean testPlus(Tester t) {
    return t.checkExpect(new Plus().apply(2.0, 10.0), 12.0)
        && t.checkExpect(new Plus().apply(-9.0, 2.0), -7.0);
  }

  // test Minus arithmetic
  boolean testMinus(Tester t) {
    return t.checkExpect(new Minus().apply(-2.0, 10.0), -12.0)
        && t.checkExpect(new Minus().apply(10.0, 5.0), 5.0);
  }

  // test Multiplication arithmetic 
  boolean testMul(Tester t) {
    return t.checkExpect(new Mul().apply(5.0, 10.0), 50.0)
        && t.checkExpect(new Mul().apply(-3.0, 4.0), -12.0);
  }

  // test Division arithmetic
  boolean testDiv(Tester t) {
    return t.checkExpect(new Div().apply(56.0, 7.0), 8.0)
        && t.checkExpect(new Div().apply(56.0, -8.0), -7.0);

  }

  // test evalvisitor
  boolean testEvalVisitor(Tester t) {
    return t.checkExpect(new EvalVisitor().apply(Plus), 12.0)
        && t.checkExpect(new EvalVisitor().apply(Minus), -8.0)
        && t.checkExpect(new EvalVisitor().apply(Mul), -18.0)
        && t.checkExpect(new EvalVisitor().apply(Div), 5.0)
        && t.checkExpect(new EvalVisitor().apply(Multiple), 10.0);

  }

  // test printvisitor
  boolean testPrintVisitor(Tester t) {
    return t.checkExpect(new PrintVisitor().apply(Plus), "(Plus 2.0 10.0)")
        && t.checkExpect(new PrintVisitor().apply(Minus), "(Minus 2.0 10.0)")
        && t.checkExpect(new PrintVisitor().apply(Multiple), "(Plus (Plus 2.0 10.0) (Neg 2.0))");
  }

  // test doubler visitor
  boolean testDoublerVisitor(Tester t) {
    return t.checkExpect(new DoublerVisitor().apply(const1), new Const(4.0)) && t.checkExpect(
        new DoublerVisitor().apply(Sqr1), new UnaryFormula(new Sqr(), "Sqr", new Const(4.0)));
  }

  // test non Negative num
  boolean testNoNegativeResults(Tester t) {
    return t.checkExpect(new NoNegativeResults().apply(Neg1), false)
        && t.checkExpect(new NoNegativeResults().apply(const1), true);
  }
}
