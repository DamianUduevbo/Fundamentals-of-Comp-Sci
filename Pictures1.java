import tester.*;


interface IPicture {
  // gets the width of the overall picture
  int getWidth();

  // gets the total number of shapes in a picture
  int countShapes();

  // gets the number of combo operations done in a picture
  int comboDepth();

  // takes the given picture and mirrors it
  IPicture mirror();

  // creates a string of the elements in the picture
  String pictureRecipe(int depth);

}

interface IOperation {
  int getWidth();

  int countShapes();

  int comboDepth();

  IOperation mirror();

  String getPictureName(int depth);
}

//represents a combo in a picture 
class Combo implements IPicture {
  String kind;
  int size;
  String name;
  IOperation operation;

  Combo(String name, IOperation operation) {
    this.name = name;
    this.operation = operation;
  }

  /*
   * Template Fields ...this.name... --string ...this.operation --operation
   * Methods ...getWidth()... -- int ...countShapes()... -- int ...comboDepth()...
   * -- int ...mirror()... -- IPicture ...pictureRecipe... -- String Method Fields
   * ...this.operation.getWidth()... --int ...this.operation.countShapes()...
   * --int ...this.operation.comboDepth()... --int ...this.operation.mirror()...
   * --IPicture
   */
  public int getWidth() {
    return this.operation.getWidth();
  }

  public int countShapes() {
    return this.operation.countShapes();
  }

  public int comboDepth() {
    return this.operation.comboDepth();
  }

  public IPicture mirror() {
    IPicture mirrorCombo = new Combo(this.name, this.operation.mirror());
    return mirrorCombo;

  }

  public String pictureRecipe(int depth) {
    if (depth <= 0) {
      return this.name;
    }
    else {
      return this.operation.getPictureName(depth);
    }
  }
}

//represents the shape of the picture 
class Shape implements IPicture {
  String kind;
  int size;

  Shape(String kind, int size) {
    this.kind = kind;
    this.size = size;
  }

  /*
   * Template Fields: ...this.kind... -- string ...this.size... -- int Methods:
   * ...getWidth()... -- int ...countShapes()... -- int ...comboDepth()... -- int
   * ...mirror()... -- IPicture ...pictureRecipe... -- String
   */
  public int getWidth() {
    return this.size;
  }

  public int countShapes() {
    return 1;
  }

  public int comboDepth() {
    return 0;
  }

  public IPicture mirror() {
    return new Shape(this.kind, this.size);
  }

  public String pictureRecipe(int depth) {
    return this.kind;
  }
}

//places two images next to eachother 
class Beside implements IOperation {
  IPicture picture1;
  IPicture picture2;

  Beside(IPicture picture1, IPicture picture2) {
    this.picture1 = picture1;
    this.picture2 = picture2;
  }
  /*
   * Template Fields ...this.picture1... --IPicture ...this.picture2... --IPicture
   * Methods: ...getWidth()... -- int ...countShapes()... -- int
   * ...comboDepth()... -- int ...mirror()... -- IPicture ...pictureRecipe... --
   * String Methods for Fields: ...this.picture1.getWidth()... -- int
   * ...this.picture2.getWidth()... --int ...this.picture1.countShapes() -- int
   * ...this.picture2.countShapes()... -- int ...this.picture1.comboDepth()...
   * --int ...this.picture2.comboDepth()... --int ...this.picture1.mirror()...
   * --IOperation ...this.picture2.mirror()... --IOperation
   * ...this.picture1.pictureRecipe(depth - 1)... --String
   * ...this.picture2.pictureRecipe(depth - 1)... --String
   */

  public int getWidth() {
    int width = this.picture1.getWidth() + this.picture2.getWidth();
    return width;
  }

  public int countShapes() {
    int count = this.picture1.countShapes() + this.picture2.countShapes();
    return count;
  }

  public int comboDepth() {
    int depth1 = this.picture1.comboDepth();
    int depth2 = this.picture2.comboDepth();

    if (depth1 > depth2) {
      return depth1 + 1;
    }
    else {
      return depth2 + 1;
    }
  }

  public IOperation mirror() {
    // CHANGED HERE
    IOperation mirror = new Beside(this.picture2.mirror(), this.picture1.mirror());
    return mirror;
  }

  public String getPictureName(int depth) {
    String name1 = this.picture1.pictureRecipe(depth - 1);
    String name2 = this.picture2.pictureRecipe(depth - 1);
    return "beside(" + name1 + ", " + name2 + ")";
  }
}

