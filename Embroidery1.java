interface IMotif {

  // find the difficulty of the motif
  double difficultyTotal();

  // get the number of motifs
  int getTotal();

  // get the info - name and what type of motif
  String getInfo();

}

interface ILoMotif {
  double difficultyTotal();

  int getTotal();

  String getInfo();

}

//represents CrossStitchMotif 
class CrossStitchMotif implements IMotif {
  String description;
  double difficulty;

  CrossStitchMotif(String description, double difficulty) {
    this.description = description;
    this.difficulty = difficulty;
  }
  /*
   * Template Fields: ...this.description... --String ...this.difficulty...
   * --double Methods: ...difficultyTotal()... --double ...getTotal()... --int
   * ...getInfo() ... --String
   */

  // returns the difficulty of a CrossStitchMotif
  public double difficultyTotal() {
    return this.difficulty;
  }

  // returns the count of 1 for a CrossStitchMotif
  public int getTotal() {
    return 1;
  }

  // returns the description and that it is a CrossStitchMotif
  public String getInfo() {
    return this.description + " (cross stitch)";
  }
}

// represents a ChainStitchMotif
class ChainStitchMotif implements IMotif {
  String description;
  double difficulty;

  ChainStitchMotif(String description, double difficulty) {
    this.description = description;
    this.difficulty = difficulty;
  }
  /*
   * Template Fields: ...this.description... --String ...this.difficulty...
   * --double Methods: ...difficultyTotal()... --double ...getTotal()... --int
   * ...getInfo() ... --String
   */

  // returns the difficulty of a ChainStitchMotif
  public double difficultyTotal() {
    return this.difficulty;
  }

  // returns the count of 1 for a ChainStitchMotif
  public int getTotal() {
    return 1;
  }

  // returns the description and that it is a ChainStitchMotif
  public String getInfo() {
    return this.description + " (chain stitch)";
  }
}

//represents a GroupMotif 
class GroupMotif implements IMotif {
  String description;
  ILoMotif motifs;

  GroupMotif(String description, ILoMotif motifs) {
    this.description = description;
    this.motifs = motifs;
  }
  /*
   * Template Fields: ...this.description... --String ...this.difficulty...
   * --double Methods: ...difficultyTotal()... --double ...getTotal()... --int
   * ...getInfo()... --String Methods for Fields:
   * ...this.motifs.difficultyTotal()... --double ...this.motifs.getTotal()...
   * --int ...this.motifs.getInfo()... --String
   */

  // returns the total difficulty of a GroupMotif
  public double difficultyTotal() {
    return this.motifs.difficultyTotal();
  }

  // returns the total count for the GroupMotif
  public int getTotal() {
    return this.motifs.getTotal();
  }

  // returns the description and what type of motif it is
  public String getInfo() {
    return this.motifs.getInfo();
  }
}

//represents an empty list of Motifs 
class MtLoMotif implements ILoMotif {
  MtLoMotif() {
  }
  /*
   * Template Fields: ...this.description... --String ...this.difficulty...
   * --double Methods: ...difficultyTotal()... --double ...getTotal()... --int
   * ...getInfo() ... --String
   * 
   */

  // returns the total difficulty of an empty list
  // this would be 0 for an empty list
  public double difficultyTotal() {
    return 0.0;
  }

  // return the total number of Motifs
  // this would be 0 because it is an empty list
  public int getTotal() {
    return 0;
  }

  // return the information about the motifs
  // this would be nothing as the list is empty
  public String getInfo() {
    return "";
  }
}

//represents a list with motifs 
class ConsLoMotif implements ILoMotif {
  IMotif first;
  ILoMotif rest;

  ConsLoMotif(IMotif first, ILoMotif rest) {
    this.first = first;
    this.rest = rest;
  }
  /*
   * Template Fields: ...this.description... --String ...this.difficulty...
   * --double Methods: ...difficultyTotal()... --double ...getTotal()... --int
   * ...getInfo()... --String Methods for fields:
   * ...this.first.difficultyTotal()... --double ...this.rest.difficultyTotal()...
   * --double ...this.first.getTotal()... --int ...this.rest.getTotal()... --int
   * ...this.first.getInfo()... --String ...this.rest.getInfo()... --String
   */

  // sums the difficulties of all the motifs
  public double difficultyTotal() {
    return this.first.difficultyTotal() + this.rest.difficultyTotal();
  }

  // gets the number of motifs in the list
  public int getTotal() {
    return this.first.getTotal() + this.rest.getTotal();
  }

  // gets the information of all the motifs in the list
  public String getInfo() {
    if (this.rest.getInfo().equals("")) {
      return this.first.getInfo();
    }
    else {
      return this.first.getInfo() + ", " + this.rest.getInfo();
    }

  }
}

// represents an Embroidery
class EmbroideryPiece {
  String name;
  IMotif motif;

  EmbroideryPiece(String name, IMotif motif) {
    this.name = name;
    this.motif = motif;
  }
  /*
   * Template: Fields: ...this.name... -- string ...this.motif ... -- IMotif
   * Methods: ...averageDifficulty()... --double ...embroideryInfo()... --String
   * Methods for Fields: ...this.motif.getTotal() ... --int
   * ...this.motif.difficultyTotal... --double ...this.motif.getInfo()... --String
   */

  // find the averageDifficulty of all the motifs in the Embroidery Piece
  double averageDifficulty() {
    if (this.motif.getTotal() == 0) {
      return 0.0;
    }
    else {
      return this.motif.difficultyTotal() / this.motif.getTotal();
    }

  }

  // Gets all the information of the motifs in the Embroidery Piece
  String embroideryInfo() {
    return this.name + ": " + this.motif.getInfo() + ".";
  }
}

class ExamplesEmbroidery {
  ExamplesEmbroidery() {
  }

  ILoMotif mtList = new MtLoMotif();

  IMotif rose = new CrossStitchMotif("rose", 5.0);
  IMotif poopy = new ChainStitchMotif("poppy", 4.75);
  IMotif daisy = new CrossStitchMotif("daisy", 3.2);

  ILoMotif consMotifs2 = new ConsLoMotif(this.rose,
      new ConsLoMotif(this.poopy, new ConsLoMotif(this.daisy, this.mtList)));

  IMotif bird = new CrossStitchMotif("bird", 4.5);
  IMotif tree = new ChainStitchMotif("tree", 3.0);

  GroupMotif flowerGroup = new GroupMotif("flowers", consMotifs2);
  ILoMotif consMotifs1 = new ConsLoMotif(this.bird,
      new ConsLoMotif(this.tree, new ConsLoMotif(this.flowerGroup, this.mtList)));

  GroupMotif natureGroup = new GroupMotif("nature", consMotifs1); // whole thing

  EmbroideryPiece pillowCover = new EmbroideryPiece("Pillow Cover", natureGroup);
}
