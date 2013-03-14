object SubtypingFun {
  abstract class A
  class B extends A

  val x1: B => Object = ???
  val x2: A => A      = ???
  
  def foo(f: A => Object) {}

  def test() {
    foo(x2)
    foo(x1)
  }
}