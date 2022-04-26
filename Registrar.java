
import tester.*;
import java.util.function.BiFunction;

// List of T
interface IList<T> {
  // foldr loop function 
  <U> U foldr(BiFunction<T, U, U> f, U base);
}

// represents A Course with a name, an Instructor, and a list of Students named students
class Course {
  String name;
  Instructor prof;
  IList<Student> students;

  Course(String name, Instructor prof) {
    this.name = name;
    this.prof = prof;
    //this.prof.courses = new ConsList<Course>(this, this.prof.courses); // Might work
    this.prof.giveCourse(this); // Better alt
    this.students = new MtList<Student>();
  }

  Course(String name, Instructor prof, IList<Student> students) {
    this.name = name;
    this.prof = prof;
    this.prof.courses = new ConsList<Course>(this, this.prof.courses);
    this.students = students;
  }
  
  // enrolls a Student s in the given Course this
  public void enroll(Student s) {
    this.students = this.addStudentToCourse1(s);
  }
  
  // enroll(Student s) helper
  public IList<Student> addStudentToCourse1(Student student) {
    IList<Student> mt = new MtList<Student>();
    if (this.students != null || this.students != mt) {
      return new ConsList<Student>(student, this.students);
    }
    return new ConsList<Student>(student, mt); // new ConsList<Course>(course, mt);
  }
}

// represents An Instructor has a name and a list of Courses named courses he or she teaches
class Instructor {
  String name;
  IList<Course> courses;

  Instructor(String name) {
    this.name = name;
    this.courses = new MtList<Course>();
  }

  Instructor(String name, IList<Course> courses) {
    this.name = name;
    this.courses = courses;
  }
  
  // determines whether the given Student is in more than one of this Instructor’s Courses
  boolean dejavu(Student c) {
    return 2 <= this.courses.foldr(new DeJaVu(c), 0);
  }
  
  // assigns an instructor to course
  void giveCourse(Course c) {
    this.courses = new ConsList<Course>(c, this.courses);
  }

}

//represents A Student with a name, an id number, and a list of Courses they are taking
class Student {
  String name;
  int id;
  IList<Course> courses;

  Student(String name, int id) {
    this.name = name;
    this.id = id;
    this.courses = new MtList<Course>();
  }

  Student(String name, int id, IList<Course> courses) {
    this.name = name;
    this.id = id;
    this.courses = courses;
  }
  
  // enrolls a Student in the given Course
  public void enroll(Course c) {
    this.courses = this.addStudentToCourse(c);
  }
  
  // enroll helper
  public IList<Course> addStudentToCourse(Course course) {
    IList<Course> mt = new MtList<Course>();
    if (this.courses != null) {
      course.enroll(this);
      return new ConsList<Course>(course, this.courses);
    }
    return new ConsList<Course>(course, mt); // new ConsList<Course>(course, mt);
  }

  // determines whether the given Student is in any of the same classes as this Student
  public boolean classmates(Student s) {
    return this.courses.foldr(new IsEnrolled(s), false);
    // return true;
  }

  // determines if 2 students are the same
  public boolean sameAs(Student s) {
    boolean sameName = this.name.equals(s.name);
    boolean sameId = (this.id == s.id);
    // boolean sameCourses = (this.courses == s.courses);
    return sameName && sameId; // && sameCourses;
  }

}

// non empty list of T objects
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  // foldr loop function 
  public <U> U foldr(BiFunction<T, U, U> f, U base) {
    return f.apply(this.first, this.rest.foldr(f, base));
  }
}

// empty list of T objects
class MtList<T> implements IList<T> {
  T first;
  IList<T> rest;

  MtList() {
  }

  // foldr loop function 
  public <U> U foldr(BiFunction<T, U, U> f, U base) {
    return base;
  }
}

// determines if a student is enrolled in a class
class IsEnrolled implements BiFunction<Course, Boolean, Boolean> {
  Student student;

  IsEnrolled(Student student) {
    this.student = student;
  }

  // applies function to elem in list 
  public Boolean apply(Course c, Boolean bool) {
    BiFunction<Student, Boolean, Boolean> hs = new HasStudent(student);
    boolean enrolledInCourse = c.students.foldr(hs, false);
    return bool || enrolledInCourse;
  }
}

// determines if a course instructor has a student
class HasStudent implements BiFunction<Student, Boolean, Boolean> {
  Student student;

  HasStudent(Student student) {
    this.student = student;
  }

  // applies function to elem in list
  public Boolean apply(Student s, Boolean bool) {
    boolean sameStudent = s.sameAs(student);
    return sameStudent || bool;
  }
}

// Determines num of courses a student has w an instructor
class DeJaVu implements BiFunction<Course, Integer, Integer> {
  Student c;

  DeJaVu(Student c) {
    this.c = c;
  }

  // applies function to elem in list
  public Integer apply(Course t, Integer u) {
    int val;
    if (t.students.foldr(new HasStudent(c), false)) {
      val = 1;
    }
    else {
      val = 0;
    }
    return u + val;
  }
}

class ExamplesRegist {
  ExamplesRegist() {
  }

  IList<Course> mtCourse = new MtList<Course>();
  IList<Student> mtStudent = new MtList<Student>();

  Student john;
  Student fitzgerald;
  Student kennedy;
  Student mike;
  Student cooper;
  IList<Student> classmatesJohn;
  IList<Student> classmatesJohnMike;

  Instructor higger;
  Instructor crooks;
  Instructor vidoje;
  Instructor munhoz;

