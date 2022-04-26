import java.util.*;
import tester.*;

/**
 * A class that defines a new permutation code, as well as methods for encoding
 * and decoding of the messages that use this code.
 */
class PermutationCode {
  /** The original list of characters to be encoded */
  ArrayList<Character> alphabet = new ArrayList<Character>(Arrays.asList(
      'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
      'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
      's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));

  ArrayList<Character> code = new ArrayList<Character>(26);

  /** A random number generator */
  Random rand = new Random();

  // Create a new instance of the encoder/decoder with a new permutation code 
  PermutationCode() {
    this.code = this.initEncoder();
  }

  // Create a new instance of the encoder/decoder with the given code 
  PermutationCode(ArrayList<Character> code) {
    this.code = code;
  }

  // Create a new instance of the encoder/decoder with the given random 
  PermutationCode(Random rand) {
    this.rand = rand;
  }

  // Initialize the encoding permutation of the characters
  ArrayList<Character> initEncoder() {
    ArrayList<Character> initCode = new ArrayList<Character>(26);
    ArrayList<Character> initAlpha = new ArrayList<Character>(
        Arrays.asList(
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'));
    
    System.out.println("HERE: " + this.initEncoderHelper(initCode, initAlpha));
    return this.initEncoderHelper(initCode, initAlpha);
  }

  // Helper for initEncoder
  ArrayList<Character> initEncoderHelper(ArrayList<Character> input, ArrayList<Character> output) {
    if (output.size() == 0) {
      return code;
    }
    
    int index = this.rand.nextInt( output.size() );
    code.add(output.get(index));
    output.remove(index);
    return this.initEncoderHelper(code, output);
  }
  
  // returns encoded given string
  String encode(String source) {
    return this.scramble(source, "");
  }

  // encodes string
  String scramble(String source, String scrambledText) {
    // String scrambledText = "";
    if (source.equals("")) {
      return scrambledText;
    }
    
    Character first = source.charAt(0);
    String rest = source.substring(1);
    
    if (this.alphabet.contains(first)) {
      String toAdd = this.code.get(this.alphabet.indexOf(first)).toString();
      scrambledText = scrambledText + toAdd;

      //System.out.println("Scrambled text: " + scrambledText);
      return this.scramble(rest, scrambledText);
    }
    
    scrambledText = scrambledText + first.toString();
    //System.out.println("Scrambled text: " + scrambledText);
    return this.scramble(rest, scrambledText);
  }
  
  // returns decoded given string
  String decode(String code) {
    return this.unscramble(code, "");
  }

  // decodes given string
  String unscramble(String code, String unscrambledText) {
    if (code.equals("")) {
      return unscrambledText;
    }
    
    Character start = code.charAt(0);
    String rest = code.substring(1);

    if (this.alphabet.contains(start)) {
      String toAdd = this.alphabet.get(this.code.indexOf(start)).toString();
      unscrambledText = unscrambledText + toAdd;

      //System.out.println("unscramble text: " + unscrambledText);
      return this.unscramble(rest, unscrambledText);
    }
    
    unscrambledText = unscrambledText + start.toString();
    //System.out.println("unscramble text: " + unscrambledText);
    return this.unscramble(rest, unscrambledText);
  }
  
}

// examples for perm
class ExamplesPerm {
  ExamplesPerm() {}
  
  PermutationCode permCode;
  PermutationCode permCode1;
  PermutationCode permByAlpha;
  PermutationCode permByCode;
  PermutationCode bruh = new PermutationCode(new Random(26));
  
  String toEncode1;
  String toDecode1;

  String toEncode2;
  String toDecode2;
  
  ArrayList<Character> alphabet;  
  ArrayList<Character> code1;
  ArrayList<Character> code2;
  
  // inits variables
  void initStuff() {
    permCode = new PermutationCode();
    permCode1 = new PermutationCode();
    
    toEncode1 = "dxcfvgbh";
    toDecode1 = "cfyv uyghuj";
    
    toEncode2 = "vghbjnjnh!";
    toDecode2 = "fcdghvbj!";
    
    alphabet = new ArrayList<Character>(
        Arrays.asList( // size 26
            'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o','p',
            'a', 's', 'd', 'f', 'g', 'h', 'j', 'k','l',
            'z', 'x', 'c', 'v', 'b', 'n', 'm'));
    
    code1 = new ArrayList<Character>(
        Arrays.asList(
            'j', 'g', 'x', 'u', 'y', 'o', 'm', 'i', 'n', 'k',
            'e', 's', 'f', 'r', 'z', 'v', 'b', 'p', 'a', 'h',
            'c', 't', 'w', 'l', 'd', 'q'));
    
    code2 = new ArrayList<Character>(
        Arrays.asList(
            'j', 'g', 'x', 'u', 'y', 'o', 'm', 'i', 'n', 'k',
            'e', 's', 'f', 'r', 'z', 'v', 'b', 'p', 'a', 'h',
            'c', 't', 'w', 'l', 'd', 'q'));
    
    permByAlpha = new PermutationCode(this.alphabet);
    permByCode = new PermutationCode(this.code1);

  }
  
  /*
  */
  
  // tests initEncoder method
  boolean testInitEncoder(Tester t) {
    this.initStuff();
    return t.checkExpect(permByAlpha.initEncoder().size(), 52)
        && t.checkExpect(permByCode.initEncoder().size(), 52)
        && t.checkExpect(permByCode.initEncoder().size(), 78)
        && t.checkExpect(this.bruh.initEncoder(), null);
  }
  
  // tests encode method
  void testEncode(Tester t) {
    this.initStuff();
    t.checkExpect(permByAlpha.encode(this.toDecode1), "eync xnuixp");
    t.checkExpect(permByAlpha.encode(this.toDecode2), "yeruicwp!");
    t.checkExpect(permByAlpha.encode(""), "");
    t.checkExpect(permByAlpha.encode("yyktaf"), "nnazqy");
  }

  // tests Scramble method
  boolean testScramble(Tester t) {
    this.initStuff();
    return t.checkExpect(permByAlpha.scramble(this.toEncode1, "guyesdf"), "guyesdfrbeycuwi")
        && t.checkExpect(permByAlpha.scramble("", "wkjwe"), "wkjwe")
        && t.checkExpect(permByAlpha.scramble("dkjsd@s!jh", " dkjs!4o3ds"),
        " dkjs!4o3dsraplr@l!pi");
  }
  
  // tests decode method
  void testDecode(Tester t) {
    this.initStuff();
    t.checkExpect(permByAlpha.decode(this.toEncode1), "muvnwoxp");
    t.checkExpect(permByAlpha.decode(this.toEncode2), "wopxqyqyp!");
    t.checkExpect(permByAlpha.decode(""), "");
    t.checkExpect(permByAlpha.decode("relew"), "dcscb");
    t.checkExpect(permByAlpha.decode(""), "");
  }

  // test the Unscramble method
  boolean testUnscramble(Tester t) {
    this.initStuff();
    return t.checkExpect(permByAlpha.unscramble(this.toEncode1, "dnewjsk"), "dnewjskmuvnwoxp")
        && t.checkExpect(permByAlpha.unscramble("", "edwedwsfewj"), "edwedwsfewj")
        && t.checkExpect(permByAlpha.unscramble("edwijoqw!@Orefd", "nidfsjk !ejknwd"),
        "nidfsjk !ejknwdcmbhqiab!@Odcnm");
  }
  
  // test Permutation
  void testPermutation(Tester t) {
    this.initStuff();
    t.checkExpect(permByAlpha.code, permByAlpha.initEncoder());
  }
}

class Pem {
  
  public static void main(String[] args) {
    PermutationCode permCode = new PermutationCode();
    ExamplesPerm examplesPerm = new ExamplesPerm();
    Tester t = new Tester();
    
    System.out.println(permCode.encode("ndjkssad"));
    /*
    examplesPerm.testEncode(t);
    examplesPerm.testDecode(t);
    */
  }
}

