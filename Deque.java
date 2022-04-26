import tester.*;
import java.util.*;
import java.util.function.Predicate;

interface IPred<T> {
  boolean check(T t);

}

class CheckSame implements IPred<String> {
  String s1;

  CheckSame(String string) {
    this.s1 = string;
  }

  @Override
  public boolean check(String t) {
    return s1.equals(t);
  }
}

//represent the abstract class 
abstract class ANode<T> {
  ANode<T> next;
  ANode<T> prev;

  // count the number of nodes
  abstract int sizeHelper();

  // remove the node from Node
  abstract T removeHelper();

  // produces the first node of an IPred for which returns true
  abstract ANode<T> findHelper(Predicate<T> pred);

  // remove the node from Node
  abstract void removeNodeHelper();

}

//represent the deque class 
class Deque<T> {
  Sentinel<T> header;

  Deque() {
    this.header = new Sentinel<T>();
  }

  Deque(Sentinel<T> header) {
    this.header = header;
  }

  // count the number of nodes in the deque list
  int size() {
    return this.header.size();
  }

  // insert "T" into the front of the list
  void addAtHead(T t) {
    this.header.addAtHead(t);
  }

  // insert T to the end of the list
  void addAtTail(T t) {
    this.header.addAtTail(t);
  }

  // remove the first node in the deque list
  T removeFromHead() {
    return this.header.removeFromHead();
  }

  // remove the last node in the deque list
  T removeFromTail() {
    return this.header.removeFromTail();
  }

  //
  ANode<T> find(Predicate<T> p) {
    return this.header.find(p);
  }

  // removes the given node from the list
  void removeNode(ANode<T> node) {
    node.removeNodeHelper();
  }

}

// represents sentinel class
class Sentinel<T> extends ANode<T> {
  Sentinel() {
    this.next = this;
    this.prev = this;
  }

  // return the number of nodes in the list
  int size() {
    return this.next.sizeHelper();
  }

  // number of nodes in the sentinel
  int sizeHelper() {
    return 0;
  }

  // add a node to the head of the sentinel
  void addAtHead(T t) {
    new Node<T>(t, this.next, this);
  }

  // add a node to the end of a sentinel
  void addAtTail(T t) {
    new Node<T>(t, this, this.prev);
  }

  // remove node from the head of the sentinel
  T removeFromHead() {
    return this.next.removeHelper();
  }

  // remove node from the end of the sentinel
  T removeFromTail() {
    return this.prev.removeHelper();
  }

  // removing a node from a sentinel
  T removeHelper() {
    throw new RuntimeException("Cannot remove a sentinel");
  }

  // find a specific node given a predicate
  ANode<T> find(Predicate<T> pred) {
    return this.next.findHelper(pred);
  }

  // find the node
  ANode<T> findHelper(Predicate<T> pred) {
    return this;
  }

  // remove the node from sentinel
  void removeNodeHelper() {
    return;
  }
}

// represents a node
class Node<T> extends ANode<T> {
  T data;

  Node(T data) {
    this.data = data;
    this.next = null;
    this.prev = null;
  }

  Node(T data, ANode<T> next, ANode<T> prev) {
    this(data);
    this.next = next;
    this.prev = prev;
    if (next == null && prev == null) {
      throw new IllegalArgumentException("Attempting to add null to a node");
    }
    else {
      next.prev = this;
      prev.next = this;
    }
  }

  // count the number of nodes
  @Override
  int sizeHelper() {
    return 1 + this.next.sizeHelper();
  }

  // remove the node from Node
  @Override
  T removeHelper() {
    prev.next = this.next;
    next.prev = this.prev;
    return this.data;
  }

  // produces the first node of an IPred for which returns true
  @Override
  ANode<T> findHelper(Predicate<T> pred) {
    if (pred.test(data)) {
      return this;
    }
    else {
      return this.next.findHelper(pred);
    }
  }

  // remove the node from Node
  @Override
  void removeNodeHelper() {
    this.removeHelper();

  }
}

//represents Examples
class ExamplesDeque {
  ExamplesDeque() {
  }

