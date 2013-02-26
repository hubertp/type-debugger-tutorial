
trait Inference02 {

  def test01: Unit = {
    def foo[T](x: Int)(y: T): T = y

    val res1 = foo(1) _
    res1(2)

    def fooVariation[T](x: Int)(z: T): Int = 0
    
    val res2 = fooVariation(1) _
    res2(2)
    ()
  }

  def test02: Unit = {

    def bip[T <: X, X](a: X)(b: T): T = b

    val res4 = bip(1) _
    res4(2)

    def bop[X, T <: X](a: X)(b: T): T = b

    val res5 = bop(1) _
    res5(2)
    ()
  }

  def test03: Unit = {

    def bar1[T <: X, X](a: X)(b: T): X = a

    val res6 = bar1(1) _
    res6(2)

    def bar2[X, T <: X](a: X)(b: T): X = a

    val res7 = bar2(1) _
    res7(2)
    ()

  }
}