package inference

trait Existentials02 {

  trait S[T]

  def foo(as: Seq[S[X]] forSome { type X }) = true
  def otherFoo(as: Seq[S[X] forSome { type X }]) = true

  def SInt: S[Int] = ???
  def SString: S[String] = ???

  def test {
    foo(Seq(SInt, SInt))         // 1)
    foo(Seq(SInt, SString))      // 2)

    otherFoo(Seq(SInt, SInt))    // 3)
    otherFoo(Seq(SInt, SString)) // 4)
  }

}