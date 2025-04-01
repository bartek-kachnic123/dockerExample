package models

import scala.collection.mutable.ListBuffer

case class Cart(id: Int, productIds: ListBuffer[Int])