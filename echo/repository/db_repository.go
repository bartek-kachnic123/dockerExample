package repository

import (
	. "echo/model"
	"errors"
	"gorm.io/gorm"
)

type PostgresProductRepository struct {
	DB *gorm.DB
}

func NewPostgresProductRepository(db *gorm.DB) *PostgresProductRepository {
	return &PostgresProductRepository{
		DB: db,
	}
}

func (r *PostgresProductRepository) AddProduct(product Product) Product {
	r.DB.Create(&product)

	return product
}

func (r *PostgresProductRepository) GetAllProducts() []Product {
	var products []Product
	result := r.DB.Find(&products)
	if result.Error != nil {
		return []Product{}
	}

	return products
}

func (r *PostgresProductRepository) GetProductById(id int) (*Product, error) {
	var product Product
	result := r.DB.First(&product, id)

	if result.Error != nil {
		if errors.Is(result.Error, gorm.ErrRecordNotFound) {
			return nil, ErrNotFound
		}
		return nil, result.Error
	}

	return &product, nil
}

func (r *PostgresProductRepository) UpdateProductById(id int, updatedProduct Product) (*Product, error) {
	var productToUpdate Product
	result := r.DB.First(&productToUpdate, id)
	if result.Error != nil {
		if errors.Is(result.Error, gorm.ErrRecordNotFound) {
			return nil, ErrNotFound
		}
		return nil, result.Error
	}

	productToUpdate.Name = updatedProduct.Name
	productToUpdate.Price = updatedProduct.Price

	saveResult := r.DB.Save(&productToUpdate)
	if saveResult.Error != nil {
		return nil, saveResult.Error
	}

	return &productToUpdate, nil
}

func (r *PostgresProductRepository) DeleteProductById(id int) error {
	result := r.DB.Delete(&Product{}, id)

	if result.Error != nil {
		return result.Error
	}

	if result.RowsAffected == 0 {
		return ErrNotFound
	}

	return nil
}