  Course cs2810;
  Course math1365;
  Course cs2510;
  Course cy2550;
  Course envr1200;
  
  Course john2810;
  Course johnMike2510;
  IList<Course> only2810;

  void init() {
    this.john = new Student("John", 65748);
    this.fitzgerald = new Student("Fitzgerald", 3290);
    this.kennedy = new Student("Kennedy", 548);
    this.mike = new Student("Mike", 9876);
    this.cooper = new Student("Cooper", 58489);

    this.higger = new Instructor("Higger");
    this.crooks = new Instructor("Crooks");
    this.vidoje = new Instructor("Vidoje");
    this.munhoz = new Instructor("Munhoz");

    this.cs2810 = new Course("MDM", this.higger); // higger.courses = conslist(cs2810, )
    this.math1365 = new Course("Math Reasoning", this.crooks);
    this.cs2510 = new Course("Fundies", this.vidoje);
    this.cy2550 = new Course("Foundies", this.vidoje);
    this.envr1200 = new Course("Dynamic Earth", this.munhoz);
    

    this.classmatesJohn = new ConsList<Student>(this.john, this.mtStudent);
    this.classmatesJohnMike = new ConsList<Student>(this.mike, this.classmatesJohn);
    
    this.john2810 = new Course("MDM", this.higger, this.classmatesJohn);
    this.johnMike2510 = new Course("Fundies", this.vidoje, this.classmatesJohnMike);
    this.only2810 = new ConsList<Course>(this.john2810, this.mtCourse);
  }
  
  
  
  //t1
  void testEnroll(Tester t) {
    this.init();
    
    t.checkExpect(this.john.courses, this.mtCourse);

    this.john.enroll(this.cs2810);
    t.checkExpect(this.john.courses, this.only2810);

    // L1
    this.fitzgerald.enroll(this.math1365);
    IList<Course> l1 = new ConsList<Course>(math1365, this.mtCourse);
    t.checkExpect(this.fitzgerald.courses, l1);

    // L2
    this.fitzgerald.enroll(this.cy2550);
    IList<Course> l2 = new ConsList<Course>(cy2550,
        new ConsList<Course>(math1365, this.mtCourse));
    t.checkExpect(this.fitzgerald.courses, l2);

    // L3
    this.kennedy.enroll(this.johnMike2510);
    IList<Course> l3 = new ConsList<Course>(new Course("Fundies", this.vidoje,
        new ConsList<Student>(this.kennedy,
            this.classmatesJohnMike)), this.mtCourse);
    t.checkExpect(this.kennedy.courses, l3);

    // L4
    this.mike.enroll(this.math1365);
    IList<Course> l4 = new ConsList<Course>(this.math1365, this.mtCourse);
    t.checkExpect(this.mike.courses, l4);

    // L5
    this.cooper.enroll(this.johnMike2510);
    IList<Course> l5 = new ConsList<Course>(new Course("Fundies", this.vidoje,
        new ConsList<Student>(this.cooper,
            new ConsList<Student>(this.kennedy,
                this.classmatesJohnMike))), this.mtCourse);
    t.checkExpect(this.cooper.courses, l5);
  }
  
  void testClassmate(Tester t) {
    this.init();
    
    t.checkExpect(this.mike.classmates(this.john), false);

    this.john.enroll(this.cs2810);
    t.checkExpect(this.john.classmates(this.mike), false);

    this.mike.enroll(this.cs2810);
    t.checkExpect(this.mike.courses, this.john.courses);
    t.checkExpect(this.cs2810.students, this.classmatesJohnMike);
    t.checkExpect(this.john.classmates(this.mike), true);
    t.checkExpect(this.mike.classmates(this.john), true);
    t.checkExpect(this.john.classmates(this.john), true);
    t.checkExpect(this.mike.classmates(this.mike), true);

    this.cooper.enroll(this.johnMike2510);
    t.checkExpect(this.cooper.classmates(this.john), true);
    t.checkExpect(this.cooper.classmates(this.fitzgerald), false);

    this.fitzgerald.enroll(this.johnMike2510);
    t.checkExpect(this.fitzgerald.classmates(this.mike), true);
    t.checkExpect(this.fitzgerald.classmates(this.john), true);

    this.kennedy.enroll(this.math1365);
    this.kennedy.enroll(this.envr1200);
    t.checkExpect(this.kennedy.classmates(this.john), false);

    this.john.enroll(this.math1365);
    t.checkExpect(this.kennedy.classmates(this.john), true);
  }

  
  void testDejavu(Tester t) {
    this.init();

    t.checkExpect(this.vidoje.dejavu(this.john), false);

    this.john.enroll(this.cs2810);
    t.checkExpect(this.vidoje.dejavu(this.john), false);

    this.john.enroll(this.envr1200);
    t.checkExpect(this.vidoje.dejavu(this.john), true);

    this.mike.enroll(this.envr1200);
    t.checkExpect(this.vidoje.dejavu(this.mike), false);

    this.mike.enroll(this.cs2810);
    t.checkExpect(this.vidoje.dejavu(this.mike), false);
    t.checkExpect(this.vidoje.dejavu(this.cooper), false);

    this.cooper.enroll(this.envr1200);
    t.checkExpect(this.vidoje.dejavu(this.cooper), false);

    this.cooper.enroll(this.cs2810);
    t.checkExpect(this.vidoje.dejavu(this.cooper), false);

    this.cooper.enroll(this.cy2550);
    t.checkExpect(this.vidoje.dejavu(this.cooper), false);
  }
  
}