  Sentinel<String> sentinel1;
  Sentinel<String> sentinel2;
  Sentinel<Integer> sentinel3;

  ANode<String> nodeOne;
  ANode<String> nodeTwo;
  ANode<String> nodeThree;
  ANode<String> nodeFour;

  ANode<Integer> nodeA;
  ANode<Integer> nodeB;
  ANode<Integer> nodeC;
  ANode<Integer> nodeD;

  Deque<String> emptyDeque;
  Deque<String> deque1;
  Deque<String> deque2;
  Deque<Integer> deque3;

  void initDeque() {
    sentinel1 = new Sentinel<String>();
    nodeOne = new Node<String>("adsd", sentinel1, sentinel1);
    nodeTwo = new Node<String>("sdfsdf", sentinel1, nodeOne);
    nodeThree = new Node<String>("gdfh", sentinel1, nodeTwo);
    nodeFour = new Node<String>("rty", sentinel1, nodeThree);

    sentinel3 = new Sentinel<Integer>();
    nodeA = new Node<Integer>(1, sentinel3, sentinel3);
    nodeB = new Node<Integer>(2, sentinel3, nodeA);
    nodeC = new Node<Integer>(3, sentinel3, nodeB);
    nodeD = new Node<Integer>(4, sentinel3, nodeC);

    deque1 = new Deque<String>();
    deque2 = new Deque<String>(this.sentinel1);
    deque3 = new Deque<Integer>(this.sentinel3);
  }

  boolean testConstructor(Tester t) {
    this.initDeque();
    return t.checkExpect(deque2.header, this.sentinel1)
        && t.checkExpect(this.nodeTwo.prev, this.nodeOne)
        && t.checkExpect(this.nodeOne.next, this.nodeTwo);
  }

  boolean testSize(Tester t) {
    this.initDeque();
    return t.checkExpect(deque1.size(), 0) && t.checkExpect(deque2.size(), 4)
        && t.checkExpect(deque3.size(), 4) && t.checkExpect(sentinel3.size(), 4);
  }

  void testAddAtHead(Tester t) {
    this.initDeque();
    t.checkExpect(deque2.header.next, this.nodeOne);
    t.checkExpect(deque2.header.prev, this.nodeFour);
    deque2.addAtHead("dp");
    t.checkExpect(deque2.header.next, new Node<String>("dp", this.nodeOne, this.sentinel1));
    deque2.addAtHead("dame");
    t.checkExpect(deque2.header.next, new Node<String>("dame",
        new Node<String>("dp", this.nodeOne, this.sentinel1), this.sentinel1));
  }

  void testAddAtTail(Tester t) {
    this.initDeque();
    deque2.addAtTail("DP");
    t.checkExpect(deque2.header.prev, new Node<String>("DP", this.sentinel1, this.nodeFour));
    deque2.addAtTail("Dame");
    t.checkExpect(deque2.header.prev, new Node<String>("Dame", this.sentinel1,
        new Node<String>("DP", this.sentinel1, this.nodeFour)));

  }

  void testRemoveFromHead(Tester t) {
    this.initDeque();
    this.sentinel2.removeFromHead();
    t.checkExpect(this.deque2.header.prev, this.nodeTwo);
    t.checkExpect(this.deque2.header.next, this.nodeFour);
  }

  void testRemoveFromTail(Tester t) {
    this.initDeque();
    this.sentinel2.removeFromTail();
    t.checkExpect(this.sentinel2.next, this.nodeOne);
    t.checkExpect(this.sentinel2.prev, this.nodeThree);
  }

  void testRemoveNode(Tester t) {
    this.initDeque();
    this.deque2.removeNode(nodeTwo);
    t.checkExpect(this.deque2.header.next.next, this.nodeThree);

    this.deque1.removeNode(nodeTwo);
    t.checkExpect(this.deque1.header, this.sentinel1);

  }
}

/*
 * void testFind(Tester t) { this.initDeque();
 * t.checkExpect(this.deque2.find(new checkSame("adsd")), this.sentinel2.next);
 * }
 */
