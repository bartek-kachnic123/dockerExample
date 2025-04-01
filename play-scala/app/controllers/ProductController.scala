package controllers

import play.api.mvc._
import play.api.libs.json._
import models.Product
import javax.inject._

@Singleton
class ProductController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  implicit val productFormat: OFormat[Product] = Json.format[Product]

  private val products = List(
    Product(1, "PC", 6999.99),
    Product(2, "Smartphone", 599.99),
    Product(3, "TV", 3999.99)
  )

  def getAllProducts: Action[AnyContent] = Action {
    Ok(Json.toJson(products))
  }
}
