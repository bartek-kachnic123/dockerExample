package controllers

import play.api.mvc._
import play.api.libs.json._
import models.Category
import javax.inject._
import scala.collection.mutable.ListBuffer

@Singleton
class CategoryController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  implicit val categoryFormat: OFormat[Category] = Json.format[Category]

  private val categories = ListBuffer(
    Category(1, "Electronics"),
    Category(2, "Clothing"),
    Category(3, "Books")
  )

  def getAllCategories: Action[AnyContent] = Action {
    Ok(Json.toJson(categories))
  }

  def getCategory(id: Int): Action[AnyContent] = Action {
    categories.find(_.id == id) match {
      case Some(category) => Ok(Json.toJson(category))
      case None => NotFound(Json.obj("error" -> s"Category with ID $id not found"))
    }
  }

  def createCategory: Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Category] match {
      case JsSuccess(category, _) =>
        categories.append(category)
        Created(Json.toJson(category))
      case JsError(errors) =>
        BadRequest(Json.obj("error" -> "Invalid category data"))
    }
  }

  def updateCategory(id: Int): Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Category] match {
      case JsSuccess(updatedCategory, _) =>
        val categoryIndex = categories.indexWhere(_.id == id)
        if (categoryIndex >= 0) {
          categories(categoryIndex) = updatedCategory
          Ok(Json.toJson(updatedCategory))
        } else {
          NotFound(Json.obj("error" -> s"Category with ID $id not found"))
        }
      case JsError(errors) =>
        BadRequest(Json.obj("error" -> "Invalid category data"))
    }
  }

  def deleteCategory(id: Int): Action[AnyContent] = Action {
    val categoryIndex = categories.indexWhere(_.id == id)
    if (categoryIndex >= 0) {
      categories.remove(categoryIndex)
      NoContent
    } else {
      NotFound(Json.obj("error" -> s"Category with ID $id not found"))
    }
  }
}
