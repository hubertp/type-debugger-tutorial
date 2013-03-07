package inference

trait Inference02 {

  def test01: Unit = {
    def foo[T](x: Int)(y: T): T = y

    val res1 = foo(1) _
    res1(2)

    def fooVariation[T](x: Int)(z: T): Int = 0
    
    val res2 = fooVariation(1) _
    res2(2)

  }

  def test02: Unit = {

    def bip[T <: X, X](a: X)(b: T): T = b

    val res4 = bip(1) _
    res4(2)

    def bop[X, T <: X](a: X)(b: T): T = b

  }
}