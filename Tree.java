
import java.awt.Color;

import tester.*; // The tester library
import javalib.worldimages.*; // images, like RectangleImage or OverlayImages
import javalib.funworld.*; // the abstract World class and the big-bang library
import javalib.worldcanvas.WorldCanvas;

interface ITree {
  // draws given tree
  WorldImage draw();
  
  // checks for drooping stems
  boolean isDrooping();
  
  boolean isDroopingHelper();

  // combines 2 trees
  ITree combine(int leftLength, int rightLength, double leftTheta,
      double rightTheta, ITree otherTree);
  
  // returns total width of image
  double getWidth();
  
  // returns total width of left side of a ITree
  double getLeftWidth(); //

  // returns total width of right side of a ITree
  double getRightWidth();
  
  // caculates total width of both sides
  double getWidthHelper(double left, double right);
  
  // rotates given ITree
  ITree rotateITree(double stemTheta);
}


class Leaf implements ITree {
  int size; // represents the radius of the leaf
  Color color; // the color to draw it

  Leaf(int size, Color color) {
    this.size = size;
    this.color = color;
  }
  
  /* TEMPLATE:
   * Fields:
   * ... this.size; ... -- int
   * ... this.color; ... -- Color
   * Methods:
   * ... this.draw() ... -- WorldImage
   * ... this.isDrooping() ... -- boolean
   * ... this.isDroopingHelper() -- boolean
   * ... this.combine(int leftLength, int rightLength, double leftTheta,
   *          double rightTheta, ITree otherTree) ... -- ITree
   * ... this.rotateITree(double stemTheta)... -- ITree
   * ... this.getWidth()... -- double
   * ... this.getLeftWidth() ... -- double
   * ... this.getRightWidth() ... -- double
   * ... this.getWidthHelper(double left, double right)  ... -- double
   * */
  
  //draws given tree
  public WorldImage draw() {
    return new CircleImage(this.size, OutlineMode.SOLID, this.color);
  }

  //checks for drooping stems
  public boolean isDrooping() {
    return false;
  }

  public boolean isDroopingHelper() {
    return false;
  }

  // combines 2 trees
  public ITree combine(int leftLength, int rightLength, double leftTheta,
      double rightTheta, ITree otherTree) {
      
    /*  Fields on parameters:
    *     n/a
    *  Methods on parameters:
    *  ... otherTree.draw() ... WorldImage
    *  ... otherTree.getWidth() ... double
    *  ... otherTree.getLeftWidth() ... double
    *  ... otherTree.getRightWidth() ... double
    *  ... otherTree.getWidthHelper() ... double
    *  ... otherTree.rotateITree(double stemTheta) ... ITree
    *  ... otherTree.combine(int leftLength, int rightLength, double leftTheta,
    *      double rightTheta, ITree otherTree) ... ITree
    *
    */
    ITree rotateLeft = this.rotateITree(leftTheta);
    ITree rotateRight = otherTree.rotateITree(rightTheta);
    ITree branch = new Branch(leftLength, rightLength, leftTheta,
            rightTheta, rotateLeft, rotateRight);
    return branch;
  }
  
  //rotates given ITree
  public ITree rotateITree(double stemTheta) {
    return this;
  }

  //returns total width of image
  public double getWidth() {
    return 0.0;
  }

  // returns total width of left side of a ITree
  public double getLeftWidth() {
    return 0;
  }

  // returns total width of right side of a ITree
  public double getRightWidth() {
    return 0;
  }

  // caculates total width of both sides
  public double getWidthHelper(double left, double right) {
    return 0;
  }
}

class Stem implements ITree {
  // How long this stick is
  int length;
  // The angle (in degrees) of this stem, relative to the +x axis
  double theta;
  // The rest of the tree
  ITree tree;
  