//scales the given picture 
class Scale implements IOperation {
  IPicture picture;

  Scale(IPicture picture) {
    this.picture = picture;
  }

  /*
   * Template Fields ...this.picture... -- IPicture Methods ...getWidth()... --
   * int ...countShapes()... -- int ...comboDepth()... -- int ...mirror()... --
   * IPicture ...pictureRecipe... -- String
   * 
   */
  public int getWidth() {
    return this.picture.getWidth() * 2;
  }

  public int countShapes() {
    return this.picture.countShapes();
  }

  public int comboDepth() {
    return this.picture.comboDepth() + 1;
  }

  public IOperation mirror() {
    IOperation mirror = new Scale(this.picture.mirror());
    return mirror;
  }

  public String getPictureName(int depth) {
    return "scale(" + this.picture.pictureRecipe(depth - 1) + ")";
  }
}

//overlays one picture over the other 
class Overlay implements IOperation {
  IPicture topPicture;
  IPicture bottomPicture;

  Overlay(IPicture topPicture, IPicture bottomPicture) {
    this.topPicture = topPicture;
    this.bottomPicture = bottomPicture;

  }
  /*
   * Template Fields ...this.topPicture... --IPicture ...this.bottomPicture...
   * --IPicture Methods ...getWidth()... -- int ...countShapes()... --int
   * ...comboDepth()... --int ...mirror()... --IOperation ...getPictureName... --
   * String Methods of Fields ...this.topPicture.getWidth()... -- int
   * ...this.bottomPicture.getWidth() -- int ...this.topPicture.countShapes()...
   * --int ...this.bottomPicture.countShapes() --int
   * ...this.topPicture.comboDepth()...-- int
   * ...this.bottomPicture.comboDepth()... -- int ...this.topPicture.mirror()...
   * -- IOperation ...this.bottomPicture.mirror()... -- IOperation
   * ...this.topPicture.pictureRecipe(depth - 1)... --string
   * ...this.bottomPicture.pictureRecipe(depth - 1)... --string
   */

  public int getWidth() {
    int topLayer1 = this.topPicture.getWidth();
    int bottomLayer1 = this.bottomPicture.getWidth();

    if (bottomLayer1 < topLayer1) {
      return topLayer1;
    }
    else {
      return bottomLayer1;
    }
  }

  public int countShapes() {
    return this.topPicture.countShapes() + this.bottomPicture.countShapes();
  }

  public int comboDepth() {
    int topLayer2 = this.topPicture.comboDepth();
    int bottomLayer2 = this.bottomPicture.comboDepth();

    if (bottomLayer2 < topLayer2) {
      return topLayer2 + 1;
    }
    else {
      return bottomLayer2 + 1;
    }
  }

  public IOperation mirror() {
    IOperation mirror = new Overlay(this.topPicture.mirror(), this.bottomPicture.mirror());
    return mirror;
  }

  public String getPictureName(int depth) {
    return "overlay(" + this.topPicture.pictureRecipe(depth - 1) + ", "
        + this.bottomPicture.pictureRecipe(depth - 1) + ")";
  }
  
}

class ExamplesPicture {
  ExamplesPicture() {
  }

  IPicture square = new Shape("square", 30);
  IPicture circle = new Shape("circle", 20);

  IPicture bigCircle = new Combo("big circle", new Scale(this.circle));
  IPicture squareOnCircle = new Combo("square on circle", new Overlay(this.square, this.bigCircle));
  IPicture doubledSquareOnCircle = new Combo("doubled square on circle",
      new Beside(this.squareOnCircle, this.squareOnCircle));

  IPicture bigSquare = new Combo("big square", new Scale(this.square));
  IPicture circleOnSquare = new Combo("circle on square", new Overlay(this.circle, this.bigSquare));
  IPicture doubledCircleOnSquare = new Combo("doubled circle on square",
      new Beside(this.circleOnSquare, this.circleOnSquare));
  
  boolean ceComboDepth(Tester t) {
    return t.checkExpect( this.squareOnCircle.comboDepth(), 4);
  }

  boolean ceCountShape(Tester t) {
    return t.checkExpect( this.doubledSquareOnCircle.countShapes(), 2);
  }

  boolean ceMirror(Tester t) {
    return t.checkExpect( this.doubledSquareOnCircle.mirror(), 0);
  }

  boolean ceRecipe(Tester t) {
    return t.checkExpect( this.doubledSquareOnCircle.pictureRecipe(1), "");
  }
  
}
