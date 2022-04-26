import tester.*;

//Interface representing all the methods of Entertainment 
interface IEntertainment {
  // compute the total price of this Entertainment
  double totalPrice();

  // computes the minutes of entertainment of this IEntertainment
  int duration();

  // produce a String that shows the name and price of this IEntertainment
  String format();

  // is this IEntertainment the same as that one?
  boolean sameEntertainment(IEntertainment that);

  // checks whether the TV series is the same as the other
  boolean sameEntertainmentHelperT(TVSeries other);

  // checks if the podcast is the same
  boolean sameEntertainmentHelperP(Podcast other);

  // checks if the magazine is the same
  boolean sameEntertainmentHelperM(Magazine other);

}

// represents all the different entertainments 
abstract class AEntertainment implements IEntertainment {
  String name;
  double price;
  int installments;

  AEntertainment(String name, double price, int installments) {
    this.name = name;
    this.price = price;
    this.installments = installments;
  }

  // computes the total price of the Entertainment
  public double totalPrice() {
    return this.price * this.installments;
  }

  // calculates the duration of TVSeries and Podcasts
  public int duration() {
    return 50 * this.installments;
  }

  // formats the given entertainment
  public String format() {
    return this.name + ", " + this.price + ".";
  }

  // checks if the entertainments are the same
  public abstract boolean sameEntertainment(IEntertainment that);

  // checks the parameters of the TVSeries to see if they are the same
  public boolean sameEntertainmentHelperT(TVSeries other) {
    return false;
  }

  // checks the parameters of the Podcast to see if they are the same
  public boolean sameEntertainmentHelperP(Podcast other) {
    return false;
  }

  // checks the parameters of the Magazine to see if they are the same
  public boolean sameEntertainmentHelperM(Magazine other) {
    return false;
  }

}

class Magazine extends AEntertainment {
  String genre;
  int pages;

  Magazine(String name, double price, String genre, int pages, int installments) {
    super(name, price, installments);
    this.genre = genre;
    this.pages = pages;

  }
  /*
   * TEMPLATE Fields: ... this.name ... -- String ... this.price ... -- double ...
   * this.genre ... -- String ... this.pages ... -- int ... this.installments...
   * -- int Methods: ... this.totalPrice() ... -- double ... this.duration() ...
   * -- int ... this.format() ... -- String ...
   * this.sameEntertainment(IEntertainment)... -- boolean ...
   * this.sameEntertainmentHelperM(Magazine) ... -- boolean Methods for fields:
   * ... that.sameEntertainmentHelperM(Magazine) ... -- boolean
   */

  // computes the minutes of entertainment of this Magazine, (includes all
  // installments)
  public int duration() {
    return (5 * this.pages) * this.installments;
  }

  // is this Magazine the same as that IEntertainment?
  public boolean sameEntertainment(IEntertainment that) {
    return that.sameEntertainmentHelperM(this);
  }

  // checks whether the magazine have the same parameters and if they are the same
  public boolean sameEntertainmentHelperM(Magazine other) {
    return this.name.equals(other.name) && Math.abs(this.price - other.price) < 0.0001
        && this.genre.equals(other.genre) && this.pages == other.pages
        && this.installments == other.installments;

  }
}

//represents the TVSeries 
class TVSeries extends AEntertainment {
  String corporation;

  TVSeries(String name, double price, int installments, String corporation) {
    super(name, price, installments);
    this.corporation = corporation;
  }

  /*
   * TEMPLATE Fields: ... this.name ... -- String ... this.price ... -- double ...
   * this.corporation ... -- String ... this.installments... -- int Methods: ...
   * this.totalPrice() ... -- double ... this.duration() ... -- int ...
   * this.format() ... -- String ... this.sameEntertainment(IEntertainment)... --
   * boolean ... this.sameEntertainmentHelperT(TVSeries) ... -- boolean Methods
   * for fields: ... that.sameEntertainmentHelperT(TVSeries) ... -- boolean
   */

  // is this TVSeries the same as that IEntertainment?
  public boolean sameEntertainment(IEntertainment that) {
    return that.sameEntertainmentHelperT(this);
  }

