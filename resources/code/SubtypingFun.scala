object SubtypingFun {
  abstract class A
  class B extends A

  val x1: A => Object = ???
  val x2: B => Object = ???
  val x3: A => A      = ???
  
  def foo(f: A => Object) {}

  def test() {

    foo(x1)
    foo(x2)
    foo(x3)

  }
}