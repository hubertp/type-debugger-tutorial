package implicits

import collection.mutable.LinkedList

trait Implicits14 {

  def test01 {
    import scala.collection.BitSet

    val lList: LinkedList[Int] = ???
    val bSet: BitSet           = ???

    val a = lList map (_.toFloat)
    
    val b = bSet map (_.toFloat)
  }

}