  // checks if all the parameters are the same with the two TVSeries
  public boolean sameEntertainmentHelperT(TVSeries other) {
    return this.name.equals(other.name) && Math.abs(this.price - other.price) < 0.0001
        && this.installments == other.installments && this.corporation.equals(other.corporation);

  }

}

//Represents the podcasts 
class Podcast extends AEntertainment {

  Podcast(String name, double price, int installments) {
    super(name, price, installments);
  }

  /*
   * TEMPLATE Fields: ... this.name ... -- String ... this.price ... -- double ...
   * this.installments... -- int Methods: ... this.totalPrice() ... -- double ...
   * this.duration() ... -- int ... this.format() ... -- String ...
   * this.sameEntertainment(IEntertainment)... -- boolean ...
   * this.sameEntertainmentHelperP(Podcast) ... -- boolean Methods for fields: ...
   * that.sameEntertainmentHelperP(Podcast) ... -- boolean
   */

  // is this Podcast the same as that IEntertainment?
  public boolean sameEntertainment(IEntertainment that) {
    return that.sameEntertainmentHelperP(this);
  }

  // checks all of the parameters to ensure they are the same
  public boolean sameEntertainmentHelperP(Podcast other) {
    return this.name.equals(other.name) && Math.abs(this.price - other.price) < 0.0001
        && this.installments == other.installments;
  }
}

//Examples of the different Entertainment 
class ExamplesEntertainment {
  IEntertainment rollingStone = new Magazine("Rolling Stone", 2.55, "Music", 60, 12);
  IEntertainment houseOfCards = new TVSeries("House of Cards", 5.25, 13, "Netflix");
  IEntertainment serial = new Podcast("Serial", 0.0, 8);

  IEntertainment playboy = new Magazine("Playboy", 2.0, "Fan Fiction", 50, 10);
  IEntertainment drakenjosh = new TVSeries("Drake and Josh", 5, 20, "Nickeledeon");
  IEntertainment views = new Podcast("Views", 0, 18);

  IEntertainment drakenjosh2 = new TVSeries("Drake and Josh", 5, 20, "Nickeledeon");
  IEntertainment rollingStone2 = new Magazine("Rolling Stone", 2.55, "Music", 60, 12);
  IEntertainment views2 = new Podcast("Views", 0, 18);

  // testing total price method
  boolean testTotalPrice(Tester t) {
    return t.checkInexact(this.rollingStone.totalPrice(), 2.55 * 12, .0001)
        && t.checkInexact(this.houseOfCards.totalPrice(), 5.25 * 13, .0001)
        && t.checkInexact(this.serial.totalPrice(), 0.0, .0001)
        && t.checkInexact(this.playboy.totalPrice(), 2.0 * 10, 0.0001)
        && t.checkInexact(this.drakenjosh.totalPrice(), 5.0 * 20, 0.0001)
        && t.checkInexact(this.views.totalPrice(), 0.0, 0.0001);
  }

  // testing the duration method
  boolean testDuration(Tester t) {
    return t.checkExpect(this.rollingStone.duration(), (5 * 60) * 12)
        && t.checkExpect(this.drakenjosh.duration(), (50 * 20))
        && t.checkExpect(this.views.duration(), (50 * 18));
  }

  // testing the Format Method
  boolean testFormat(Tester t) {
    return t.checkExpect(this.rollingStone.format(), "Rolling Stone, 2.55.")
        && t.checkExpect(this.drakenjosh.format(), "Drake and Josh, 5.0.")
        && t.checkExpect(this.views.format(), "Views, 0.0.");

  }

  // testing for the same entertainment
  boolean testSameEntertainment(Tester t) {
    return t.checkExpect(this.rollingStone.sameEntertainment(drakenjosh), false)
        && t.checkExpect(this.drakenjosh.sameEntertainment(drakenjosh2), true)
        && t.checkExpect(this.serial.sameEntertainment(views), false)
        && t.checkExpect(this.views.sameEntertainment(views2), true)
        && t.checkExpect(this.rollingStone.sameEntertainment(playboy), false)
        && t.checkExpect(this.rollingStone.sameEntertainment(rollingStone2), true);
  }

}