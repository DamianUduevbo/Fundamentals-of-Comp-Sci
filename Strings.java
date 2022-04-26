import tester.*;

//to represent a list of Strings
interface ILoString {
  // combine all Strings in this list into one
  String combine();

  // sorts a list in alphabetical order
  ILoString sort();

  // checks whether the first item in ILoString comes before the rest element if
  // yes then return that function if not then return the other item and
  // recurisvely call it
  ILoString sortHelper(String s);

  // checks whether the list is sorted
  boolean isSorted();

  // compares the first element to the rest element and checks whether it is in
  // alphabetical order
  boolean isSortedHelper(String s);

  // takes two lists and puts one list as the odd elements in the interleave list
  // and puts the otherList in the even elements
  ILoString interleave(ILoString otherList);

  // merges two sorted list
  ILoString merge(ILoString sortedList);

  // checks the order of the elements in the list and then merges them
  ILoString mergeHelper(String s, ILoString sortedList);

  // takes a list and returns the reverse order of the list
  ILoString reverse();

  // takes the last element and puts it first and then reverse the rest of the
  // list
  ILoString reverseHelp(ILoString ilo);

  // checks whether the list has two of the same elements repeated e.g. aa bb cc
  boolean isDoubledList();

  // checks whether the first element and the second element are the same and
  // recursively calls the function
  boolean isDoubledListHelper(String s);

  // checks whether the list is the same in normal order and the reverse order
  boolean isPalindromeList();
}

//represents an empty list of strings
class MtLoString implements ILoString {
  MtLoString() {
  }

  public String combine() {
    return "";
  }

  public ILoString sort() {
    return this;
  }

  public ILoString sortHelper(String s) {
    return new ConsLoString(s, this);
  }

  public boolean isSorted() {
    return true;
  }

  public boolean isSortedHelper(String s) {
    return true;
  }

  public ILoString interleave(ILoString otherList) {
    return otherList;
  }

  public ILoString merge(ILoString sortedList) {
    return sortedList;
  }

  public ILoString mergeHelper(String s, ILoString sortedList) {
    return new ConsLoString(s, sortedList);
  }

  public ILoString reverse() {
    return this;
  }

  public ILoString reverseHelp(ILoString ilo) {
    return ilo;
  }

  public boolean isDoubledList() {
    return true;
  }

  public boolean isDoubledListHelper(String s) {
    return false;
  }

  public boolean isPalindromeList() {
    return true;
  }

}

//to represent a nonempty list of Strings
class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }
  /*
   * TEMPLATE FIELDS: ... this.first ... -- String ... this.rest ... -- ILoString
   * 
   * METHODS ... this.combine() ... -- String ... this.sort() ... -- ILoString ...
   * this.sortHelper()... -- ILoString ... this.isSorted()... -- boolean ...
   * this.isSortedHelper() ... -- boolean ... this.interleave()... -- ILoString
   * ... this.merge()... -- ILoString ... this.mergeHelper()... -- ILoString ...
   * this.reverse()... -- ILoString ... this.reverseHelper()... -- ILoString ...
   * this.isDoubledList()... -- boolean ... this.isDoubledListHelper() -- boolean
   * ... this.isPalindrome()... -- boolean
   * 
   * METHODS FOR FIELDS ... this.first.concat(String) ... -- String ...
   * this.first.compareTo(String) ... -- int ... this.rest.combine() ... -- String
   * ... this.rest.sort() ... -- ILoString ... this.rest.sortHelper(s) --
   * ILoString ... this.rest.isSorted() -- boolean ... this.rest.isSortedHelper()
   * -- boolean ... ILoString.interleave() -- ILoStrings ...
   * ILoString.mergeHelper(string, ILoStrings) --ILoStrings ...
   * this.ILoString.merge() -- ILoStrings ... this.reverseHelp() -- ILoStrings ...
   * this.rest.isDoubledListHelper() -- ILoStrings ... this.rest.isDoubledList()
   * -- ILoStrings ...
   */

  public String combine() {
    return this.first.concat(this.rest.combine());
  }

  public ILoString sort() {
    return this.rest.sort().sortHelper(this.first);
  }

  public ILoString sortHelper(String s) {
    if (this.first.toLowerCase().compareTo(s.toLowerCase()) > 0) {
      return new ConsLoString(s, this);
    }
    else {
      return new ConsLoString(this.first, this.rest.sortHelper(s));
    }
  }

  public boolean isSorted() {
    return this.rest.isSortedHelper(this.first) && this.rest.isSorted();
  }

  public boolean isSortedHelper(String s) {
    return this.first.toLowerCase().compareTo(s.toLowerCase()) >= 0;
  }

  public ILoString interleave(ILoString otherList) {
    return new ConsLoString(this.first, otherList.interleave(this.rest));
  }

  public ILoString merge(ILoString sortedList) {
    return sortedList.mergeHelper(this.first, this.rest);
  }

  public ILoString mergeHelper(String s, ILoString sortedList) {
    if (this.first.toLowerCase().compareTo(s.toLowerCase()) <= 0) {
      return new ConsLoString(this.first, this.rest.merge(new ConsLoString(s, sortedList)));
    }
    else {
      return new ConsLoString(s, this.merge(sortedList));
    }
  }

  public ILoString reverse() {
    return this.reverseHelp(new MtLoString());
  }

  public ILoString reverseHelp(ILoString ilo) {
    return this.rest.reverseHelp(new ConsLoString(this.first, ilo));
  }

  public boolean isDoubledList() {
    return this.rest.isDoubledListHelper(this.first);
  }

  public boolean isDoubledListHelper(String s) {
    if (this.first.equals(s)) {
      return this.rest.isDoubledList();
    }
    else {
      return false;
    }
  }

  public boolean isPalindromeList() {
    return this.interleave(this.reverse()).isDoubledList();
  }

}

