object TypeClasses {
  trait Serializer[T] {
    def pickle(x: T): Array[Byte]
    def unpickle(x: Array[Byte]): T
  }

  object Serializer {
    implicit def genSerializer[A <: AnyRef]: Serializer[A] = new Serializer[A] {
      def pickle(x: A): Array[Byte] = ???
      def unpickle(x: Array[Byte]): A = ???
    }
  }

  sealed abstract class Tree[+A]
  object Tree {
    implicit def treeSerializer[A: Serializer]: Serializer[Tree[A]] = new Serializer[Tree[A]] {
      def pickle(x: Tree[A]): Array[Byte]   = ???
      def unpickle(x: Array[Byte]): Tree[A] = ???
    }
    implicit def longTreeSerializer: Serializer[Tree[Long]] = new Serializer[Tree[Long]] {
      def pickle(x: Tree[Long]): Array[Byte]   = ???
      def unpickle(x: Array[Byte]): Tree[Long] = ???
    }
    implicit def strTreeSerializer: Serializer[Tree[String]] = new Serializer[Tree[String]] {
      def pickle(x: Tree[String]): Array[Byte]   = ???
      def unpickle(x: Array[Byte]): Tree[String] = ???
    }
  }

  case class Leaf[+A: Serializer](x: A) extends Tree[A]
  case class BinaryNode[+A: Serializer](x: Tree[A], y: Tree[A]) extends Tree[A]

  def testSerialization[T: Serializer](x: T): Boolean = {
    val pickled = implicitly[Serializer[T]].pickle(x)
    val unpickled = implicitly[Serializer[T]].unpickle(pickled)
    unpickled == x
  }

  def test {
    val a: Int = ???
    val b: Tree[Long] = ???
    val c: Tree[Int] = ???

    class Foo

    val d: Tree[Tree[Foo]] = ???
    val e: Foo = ???
    val f: Tree[Char] = ???

    testSerialization(a)
    testSerialization(b)
    testSerialization(c)
    testSerialization(d)
    testSerialization(e)
    testSerialization(f)
  }
}