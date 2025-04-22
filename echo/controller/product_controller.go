package controller

import (
	. "echo/model"
	. "echo/repository"
	"errors"
	"github.com/labstack/echo/v4"
	"net/http"
	"strconv"
)

type ProductController struct {
	Repo ProductRepository
}

func NewProductController(repo ProductRepository) *ProductController {
	return &ProductController{
		Repo: repo,
	}
}

func (pc *ProductController) CreateProduct(c echo.Context) error {
	product := new(Product)
	if err := c.Bind(product); err != nil {
		return c.JSON(http.StatusBadRequest, map[string]string{"error": "Invalid request body"})
	}

	addedProduct := pc.Repo.AddProduct(*product)

	return c.JSON(http.StatusCreated, addedProduct)
}

func (pc *ProductController) GetProducts(c echo.Context) error {
	products := pc.Repo.GetAllProducts()
	return c.JSON(http.StatusOK, products)
}

func (pc *ProductController) GetProduct(c echo.Context) error {
	idStr := c.Param("id")
	id, err := strconv.Atoi(idStr)
	if err != nil {
		return c.JSON(http.StatusBadRequest, map[string]string{"error": "Invalid product index format"})
	}

	product, err := pc.Repo.GetProductById(id)
	if err != nil {
		if errors.Is(err, ErrNotFound) {
			return c.JSON(http.StatusNotFound, map[string]string{"error": err.Error()})
		}
		return c.JSON(http.StatusInternalServerError, map[string]string{"error": "Internal server error"})
	}

	return c.JSON(http.StatusOK, product)
}

func (pc *ProductController) UpdateProduct(c echo.Context) error {
	idStr := c.Param("id")
	id, err := strconv.Atoi(idStr)
	if err != nil {
		return c.JSON(http.StatusBadRequest, map[string]string{"error": "Invalid product id format"})
	}

	updatedProductData := new(Product)
	if err := c.Bind(updatedProductData); err != nil {
		return c.JSON(http.StatusBadRequest, map[string]string{"error": "Invalid request body"})
	}

	updatedProduct, err := pc.Repo.UpdateProductById(id, *updatedProductData)
	if err != nil {
		if errors.Is(err, ErrNotFound) {
			return c.JSON(http.StatusNotFound, map[string]string{"error": err.Error()})
		}
		return c.JSON(http.StatusInternalServerError, map[string]string{"error": "Internal server error"})
	}

	return c.JSON(http.StatusOK, updatedProduct)
}

func (pc *ProductController) DeleteProduct(c echo.Context) error {
	idStr := c.Param("id")
	id, err := strconv.Atoi(idStr)
	if err != nil {
		return c.JSON(http.StatusBadRequest, map[string]string{"error": "Invalid product id format"})
	}

	err = pc.Repo.DeleteProductById(id)
	if err != nil {
		if errors.Is(err, ErrNotFound) {
			return c.JSON(http.StatusNotFound, map[string]string{"error": err.Error()})
		}
		return c.JSON(http.StatusInternalServerError, map[string]string{"error": "Internal server error"})
	}

	return c.NoContent(http.StatusNoContent)
}