//to represent examples for lists of strings
class ExamplesStrings {
  ExamplesStrings() {
  }

  MtLoString mtlist = new MtLoString();

  ConsLoString dnd = new ConsLoString("Dhilan", new ConsLoString("and", new ConsLoString("Damian",
      new ConsLoString("like", new ConsLoString("Fundies", this.mtlist)))));
  ConsLoString dndSorted = new ConsLoString("and",
      new ConsLoString("Damian", new ConsLoString("Dhilan",
          new ConsLoString("Fundies", new ConsLoString("like", this.mtlist)))));
  ConsLoString mary = new ConsLoString("Mary",
      new ConsLoString("have", new ConsLoString("Lambda", this.mtlist)));

  ConsLoString alphabet = new ConsLoString("b", new ConsLoString("d",
      new ConsLoString("f", new ConsLoString("j", new ConsLoString("e", this.mtlist)))));

  ConsLoString alphabetSorted = new ConsLoString("b", new ConsLoString("d",
      new ConsLoString("e", new ConsLoString("f", new ConsLoString("j", this.mtlist)))));

  ConsLoString aabb = new ConsLoString("a",
      new ConsLoString("a", new ConsLoString("b", new ConsLoString("b", this.mtlist))));

  ConsLoString aabbc = new ConsLoString("a", new ConsLoString("a",
      new ConsLoString("b", new ConsLoString("b", new ConsLoString("c", this.mtlist)))));

  ConsLoString palindromeExample = new ConsLoString("a", new ConsLoString("a", this.mtlist));

  ConsLoString notpalindromeExample = new ConsLoString("a", new ConsLoString("b", this.mtlist));

  //test the method combine for the lists of Strings
  boolean testSort(Tester t) {
    return t.checkExpect(this.mary.sort(),
        new ConsLoString("have", new ConsLoString("Lambda", new ConsLoString("Mary", this.mtlist))))
        && t.checkExpect(this.alphabet.sort(), new ConsLoString("b",
            new ConsLoString("d",
                new ConsLoString("e", new ConsLoString("f", new ConsLoString("j", this.mtlist))))))
        && t.checkExpect(this.mtlist.sort(), this.mtlist);

  }

  boolean testisSorted(Tester t) {
    return t.checkExpect(this.alphabetSorted.isSorted(), true)
        && t.checkExpect(this.alphabet.isSorted(), false)
        && t.checkExpect(this.mtlist.isSorted(), true);
  }

  boolean testInterleave(Tester t) {
    return t.checkExpect(mary.interleave(alphabet),
        new ConsLoString("Mary",
            new ConsLoString("b",
                new ConsLoString("have",
                    new ConsLoString("d",
                        new ConsLoString("Lambda",
                            new ConsLoString("f",
                                new ConsLoString("j", new ConsLoString("e", this.mtlist)))))))))
        && t.checkExpect(dnd.interleave(mary),
            new ConsLoString("Dhilan", new ConsLoString("Mary",
                new ConsLoString("and", new ConsLoString("have", new ConsLoString("Damian",
                    new ConsLoString("Lambda",
                        new ConsLoString("like", new ConsLoString("Fundies", this.mtlist)))))))))
        && t.checkExpect(dnd.interleave(mtlist),
            new ConsLoString("Dhilan", new ConsLoString("and", new ConsLoString("Damian",
                new ConsLoString("like", new ConsLoString("Fundies", this.mtlist))))));

  }

  boolean testMerge(Tester t) {
    return t
        .checkExpect(dndSorted.merge(alphabetSorted),
            new ConsLoString("and",
                new ConsLoString("b", new ConsLoString("d", new ConsLoString("Damian",
                    new ConsLoString("Dhilan", new ConsLoString("e", new ConsLoString("f",
                        new ConsLoString("Fundies",
                            new ConsLoString("j", new ConsLoString("like", this.mtlist)))))))))))
        && t.checkExpect(dndSorted.merge(this.mtlist), dndSorted);

  }

  boolean testReverse(Tester t) {
    return t.checkExpect(this.mary.reverse(),
        new ConsLoString("Lambda", new ConsLoString("have", new ConsLoString("Mary", this.mtlist))))
        && t.checkExpect(this.mtlist.reverse(), this.mtlist);
  }

  boolean testIsDoubledList(Tester t) {
    return t.checkExpect(aabb.isDoubledList(), true) && t.checkExpect(aabbc.isDoubledList(), false)
        && t.checkExpect(this.mtlist.isDoubledList(), true);

  }

  boolean testIsPalindrome(Tester t) {
    return t.checkExpect(palindromeExample.isPalindromeList(), true)
        && t.checkExpect(notpalindromeExample.isPalindromeList(), false)
        && t.checkExpect(mtlist.isPalindromeList(), true);
  }

}
