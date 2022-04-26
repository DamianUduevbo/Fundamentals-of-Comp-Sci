import tester.Tester;

class BagelRecipe {
  double flour;
  double water;
  double yeast;
  double salt;
  double malt;
  
  /* produces a perfect bagel recipe using flour, water, yeast, salt and malt where:
  * the weight of the flour should be equal to the weight of the water
  * the weight of the yeast should be equal the weight of the malt
  * the weight of the salt + yeast should be 1/20th the weight of the flour
  */
  BagelRecipe(double flour, double water, double yeast, double salt, double malt) {
    if (Math.abs(flour - water) < 0.001) {
      this.flour = flour;
      this.water = water;
    }
    else {
      throw new IllegalArgumentException("Invalid water and flour weights");
    }
    
    if (Math.abs(yeast - malt) < 0.001) {
      this.yeast = yeast;
      this.malt = malt;
    }
    else {
      throw new IllegalArgumentException("Invalid malt and yeast weights");
    }
    
    if (Math.abs(20 * (salt + yeast) - flour) < 0.001) {
      this.salt = salt;
    }
    else {
      throw new IllegalArgumentException("Invalid salt and yeast weights");
    }
  }
  /* 
   * TEMPLATE FIELDS: 
   * ... this.flour ... -- double
   * ... this.water ... -- double
   * ... this.yeast ... -- double
   * ... this.salt ... -- double
   * ... this.malt ... -- double
   * 
   * METHODS:
   * ... this.sameRecipe(BagelRecipe) ... -- boolean
   * ... this.sameRecipeHelper(BagelRecipe) ... -- boolean
   * METHODS for FIELDS:
   * ... this.sameRecipeHelper(BagelRecipe) ... -- boolean
   */
  
  // produces a perfect bagel recipe using weights of flour and yeast
  BagelRecipe(double flour, double yeast) {
    this(flour, flour, yeast, ( (flour / 20) - yeast), yeast);
  }
  /* 
   * TEMPLATE FIELDS: 
   * ... this.flour ... -- double
   * ... this.yeast ... -- double
   * 
   * METHODS:
   * ... this.sameRecipe(BagelRecipe) ... -- boolean
   * ... this.sameRecipeHelper(BagelRecipe) ... -- boolean
   * METHODS for FIELDS:
   * ... this.sameRecipeHelper(BagelRecipe) ... -- boolean
   */
  
  // produce a perfect recipe using flour, yeast and salt as volumes rather than weights
  BagelRecipe(double flour, double yeast, double salt) {
    this((flour * 17) / 4, (flour * 17) / 4, (yeast * 5) / 48,
        (salt * 10) / 48, (yeast * 5) / 48);
    
    double saltYeastAlgo = (( ((salt * 2) + yeast) * (20 * 5) ) / 48 );
    double flourAlgo = ((flour * 17) / 4);
    
    if (Math.abs( saltYeastAlgo - flourAlgo) >= 0.001) {
      throw new IllegalArgumentException("Invalid volumes");
    }
  }
  /* 
  * TEMPLATE FIELDS: 
  * ... this.flour ... -- double
  * ... this.yeast ... -- double
  * ... this.salt ... -- double
  * 
  * METHODS:
  * ... this.sameRecipe(BagelRecipe) ... -- boolean
  * ... this.sameRecipeHelper(BagelRecipe) ... -- boolean
  * METHODS for FIELDS:
  * ... this.sameRecipeHelper(BagelRecipe) ... -- boolean
  */
  
  // determines if two BagelRecipe's are similar to each other
  boolean sameRecipe(BagelRecipe other) {
    return this.sameRecipeHelper(other);
  }
  
  // compares each field in one BagelRecipe to another
  boolean sameRecipeHelper(BagelRecipe other) {
    boolean flourBool = (Math.abs(this.flour - other.flour) < 0.001);
    boolean waterBool = (Math.abs(this.water - other.water) < 0.001);
    boolean yeastBool = (Math.abs(this.yeast - other.yeast) < 0.001);
    boolean saltBool = (Math.abs(this.salt - other.salt) < 0.001);
    boolean maltBool = (Math.abs(this.malt - other.malt) < 0.001);

    return flourBool && waterBool && yeastBool && saltBool && maltBool;
  }  
}

// examples of different BagelRecipe
class ExamplesBagels {
  ExamplesBagels() {
  }

  BagelRecipe bagel = new BagelRecipe(40, 40, 1, 1, 1);
  BagelRecipe bagel1 = new BagelRecipe(40, 1);
  BagelRecipe bagel2 = new BagelRecipe(56, 6, 56);
  BagelRecipe bagel3 = new BagelRecipe(56, 6, 56);
  
  // tests sameRecipe method
  boolean testSameRecipe(Tester t) {
    return t.checkExpect(this.bagel.sameRecipe(bagel1), false)
        && t.checkExpect(this.bagel1.sameRecipe(bagel2), false)
        && t.checkExpect(this.bagel2.sameRecipe(bagel3), true);
  }
}