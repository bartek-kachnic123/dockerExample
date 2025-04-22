package main

import (
	. "echo/controller"
	"github.com/labstack/echo/v4"
)

func SetupProductRoutes(e *echo.Echo, pc *ProductController) {
	productGroup := e.Group("/products")

	productGroup.POST("", pc.CreateProduct)
	productGroup.GET("", pc.GetProducts)
	productGroup.GET("/:id", pc.GetProduct)
	productGroup.PUT("/:id", pc.UpdateProduct)
	productGroup.DELETE("/:id", pc.DeleteProduct)
}