  /* TEMPLATE:
   * Fields:
   * ... this.length; ... -- int
   * ... this.theta; ... -- double
   * ... this.tree; ... ITree
   * Methods:
   * ... this.draw() ... -- WorldImage
   * ... this.isDrooping() ... -- boolean
   * ... this.isDroopingHelper() -- boolean
   * ... this.combine(int leftLength, int rightLength, double leftTheta,
   *          double rightTheta, ITree otherTree) ... -- ITree
   * ... this.rotateITree(double stemTheta)... -- ITree
   * ... this.getWidth()... -- double
   * ... this.getLeftWidth() ... -- double
   * ... this.getRightWidth() ... -- double
   * ... this.getWidthHelper(double left, double right)  ... -- double
   * 
   * Methods for fields:
   * ... this.tree.draw() ... -- WorldImage
   * ... this.tree.isDrooping() ... -- boolean
   * ... this.tree.isDroopingHelper() -- boolean
   * ... this.tree.combine(int leftLength, int rightLength, double leftTheta,
   *          double rightTheta, ITree otherTree) ... -- ITree
   * ... this.tree.rotateITree(double stemTheta)... -- ITree
   * ... this.tree.getWidth()... -- double
   * ... this.tree.getLeftWidth() ... -- double
   * ... this.tree.getRightWidth() ... -- double
   * ... this.tree.getWidthHelper(double left, double right)  ... -- double
   * 
   */
  
  Stem(int length, double theta, ITree tree) {
    this.length = length;
    this.theta = theta;
    this.tree = tree;
  }
  
  //draws given tree
  public WorldImage draw() {
    double mathSine = Math.sin(Math.toRadians(this.theta));
    double mathCosine = Math.cos(Math.toRadians(this.theta));
    int x = (int) (this.length * mathCosine);
    int y = (int) (this.length * mathSine);
    
    LineImage stemLine = new LineImage(new Posn(x, -y), Color.BLACK);
    WorldImage stemPinhole = stemLine.movePinhole(x / 2, -y / 2);
    OverlayImage overlayImage = new OverlayImage(tree.draw(), stemPinhole );
    
    return overlayImage.movePinhole(-x, y);
  }

  //checks for drooping stems
  public boolean isDrooping() {
    // if the stem is upward facing
    if (this.isDroopingHelper()) {
      return true;
    }
    else {
      return this.tree.isDrooping();
    }
  }

  public boolean isDroopingHelper() {
    boolean scene1 = (this.theta >= 0) && (this.theta % 360 > 180);
    boolean scene2 = (this.theta < 0) && (this.theta % 360 < 180);
    
    return (scene1) || (scene2);
  }

  // combines 2 trees
  public ITree combine(int leftLength, int rightLength, double leftTheta,
      double rightTheta, ITree otherTree) {
    
    /*  Fields on parameters:
    *     n/a
    *  Methods on parameters:
    *  ... otherTree.draw() ... WorldImage
    *  ... otherTree.getWidth() ... double
    *  ... otherTree.getLeftWidth() ... double
    *  ... otherTree.getRightWidth() ... double
    *  ... otherTree.getWidthHelper() ... double
    *  ... otherTree.rotateITree(double stemTheta) ... ITree
    *  ... otherTree.combine(int leftLength, int rightLength, double leftTheta,
    *      double rightTheta, ITree otherTree) ... ITree
    */
    ITree rotateLeft = this.rotateITree(leftTheta - 90);
    ITree rotateRight = otherTree.rotateITree(rightTheta - 90);
    return new Branch(leftLength, rightLength, leftTheta, rightTheta, rotateLeft, rotateRight);
  }

  //rotates given ITree
  public ITree rotateITree(double stemTheta) {
    double thetaAlgo = this.theta + (stemTheta);
    ITree rotateStem = this.tree.rotateITree(stemTheta);
    
    ITree stemITree = new Stem(this.length, thetaAlgo, rotateStem);
    return stemITree;
  }

  //returns total width of image
  public double getWidth() {
    return this.getWidthHelper(this.getLeftWidth(), this.getRightWidth());
  }
  
  // returns total width of left side of a ITree
  public double getLeftWidth() {
    double x = this.length * Math.cos(Math.toRadians(theta));
    double leftWide = x + this.tree.getLeftWidth();
    return leftWide;
  }
  
  // returns total width of right side of a ITree
  public double getRightWidth() {
    double x = this.length * Math.cos(Math.toRadians(theta));
    double rightWide = x + this.tree.getRightWidth();
    return rightWide;
  }

  // caculates total width of both sides
  public double getWidthHelper(double left, double right) {
    if ( (left > 0 && right > 0) || (left < 0 && right < 0)) {
      double absLeft = Math.abs(left);
      double absRight = Math.abs(right);
      return Math.max(absLeft, absRight);
    }
    else {
      return Math.abs(right - left);
    }
  }
}

class Branch implements ITree {
  // How long the left and right branches are
  int leftLength;
  int rightLength;
  // The angle (in degrees) of the two branches, relative to the +x axis,
  double leftTheta;
  double rightTheta;
  // The remaining parts of the tree
  ITree left;
  ITree right;
  
