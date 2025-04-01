package controllers

import play.api.mvc._
import play.api.libs.json._
import models.Product
import javax.inject._
import scala.collection.mutable.ListBuffer

@Singleton
class ProductController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  implicit val productFormat: OFormat[Product] = Json.format[Product]

  private val products = ListBuffer(
    Product(1, "PC", 6999.99),
    Product(2, "Smartphone", 599.99),
    Product(3, "TV", 3999.99)
  )

  def getAllProducts: Action[AnyContent] = Action {
    Ok(Json.toJson(products))
  }

  def getProduct(id: Int): Action[AnyContent] = Action {
    products.find(_.id == id) match {
      case Some(product) => Ok(Json.toJson(product))
      case None => NotFound(Json.obj("error" -> s"Product with ID $id not found"))
    }
  }

  def createProduct: Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Product] match {
      case JsSuccess(product, _) =>
        products.append(product)
        Created(Json.toJson(product))
      case JsError(errors) =>
        BadRequest(Json.obj("error" -> "Invalid product data"))
    }
  }

  def updateProduct(id: Int): Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Product] match {
      case JsSuccess(updatedProduct, _) =>
        val productIndex = products.indexWhere(_.id == id)
        if (productIndex >= 0) {
          products(productIndex) = updatedProduct
          Ok(Json.toJson(updatedProduct))
        } else {
          NotFound(Json.obj("error" -> s"Product with ID $id not found"))
        }
      case JsError(errors) =>
        BadRequest(Json.obj("error" -> "Invalid product data"))
    }
  }

  def deleteProduct(id: Int): Action[AnyContent] = Action {
    val productIndex = products.indexWhere(_.id == id)
    if (productIndex >= 0) {
      products.remove(productIndex)
      NoContent
    } else {
      NotFound(Json.obj("error" -> s"Product with ID $id not found"))
    }
  }
}
