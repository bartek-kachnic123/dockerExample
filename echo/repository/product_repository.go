package repository

import (
	. "echo/model"
)

type ProductRepository interface {
	AddProduct(product Product) Product
	GetAllProducts() []Product
	GetProductById(id int) (*Product, error)
	UpdateProductById(id int, updatedProduct Product) (*Product, error)
	DeleteProductById(id int) error
}