  /* TEMPLATE:
   * Fields:
   * ... this.leftLength; ... -- int
   * ... this.rightLength; ... -- int
   * ... this.leftTheta; ... -- double
   * ... this.rightTheta; ... -- double
   * ... this.left; ... -- ITree
   * ... this.right; ... ITree
   * Methods:
   * ... this.draw() ... -- WorldImage
   * ... this.isDrooping() ... -- boolean
   * ... this.isDroopingHelper() -- boolean
   * ... this.combine(int leftLength, int rightLength, double leftTheta,
   *          double rightTheta, ITree otherTree) ... -- ITree
   * ... this.rotateITree(double stemTheta)... -- ITree
   * ... this.getWidth()... -- double
   * ... this.getLeftWidth() ... -- double
   * ... this.getRightWidth() ... -- double
   * ... this.getWidthHelper(double left, double right)  ... -- double
   * 
   * Methods for fields:
   * ... this.left.draw() ... -- WorldImage
   * ... this.left.isDrooping() ... -- boolean
   * ... this.left.isDroopingHelper() -- boolean
   * ... this.left.combine(int leftLength, int rightLength, double leftTheta,
   *          double rightTheta, ITree otherTree) ... -- ITree
   * ... this.left.rotateITree(double stemTheta)... -- ITree
   * ... this.left.getWidth()... -- double
   * ... this.left.getLeftWidth() ... -- double
   * ... this.left.getRightWidth() ... -- double
   * ... this.left.getWidthHelper(double left, double right)  ... -- double
   * ... this.right.draw() ... -- WorldImage
   * ... this.right.isDrooping() ... -- boolean
   * ... this.right.isDroopingHelper() -- boolean
   * ... this.right.combine(int leftLength, int rightLength, double leftTheta,
   *          double rightTheta, ITree otherTree) ... -- ITree
   * ... this.right.rotateITree(double stemTheta)... -- ITree
   * ... this.right.getWidth()... -- double
   * ... this.right.getLeftWidth() ... -- double
   * ... this.right.getRightWidth() ... -- double
   * ... this.right.getWidthHelper(double left, double right)  ... -- double
   * 
   */

  Branch(int leftLength, int rightLength, double leftTheta, double rightTheta,
      ITree left, ITree right) {
    this.leftLength = leftLength;
    this.rightLength = rightLength;
    this.leftTheta = leftTheta;
    this.rightTheta = rightTheta;
    this.left = left;
    this.right = right;
  }

  //draws given tree
  public WorldImage draw() {
    ITree stemLeft = new Stem(this.leftLength, this.leftTheta, this.left);
    ITree stemRight = new Stem(this.rightLength, this.rightTheta, this.right);
    return new OverlayImage(stemLeft.draw(), stemRight.draw());
  }

  //checks for drooping stems
  public boolean isDrooping() {
    return this.isDroopingHelper() || this.left.isDrooping() || this.right.isDrooping();
  }

  public boolean isDroopingHelper() {
    boolean scene1 = (this.leftTheta >= 0) && (this.leftTheta % 360 > 180);
    boolean scene2 = (this.leftTheta < 0) && (this.leftTheta % 360 < 180);
    boolean scene3 = (this.rightTheta >= 0) && (this.rightTheta % 360 > 180);
    boolean scene4 = (this.rightTheta < 0) && (this.rightTheta % 360 < 180);
    return (scene1) || (scene2) && (scene3) || (scene4);
  }

  // combines 2 trees
  public ITree combine(int leftLength, int rightLength, double leftTheta,
          double rightTheta, ITree otherTree) {
    /*  Fields on parameters:
    *     n/a
    *  Methods on parameters:
    *  ... otherTree.draw() ... WorldImage
    *  ... otherTree.getWidth() ... double
    *  ... otherTree.getLeftWidth() ... double
    *  ... otherTree.getRightWidth() ... double
    *  ... otherTree.getWidthHelper() ... double
    *  ... otherTree.rotateITree(double stemTheta) ... ITree
    *  ... otherTree.combine(int leftLength, int rightLength, double leftTheta,
    *      double rightTheta, ITree otherTree) ... ITree
    */
    ITree rotateLeft = this.rotateITree(leftTheta - 90);
    ITree rotateRight = otherTree.rotateITree(rightTheta - 90);
    return new Branch(leftLength, rightLength, leftTheta, rightTheta, rotateLeft, rotateRight);
  }

