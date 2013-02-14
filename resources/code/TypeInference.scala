object TypeInference {

  def funWithFold() {

    val a = List(1,2,3)

    // increment all elements in a using fold
    a.foldRight(Nil)((xs: List[Int], elem: Int) => (elem + 1) :: xs)

  }

}