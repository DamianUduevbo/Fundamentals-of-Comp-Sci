interface Housing {
}

class Hut implements Housing {
  int capacity;
  int population;

  Hut(int capacity, int population) {
    if (capacity > population) {
      this.capacity = capacity;
      this.population = population;
    }
  }
}

class Inn implements Housing {
  String name;
  int capacity;
  int population;
  int stalls;

  Inn(String name, int capacity, int population, int stalls) {
    this.name = name;
    if (capacity > population) {
      this.capacity = capacity;
      this.population = population;
    }
    this.stalls = stalls;
  }
}

class Castle implements Housing {
  String name;
  String familyName;
  int carriageHouse;
  int population;

  Castle(String name, String familyName, int population, int carriageHouse) {
    this.name = name;
    this.familyName = familyName;
    this.population = population;
    this.carriageHouse = carriageHouse;
  }
}

class Horse implements Housing {
  String name;
  String color;
  Housing to;
  Housing from;

  Horse(Housing to, Housing from, String name, String color) {
    this.name = name;
    this.color = color;
    this.to = to;
    this.from = from;
  }
}

class Carriage implements Housing {
  Housing to;
  Housing from;
  int tonnage;

  Carriage(Housing to, Housing from, int tonnage) {
    this.to = to;
    this.from = from;
    this.tonnage = tonnage;
  }
}

class ExamplesTravel {
  Housing hovel = new Hut(5, 1);
  Housing winterfell = new Castle("Winterfell", "Stark", 500, 6);
  Housing crossroads = new Inn("Inn At The Crossroads", 40, 20, 12);

  Housing horse1 = new Horse(winterfell, crossroads, "Boo", "Purple");
  Housing horse2 = new Horse(winterfell, crossroads, "Boo", "Purple");

  Housing carriage1 = new Carriage(winterfell, crossroads, 100);
  Housing carriage2 = new Carriage(crossroads, winterfell, 1000);
}