  //rotates given ITree
  public ITree rotateITree(double stemTheta) {
    double algoLeft = this.leftTheta + stemTheta;
    double algoRight = this.rightTheta + stemTheta;
    ITree rotateLeft = this.left.rotateITree(stemTheta);
    ITree rotateRight = this.right.rotateITree(stemTheta);
      
    ITree branch = new Branch(this.leftLength, this.rightLength, 
              algoLeft, algoRight, rotateLeft, rotateRight);
    return branch;
  }

  //returns total width of image
  public double getWidth() {
    return this.getWidthHelper(this.getLeftWidth(), this.getRightWidth());
  }
  
  // returns total width of left side of a ITree
  public double getLeftWidth() {
    double leftX = this.leftLength * Math.cos(Math.toRadians(leftTheta));
    double rightX = this.rightLength * Math.cos(Math.toRadians(rightTheta));
    
    double minLeft = leftX + this.left.getLeftWidth();
    double minRight = rightX + this.right.getLeftWidth();
    return Math.min(minRight, minLeft);
  }

  // returns total width of right side of a ITree
  public double getRightWidth() {
    double leftX = this.leftLength * Math.cos(Math.toRadians(leftTheta));
    double rightX = this.rightLength * Math.cos(Math.toRadians(rightTheta));
    
    double maxLeft = leftX + this.left.getLeftWidth();
    double maxRight = rightX + this.right.getLeftWidth();
    return Math.max(maxRight, maxLeft);
  }

  // caculates total width of both sides
  public double getWidthHelper(double left, double right) {
    if ( (left > 0 && right > 0) || (left < 0 && right < 0) ) {
      double leftAbs = Math.abs(left);
      double rightAbs = Math.abs(right);
      return Math.max(leftAbs, rightAbs);
    }
    else {
      return Math.abs(right - left);
    }
  }
}

class ExamplesTree {
  ExamplesTree() {
  }

  ITree leaf = new Leaf(10, Color.RED);
  
  ITree leaf1 = new Leaf(30, Color.GREEN);
  
  ITree leaf2 = new Leaf(12, Color.RED);

  ITree stem = new Stem(30, 90, this.leaf);

  ITree tree1 = new Branch(30, 30, 135, 40, new Leaf(10, Color.RED), new Leaf(15, Color.BLUE));
  
  ITree tree2 = new Branch(30, 30, 115, 65, new Leaf(15, Color.GREEN), new Leaf(8, Color.ORANGE));

  ITree stem1 = new Stem(40, 90, this.tree1);

  ITree stem2 = new Stem(50, 90, this.tree2);

  ITree droopTree = new Branch(30, 30, 181, 65,
          new Leaf(15, Color.GREEN),
          new Leaf(8, Color.ORANGE));
  
  ITree connectedBranches = new Branch(1, 1, 45, 130, leaf1,
          new Branch(1, 1, 45, 130, leaf1, leaf2));
  
  ITree branchTrees = new Branch(40, 50, 150, 30, this.tree1, this.tree2);
  
  ITree branch1 = new Branch(80, 200, 100, 60, leaf1, leaf2);
  
  ITree branch2 = new Branch(70, 100, 120, 340, leaf1, branch1);
  
  ITree combineTree1 = this.tree1.combine(40, 50, 150, 30, this.tree2);

  ITree combineTree2 = this.tree1.combine(40, 50, 150, 30, this.combineTree1);

  ITree combineTree3 = this.tree2.combine(40, 50, 150, 30, this.combineTree2);

  boolean testImages(Tester t) {
    return t.checkExpect(
        new RectangleImage(30, 20, OutlineMode.SOLID, Color.GRAY),
        new RectangleImage(30, 20, OutlineMode.SOLID, Color.GRAY));
  }

  boolean testDrawTree(Tester t) {
    WorldCanvas c = new WorldCanvas(500, 500);
    WorldScene s = new WorldScene(500, 500);
    return c.drawScene(s.placeImageXY(this.combineTree1.draw(), 250, 250)) && c.show();
  }

  boolean testIsDrooping(Tester t) {
    return t.checkExpect(this.tree1.isDrooping(), false)
            && t.checkExpect(this.droopTree.isDrooping(), true);
  }
  
  
  boolean testGetWidth(Tester t) {
    return t.checkExpect(this.tree1.getWidth(), 44.19453672916576);
  }
  /* */
}




