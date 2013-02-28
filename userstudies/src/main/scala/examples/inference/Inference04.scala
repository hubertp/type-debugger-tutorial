package inference

trait Inference04 {

  def test01 {
    trait R[T]
    class A[T] extends R[T]
    class B[T] extends R[T]
    
    def foo[S](x: S, y: S): Unit = ???
    foo(new A(), new B[Int]())
  }
  
}