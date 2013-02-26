package ch.epfl.lamp
package userstudies
package subtyping


trait Subtyping01 {
  // function subtyping
  // examples from the exam
  class S
  class T extends S

  class U
  class V extends U

  // no permutation subtyping
  class Record1[+A](x: A)
  class Record2[+A, +B](x: A, y: B) extends Record1(x)

  class MyWeirdFun[+From, -To]


  def test1(f: Any)                                   = ???
  def test2(f: Record2[U, T] => T)                    = ???
  def test3(f: V => Any)                              = ???
  def test4(f: (V => S) => V)                         = ???
  def test5(f: (Record2[T, V] => S) => Record2[T, V]) = ???
  def test6(f: ((S => Record1[T]) => S) => Any)       = ???
  def test7(f: MyWeirdFun[Record1[S], Record1[U]])    = ???

  def test {
    val arg1: Any => Any         = ???
    val arg2: Record2[S, U] => S = ???
    val arg3: Any => S           = ??? 
    val arg4: (S => T) => V      = ???
    val arg5: (Record1[S] => T) => Record1[U]       = ??? 
    val arg6: (T => Record2[S, S]) => T             = ???
    val arg7: MyWeirdFun[Record1[T], Record2[V, U]] = ???

    // Q: can you answer those without the compiler
    // Q: did you get all of them right
    // Q: does the debugger help in understanding the subtyping tests
    test1 (arg1)
    test2 (arg2) // todo: avoid producing lots of subtype tests
    test3 (arg3)
    test4 (arg4)
    test5 (arg5)
    test6 (arg6)
    test7 (arg7)
  }

}


trait Subtyping02 {

  abstract class Base
  class A extends Base
  class B extends Base
  class A1 extends A

  def m1(x: A)(func: A => B): B                  = ???
  def m2(func: Base => A): A => Base             = ???
  def m3(x: A, y: B)(func: A => B => Base): Base = ???
  def m4(x: A)(func: A => B): B                  = ???
  def m5(x: A, y: B)(func: A => B => B): Base    = ???


  def test: Unit = {

    val a = new A()
    val b = new B()

    m1(a)(_ => new B())
    m1(a)((x: Base) => new B())
    m3(a, b)((x: Base) => (y: Base) => new A())

    val f1: B => A = (x: B) => a
    val f2: A1 => B = (x: A1) => b

    m4(a)(f1)
    m4(a)(f2)

    m5(a)(a)
  }
}

trait Subtyping03 {
  trait Op[+X <: Op[X]] // def change to contravariant
  class Foo extends Op[Foo]
  class Bar extends Foo

  def app1[U <: Op[U]](x: U): U = x
  def app2[V <: Op[_]](x: V): V = x

  def test {
    val x = new Foo()
    val y = new Bar()

    app1(x)
    app1(y)

    app2(x)
    app2(y)
  }
} 

trait Subtyping04 {

  abstract class Tree {
    type Elem

    def appendChild(a: Elem)
    def createChild: Elem
    def children: List[Elem]
  }

  object Tree {
    def newElem(t: Tree): t.Elem = t.createChild
  }

  def test(x: Tree, y: Tree) = {
    // Q: What is the type of e1
    val e1 = Tree.newElem(x)
    // Q: what is the type of e2
    val e2 = Tree.newElem(y)

    x.appendChild(e1)

    y.appendChild(e2)
    
    // Q: Why do we get an error even though both are Elem
    x.appendChild(e2)
  }
}

// more of inference
trait Subtyping05 {
  def foo[A, CC <: Seq[A]](xs: CC): Unit = ()
  def bar[A, CC[X] <: Seq[X]](xs: CC[A]): Unit = ()

  def test {
    val x: List[Int] = List(1,2,3)
    foo(x)
    bar(x)
  }
}