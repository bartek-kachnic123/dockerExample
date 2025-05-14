package controllers

import play.api.mvc._
import play.api.libs.json._
import models.Cart
import javax.inject._
import scala.collection.mutable.ListBuffer

@Singleton
class CartController @Inject() (cc: ControllerComponents)
    extends AbstractController(cc) {

  implicit val cartFormat: OFormat[Cart] = Json.format[Cart]

  private val carts = ListBuffer(
    Cart(1, ListBuffer(1, 2)),
    Cart(2, ListBuffer(3))
  )

  def getAllCarts: Action[AnyContent] = Action {
    Ok(Json.toJson(carts))
  }

  def getCart(id: Int): Action[AnyContent] = Action {
    carts.find(_.id == id) match {
      case Some(cart) => Ok(Json.toJson(cart))
      case None => NotFound(Json.obj("error" -> s"Cart with ID $id not found"))
    }
  }

  def createCart: Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Cart] match {
      case JsSuccess(cart, _) =>
        carts.append(cart)
        Created(Json.toJson(cart))
      case JsError(errors) =>
        BadRequest(Json.obj("error" -> "Invalid cart data"))
    }
  }

  def updateCart(id: Int): Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Cart] match {
      case JsSuccess(updatedCart, _) =>
        val cartIndex = carts.indexWhere(_.id == id)
        if (cartIndex >= 0) {
          carts(cartIndex) = updatedCart
          Ok(Json.toJson(updatedCart))
        } else {
          NotFound(Json.obj("error" -> s"Cart with ID $id not found"))
        }
      case JsError(errors) =>
        BadRequest(Json.obj("error" -> "Invalid cart data"))
    }
  }

  def deleteCart(id: Int): Action[AnyContent] = Action {
    val cartIndex = carts.indexWhere(_.id == id)
    if (cartIndex >= 0) {
      carts.remove(cartIndex)
      NoContent
    } else {
      NotFound(Json.obj("error" -> s"Cart with ID $id not found"))
    }
  }
}
